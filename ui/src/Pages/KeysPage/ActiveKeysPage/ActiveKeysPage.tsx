import React from "react";
import KeysPageTabs from "../../../PresentationalComponents/KeysPageTabs";

const ActiveKeysPage: React.FC = () => {
  return (
    <React.Fragment>
      <KeysPageTabs activeKey={0}>Active key page</KeysPageTabs>
    </React.Fragment>
  );
};

export default ActiveKeysPage;
