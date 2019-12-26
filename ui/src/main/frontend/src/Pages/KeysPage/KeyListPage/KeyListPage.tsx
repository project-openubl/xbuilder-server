import React from "react";
import KeysPageTabs from "../../../PresentationalComponents/KeysPageTabs";

const KeyListPage: React.FC = () => {
  return (
    <React.Fragment>
      <KeysPageTabs activeKey={1}>Key list page</KeysPageTabs>
    </React.Fragment>
  );
};

export default KeyListPage;
