import React from "react";
import { Switch, Route } from "react-router-dom";
import ActiveKeysPage from "./ActiveKeysPage";
import KeyProviderspage from "./KeyProvidersPage";
import KeyListPage from "./KeyListPage";
import { organizationContextActions } from "../../store/organizationContext";
import { OrganizationRepresentation } from "../../models/xml-builder";

interface Props {
  match: any;
  history: any;
  location: any;

  organizations: OrganizationRepresentation[];
  selectOrganizationContext: typeof organizationContextActions.selectOrganizationContext;
}

const KeysPage: React.FC<Props> = ({
  match,
  organizations,
  selectOrganizationContext
}) => {
  const organizationId = match.params.organizationId;
  if (match.params.organizationId) {
    const organization = organizations.find(p => p.id === organizationId);
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
          exact={false}
        />
      </Switch>
    </React.Fragment>
  );
};

export default KeysPage;
