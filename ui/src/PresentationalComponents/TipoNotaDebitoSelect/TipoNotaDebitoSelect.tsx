import React from "react";
import { FormSelect, FormSelectOption } from "@patternfly/react-core";

type TipoNotaDebito =
  | ""
  | "INTERES_POR_MORA"
  | "AUMENTO_EN_EL_VALOR"
  | "PENALIDAD_OTROS_CONCEPTOR";

const tiposDeNotasDeDebito: Map<TipoNotaDebito, string> = new Map([
  ["", "Seleccione"],
  ["INTERES_POR_MORA", "INTERES POR MORA"],
  ["AUMENTO_EN_EL_VALOR", "AUMENTO EN EL VALOR"],
  ["PENALIDAD_OTROS_CONCEPTOR", "PENALIDAD OTROS CONCEPTOR"]
]);

export interface ControlProps {
  value?: string;
  onChange?: (selected: string) => any;
}

export interface Props extends ControlProps {
  error: any;
}

export interface State {
  selected: string;
}

class TipoNotaDebitoSelect extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      selected: props.value ? props.value : ""
    };
  }

  handleOnChange = (value: string) => {
    this.setState({
      selected: value
    });
    if (this.props.onChange) {
      this.props.onChange(value);
    }
  };

  render() {
    const { error } = this.props;
    const { selected } = this.state;

    return (
      <React.Fragment>
        <FormSelect
          value={selected}
          onChange={this.handleOnChange}
          isValid={!error}
          aria-label="Tipo comprobante Select"
        >
          {Array.from(tiposDeNotasDeDebito.keys()).map((key, index) => {
            const label = tiposDeNotasDeDebito.get(key) || "Invalid key";
            return <FormSelectOption key={index} value={key} label={label} />;
          })}
        </FormSelect>
      </React.Fragment>
    );
  }
}

export default TipoNotaDebitoSelect;
