import React from "react";
import { Route, RouteComponentProps, Switch, Redirect } from "react-router-dom";
import WelcomePage from "./Pages/WelcomePage";
import NotFoundPage from "./Layout/NotFoundPage";
import KeysPage from "./Pages/KeysPage";
import OrganizationListPage from "./Pages/OrganizationListPage";
import OrganizationPage from "./Pages/OrganizationPage";

export const AppRoutes = () => {
  return (
    <Switch>
      <Route path="/home" component={WelcomePage} />
      <Route path="/organizations/list" component={OrganizationListPage} />
      <Route path="/organizations/create" component={OrganizationPage} />
      <Route
        path="/organizations/edit/:organizationId"
        component={OrganizationPage}
      />
      <Route
        path="/organizations/manage/:organizationId/keys"
        component={KeysPage}
      />
      <Route path="/not-found" component={NotFoundPage} />
      <Route path="/" render={() => <Redirect to={"/home"} />} />
      <Route component={NotFoundPage} />
    </Switch>
  );
};
