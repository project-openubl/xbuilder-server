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
  ToolbarItem
} from "@patternfly/react-core";
import { JsIcon, FileIcon } from "@patternfly/react-icons";
import StandardDocumentForm from "../../../PresentationalComponents/StandardDocumentForm";
import { DocumentType } from "../../../models/xml-builder";

interface StateToProps {}

interface DispatchToProps {
  requestEnrichDocument: (
    organizationId: string,
    documentType: DocumentType,
    document: any
  ) => Promise<any>;
}

interface Props extends StateToProps, DispatchToProps, XmlBuilderRouterProps {}

interface State {
  formData: any;
  enrichData: any;
}

class StandardDocumentPage extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      formData: null,
      enrichData: null
    };
  }

  renderToolbar = () => {
    const { formData } = this.state;

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
                      <Button variant="plain">
                        <JsIcon /> JSON
                      </Button>
                    </ToolbarItem>
                    <ToolbarItem>
                      <Button variant="plain">
                        <FileIcon /> XML
                      </Button>
                    </ToolbarItem>
                  </ToolbarGroup>
                </Toolbar>
              </StackItem>
            )}
            <StackItem>
              {formData && (
                <div className="pf-c-content">
                  <dl>
                    <dt>T. Comprobante</dt>
                    <dd>{formData.tipoComprobante}</dd>
                    <dt>Serie/número</dt>
                    <dd>
                      {formData.serie}-{formData.numero}
                    </dd>
                    <dt>Proveedor</dt>
                    <dd>
                      {formData.proveedorRuc} /{" "}
                      {formData.proveedorNombreComercial} /{" "}
                      {formData.proveedorCodigoPostal}
                    </dd>
                    <dt>Cliente</dt>
                    <dd>
                      {formData.clienteTipoDocumento} /{" "}
                      {formData.clienteNumeroDocumento} /{" "}
                      {formData.clienteNombre}
                    </dd>
                  </dl>
                </div>
              )}
              {!formData && <small>No hay datos que mostrar</small>}
            </StackItem>
          </Stack>
        </CardBody>
      </Card>
    );
  };

  handleOnFormSubmit = (formData: any) => {
    // this.setState({ formData });
    const { match, requestEnrichDocument } = this.props;
    const organizationId = match.params.organizationId;

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

    requestEnrichDocument(organizationId, "invoice", payload).then(
      (response: any) => {
        this.setState({ enrichData: response });
      }
    );
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
