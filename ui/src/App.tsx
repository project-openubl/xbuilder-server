import React from "react";
import { HashRouter } from "react-router-dom";
import { AppRoutes } from "./Routes";

import "./App.css";
import "./App.scss";
import BasicLayout from "./Pages/Layout/BasicLayout";
import OrganizationContextLoader from "./SmartComponents/OrganizationContextLoader";
import DeleteMessageDialog from "./SmartComponents/DeleteDialog";

const App: React.FC = () => {
  return (
    <React.Fragment>
      <HashRouter>
        <OrganizationContextLoader>
          <BasicLayout>
            <AppRoutes />
            <DeleteMessageDialog />
          </BasicLayout>
        </OrganizationContextLoader>
      </HashRouter>
    </React.Fragment>
  );
};

export default App;
