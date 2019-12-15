import React from "react";
import { editableTableBody } from "@patternfly/react-inline-edit-extension";

const ComposedBody = props => {
  const { tableBody, ...rest } = props;
  const Component = editableTableBody(tableBody);
  return <Component {...rest} />;
};

export default ComposedBody;
