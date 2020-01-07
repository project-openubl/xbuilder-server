import React from "react";
import {
  Select,
  SelectVariant,
  SelectOption,
  SelectOptionObject,
  SelectDirection
} from "@patternfly/react-core";
import { ConfigPropertyRepresentation } from "../../models/xml-builder";

export interface Props {
  defaultValue: string;
  property: ConfigPropertyRepresentation;
  onChange: (selected: string) => any;
}

export interface State {
  selected: string;
  isExpanded: boolean;
}

class PropertySelect extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      selected: props.defaultValue,
      isExpanded: false
    };
  }

  handleOnToggle = (isExpanded: boolean) => {
    this.setState({ isExpanded });
  };

  handleOnSelect = (
    event: React.MouseEvent | React.ChangeEvent,
    value: string | SelectOptionObject,
    isPlaceholder?: boolean
  ) => {
    if (isPlaceholder) {
      this.clearSelection();
    } else {
      const selected = typeof value === "string" ? value : value.toString();
      this.setState({
        selected: selected,
        isExpanded: false
      });
      this.props.onChange(selected);
    }
  };

  clearSelection = () => {
    this.setState({
      selected: "",
      isExpanded: false
    });
  };

  render() {
    const { property } = this.props;
    const { selected, isExpanded } = this.state;

    return (
      <React.Fragment>
        <Select
          variant={SelectVariant.single}
          aria-label={property.label}
          onToggle={this.handleOnToggle}
          onSelect={this.handleOnSelect}
          selections={selected}
          isExpanded={isExpanded}
          ariaLabelledBy={property.label}
          direction={SelectDirection.up}
        >
          {property.options.map((option: string, index) => (
            <SelectOption key={index} value={option} />
          ))}
        </Select>
      </React.Fragment>
    );
  }
}

export default PropertySelect;
