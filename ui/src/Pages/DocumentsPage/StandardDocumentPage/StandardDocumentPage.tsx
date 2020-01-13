import React from "react";
import DocumentsPageTabs from "../../../PresentationalComponents/DocumentsPageTabs";
import { XmlBuilderRouterProps } from "../../../models/routerProps";

interface StateToProps {}

interface DispatchToProps {}

interface Props extends StateToProps, DispatchToProps, XmlBuilderRouterProps {}

interface State {}

class StandardDocumentPage extends React.Component<Props, State> {
  render() {
    return (
      <React.Fragment>
        <DocumentsPageTabs activeKey={0}>estandard</DocumentsPageTabs>
      </React.Fragment>
    );
  }
}

export default StandardDocumentPage;
