import React from "react";
import { BrowserRouter } from "react-router-dom";
import { AppRoutes } from "./Routes";

import "./App.css";
import "./App.scss";
import BasicLayout from "./Pages/Layout/BasicLayout";
import AllOrganizations from "./SmartComponents/AllOrganizations";

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
