import React from "react";
import { PageSection } from "@patternfly/react-core";

interface Props {
  fetchOrganizations: any;
}

interface State {
  filterText: string;
  page: number;
  pageSize: number;
}

class OrganizationListPage extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = { filterText: "", page: 1, pageSize: 10 };
  }

  componentDidMount() {
    this.refreshData();
  }

  refreshData = (
    filterText: string = this.state.filterText,
    page: number = this.state.page,
    pageSize: number = this.state.pageSize
  ) => {
    const { fetchOrganizations } = this.props;
    fetchOrganizations(filterText, page, pageSize);
  };

  render() {
    return (
      <React.Fragment>
        <PageSection>hola</PageSection>
      </React.Fragment>
    );
  }
}

export default OrganizationListPage;
