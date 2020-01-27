import React from "react";
import { Switch, Route } from "react-router-dom";
import { organizationContextActions } from "../../store/organizationContext";
import { OrganizationRepresentation } from "../../models/xml-builder";
import { XmlBuilderRouterProps } from "../../models/routerProps";
import StandardDocumentPage from "./StandardDocumentPage";
import BajaDocumentPage from "./BajaDocumentPage";

interface StateToProps {
  contextOrganizations: OrganizationRepresentation[];
}

interface DispatchToProps {
  selectOrganizationContext: typeof organizationContextActions.selectOrganizationContext;
}

interface Props extends StateToProps, DispatchToProps, XmlBuilderRouterProps {}

const DocumentsPage: React.FC<Props> = ({
  match,
  contextOrganizations,
  selectOrganizationContext
}) => {
  // Select organization context
  const organizationId = match.params.organizationId;
  if (match.params.organizationId) {
    const organization = contextOrganizations.find(
      p => p.id === organizationId
    );
    if (organization) {
      selectOrganizationContext(organization);
    }
  }

  return (
    <React.Fragment>
      <Switch>
        <Route
          path={`${match.path}`}
          component={StandardDocumentPage}
          exact={true}
        />
        <Route
          path={`${match.path}/baja`}
          component={BajaDocumentPage}
          exact={true}
        />
      </Switch>
    </React.Fragment>
  );
};

export default DocumentsPage;
