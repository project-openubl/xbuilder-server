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
  fetchOrganizations: () => any;
}

interface Props extends StateToProps, DispatchToProps {}

interface State {}

class OrganizationContextLoader extends React.Component<Props, State> {
  componentDidMount() {
    const { fetchOrganizations } = this.props;
    fetchOrganizations();
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

export default OrganizationContextLoader;
