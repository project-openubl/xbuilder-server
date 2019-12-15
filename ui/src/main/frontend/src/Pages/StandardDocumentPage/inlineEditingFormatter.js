import React from "react";
import { inlineEditFormatterFactory } from "@patternfly/react-inline-edit-extension";

export const makeId = ({ column, rowIndex, columnIndex, name }) =>
  `${column.property}-${rowIndex}-${columnIndex}${name ? `-${name}` : ""}`;

export const inlineEditingFormatter = (textInput) => {
  return inlineEditFormatterFactory({
    renderEdit: (
      value,
      { columnIndex, rowIndex, column },
      { activeEditId }
    ) => {
      const id = this.makeId({ rowIndex, columnIndex, column });
      const TableTextInput = textInput;
      return (
        <TableTextInput
          id={id}
          defaultValue={value}
          onBlur={() => {}}
          autoFocus={activeEditId === id}
        />
      );
    },
    renderValue: (value, { rowData }) =>
      rowData.isTableEditing ? `${value} (Not Editable)` : value,
    isEditable: ({ rowIndex }) => rowIndex !== 1
  });
};
