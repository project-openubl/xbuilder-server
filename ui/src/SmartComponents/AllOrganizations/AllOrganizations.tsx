import React from "react";
import { OrganizationRepresentation } from "../../models/xml-builder";
import { FetchStatus } from "../../store/common";
import { AxiosError } from "axios";
import { Bullseye } from "@patternfly/react-core";

interface StateToProps {
  organizations: OrganizationRepresentation[];
  error: AxiosError<any> | null;
  status: FetchStatus | null;
}

interface DispatchToProps {
  fetchAllOrganizations: () => any;
}

interface Props extends StateToProps, DispatchToProps {}

interface State {}

class AllOrganizations extends React.Component<Props, State> {
  componentDidMount() {
    const { fetchAllOrganizations } = this.props;
    fetchAllOrganizations();
  }

  render() {
    const { status, children } = this.props;

    switch (status) {
      case "complete":
        return <React.Fragment>{children}</React.Fragment>;
      default:
        return (
          <React.Fragment>
            <Bullseye>Loading...</Bullseye>
          </React.Fragment>
        );
    }
  }
}

export default AllOrganizations;
