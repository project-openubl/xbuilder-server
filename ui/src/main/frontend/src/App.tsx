import React from "react";
import { BrowserRouter } from "react-router-dom";
import { AppRoutes, routes } from "./Routes";
import BasicLayout from "./Layout/BasicLayout";

import "./App.css";
import "./App.scss";

const App: React.FC = () => {
  return (
    <React.Fragment>
      <BrowserRouter>
        <BasicLayout sidebar={routes}>
          <AppRoutes />
        </BasicLayout>
      </BrowserRouter>
    </React.Fragment>
  );
};

export default App;
