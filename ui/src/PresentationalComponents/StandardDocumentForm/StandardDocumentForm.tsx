import React from "react";
import { useForm, useFieldArray, Controller } from "react-hook-form";
import * as yup from "yup";
import {
  Form,
  FormGroup,
  Grid,
  GridItem,
  Bullseye,
  Button,
  TextInput
} from "@patternfly/react-core";
import TipoComprobanteSelect from "../TipoComprobanteSelect";
import {
  Table,
  TableHeader,
  TableBody,
  cellWidth,
  ICell,
  IRow
} from "@patternfly/react-table";
import { TimesIcon } from "@patternfly/react-icons";
import TipoDocumentoIdentidadSelect from "../TipoDocumentoIdentidadSelect";

export type FormData = {
  [key: string]: string | boolean | number | Array<{}>;
};

interface Props {
  onSubmit: (value: FormData) => void;
}

const StandardDocumentForm: React.FC<Props> = ({ onSubmit }) => {
  const validationSchema = yup.object().shape({
    tipoComprobante: yup
      .string()
      .trim()
      .required("Este dato es requerido."),
    serie: yup
      .string()
      .trim()
      .required("Este dato es requerido.")
      .min(1, "Este campo debe de contener al menos 1 caracteres."),
    numero: yup
      .number()
      .typeError("Número inválido")
      .required("Este dato es requerido.")
      .min(1, "Este campo debe de contener al menos 2 caracteres."),
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
    clienteTipoDocumento: yup
      .string()
      .trim("Dato inválido")
      .required("Este dato es requerido.")
      .min(1, "Este campo debe de contener al menos 1 caracteres."),
    clienteNumeroDocumento: yup
      .string()
      .trim("Dato inválido")
      .required("Este dato es requerido.")
      .min(1, "Este campo debe de contener al menos 1 caracteres."),
    clienteNombre: yup
      .string()
      .trim("Dato inválido")
      .required("Este dato es requerido.")
      .min(1, "Este campo debe de contener al menos 1 caracteres."),
    detalle: yup
      .array()
      .of(
        yup.object().shape({
          cantidad: yup
            .number()
            .required("Este dato es requerido.")
            .min(0, "Este campo debe de ser mayor a 0."),
          descripcion: yup
            .string()
            .trim()
            .required("Este dato es requerido.")
            .min(1, "Este campo debe de tener al menos 1 caracter."),
          precioUnitario: yup
            .number()
            .required("Este dato es requerido.")
            .min(0, "Este campo debe de ser mayor a 0.")
        })
      )
      .required("Este dato es requerido.")
      .min(1, "Este campo debe de contener al menos 1 elemento.")
  });

  const defaultDetalleValues = {
    cantidad: 1,
    descripcion: "Nombre de producto o servicio",
    precioUnitario: 1
  };
  const defaultValues = {
    tipoComprobante: "FACTURA",
    serie: "F001",
    numero: 1,
    proveedorRuc: "12345678912",
    proveedorNombreComercial: "Project OpenUBL",
    proveedorCodigoPostal: "050101",
    clienteTipoDocumento: "RUC",
    clienteNumeroDocumento: "12312312312",
    clienteNombre: "Nombre de mi cliente",
    detalle: [{ ...defaultDetalleValues }]
  };

  const { register, errors, control, handleSubmit } = useForm<FormData>({
    mode: "onSubmit",
    validationSchema,
    defaultValues
  });

  const { fields, append, remove } = useFieldArray({
    control,
    name: "detalle"
  });

  const columns: ICell[] = [
    { title: "Cantidad", transforms: [cellWidth("10")] },
    { title: "Descripcion", transforms: [cellWidth("50")] },
    { title: "Precio Unitario", transforms: [cellWidth("10")] },
    { title: "", transforms: [cellWidth("10")] }
  ];
  const rows: IRow[] = fields.map((item: any, index: number) => {
    const onRemoveClick = () => {
      remove(index);
    };

    const detalleErrors: any[] = errors.detalle ? (errors.detalle as any) : [];
    const detalleError = detalleErrors[index] ? detalleErrors[index] : {};

    return {
      cells: [
        {
          title: (
            <TextInput
              name={`detalle[${index}].cantidad`}
              defaultValue={item.cantidad}
              type="number"
              aria-label="Cantidad"
              ref={register}
              isValid={!detalleError.cantidad}
            />
          )
        },
        {
          title: (
            <TextInput
              name={`detalle[${index}].descripcion`}
              defaultValue={item.descripcion}
              type="text"
              aria-label="Descripcion"
              ref={register}
              isValid={!detalleError.descripcion}
            />
          )
        },
        {
          title: (
            <TextInput
              name={`detalle[${index}].precioUnitario`}
              defaultValue={item.precioUnitario}
              type="number"
              aria-label="Precio unitario"
              placeholder="Descripción del producto o servicio"
              ref={register}
              isValid={!detalleError.precioUnitario}
            />
          )
        },
        {
          title: (
            <Button variant="plain" aria-label="Action" onClick={onRemoveClick}>
              <TimesIcon />
            </Button>
          )
        }
      ]
    };
  });

  const handleAgregarDetalle = () => {
    append({ ...defaultDetalleValues });
  };

  const onFormSubmit = (data: FormData) => {
    onSubmit(data);
  };

  return (
    <React.Fragment>
      <Form onSubmit={handleSubmit(onFormSubmit)}>
        <Grid md={6} lg={3} gutter="sm">
          <GridItem>
            <FormGroup
              isRequired={true}
              label="Tipo comprobante"
              fieldId="tipoComprobante"
              isValid={!errors.tipoComprobante}
              helperTextInvalid={
                errors.tipoComprobante && errors.tipoComprobante.message
              }
            >
              <Controller
                as={<TipoComprobanteSelect error={errors.tipoComprobante} />}
                name="tipoComprobante"
                control={control}
              />
            </FormGroup>
          </GridItem>
        </Grid>
        <Grid md={6} lg={3} gutter="sm">
          <GridItem>
            <FormGroup
              isRequired={true}
              label="Serie"
              fieldId="serie"
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
          </GridItem>
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
              isRequired={true}
              label="Cliente tipo documento"
              fieldId="clienteTipoDocumento"
              isValid={!errors.clienteTipoDocumento}
              helperTextInvalid={
                errors.clienteTipoDocumento &&
                errors.clienteTipoDocumento.message
              }
            >
              <Controller
                as={
                  <TipoDocumentoIdentidadSelect
                    error={errors.clienteTipoDocumento}
                  />
                }
                name="clienteTipoDocumento"
                control={control}
              />
            </FormGroup>
          </GridItem>
          <GridItem>
            <FormGroup
              isRequired={true}
              label="Número documento"
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
          </GridItem>
          <GridItem>
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
          </GridItem>
        </Grid>
        <Grid md={12} lg={12} gutter="sm">
          <GridItem>
            <Table aria-label="Document Line Table" cells={columns} rows={rows}>
              <TableHeader />
              <TableBody />
              <tfoot>
                <tr>
                  <td colSpan={4}>
                    <Bullseye>
                      <Button variant="primary" onClick={handleAgregarDetalle}>
                        Agregar
                      </Button>
                    </Bullseye>
                  </td>
                </tr>
              </tfoot>
            </Table>
          </GridItem>
        </Grid>
        <Button type="submit">Guardar</Button>
      </Form>
    </React.Fragment>
  );
};

export default StandardDocumentForm;
