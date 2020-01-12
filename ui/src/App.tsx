import React from "react";
import { HashRouter } from "react-router-dom";
import { AppRoutes } from "./Routes";

import "./App.css";
import "./App.scss";
import BasicLayout from "./Pages/Layout/BasicLayout";
import OrganizationContextLoader from "./SmartComponents/OrganizationContextLoader";
import DeleteMessageDialog from "./SmartComponents/DeleteDialog";

import '@redhat-cloud-services/frontend-components-notifications/index.css';
const frontendComponentsNotifications = require("@redhat-cloud-services/frontend-components-notifications");

const App: React.FC = () => {
  const NotificationsPortal = frontendComponentsNotifications.NotificationsPortal;

  return (
    <React.Fragment>
      <HashRouter>
        <OrganizationContextLoader>
          <BasicLayout>
            <AppRoutes />
            <DeleteMessageDialog />
            <NotificationsPortal />
          </BasicLayout>
        </OrganizationContextLoader>
      </HashRouter>
    </React.Fragment>
  );
};

export default App;
