import React from "react";
import DocumentsPageTabs from "../../../PresentationalComponents/DocumentsPageTabs";
import { XmlBuilderRouterProps } from "../../../models/routerProps";
import GenericDocument from "../../../SmartComponents/GenericDocument";

interface StateToProps {}

interface DispatchToProps {}

interface Props extends StateToProps, DispatchToProps, XmlBuilderRouterProps {}

interface State {
  form: any;
}

class BajaDocumentPage extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      form: null
    };
  }

  renderForm = () => {
    return <p>hola</p>;
  };

  render() {
    return (
      <React.Fragment>
        <DocumentsPageTabs activeKey={1}>
          <GenericDocument documentType="voided-document" inputDocument={null}>
            {this.renderForm()}
          </GenericDocument>
        </DocumentsPageTabs>
      </React.Fragment>
    );
  }
}

export default BajaDocumentPage;
