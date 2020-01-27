import React, { useState } from "react";
import { useForm, useFieldArray, Controller } from "react-hook-form";
import * as yup from "yup";
import {
  Form,
  FormGroup,
  Grid,
  GridItem,
  Button,
  TextInput,
  InputGroup,
  InputGroupText,
  Card,
  CardBody,
  Stack,
  StackItem
} from "@patternfly/react-core";
import TipoComprobanteSelect from "../TipoComprobanteSelect";
import {
  Table,
  TableHeader,
  TableBody,
  cellWidth,
  ICell
} from "@patternfly/react-table";
import {
  TimesIcon,
  CalendarAltIcon,
  AddCircleOIcon
} from "@patternfly/react-icons";
import TipoDocumentoIdentidadSelect from "../TipoDocumentoIdentidadSelect";
import TipoIgvSelect from "../TipoIgvSelect";
import SwitchController from "../SwitchController";
import TipoNotaCreditoSelect from "../TipoNotaCreditoSelect";
import TipoNotaDebitoSelect from "../TipoNotaDebitoSelect";

export type DetalleFormData = {
  cantidad: number;
  descripcion: string;
  precioUnitario: number;
  tipoIgv: string;
  unidadMedida: string;
  icb: boolean;
};

export type StandardDocumentFormData = {
  tipoComprobante: string;
  serie: string;
  numero: number;
  fechaEmision: Date;
  totalDescuentos: number;
  totalOtrosCargos: number;
  proveedorRuc: string;
  proveedorNombreComercial: string;
  proveedorCodigoPostal: string;
  clienteTipoDocumento: string;
  clienteNumeroDocumento: string;
  clienteNombre: string;
  firmanteRuc: string;
  firmanteRazonSocial: string;
  detalle: Array<DetalleFormData>;

  tipoNota: string;
  comprobanteAfectado: string;
  comprobanteAfectadoSustento: string;
};

interface Props {
  onSubmit: (value: StandardDocumentFormData) => void;
}

const StandardDocumentForm: React.FC<Props> = ({ onSubmit }) => {
  const [isOpenRows, setIsOpenRows] = useState([false]);

  const validationSchema = yup.object().shape({
    tipoComprobante: yup
      .string()
      .trim()
      .required("Este dato es requerido.")
      .transform((v: string) => {
        if (v === "FACTURA" || v === "BOLETA") return "invoice";
        else if (v === "NOTA_CREDITO") return "credit-note";
        else if (v === "NOTA_DEBITO") return "debit-note";
        else return v;
      }),
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
    fechaEmision: yup
      .date()
      .nullable()
      .transform((v: any) => (isNaN(v) ? undefined : v)),
    totalDescuentos: yup
      .number()
      .nullable()
      .min(0, "El valor minimo es 0.")
      .transform((v: number) => (isNaN(v) ? undefined : v)),
    totalOtrosCargos: yup
      .number()
      .nullable()
      .min(0, "El valor minimo es 0.")
      .transform((v: number) => (isNaN(v) ? undefined : v)),
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
    firmanteRuc: yup
      .string()
      .nullable()
      .trim(),
    firmanteRazonSocial: yup
      .string()
      .nullable()
      .trim(),
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
            .min(0, "Este campo debe de ser mayor a 0."),
          tipoIgv: yup.string().nullable(),
          unidadMedida: yup
            .string()
            .trim()
            .nullable(),
          icb: yup.boolean().nullable()
        })
      )
      .required("Este dato es requerido.")
      .min(1, "Este campo debe de contener al menos 1 elemento.")
  });

  const defaultDetalleValues = {
    cantidad: 1,
    descripcion: "Nombre de producto o servicio",
    precioUnitario: 1,
    tipoIgv: undefined,
    unidadMedida: undefined,
    icb: undefined
  };
  const defaultValues = {
    tipoComprobante: "FACTURA",
    serie: "F001",
    numero: 1,
    fechaEmision: undefined,
    proveedorRuc: "12345678912",
    proveedorNombreComercial: "Project OpenUBL",
    proveedorCodigoPostal: "050101",
    clienteTipoDocumento: "RUC",
    clienteNumeroDocumento: "12312312312",
    clienteNombre: "Nombre de mi cliente",
    detalle: [{ ...defaultDetalleValues }]
  };

  const { register, errors, control, handleSubmit, watch } = useForm<StandardDocumentFormData>({
    mode: "onSubmit",
    validationSchema,
    defaultValues
  });
  const { fields, append, remove } = useFieldArray({
    control,
    name: "detalle"
  });
  const watchTipoComprobante = watch("tipoComprobante");

  const columns: ICell[] = [
    { title: "Cantidad", transforms: [cellWidth("10")] },
    { title: "Descripcion", transforms: [cellWidth("50")] },
    { title: "Precio Unitario", transforms: [cellWidth("10")] },
    { title: "", transforms: [cellWidth("10")] }
  ];

  const rows = fields.reduce((a: any[], item: any, index: number) => {
    const onRemoveClick = () => {
      remove(index);
    };

    const detalleErrors: any[] = errors.detalle ? (errors.detalle as any) : [];
    const detalleError = detalleErrors[index] ? detalleErrors[index] : {};

    a.push(
      {
        isOpen:
          isOpenRows[index * 2] !== undefined ? isOpenRows[index * 2] : false,
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
              <Button
                variant="plain"
                aria-label="Action"
                onClick={onRemoveClick}
              >
                <TimesIcon />
              </Button>
            )
          }
        ]
      },
      {
        parent: index * 2,
        fullWidth: false,
        cells: [
          {
            title: (
              <div
                className="pf-c-form pf-m-horizontal"
                style={{ marginTop: 10, marginBottom: 10 }}
              >
                <FormGroup
                  isRequired={false}
                  label="Tipo IGV"
                  fieldId="tipoIgv"
                  isValid={!detalleError.tipoIgv}
                  helperTextInvalid={
                    detalleError.tipoIgv && detalleError.tipoIgv.message
                  }
                >
                  <Controller
                    as={<TipoIgvSelect error={detalleError.tipoIgv} />}
                    name={`detalle[${index}].tipoIgv`}
                    control={control}
                  />
                </FormGroup>
                <FormGroup
                  isRequired={false}
                  label="Unidad medida"
                  fieldId="unidadMedida"
                  isValid={!detalleError.unidadMedida}
                  helperTextInvalid={
                    detalleError.unidadMedida &&
                    detalleError.unidadMedida.message
                  }
                >
                  <TextInput
                    name={`detalle[${index}].unidadMedida`}
                    defaultValue={item.unidadMedida}
                    type="text"
                    aria-label="unidadMedida"
                    ref={register}
                    isValid={!detalleError.unidadMedida}
                  />
                </FormGroup>
                <FormGroup
                  isRequired={false}
                  label="ICB"
                  fieldId="icb"
                  isValid={!detalleError.icb}
                  helperTextInvalid={
                    detalleError.icb && detalleError.icb.message
                  }
                >
                  <Controller
                    as={
                      <SwitchController
                        error={detalleError.icb}
                        labelOn="Activado"
                        labelOff="desactivado"
                      />
                    }
                    name={`detalle[${index}].icb`}
                    control={control}
                  />
                </FormGroup>
              </div>
            )
          }
        ]
      }
    );
    return a;
  }, []);

  const handleAgregarDetalle = () => {
    append({ ...defaultDetalleValues });
  };

  const onDetalleRowCollapse = (
    event: React.MouseEvent,
    rowIndex: number,
    isOpen: boolean
  ) => {
    const newValues = [...isOpenRows];
    newValues[rowIndex] = isOpen;
    setIsOpenRows(newValues);
  };

  const onFormSubmit = (data: StandardDocumentFormData) => {
    onSubmit(data);
  };

  const isNotaCredito = watchTipoComprobante === "NOTA_CREDITO";
  const isNotaDebito = watchTipoComprobante === "NOTA_DEBITO";
  const isNota = isNotaCredito || isNotaDebito;

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
          <GridItem>
            {isNota && (
              <FormGroup
                isRequired={false}
                label="Tipo nota"
                fieldId="tipoNota"
                isValid={!errors.tipoNota}
                helperTextInvalid={errors.tipoNota && errors.tipoNota.message}
              >
                {isNotaCredito && (
                  <Controller
                    as={<TipoNotaCreditoSelect error={errors.tipoNota} />}
                    name="tipoNota"
                    control={control}
                  />
                )}
                {isNotaDebito && (
                  <Controller
                    as={<TipoNotaDebitoSelect error={errors.tipoNota} />}
                    name="tipoNota"
                    control={control}
                  />
                )}
              </FormGroup>
            )}
          </GridItem>

          <GridItem>
            {isNota && (
              <FormGroup
                isRequired={true}
                label="Comprobante afectado"
                fieldId="comprobanteAfectado"
                isValid={!errors.comprobanteAfectado}
                helperTextInvalid={
                  errors.comprobanteAfectado &&
                  errors.comprobanteAfectado.message
                }
              >
                <TextInput
                  isRequired
                  type="text"
                  id="comprobanteAfectado"
                  name="comprobanteAfectado"
                  aria-describedby="comprobanteAfectado"
                  ref={register}
                  isValid={!errors.comprobanteAfectado}
                  defaultValue="F001-100"
                />
              </FormGroup>
            )}
          </GridItem>
          <GridItem>
            {isNota && (
              <FormGroup
                isRequired={true}
                label="Comprobante afectado sustento"
                fieldId="comprobanteAfectado"
                isValid={!errors.comprobanteAfectadoSustento}
                helperTextInvalid={
                  errors.comprobanteAfectadoSustento &&
                  errors.comprobanteAfectadoSustento.message
                }
              >
                <TextInput
                  isRequired
                  type="text"
                  id="comprobanteAfectadoSustento"
                  name="comprobanteAfectadoSustento"
                  aria-describedby="comprobanteAfectadoSustento"
                  ref={register}
                  isValid={!errors.comprobanteAfectadoSustento}
                  defaultValue="Sustento de la nota"
                />
              </FormGroup>
            )}
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
          <GridItem span={9}>
            <Card>
              <CardBody>
                <Table
                  aria-label="Document Line Table"
                  cells={columns}
                  rows={rows}
                  onCollapse={onDetalleRowCollapse}
                  caption="Productos o servicios vendidos"
                >
                  <TableHeader />
                  <TableBody />
                  <tfoot>
                    <tr>
                      <td colSpan={5}>
                        <Button
                          variant="tertiary"
                          onClick={handleAgregarDetalle}
                        >
                          <AddCircleOIcon /> Agregar item
                        </Button>
                      </td>
                    </tr>
                  </tfoot>
                </Table>
              </CardBody>
            </Card>
          </GridItem>
          <GridItem>
            <Card>
              <CardBody>
                <Stack gutter="sm">
                  <StackItem>
                    <FormGroup
                      isRequired={false}
                      label="Total Descuentos"
                      fieldId="totalDescuentos"
                      isValid={!errors.totalDescuentos}
                      helperTextInvalid={
                        errors.totalDescuentos && errors.totalDescuentos.message
                      }
                    >
                      <TextInput
                        isRequired
                        type="number"
                        id="totalDescuentos"
                        name="totalDescuentos"
                        aria-describedby="totalDescuentos"
                        ref={register}
                        isValid={!errors.totalDescuentos}
                      />
                    </FormGroup>
                  </StackItem>
                  <StackItem>
                    <FormGroup
                      isRequired={false}
                      label="Total Otros cargos"
                      fieldId="totalOtrosCargos"
                      isValid={!errors.totalOtrosCargos}
                      helperTextInvalid={
                        errors.totalOtrosCargos &&
                        errors.totalOtrosCargos.message
                      }
                    >
                      <TextInput
                        isRequired
                        type="number"
                        id="totalOtrosCargos"
                        name="totalOtrosCargos"
                        aria-describedby="totalOtrosCargos"
                        ref={register}
                        isValid={!errors.totalOtrosCargos}
                      />
                    </FormGroup>
                  </StackItem>
                </Stack>
              </CardBody>
            </Card>
          </GridItem>
        </Grid>
        <Button type="submit">Guardar</Button>
      </Form>
    </React.Fragment>
  );
};

export default StandardDocumentForm;
