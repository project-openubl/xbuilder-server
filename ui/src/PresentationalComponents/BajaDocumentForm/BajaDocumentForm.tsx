import React from "react";
import { useForm, Controller } from "react-hook-form";
import * as yup from "yup";
import {
  Form,
  FormGroup,
  Grid,
  GridItem,
  Button,
  TextInput,
  InputGroup,
  InputGroupText
} from "@patternfly/react-core";
import TipoComprobanteSelect from "../TipoComprobanteSelect";
import { CalendarAltIcon } from "@patternfly/react-icons";

export type BajaDocumentFormData = {
  numero: number;
  fechaEmision: Date;
  proveedorRuc: string;
  proveedorNombreComercial: string;
  proveedorCodigoPostal: string;
  firmanteRuc: string;
  firmanteRazonSocial: string;

  tipoDocumentReference: string;
  fechaEmisionDocumentReference: Date;
  serieNumeroDocumentReference: string;
  motivoBajaDocumentReference: string;
};

interface Props {
  onSubmit: (value: BajaDocumentFormData) => void;
}

const BajaDocumentForm: React.FC<Props> = ({ onSubmit }) => {
  const validationSchema = yup.object().shape({
    numero: yup
      .number()
      .required("Este dato es requerido.")
      .min(1, "Valor minimo es 1"),
    fechaEmision: yup
      .date()
      .nullable()
      .transform((v: any) => (isNaN(v) ? undefined : v)),
    tipoDocumentReference: yup
      .string()
      .trim()
      .required(),
    serieNumeroDocumentReference: yup
      .string()
      .trim()
      .required(),
    fechaEmisionDocumentReference: yup
      .date()
      .required()
      .transform((v: any) => (isNaN(v) ? undefined : v)),
    motivoBajaDocumentReference: yup
      .string()
      .trim()
      .required(),
    proveedorRuc: yup
      .string()
      .trim("Dato inválido")
      .required("Este dato es requerido.")
      .min(1, "Este campo debe de contener al menos 1 caracteres."),
    proveedorNombreComercial: yup
      .string()
      .trim()
      .required("Este dato es requerido.")
      .min(1, "Este campo debe de contener al menos 1 caracteres."),
    proveedorCodigoPostal: yup
      .string()
      .trim()
      .required("Este dato es requerido.")
      .min(1, "Este campo debe de contener al menos 1 caracteres."),
    firmanteRuc: yup
      .string()
      .nullable()
      .trim(),
    firmanteRazonSocial: yup
      .string()
      .nullable()
      .trim()
  });

  let today = new Date().toISOString();
  today = today.substring(0, today.indexOf("T"));

  const defaultValues = {
    numero: 1,
    fechaEmision: undefined,
    proveedorRuc: "12345678912",
    proveedorNombreComercial: "Project OpenUBL",
    proveedorCodigoPostal: "050101",
    tipoDocumentReference: "FACTURA",
    serieNumeroDocumentReference: "F001-1",
    fechaEmisionDocumentReference: today,
    motivoBajaDocumentReference: "El motivo de baja del comprobante"
  };

  const { register, errors, control, handleSubmit } = useForm<
    BajaDocumentFormData
  >({
    mode: "onSubmit",
    validationSchema,
    defaultValues
  });

  const onFormSubmit = (data: BajaDocumentFormData) => {
    onSubmit(data);
  };

  return (
    <React.Fragment>
      <Form onSubmit={handleSubmit(onFormSubmit)}>
        <Grid md={6} lg={3} gutter="sm">
          <GridItem>
            <FormGroup
              isRequired={true}
              label="Número"
              fieldId="numero"
              isValid={!errors.numero}
              helperTextInvalid={errors.numero && errors.numero.message}
            >
              <TextInput
                isRequired
                type="number"
                id="numero"
                name="numero"
                aria-describedby="numero"
                ref={register}
                isValid={!errors.numero}
              />
            </FormGroup>
          </GridItem>
          <GridItem>
            <FormGroup
              isRequired={false}
              label="Fecha Emisión"
              fieldId="fechaEmision"
              isValid={!errors.fechaEmision}
              helperTextInvalid={
                errors.fechaEmision && errors.fechaEmision.message
              }
            >
              <InputGroup>
                <InputGroupText component="label" htmlFor="fechaEmision">
                  <CalendarAltIcon />
                </InputGroupText>
                <TextInput
                  isRequired={false}
                  type="date"
                  id="fechaEmision"
                  name="fechaEmision"
                  aria-describedby="fechaEmision"
                  ref={register}
                  isValid={!errors.fechaEmision}
                />
              </InputGroup>
            </FormGroup>
          </GridItem>
        </Grid>
        <Grid md={6} lg={3} gutter="sm">
          <GridItem>
            <FormGroup
              isRequired={true}
              label="Proveedor RUC"
              fieldId="proveedorRuc"
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
        <Grid md={6} lg={3} gutter="sm">
          <GridItem>
            <FormGroup
              isRequired={false}
              label="Firmante RUC"
              fieldId="firmanteRuc"
              isValid={!errors.firmanteRuc}
              helperTextInvalid={
                errors.firmanteRuc && errors.firmanteRuc.message
              }
            >
              <TextInput
                isRequired={false}
                type="text"
                id="firmanteRuc"
                name="firmanteRuc"
                aria-describedby="firmanteRuc"
                ref={register}
                isValid={!errors.firmanteRuc}
              />
            </FormGroup>
          </GridItem>
          <GridItem span={6}>
            <FormGroup
              isRequired={false}
              label="Firmante Razón Social"
              fieldId="firmanteRazonSocial"
              isValid={!errors.firmanteRazonSocial}
              helperTextInvalid={
                errors.firmanteRazonSocial && errors.firmanteRazonSocial.message
              }
            >
              <TextInput
                isRequired={false}
                type="text"
                id="firmanteRazonSocial"
                name="firmanteRazonSocial"
                aria-describedby="firmanteRazonSocial"
                ref={register}
                isValid={!errors.firmanteRazonSocial}
              />
            </FormGroup>
          </GridItem>
        </Grid>
        <Grid md={6} lg={3} gutter="sm">
          <GridItem>
            <FormGroup
              isRequired={true}
              label="Tipo documento baja "
              fieldId="tipoDocumentReference"
              isValid={!errors.tipoDocumentReference}
              helperTextInvalid={
                errors.tipoDocumentReference &&
                errors.tipoDocumentReference.message
              }
            >
              <Controller
                as={
                  <TipoComprobanteSelect error={errors.tipoDocumentReference} />
                }
                name="tipoDocumentReference"
                control={control}
              />
            </FormGroup>
          </GridItem>
          <GridItem>
            <FormGroup
              isRequired={true}
              label="Serie y número documento baja"
              fieldId="serieNumeroDocumentReference"
              isValid={!errors.serieNumeroDocumentReference}
              helperTextInvalid={
                errors.serieNumeroDocumentReference &&
                errors.serieNumeroDocumentReference.message
              }
            >
              <TextInput
                isRequired
                type="text"
                id="serieNumeroDocumentReference"
                name="serieNumeroDocumentReference"
                aria-describedby="serieNumeroDocumentReference"
                ref={register}
                isValid={!errors.serieNumeroDocumentReference}
              />
            </FormGroup>
          </GridItem>
          <GridItem>
            <FormGroup
              isRequired={true}
              label="Fecha emisión documento baja"
              fieldId="fechaEmisionDocumentReference"
              isValid={!errors.fechaEmisionDocumentReference}
              helperTextInvalid={
                errors.fechaEmisionDocumentReference &&
                errors.fechaEmisionDocumentReference.message
              }
            >
              <InputGroup>
                <InputGroupText
                  component="label"
                  htmlFor="fechaEmisionDocumentReference"
                >
                  <CalendarAltIcon />
                </InputGroupText>
                <TextInput
                  isRequired={false}
                  type="date"
                  id="fechaEmisionDocumentReference"
                  name="fechaEmisionDocumentReference"
                  aria-describedby="fechaEmisionDocumentReference"
                  ref={register}
                  isValid={!errors.fechaEmisionDocumentReference}
                />
              </InputGroup>
            </FormGroup>
          </GridItem>
        </Grid>
        <Grid lg={12}>
          <GridItem>
            <FormGroup
              isRequired={true}
              label="Sustento de la baja del documento"
              fieldId="motivoBajaDocumentReference"
              isValid={!errors.motivoBajaDocumentReference}
              helperTextInvalid={
                errors.motivoBajaDocumentReference &&
                errors.motivoBajaDocumentReference.message
              }
            >
              <textarea
                className="pf-c-form-control"
                id="motivoBajaDocumentReference"
                name="motivoBajaDocumentReference"
                placeholder="Sustento."
                ref={register}
              ></textarea>
            </FormGroup>
          </GridItem>
        </Grid>
        <Button type="submit">Guardar</Button>
      </Form>
    </React.Fragment>
  );
};

export default BajaDocumentForm;
