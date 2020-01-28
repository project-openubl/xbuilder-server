import React from "react";
import { FormSelect, FormSelectOption } from "@patternfly/react-core";

type TipoComprobantePago =
  | ""
  | "FACTURA"
  | "BOLETA"
  | "NOTA_CREDITO"
  | "NOTA_DEBITO";
const tiposDeComprobantes: Map<TipoComprobantePago, string> = new Map([
  ["", "Seleccione"],
  ["FACTURA", "FACTURA"],
  ["BOLETA", "BOLETA"],
  ["NOTA_CREDITO", "NOTA DE CRÉDITO"],
  ["NOTA_DEBITO", "NOTA DE DÉBITO"]
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

class TipoComprobanteSelect extends React.Component<Props, State> {
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
          {Array.from(tiposDeComprobantes.keys()).map((key, index) => {
            const label = tiposDeComprobantes.get(key) || "Invalid key";
            return <FormSelectOption key={index} value={key} label={label} />;
          })}
        </FormSelect>
      </React.Fragment>
    );
  }
}

export default TipoComprobanteSelect;
