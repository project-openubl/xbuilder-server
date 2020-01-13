import React from "react";
import DocumentsPageTabs from "../../../PresentationalComponents/DocumentsPageTabs";
import { XmlBuilderRouterProps } from "../../../models/routerProps";

interface StateToProps {}

interface DispatchToProps {}

interface Props extends StateToProps, DispatchToProps, XmlBuilderRouterProps {}

interface State {}

class SunatDocumentPage extends React.Component<Props, State> {
  render() {
    return (
      <React.Fragment>
        <DocumentsPageTabs activeKey={1}>sunat</DocumentsPageTabs>
      </React.Fragment>
    );
  }
}

export default SunatDocumentPage;
