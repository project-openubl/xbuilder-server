import React from "react";
import ManageProviderModal from "../../../SmartComponents/ManageProviderModal";

interface Props {
  match: any;
  history: any;
  location: any;
}

interface State {}

class ManageProviderPage extends React.Component<Props, State> {
  componentDidMount() {
    const { match } = this.props;
    console.log(match);
    
  }
  render() {
    return (
      <React.Fragment>
        <ManageProviderModal />
      </React.Fragment>
    );
  }
}

export default ManageProviderPage;
