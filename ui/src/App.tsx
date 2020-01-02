import React from "react";
import { BrowserRouter } from "react-router-dom";
import { AppRoutes } from "./Routes";

import "./App.css";
import "./App.scss";
import BasicLayout from "./Pages/Layout/BasicLayout";
import OrganizationContextLoader from "./SmartComponents/OrganizationContextLoader";

const App: React.FC = () => {
  return (
    <React.Fragment>
      <BrowserRouter>
        <OrganizationContextLoader>
          <BasicLayout>
            <AppRoutes />
          </BasicLayout>
        </OrganizationContextLoader>
      </BrowserRouter>
    </React.Fragment>
  );
};

export default App;
