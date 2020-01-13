import React from "react";
import { AxiosError } from "axios";
import { ContextSelector, ContextSelectorItem } from "@patternfly/react-core";
import { OrganizationRepresentation } from "../../models/xml-builder";
import { FetchStatus } from "../../store/common";
import { XmlBuilderRouterProps } from "../../models/routerProps";

interface StateToProps {
  selectedOrganization: OrganizationRepresentation | null;
  organizations: OrganizationRepresentation[];
  error: AxiosError<any> | null;
  status: FetchStatus | null;
}

interface DispatchToProps {}

interface Props extends StateToProps, DispatchToProps, XmlBuilderRouterProps {
  onSelect: (organization: OrganizationRepresentation) => any;
}

interface State {
  isOpen: boolean;
  searchValue: string;
  filteredItems: OrganizationRepresentation[];
}

class OrganizationContextSelector extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      isOpen: false,
      searchValue: "",
      filteredItems: props.organizations
    };
  }

  onToggle = (isOpen: any) => {
    this.setState({
      // Workaround until <ContextSelector> props are fixed
      isOpen: !this.state.isOpen
    });
  };

  onSelect = (event: any, value: any) => {
    const { organizations, onSelect } = this.props;

    const organization = organizations.find(p => p.name === value);
    if (organization) {
      this.setState({ isOpen: !this.state.isOpen }, () => {
        onSelect(organization);
      });
    }
  };

  onSearchInputChange = (value: string) => {
    this.setState({ searchValue: value });
  };

  onSearchButtonClick = (event: any) => {
    const filtered: OrganizationRepresentation[] =
      this.state.searchValue.trim() === ""
        ? this.props.organizations
        : this.props.organizations.filter(
            (org: OrganizationRepresentation) =>
              org.name
                .toLowerCase()
                .indexOf(this.state.searchValue.toLowerCase()) !== -1
          );

    this.setState({ filteredItems: filtered || [] });
  };

  render() {
    const { selectedOrganization } = this.props;
    const { isOpen, searchValue, filteredItems } = this.state;
    return (
      <ContextSelector
        toggleText={selectedOrganization ? selectedOrganization.name : ""}
        onSearchInputChange={this.onSearchInputChange}
        isOpen={isOpen}
        searchInputValue={searchValue}
        onToggle={this.onToggle}
        onSelect={this.onSelect}
        onSearchButtonClick={this.onSearchButtonClick}
        screenReaderLabel="Selected organization:"
      >
        {filteredItems.map(
          (item: OrganizationRepresentation, index: number) => (
            <ContextSelectorItem key={index}>{item.name}</ContextSelectorItem>
          )
        )}
      </ContextSelector>
    );
  }
}

export default OrganizationContextSelector;
