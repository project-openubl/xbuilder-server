import React from "react";
import { Switch, Route } from "react-router-dom";
import ActiveKeysPage from "./ActiveKeysPage";
import KeyProviderspage from "./KeyProvidersPage";
import KeyListPage from "./KeyListPage";
import { organizationContextActions } from "../../store/organizationContext";
import { OrganizationRepresentation } from "../../models/xml-builder";
import ManageProviderPage from "./ManageProviderPage";
import { XmlBuilderRouterProps } from "../../models/routerProps";

interface StateToProps {
  contextOrganizations: OrganizationRepresentation[];
}

interface DispatchToProps {
  selectOrganizationContext: typeof organizationContextActions.selectOrganizationContext;
}

interface Props extends StateToProps, DispatchToProps, XmlBuilderRouterProps {}

const KeysPage: React.FC<Props> = ({
  match,
  contextOrganizations,
  selectOrganizationContext
}) => {
  // Select organization context
  const organizationId = match.params.organizationId;
  if (match.params.organizationId) {
    const organization = contextOrganizations.find(p => p.id === organizationId);
    if (organization) {
      selectOrganizationContext(organization);
    }
  }

  return (
    <React.Fragment>
      <Switch>
        <Route path={match.path} component={ActiveKeysPage} exact={true} />
        <Route
          path={`${match.path}/list`}
          component={KeyListPage}
          exact={false}
        />
        <Route
          path={`${match.path}/providers`}
          component={KeyProviderspage}
          exact={true}
        />
        <Route
          path={`${match.path}/providers/:providerId`}
          component={ManageProviderPage}
          exact={true}
        />
        <Route
          path={`${match.path}/providers/:providerId/:componentId`}
          component={ManageProviderPage}
          exact={true}
        />
      </Switch>
    </React.Fragment>
  );
};

export default KeysPage;
