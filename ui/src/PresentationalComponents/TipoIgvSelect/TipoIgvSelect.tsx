import React from "react";
import { FormSelect, FormSelectOption } from "@patternfly/react-core";

type TipoIgv =
  | ""
  | "GRAVADO_OPERACION_ONEROSA"
  | "GRAVADO_RETIRO_POR_PREMIO"
  | "GRAVADO_RETIRO_POR_DONACION"
  | "GRAVADO_RETIRO"
  | "GRAVADO_RETIRO_POR_PUBLICIDAD"
  | "GRAVADO_BONIFICACIONES"
  | "GRAVADO_RETIRO_POR_ENTREGA_A_TRABAJADORES"
  | "GRAVADO_IVAP"
  | "EXONERADO_OPERACION_ONEROSA"
  | "EXONERADO_TRANSFERENCIA_GRATUITA"
  | "INAFECTO_OPERACION_ONEROSA"
  | "INAFECTO_RETIRO_POR_BONIFICACION"
  | "INAFECTO_RETIRO"
  | "INAFECTO_RETIRO_POR_MUESTRAS_MEDICAS"
  | "INAFECTO_RETIRO_POR_CONVENIO_COLECTIVO"
  | "INAFECTO_RETIRO_POR_PREMIO"
  | "INAFECTO_RETIRO_POR_PUBLICIDAD"
  | "EXPORTACION";

const tiposDeIgv: Map<TipoIgv, string> = new Map([
  ["", "Seleccione"],
  ["GRAVADO_OPERACION_ONEROSA", "GRA. OPERACION ONEROSA"],
  ["GRAVADO_RETIRO_POR_PREMIO", "GRA. RETIRO POR PREMIO"],
  ["GRAVADO_RETIRO_POR_DONACION", "GRA. RETIRO POR DONACIÓN"],
  ["GRAVADO_RETIRO", "GRA. RETIRO"],
  ["GRAVADO_RETIRO_POR_PUBLICIDAD", "GRA. RETIRO POR PUBLICIDAD"],
  ["GRAVADO_BONIFICACIONES", "GRA. BONIFICACIONES"],
  [
    "GRAVADO_RETIRO_POR_ENTREGA_A_TRABAJADORES",
    "GRA. RETIRO POR ENTREGA A TRAB."
  ],
  ["GRAVADO_IVAP", "GRA. IVAP"],
  ["EXONERADO_OPERACION_ONEROSA", "EXO. OPERACIÓN ONEROSA"],
  ["EXONERADO_TRANSFERENCIA_GRATUITA", "EXO. TRANSFERENCIA GRATUITA"],
  ["INAFECTO_OPERACION_ONEROSA", "INA. OPERACIÓN ONEROSA"],
  ["INAFECTO_RETIRO_POR_BONIFICACION", "INA. RETIRO POR BONIFICACIÓN"],
  ["INAFECTO_RETIRO", "INA. RETIRO"],
  ["INAFECTO_RETIRO_POR_MUESTRAS_MEDICAS", "INA. RETIRO POR MUESTRAS MÉDICAS"],
  [
    "INAFECTO_RETIRO_POR_CONVENIO_COLECTIVO",
    "INA. RETIRO POR CONVENIO COLECTIVO"
  ],
  ["INAFECTO_RETIRO_POR_PREMIO", "INA. RETIRO POR PREMIO"],
  ["INAFECTO_RETIRO_POR_PUBLICIDAD", "INA. RETIRO POR PUBLICIDAD"],
  ["EXPORTACION", "EXPORTACION"]
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

class TipoIgvSelect extends React.Component<Props, State> {
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
          aria-label="Tipo IGV Select"
        >
          {Array.from(tiposDeIgv.keys()).map((key, index) => {
            const label = tiposDeIgv.get(key) || "Invalid key";
            return <FormSelectOption key={index} value={key} label={label} />;
          })}
        </FormSelect>
      </React.Fragment>
    );
  }
}

export default TipoIgvSelect;
