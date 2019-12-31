import React from "react";
import ManageOrganization from "../../SmartComponents/ManageOrganizationModal";

interface Props {
  match: any;
  history: any;
  location: any;
}

interface State {}

class OrganizationPage extends React.Component<Props, State> {
  render() {
    const { match } = this.props;

    return (
      <React.Fragment>
        <ManageOrganization organizationId={match.params.organizationId} />
      </React.Fragment>
    );
  }
}

export default OrganizationPage;
