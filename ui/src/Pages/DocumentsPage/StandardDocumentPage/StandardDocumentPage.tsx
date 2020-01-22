import React from "react";
import DocumentsPageTabs from "../../../PresentationalComponents/DocumentsPageTabs";
import { XmlBuilderRouterProps } from "../../../models/routerProps";
import {
  Card,
  CardHeader,
  CardBody,
  SplitItem,
  Stack,
  StackItem,
  Button,
  Grid,
  GridItem,
  Toolbar,
  ToolbarGroup,
  ToolbarItem,
  Tabs,
  Tab} from "@patternfly/react-core";
import { FileIcon } from "@patternfly/react-icons";
import ReactJson from "react-json-view";
import AceEditor from "react-ace";
import "ace-builds/src-noconflict/mode-xml";
import "ace-builds/src-noconflict/theme-xcode";

import StandardDocumentForm from "../../../PresentationalComponents/StandardDocumentForm";
import { DocumentType } from "../../../models/xml-builder";

interface StateToProps {}

interface DispatchToProps {
  requestEnrichDocument: (
    organizationId: string,
    documentType: DocumentType,
    document: any
  ) => Promise<any>;
  requestCreateDocument: (
    organizationId: string,
    documentType: DocumentType,
    document: any
  ) => Promise<any>;
}

interface Props extends StateToProps, DispatchToProps, XmlBuilderRouterProps {}

interface State {
  formData: any;
  xmlData: any;
  xmlFileName: string;
  enrichData: any;
  activeRequestResponseKey: number | string;
}

class StandardDocumentPage extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      formData: null,
      xmlData: null,
      xmlFileName: "",
      enrichData: null,
      activeRequestResponseKey: 0
    };
  }

  renderToolbar = () => {
    const { formData, enrichData, xmlData } = this.state;
    return (
      <Card>
        {/* <CardHeader>Toolbar</CardHeader> */}
        <CardBody>
          <Stack gutter="sm">
            {formData && (
              <StackItem>
                <Toolbar>
                  <ToolbarGroup>
                    <ToolbarItem>
                      <Button
                        variant="plain"
                        onClick={this.handleOnDownloadXML}
                      >
                        <FileIcon /> Descargar XML
                      </Button>
                    </ToolbarItem>
                  </ToolbarGroup>
                </Toolbar>
              </StackItem>
            )}
            <StackItem>
              <Tabs
                isFilled
                activeKey={this.state.activeRequestResponseKey}
                onSelect={this.handleRequestResponseTabClick}
              >
                <Tab eventKey={0} title="JSON Request">
                  <ReactJson src={formData || {}} name={null} />
                </Tab>
                <Tab eventKey={1} title="JSON Response">
                  <ReactJson src={enrichData || {}} name={null} />
                </Tab>
                <Tab eventKey={2} title="XML Response">
                  <AceEditor
                    mode="xml"
                    theme="xcode"
                    onChange={() => {}}
                    name="xmlResponse"
                    editorProps={{ $blockScrolling: true }}
                    readOnly={true}
                    value={xmlData || ""}
                  />
                </Tab>
              </Tabs>
            </StackItem>
          </Stack>
        </CardBody>
      </Card>
    );
  };

  getOrganizationId = () => {
    const { match } = this.props;
    return match.params.organizationId;
  };

  getPayload = () => {
    const { formData } = this.state;

    const payload = {
      serie: formData.serie,
      numero: formData.numero,
      proveedor: {
        ruc: formData.proveedorRuc,
        razonSocial: formData.proveedorNombreComercial,
        codigoPostal: formData.proveedorCodigoPostal
      },
      cliente: {
        tipoDocumentoIdentidad: formData.clienteTipoDocumento,
        numeroDocumentoIdentidad: formData.clienteNumeroDocumento,
        nombre: formData.clienteNombre
      },
      detalle: formData.detalle.map((item: any) => ({
        descripcion: item.descripcion,
        precioUnitario: item.precioUnitario,
        cantidad: item.cantidad
      }))
    };

    return payload;
  };

  extractFilenameFromContentDispositionHeaderValue = (headers: any) => {
    const contentDisposition = headers["content-disposition"];

    let filename = "";
    if (contentDisposition && contentDisposition.indexOf("attachment") !== -1) {
      const filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
      const matches = filenameRegex.exec(contentDisposition);
      if (matches != null && matches[1]) {
        filename = matches[1].replace(/['"]/g, "");
      }
    }

    return filename;
  };

  downloadXml = () => {
    const { formData } = this.state;
    const { requestCreateDocument } = this.props;
    if (formData) {
      requestCreateDocument(
        this.getOrganizationId(),
        "invoice",
        this.getPayload()
      ).then((response: any) => {
        const fileName = this.extractFilenameFromContentDispositionHeaderValue(
          response.headers
        );
        this.setState({ xmlData: response.data, xmlFileName: fileName });
      });
    }
  };

  enrichDocument = () => {
    const { requestEnrichDocument } = this.props;

    requestEnrichDocument(
      this.getOrganizationId(),
      "invoice",
      this.getPayload()
    ).then((response: any) => {
      this.setState({ enrichData: response });
    });
  };

  handleOnDownloadXML = () => {
    const { xmlFileName, xmlData } = this.state;
    const downloadUrl = window.URL.createObjectURL(new Blob([xmlData]));
    const link = document.createElement("a");
    link.href = downloadUrl;
    link.setAttribute("download", xmlFileName);
    document.body.appendChild(link);
    link.click();
    link.remove();
  };

  handleOnFormSubmit = (formData: any) => {
    this.setState({ formData }, () => {
      this.enrichDocument();
      this.downloadXml();
    });
  };

  handleRequestResponseTabClick = (event: any, tabIndex: number | string) => {
    this.setState({
      activeRequestResponseKey: tabIndex
    });
  };

  renderForm = () => {
    return (
      <Card>
        <CardHeader>Datos del comprobante de pago</CardHeader>
        <CardBody>
          <StandardDocumentForm onSubmit={this.handleOnFormSubmit} />
        </CardBody>
      </Card>
    );
  };

  render() {
    return (
      <React.Fragment>
        <DocumentsPageTabs activeKey={0}>
          <Grid lg={2} gutter="sm">
            <GridItem span={8}>
              <SplitItem>{this.renderForm()}</SplitItem>
            </GridItem>
            <GridItem span={4}>
              <SplitItem>{this.renderToolbar()}</SplitItem>
            </GridItem>
          </Grid>
        </DocumentsPageTabs>
      </React.Fragment>
    );
  }
}

export default StandardDocumentPage;
