import React from "react";
import { BrowserRouter } from "react-router-dom";
import { AppRoutes } from "./Routes";
import AllOrganizations from "./SmartComponents/AllOrganizations";
import BasicLayout from "./Layout/BasicLayout";

import "./App.css";
import "./App.scss";

const App: React.FC = () => {
  return (
    <React.Fragment>
      <BrowserRouter>
        <AllOrganizations>
          <BasicLayout>
            <AppRoutes />
          </BasicLayout>
        </AllOrganizations>
      </BrowserRouter>
    </React.Fragment>
  );
};

export default App;
