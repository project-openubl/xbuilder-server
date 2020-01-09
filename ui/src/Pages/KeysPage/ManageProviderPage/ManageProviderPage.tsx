import React from "react";
import { AxiosError } from "axios";
import {
  ComponentRepresentation,
  ServerInfoRepresentation,
  ComponentTypeRepresentation
} from "../../../models/xml-builder";
import { FetchStatus } from "../../../store/common";
import { XmlBuilderRouterProps } from "../../../models/routerProps";
import ManageProviderModal from "../../../SmartComponents/ManageProviderModal";

interface StateToProps {
  serverInfo: ServerInfoRepresentation | undefined;
  serverInfoError: AxiosError<any> | undefined;
  serverInfoFetchStatus: FetchStatus | undefined;
  component: ComponentRepresentation | undefined;
  componentError: AxiosError<any> | undefined;
  componentFetchStatus: FetchStatus | undefined;
}

interface DispatchToProps {
  fetchServerInfo: () => any;
  fetchComponent: (organizationId: string, componentId: string) => any;
}

interface Props extends StateToProps, DispatchToProps, XmlBuilderRouterProps {}

interface State {}

class ManageProviderPage extends React.Component<Props, State> {
  componentDidMount() {
    const { fetchComponent, fetchServerInfo } = this.props;

    // fetch server info
    fetchServerInfo();

    // fetch component
    const organizationId = this.getOrganizationId();
    const componentId = this.getComponentId();
    if (organizationId && componentId) {
      fetchComponent(organizationId, componentId);
    }
  }

  getOrganizationId = (): string => {
    const { match } = this.props;
    return match.params.organizationId;
  };

  getProviderId = (): string => {
    const { match } = this.props;
    return match.params.providerId;
  };

  getComponentId = (): string => {
    const { match } = this.props;
    return match.params.componentId;
  };

  getProvider = (): ComponentTypeRepresentation | undefined => {
    const { serverInfo } = this.props;
    if (serverInfo) {
      const providerId = this.getProviderId();

      const keyProviders = serverInfo.componentTypes.keyProviders;
      for (let i = 0; i < keyProviders.length; i++) {
        const provider = keyProviders[i];
        if (provider.id === providerId) {
          return provider;
        }
      }
    }
  };

  render() {
    const { component } = this.props;
    const provider = this.getProvider();

    return (
      <React.Fragment>
        <ManageProviderModal component={component} provider={provider} />
      </React.Fragment>
    );
  }
}

export default ManageProviderPage;
