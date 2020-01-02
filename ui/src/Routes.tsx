import React from "react";
import { Route, Switch, Redirect } from "react-router-dom";
import NotFound403 from "./Pages/Layout/Forbidden403";
import NotFound404 from "./Pages/Layout/NotFound404";
import ServiceUnavailable503 from "./Pages/Layout/ServiceUnavailable503";
import WelcomePage from "./Pages/WelcomePage";
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
      <Route path="/error/403" component={NotFound403} />
      <Route path="/error/404" component={NotFound404} />
      <Route path="/error/503" component={ServiceUnavailable503} />

      <Route path="/" render={() => <Redirect to={"/organizations/list"} />} />
    </Switch>
  );
};
