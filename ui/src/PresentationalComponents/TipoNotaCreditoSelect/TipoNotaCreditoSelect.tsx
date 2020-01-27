import React from "react";
import { FormSelect, FormSelectOption } from "@patternfly/react-core";

type TipoNotaCredito =
  | ""
  | "ANULACION_DE_LA_OPERACION"
  | "ANULACION_POR_ERROR_EN_EL_RUC"
  | "CORRECCION_POR_ERROR_EN_LA_DESCRIPCION"
  | "DESCUENTO_GLOBAL"
  | "DESCUENTO_POR_ITEM"
  | "DEVOLUCION_TOTAL"
  | "DEVOLUCION_POR_ITEM"
  | "BONIFICACION"
  | "DISMINUCION_EN_EL_VALOR"
  | "OTROS_CONCEPTOS";

const tiposDeNotasDeCredito: Map<TipoNotaCredito, string> = new Map(
  [
    ["", "Seleccione"],
    ["ANULACION_DE_LA_OPERACION", "ANULACIÓN DE LA OPERACIÓN"],
    ["ANULACION_POR_ERROR_EN_EL_RUC", "ANULACIÓN POR ERROR EN RUC"],
    [
      "CORRECCION_POR_ERROR_EN_LA_DESCRIPCION",
      "CORRECCION POR ERROR EN DESCRIPCION"
    ],
    ["DESCUENTO_GLOBAL", "DESCUENTO GLOBAL"],
    ["DESCUENTO_POR_ITEM", "DESCUENTO POR ITEM"],
    ["DEVOLUCION_TOTAL", "DEVOLUCIÓN TOTAL"],
    ["DEVOLUCION_POR_ITEM", "DEVOLUCION POR ITEM"],
    ["BONIFICACION", "BONIFICACIÓN"],
    ["DISMINUCION_EN_EL_VALOR", "DISMINUCIÓN EN EL VALOR"],
    ["OTROS_CONCEPTOS", "OTROS CONCEPTOS"]
  ]
);

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

class TipoNotaCreditoSelect extends React.Component<Props, State> {
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
          {Array.from(tiposDeNotasDeCredito.keys()).map((key, index) => {
            const label = tiposDeNotasDeCredito.get(key) || "Invalid key";
            return <FormSelectOption key={index} value={key} label={label} />;
          })}
        </FormSelect>
      </React.Fragment>
    );
  }
}

export default TipoNotaCreditoSelect;
