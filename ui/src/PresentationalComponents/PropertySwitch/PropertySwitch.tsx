import React from "react";
import { Switch } from "@patternfly/react-core";
import { ConfigPropertyRepresentation } from "../../models/xml-builder";

export interface Props {
  defaultValue: boolean | string;
  property: ConfigPropertyRepresentation;
  onChange: (isChecked: boolean) => any;
}

export interface State {
  isChecked: boolean;
}

class PropertySwitch extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      isChecked:
        typeof props.defaultValue === "string"
          ? props.defaultValue === "true"
          : props.defaultValue
    };
  }

  handleChange = (isChecked: boolean) => {
    this.setState({ isChecked });
    this.props.onChange(isChecked);
  };

  render() {
    const { property } = this.props;
    const { isChecked } = this.state;

    return (
      <React.Fragment>
        <Switch
          id={`${property.type}-${property.name}`}
          label="Activado"
          labelOff="Desactivado"
          isChecked={isChecked}
          onChange={this.handleChange}
          aria-label="Switch property"
        />
      </React.Fragment>
    );
  }
}

export default PropertySwitch;
