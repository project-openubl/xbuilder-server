import React from "react";
import DocumentsPageTabs from "../../../PresentationalComponents/DocumentsPageTabs";
import { XmlBuilderRouterProps } from "../../../models/routerProps";
import {
  Card,
  CardHeader,
  CardBody,
  Split,
  SplitItem,
  Stack,
  StackItem,
  Button,
  Grid,
  GridItem
} from "@patternfly/react-core";
import { JsIcon, FileIcon } from "@patternfly/react-icons";
import StandardDocumentForm from "../../../PresentationalComponents/StandardDocumentForm";

interface StateToProps {}

interface DispatchToProps {}

interface Props extends StateToProps, DispatchToProps, XmlBuilderRouterProps {}

interface State {}

class StandardDocumentPage extends React.Component<Props, State> {
  renderToolbar = () => {
    return (
      <Card>
        <CardHeader>Toolbar</CardHeader>
        <CardBody>
          <Stack>
            <StackItem>
              <Button variant="plain">
                <JsIcon /> JSON
              </Button>
            </StackItem>
            <StackItem>
              <Button variant="plain">
                <FileIcon /> XML
              </Button>
            </StackItem>
          </Stack>
        </CardBody>
      </Card>
    );
  };

  renderForm = () => {
    return (
      <Card>
        <CardHeader>Datos del comprobante de pago</CardHeader>
        <CardBody>
          <StandardDocumentForm onChange={() => {}} />
        </CardBody>
      </Card>
    );
  };

  render() {
    return (
      <React.Fragment>
        <DocumentsPageTabs activeKey={0}>
          <Grid lg={2} gutter="sm">
            <GridItem>
              <SplitItem>{this.renderToolbar()}</SplitItem>
            </GridItem>
            <GridItem span={10} smRowSpan={12} mdRowSpan={12} lgRowSpan={12}>
              <SplitItem>{this.renderForm()}</SplitItem>
            </GridItem>
          </Grid>
        </DocumentsPageTabs>
      </React.Fragment>
    );
  }
}

export default StandardDocumentPage;
