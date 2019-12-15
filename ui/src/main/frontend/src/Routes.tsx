import React from "react";
import { Route, RouteComponentProps, Switch } from "react-router-dom";
import WelcomePage from "./Pages/WelcomePage";
import NotFoundPage from "./Pages/NotFoundPage";
import StandardDocumentPage from "./Pages/StandardDocumentPage";

export interface IAppRoute {
  path: string;
  component:
    | React.ComponentType<RouteComponentProps<any>>
    | React.ComponentType<any>;
  sidebarLabel?: string;
  exact: boolean;
}

export const routes: IAppRoute[] = [
  {
    path: "/",
    component: WelcomePage,
    sidebarLabel: "Bienvenido",
    exact: true
  },
  {
    path: "/documents/standard",
    component: StandardDocumentPage,
    sidebarLabel: "Documentos estandar",
    exact: true
  }
];

export const AppRoutes = () => {
  return (
    <Switch>
      {routes.map(({ path, component, exact }, idx) => (
        <Route path={path} component={component} exact={exact} key={idx} />
      ))}
      <Route component={NotFoundPage} />
    </Switch>
  );
};
