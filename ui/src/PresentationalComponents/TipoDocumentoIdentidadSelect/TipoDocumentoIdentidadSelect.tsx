import React from "react";
import { FormSelect, FormSelectOption } from "@patternfly/react-core";

type TipoDocumentoIdentidad =
  | ""
  | "DOC_TRIB_NO_DOM_SIN_RUC"
  | "DNI"
  | "EXTRANJERIA"
  | "RUC"
  | "PASAPORTE"
  | "DEC_DIPLOMATICA";

const tiposDeDocumentosIdentidad: Map<TipoDocumentoIdentidad, string> = new Map(
  [
    ["", "Seleccione"],
    ["DOC_TRIB_NO_DOM_SIN_RUC", "DOC. TRIB. NO DOM. SIN RUC"],
    ["DNI", "DNI"],
    ["EXTRANJERIA", "EXTRANJERIA"],
    ["RUC", "RUC"],
    ["PASAPORTE", "PASAPORTE"],
    ["DEC_DIPLOMATICA", "DEC_DIPLOMATICA"]
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

class TipoDocumentoIdentidadSelect extends React.Component<Props, State> {
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
          {Array.from(tiposDeDocumentosIdentidad.keys()).map((key, index) => {
            const label = tiposDeDocumentosIdentidad.get(key) || "Invalid key";
            return <FormSelectOption key={index} value={key} label={label} />;
          })}
        </FormSelect>
      </React.Fragment>
    );
  }
}

export default TipoDocumentoIdentidadSelect;
