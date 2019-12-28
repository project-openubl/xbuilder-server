import React from "react";
import { Switch, Route } from "react-router-dom";
import ActiveKeysPage from "./ActiveKeysPage";
import KeyProviderspage from "./KeyProvidersPage";
import KeyListPage from "./KeyListPage";

interface Props {
  match: any;
  history: any;
  location: any;
}
const KeysPage: React.FC<Props> = ({ match }) => {
  return (
    <React.Fragment>
      <Switch>
        <Route path={match.path} component={ActiveKeysPage} exact={true} />
        <Route
          path={`${match.path}/list`}
          component={KeyListPage}
          exact={false}
        />
        <Route
          path={`${match.path}/providers`}
          component={KeyProviderspage}
          exact={false}
        />
      </Switch>
    </React.Fragment>
  );
};

export default KeysPage;
