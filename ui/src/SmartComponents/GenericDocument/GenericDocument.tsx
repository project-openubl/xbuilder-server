import React from "react";
import {
  Card,
  CardBody,
  Stack,
  StackItem,
  Toolbar,
  ToolbarGroup,
  ToolbarItem,
  Button,
  Tabs,
  Tab,
  Grid,
  GridItem,
  SplitItem,
  CardHeader
} from "@patternfly/react-core";
import { FileIcon } from "@patternfly/react-icons";
import ReactJson from "react-json-view";
import AceEditor from "react-ace";
import { DocumentType } from "../../models/xml-builder";
import { XmlBuilderRouterProps } from "../../models/routerProps";
import { extractFilenameFromContentDispositionHeaderValue } from "../../Utilities/Utils";
import "ace-builds/src-noconflict/mode-xml";
import "ace-builds/src-noconflict/theme-xcode";

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

interface Props extends StateToProps, DispatchToProps, XmlBuilderRouterProps {
  documentType: DocumentType;
  inputDocument: any;
}

interface State {
  xml: any;
  xmlFilename: string;
  enrichedData: any;
  activeTab: number | string;
}

class GenericDocument extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      xml: null,
      xmlFilename: "",
      enrichedData: {},
      activeTab: 0
    };
  }

  componentDidUpdate(_prevProps: Props, prevState: State) {
    if (
      this.props.inputDocument &&
      this.props.inputDocument !== _prevProps.inputDocument
    ) {
      this.refresh();
    }
  }

  refresh = () => {
    this.enrichDocument();
    this.createDocument();
  };

  getOrganizationId = () => {
    const { match } = this.props;
    return match.params.organizationId;
  };

  createDocument = () => {
    const { requestCreateDocument, inputDocument, documentType } = this.props;
    const organizationId = this.getOrganizationId();

    requestCreateDocument(organizationId, documentType, inputDocument).then(
      (response: any) => {
        if (response) {
          const fileName = extractFilenameFromContentDispositionHeaderValue(
            response.headers
          );
          this.setState({
            xml: response.data,
            xmlFilename: fileName
          });
        }
      }
    );
  };

  enrichDocument = () => {
    const { requestEnrichDocument, inputDocument, documentType } = this.props;
    const organizationId = this.getOrganizationId();

    requestEnrichDocument(organizationId, documentType, inputDocument).then(
      (response: any) => {
        this.setState({ enrichedData: response });
      }
    );
  };

  // Handlers

  handleOnTabClick = (event: any, tabIndex: number | string) => {
    this.setState({
      activeTab: tabIndex
    });
  };

  handleOnDownloadXml = () => {
    const { xml, xmlFilename } = this.state;
    const downloadUrl = window.URL.createObjectURL(new Blob([xml]));
    const link = document.createElement("a");
    link.href = downloadUrl;
    link.setAttribute("download", xmlFilename);
    document.body.appendChild(link);
    link.click();
    link.remove();
  };

  renderDataView = () => {
    const { inputDocument } = this.props;
    const { enrichedData, xml, activeTab } = this.state;
    return (
      <Card>
        <CardBody>
          <Stack gutter="sm">
            {inputDocument && (
              <StackItem>
                <Toolbar>
                  <ToolbarGroup>
                    <ToolbarItem>
                      <Button
                        variant="plain"
                        onClick={this.handleOnDownloadXml}
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
                activeKey={activeTab}
                onSelect={this.handleOnTabClick}
              >
                <Tab eventKey={0} title="JSON Request">
                  <ReactJson src={inputDocument || {}} name={false} />
                </Tab>
                <Tab eventKey={1} title="JSON Response">
                  <ReactJson src={enrichedData} name={false} />
                </Tab>
                <Tab eventKey={2} title="XML Response">
                  <AceEditor
                    mode="xml"
                    theme="xcode"
                    onChange={() => {}}
                    name="xml"
                    editorProps={{ $blockScrolling: false }}
                    readOnly={true}
                    value={xml || ""}
                    width="100" // This value does not really affect but avoid horizontal overflow
                  />
                </Tab>
              </Tabs>
            </StackItem>
          </Stack>
        </CardBody>
      </Card>
    );
  };

  render() {
    const { children } = this.props;
    return (
      <Grid lg={2} gutter="sm">
        <GridItem span={8}>
          <SplitItem>
            <Card>
              <CardHeader>Datos del comprobante de pago</CardHeader>
              <CardBody>{children}</CardBody>
            </Card>
          </SplitItem>
        </GridItem>
        <GridItem span={4}>
          <SplitItem>{this.renderDataView()}</SplitItem>
        </GridItem>
      </Grid>
    );
  }
}

export default GenericDocument;
