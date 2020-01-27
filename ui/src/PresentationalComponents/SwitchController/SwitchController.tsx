import React from "react";
import { Switch } from "@patternfly/react-core";

export interface ControlProps {
  value?: boolean;
  onChange?: (isChecked: boolean) => any;
}

export interface Props extends ControlProps {
  error: any;
  labelOn: string;
  labelOff: string;
}

export interface State {
  isChecked: boolean;
}

class SwitchController extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      isChecked: props.value ? props.value : false
    };
  }

  handleOnChange = (value: boolean) => {
    console.log(value);
    this.setState({
      isChecked: value
    });
    if (this.props.onChange) {
      this.props.onChange(value);
    }
  };

  render() {
    const { labelOn, labelOff } = this.props;
    const { isChecked } = this.state;

    return (
      <React.Fragment>
        <Switch
          id="switchController"
          label={labelOn}
          labelOff={labelOff}
          isChecked={isChecked}
          onChange={this.handleOnChange}
        />
      </React.Fragment>
    );
  }
}

export default SwitchController;
