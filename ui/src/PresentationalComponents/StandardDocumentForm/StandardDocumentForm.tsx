import React from "react";
import { useForm } from "react-hook-form";
import * as yup from "yup";
import {
  Form,
  FormGroup,
  TextInput,
  Grid,
  GridItem
} from "@patternfly/react-core";
import {
  ComponentRepresentation,
  ComponentTypeRepresentation,
  ConfigPropertyRepresentation
} from "../../models/xml-builder";
import PropertySwitch from "../PropertySwitch";
import PropertySelect from "../PropertySelect";
import PropertyFile from "../PropertyFile";

export type FormData = { [key: string]: string | boolean };

interface Props {
  onChange: (isValid: boolean, value: FormData) => void;
}

const StandardDocumentForm: React.FC<Props> = ({ onChange }) => {
  const validationSchema = yup.object().shape({
    id: yup
      .string()
      .trim()
      .required("Este dato es requerido.")
      .max(250, "Este campo debe de contener menos de 250 caracteres."),
    name: yup
      .string()
      .trim()
      .required("Este dato es requerido.")
      .min(3, "Este campo debe de contener al menos 3 caracteres.")
      .max(250, "Este campo debe de contener menos de 250 caracteres.")
  });

  const { register, errors, triggerValidation, setValue, getValues } = useForm<
    FormData
  >({
    mode: "onSubmit",
    validationSchema
  });

  // Handlers

  const handleOnFormChange = () => {
    // triggerValidation().then((isValid: boolean) => {
    //   onChange(isValid, getValues());
    // });
  };

  return (
    <React.Fragment>
      <Form onSubmit={() => {}} onChange={handleOnFormChange}>
        <div className="pf-l-grid pf-m-all-6-col-on-md pf-m-all-3-col-on-lg pf-m-gutter">
          <div className="pf-l-grid__item">
            <FormGroup
              isRequired={true}
              label="Serie"
              fieldId="serie"
              helperText="Ingresa la serie del comprobante. Ej. F001"
              isValid={!errors.serie}
              helperTextInvalid={errors.serie && errors.serie.message}
            >
              <TextInput
                isRequired
                type="text"
                id="serie"
                name="serie"
                aria-describedby="serie"
                ref={register}
                isValid={!errors.serie}
              />
            </FormGroup>
          </div>
          <div className="pf-l-grid__item">
            <FormGroup
              isRequired={true}
              label="Número"
              fieldId="numero"
              isValid={!errors.numero}
              helperTextInvalid={errors.numero && errors.numero.message}
            >
              <TextInput
                isRequired
                type="text"
                id="numero"
                name="numero"
                aria-describedby="numero"
                ref={register}
                isValid={!errors.numero}
              />
            </FormGroup>
          </div>
        </div>
        <Grid sm={12} md={6} gutter="sm">
          <GridItem>
            <FormGroup
              isRequired={true}
              label="Proveedor RUC"
              fieldId="proveedorRuc"
              helperText="Ingresa el número RUC del proveedor"
              isValid={!errors.proveedorRuc}
              helperTextInvalid={
                errors.proveedorRuc && errors.proveedorRuc.message
              }
            >
              <TextInput
                isRequired
                type="text"
                id="proveedorRuc"
                name="proveedorRuc"
                aria-describedby="proveedorRuc"
                ref={register}
                isValid={!errors.proveedorRuc}
              />
            </FormGroup>
          </GridItem>
          <GridItem>
            <FormGroup
              isRequired={true}
              label="Nombre comercial"
              fieldId="proveedorNombreComercial"
              isValid={!errors.proveedorNombreComercial}
              helperTextInvalid={
                errors.proveedorNombreComercial &&
                errors.proveedorNombreComercial.message
              }
            >
              <TextInput
                isRequired
                type="text"
                id="proveedorNombreComercial"
                name="proveedorNombreComercial"
                aria-describedby="proveedorNombreComercial"
                ref={register}
                isValid={!errors.proveedorNombreComercial}
              />
            </FormGroup>
          </GridItem>
          <GridItem>
            <FormGroup
              isRequired={true}
              label="Código postal"
              fieldId="proveedorCodigoPostal"
              isValid={!errors.proveedorCodigoPostal}
              helperTextInvalid={
                errors.proveedorCodigoPostal &&
                errors.proveedorCodigoPostal.message
              }
            >
              <TextInput
                isRequired
                type="text"
                id="proveedorCodigoPostal"
                name="proveedorCodigoPostal"
                aria-describedby="proveedorCodigoPostal"
                ref={register}
                isValid={!errors.proveedorCodigoPostal}
              />
            </FormGroup>
          </GridItem>
        </Grid>
        <div className="pf-l-grid pf-m-all-6-col-on-md pf-m-all-3-col-on-lg pf-m-gutter">
          <div className="pf-l-grid__item">
            <FormGroup
              isRequired={true}
              label="Cliente tipo doc."
              fieldId="clienteTipoDocumento"
              isValid={!errors.clienteTipoDocumento}
              helperTextInvalid={
                errors.clienteTipoDocumento &&
                errors.clienteTipoDocumento.message
              }
            >
              <TextInput
                isRequired
                type="text"
                id="clienteTipoDocumento"
                name="clienteTipoDocumento"
                aria-describedby="clienteTipoDocumento"
                ref={register}
                isValid={!errors.clienteTipoDocumento}
              />
            </FormGroup>
          </div>
          <div className="pf-l-grid__item">
            <FormGroup
              isRequired={true}
              label="Número doc."
              fieldId="clienteNumeroDocumento"
              isValid={!errors.clienteNumeroDocumento}
              helperTextInvalid={
                errors.clienteNumeroDocumento &&
                errors.clienteNumeroDocumento.message
              }
            >
              <TextInput
                isRequired
                type="text"
                id="clienteNumeroDocumento"
                name="clienteNumeroDocumento"
                aria-describedby="clienteNumeroDocumento"
                ref={register}
                isValid={!errors.clienteNumeroDocumento}
              />
            </FormGroup>
          </div>
          <div className="pf-l-grid__item">
            <FormGroup
              isRequired={true}
              label="Nombre/razón social"
              fieldId="clienteNombre"
              isValid={!errors.clienteNombre}
              helperTextInvalid={
                errors.clienteNombre && errors.clienteNombre.message
              }
            >
              <TextInput
                isRequired
                type="text"
                id="clienteNombre"
                name="clienteNombre"
                aria-describedby="clienteNombre"
                ref={register}
                isValid={!errors.clienteNombre}
              />
            </FormGroup>
          </div>
        </div>
      </Form>
    </React.Fragment>
  );
};

export default StandardDocumentForm;
