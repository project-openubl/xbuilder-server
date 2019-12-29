import React from "react";
import { Redirect } from "react-router-dom";
import { OrganizationRepresentation } from "../../models/xml-builder";
import { FetchStatus } from "../../store/common";
import { AxiosError } from "axios";

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
  public componentDidMount() {
    const { fetchAllOrganizations } = this.props;
    fetchAllOrganizations();
  }

  public render() {
    const { status, error, children } = this.props;

    if (error) {
      return <Redirect to="/not-found" />;
    }

    switch (status) {
      case "complete":
        return <React.Fragment>{children}</React.Fragment>;
      default:
        return <React.Fragment>{null}</React.Fragment>;
    }
  }
}

export default AllOrganizations;
