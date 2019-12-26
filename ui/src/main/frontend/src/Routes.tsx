import React from "react";
import { Route, RouteComponentProps, Switch, Redirect } from "react-router-dom";
import WelcomePage from "./Pages/WelcomePage";
import NotFoundPage from "./Pages/NotFoundPage";
import KeysPage from "./Pages/KeysPage";

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
    path: "/home",
    component: WelcomePage,
    sidebarLabel: "Home",
    exact: true
  },
  {
    path: "/keys",
    component: KeysPage,
    sidebarLabel: "Certificados",
    exact: false
  }
];

export const AppRoutes = () => {
  return (
    <Switch>
      {routes.map(({ path, component, exact }, idx) => (
        <Route path={path} component={component} exact={exact} key={idx} />
      ))}
      <Route path="/" render={() => <Redirect to={"/home"} />} />
      <Route component={NotFoundPage} />
    </Switch>
  );
};
