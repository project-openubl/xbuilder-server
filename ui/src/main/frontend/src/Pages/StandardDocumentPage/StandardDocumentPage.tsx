import React from "react";
import {
  PageSection,
  PageSectionVariants,
  TextContent,
  Text,
  Form,
  FormGroup,
  TextInput,
  Flex,
  FlexItem,
  FormSelect,
  FormSelectOption,
  Button,
  Bullseye
} from "@patternfly/react-core";
import {
  Table,
  TableHeader,
  TableBody,
  TableVariant,
  ICell,
  IRow,
  cellWidth
} from "@patternfly/react-table";
import { Formik, FormikErrors, FormikTouched, FieldArray } from "formik";
import * as Yup from "yup";
import { validateForm } from "../../Utilities/FormUtils";
import { TableTextInput } from "@patternfly/react-inline-edit-extension";
import { TimesIcon } from "@patternfly/react-icons";

enum TipoComprobantePago {
  FACTURA = "FACTURA",
  BOLETA = "BOLETA",
  NOTA_CREDITO = "NOTA_CREDITO",
  NOTA_DEBITO = "NOTA_DEBITO"
}

enum TipoDocumento {
  DOC_TRIB_NO_DOM_SIN_RUC = "DOC_TRIB_NO_DOM_SIN_RUC",
  DNI = "DNI",
  EXTRANJERIA = "EXTRANJERIA",
  RUC = "RUC",
  PASAPORTE = "PASAPORTE",
  DEC_DIPLOMATICA = "DEC_DIPLOMATICA"
}

type Props = {};

type State = {
  tiposComprobante: TipoComprobantePago[];
  tiposDocumento: TipoDocumento[];
  detalleComprobanteColumns: (ICell | string)[];
};

type DetalleComprobanteRow = {
  cantidad: number | undefined;
  descripcion: string;
  precioUnitario: number | undefined;
};

const DEFAULT_DETALLE_COMPROBANTE_ROW: DetalleComprobanteRow = {
  cantidad: undefined,
  descripcion: "",
  precioUnitario: undefined
};

type FormValue = {
  tipoComprobanteSeleccionado: TipoComprobantePago;
  serie: string;
  numero: number;
  proveedorRUC: string;
  proveedorRazonSocial: string;
  proveedorNombreComercial: string | null;
  proveedorCodigoPostal: string;
  clienteTipoDocumento: TipoDocumento;
  clienteNumeroDocumento: string;
  clienteNombre: string;
  detalle: DetalleComprobanteRow[];
};

const INITIAL_FORM_VALUE: FormValue = {
  tipoComprobanteSeleccionado: TipoComprobantePago.BOLETA,
  serie: "F001",
  numero: 1,
  proveedorRUC: "12345678912",
  proveedorRazonSocial: "Proveedor razon social",
  proveedorNombreComercial: null,
  proveedorCodigoPostal: "050101",
  clienteTipoDocumento: TipoDocumento.DNI,
  clienteNumeroDocumento: "98765432",
  clienteNombre: "Cliente nombre",
  detalle: []
};

const FORM_VALIDATION_SCHEMA = () => {
  return Yup.object().shape({
    tipoComprobanteSeleccionado: Yup.string().required("Campo requerido"),
    serie: Yup.string()
      .trim()
      .required("Campo requerido")
      .min(4, "Debe tener 4 caracteres")
      .max(4, "Debe tener 4 caracteres"),
    numero: Yup.number()
      .required("Campo requerido")
      .min(1, "Debe de ser mayor a 0"),
    proveedorRUC: Yup.string()
      .trim()
      .required("Campo requerido")
      .min(11, "Debe tener 11 caracteres")
      .max(11, "Debe tener 11 caracteres"),
    proveedorRazonSocial: Yup.string()
      .trim()
      .required("Campo requerido"),
    proveedorNombreComercial: Yup.string().nullable(),
    proveedorCodigoPostal: Yup.string()
      .trim()
      .required("Campo requerido"),
    clienteTipoDocumento: Yup.string().required("Campo requerido"),
    clienteNumeroDocumento: Yup.string()
      .trim()
      .required("Campo requerido"),
    clienteNombre: Yup.string()
      .trim()
      .required("Campo requerido"),
    detalle: Yup.array()
      .of(
        Yup.object().shape({
          cantidad: Yup.number()
            .min(0, "Debe ser mayor a 0")
            .required("Requerido"),
          descripcion: Yup.string()
            .trim()
            .min(1, "No debe de ser vacío")
            .required("Required") // these constraints take precedence
        })
      )
      .required("Must have friends") // these constraints are shown if and only if inner constraints are satisfied
      .min(1, "Debe de tener al menos un detalle")
  });
};

const isValid = (
  errors: FormikErrors<any>,
  touched: FormikTouched<any>,
  field: string
): boolean => {
  return errors[field] && touched[field] ? false : true;
};

class StandardDocumentPage extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      tiposComprobante: Object.values(TipoComprobantePago),
      tiposDocumento: Object.values(TipoDocumento),
      detalleComprobanteColumns: [
        { title: "Cantidad", transforms: [cellWidth("10")] },
        { title: "Descripcion", transforms: [cellWidth("50")] },
        { title: "Precio Unitario", transforms: [cellWidth("10")] },
        { title: "Total", transforms: [cellWidth("20")] },
        { title: "", transforms: [cellWidth("10")] }
      ]
    };
  }

  componentDidMount() {
    this.addDocumentLine();
  }

  addDocumentLine = () => {};

  // Event handlers

  handleAgregarDocumentLine = () => {
    this.addDocumentLine();
  };

  handleEliminarDocumentLine = (index: number) => {
    // const { detalleComprobanteRows } = this.state;
    // this.setState({
    //   detalleComprobanteRows: detalleComprobanteRows.splice(index, 1)
    // });
  };

  handleValidateForm = (values: any) => {
    return validateForm(values, FORM_VALIDATION_SCHEMA);
  };

  handleFormSubmit = () => {};

  renderDocumentLine = (
    values: FormValue,
    errors: FormikErrors<FormValue>,
    touched: FormikTouched<FormValue>,
    handleOnChange: (value: string, event: any) => void,
    handleBlur: (value: string, event: any) => void,
    setFieldValue: any
  ) => {
    const detalle: DetalleComprobanteRow[] = values.detalle;
    const { detalleComprobanteColumns } = this.state;

    const rows: (IRow | string[])[] = detalle.map(
      (item: DetalleComprobanteRow, index: number) => {
        const onRemoveClick = () => {
          const copy = [...detalle];
          console.log(copy);
          copy.splice(index, 1);
          console.log(copy);
          // setFieldValue("detalle", copy);
        };

        return {
          cells: [
            {
              title: (
                <TableTextInput
                  type="number"
                  aria-label="text input example"
                  value={values.detalle[index].cantidad}
                  onChange={handleOnChange}
                  onBlur={handleBlur}
                  isValid={isValid(errors, touched, "detalle")}
                />
              )
            },
            {
              title: (
                <TableTextInput
                  type="text"
                  onBlur={() => {}}
                  aria-label="text input example"
                />
              )
            },
            {
              title: (
                <TableTextInput
                  type="number"
                  onBlur={() => {}}
                  onChange={() => {}}
                  aria-label="text input example"
                />
              )
            },
            {
              title: (
                <TableTextInput
                  type="number"
                  onBlur={() => {}}
                  onChange={() => {}}
                  aria-label="text input example"
                  isDisabled={true}
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
        };
      }
    );

    const onClick = () => {
      setFieldValue("detalle", [
        ...values.detalle,
        { ...DEFAULT_DETALLE_COMPROBANTE_ROW }
      ]);
    };

    return (
      <Table
        aria-label="Document Line Table"
        cells={detalleComprobanteColumns}
        rows={rows}
      >
        <TableHeader />
        <TableBody />
        <tfoot>
          <tr>
            <td colSpan={4}>
              <Bullseye>
                <Button variant="primary" onClick={onClick}>
                  Agregar
                </Button>
              </Bullseye>
            </td>
          </tr>
        </tfoot>
      </Table>
    );
  };

  renderDocumentSummary = () => {
    const columns: (ICell | string)[] = [
      { title: "Llave" },
      { title: "Valor" }
    ];
    const rows: (IRow | string[])[] = [
      {
        cells: ["Gravada", "two"]
      },
      {
        cells: ["Exonerada", "two"]
      },
      {
        cells: ["Inafecta", "two"]
      },
      {
        cells: ["Gratuita", "two"]
      },
      {
        cells: ["IGV", "two"]
      },
      {
        cells: ["ICB", "two"]
      },
      {
        cells: ["Total", "two"]
      }
    ];

    return (
      <Table
        aria-label="Document summary table"
        variant={TableVariant.compact}
        cells={columns}
        rows={rows}
      >
        <thead>
          <tr>
            <td colSpan={4}>Resumen</td>
          </tr>
        </thead>
        <TableBody />
      </Table>
    );
  };

  renderForm = () => {
    const { tiposComprobante, tiposDocumento } = this.state;

    return (
      <Formik
        validate={this.handleValidateForm}
        initialValues={INITIAL_FORM_VALUE}
        onSubmit={(values, actions) => {
          setTimeout(() => {
            alert(JSON.stringify(values, null, 2));
            actions.setSubmitting(false);
          }, 1000);
        }}
      >
        {({
          values,
          handleSubmit,
          handleChange,
          handleBlur,
          errors,
          touched,
          setFieldValue
        }) => {
          const handleOnChange = (
            value: string,
            event: React.FormEvent<any>
          ): void => {
            handleChange(event);
          };

          return (
            <Form onSubmit={handleSubmit}>
              <div className="pf-l-grid pf-m-all-6-col-on-md pf-m-all-3-col-on-lg pf-m-gutter">
                <div className="pf-l-grid__item">
                  <FormGroup
                    isRequired={true}
                    label="Tipo de comprobante de pago"
                    fieldId="tipoComprobante"
                    helperText="Seleccione un tipo de comprobante"
                    helperTextInvalid={errors.tipoComprobanteSeleccionado}
                    isValid={isValid(
                      errors,
                      touched,
                      "tipoComprobanteSeleccionado"
                    )}
                  >
                    <FormSelect
                      aria-label="Tipo comprobante Select"
                      value={values.tipoComprobanteSeleccionado}
                      onChange={handleOnChange}
                      onBlur={handleBlur}
                      isValid={isValid(
                        errors,
                        touched,
                        "tipoComprobanteSeleccionado"
                      )}
                    >
                      {tiposComprobante.map((option, index) => (
                        <FormSelectOption
                          key={index}
                          value={option}
                          label={option}
                        />
                      ))}
                    </FormSelect>
                  </FormGroup>
                </div>
              </div>
              <div className="pf-l-grid pf-m-all-6-col-on-md pf-m-all-3-col-on-lg pf-m-gutter">
                <div className="pf-l-grid__item">
                  <FormGroup
                    isRequired={true}
                    label="Serie"
                    fieldId="serie"
                    helperText="Ingresa la serie del comprobante. Ej. F001"
                    helperTextInvalid={errors.serie}
                    isValid={isValid(errors, touched, "serie")}
                  >
                    <TextInput
                      isRequired
                      type="text"
                      id="serie"
                      name="serie"
                      value={values.serie}
                      onChange={handleOnChange}
                      onBlur={handleBlur}
                      isValid={isValid(errors, touched, "serie")}
                    />
                  </FormGroup>
                </div>
                <div className="pf-l-grid__item">
                  <FormGroup
                    isRequired={true}
                    label="Número"
                    fieldId="numero"
                    helperTextInvalid={errors.numero}
                    isValid={isValid(errors, touched, "numero")}
                  >
                    <TextInput
                      isRequired
                      type="number"
                      id="numero"
                      name="numero"
                      value={values.numero}
                      onChange={handleOnChange}
                      onBlur={handleBlur}
                      isValid={isValid(errors, touched, "numero")}
                    />
                  </FormGroup>
                </div>
              </div>
              <div className="pf-l-grid pf-m-all-6-col-on-md pf-m-all-3-col-on-lg pf-m-gutter">
                <div className="pf-l-grid__item">
                  <FormGroup
                    isRequired={true}
                    label="Proveedor RUC"
                    fieldId="proveedorRUC"
                    helperText="Ingresa el número RUC del proveedor"
                    helperTextInvalid={errors.proveedorRUC}
                    isValid={isValid(errors, touched, "proveedorRUC")}
                  >
                    <TextInput
                      isRequired
                      type="text"
                      id="proveedorRUC"
                      name="proveedorRUC"
                      value={values.proveedorRUC}
                      onChange={handleOnChange}
                      onBlur={handleBlur}
                      isValid={isValid(errors, touched, "proveedorRUC")}
                    />
                  </FormGroup>
                </div>
                <div className="pf-l-grid__item">
                  <FormGroup
                    isRequired={true}
                    label="Proveedor razón social"
                    fieldId="proveedorRazonSocial"
                    helperTextInvalid={errors.proveedorRazonSocial}
                    isValid={isValid(errors, touched, "proveedorRazonSocial")}
                  >
                    <TextInput
                      isRequired
                      type="text"
                      id="proveedorRazonSocial"
                      name="proveedorRazonSocial"
                      value={values.proveedorRazonSocial}
                      onChange={handleOnChange}
                      onBlur={handleBlur}
                      isValid={isValid(errors, touched, "proveedorRazonSocial")}
                    />
                  </FormGroup>
                </div>
                <div className="pf-l-grid__item">
                  <FormGroup
                    isRequired={false}
                    label="Proveedor nombre comercial"
                    fieldId="proveedorNombreComercial"
                    helperTextInvalid={errors.proveedorNombreComercial}
                    isValid={isValid(
                      errors,
                      touched,
                      "proveedorNombreComercial"
                    )}
                  >
                    <TextInput
                      isRequired
                      type="text"
                      id="proveedorNombreComercial"
                      name="proveedorNombreComercial"
                      value={values.proveedorNombreComercial || undefined}
                      onChange={handleOnChange}
                      onBlur={handleBlur}
                      isValid={isValid(
                        errors,
                        touched,
                        "proveedorNombreComercial"
                      )}
                    />
                  </FormGroup>
                </div>
                <div className="pf-l-grid__item">
                  <FormGroup
                    isRequired={true}
                    label="Proveedor código postal"
                    fieldId="proveedorCodigoPostal"
                    helperTextInvalid={errors.proveedorCodigoPostal}
                    isValid={isValid(errors, touched, "proveedorCodigoPostal")}
                  >
                    <TextInput
                      isRequired
                      type="text"
                      id="proveedorCodigoPostal"
                      name="proveedorCodigoPostal"
                      value={values.proveedorCodigoPostal}
                      onChange={handleOnChange}
                      onBlur={handleBlur}
                      isValid={isValid(
                        errors,
                        touched,
                        "proveedorCodigoPostal"
                      )}
                    />
                  </FormGroup>
                </div>
              </div>
              <div className="pf-l-grid pf-m-all-6-col-on-md pf-m-all-3-col-on-lg pf-m-gutter">
                <div className="pf-l-grid__item">
                  <FormGroup
                    isRequired={true}
                    label="Cliente tipo documento"
                    fieldId="clienteTipoDocumento"
                    helperTextInvalid={errors.clienteTipoDocumento}
                    isValid={isValid(errors, touched, "clienteTipoDocumento")}
                  >
                    <FormSelect
                      aria-label="Cliente tipo documento Select"
                      value={values.clienteTipoDocumento}
                      onChange={handleOnChange}
                      onBlur={handleBlur}
                      isValid={isValid(errors, touched, "clienteTipoDocumento")}
                    >
                      {tiposDocumento.map((option, index) => (
                        <FormSelectOption
                          key={index}
                          value={option}
                          label={option}
                        />
                      ))}
                    </FormSelect>
                  </FormGroup>
                </div>
                <div className="pf-l-grid__item">
                  <FormGroup
                    isRequired={true}
                    label="Cliente número de documento"
                    fieldId="clienteNumeroDocumento"
                    helperTextInvalid={errors.clienteNumeroDocumento}
                    isValid={isValid(errors, touched, "clienteNumeroDocumento")}
                  >
                    <TextInput
                      isRequired
                      type="text"
                      id="clienteNumeroDocumento"
                      name="clienteNumeroDocumento"
                      value={values.clienteNumeroDocumento}
                      onChange={handleOnChange}
                      onBlur={handleBlur}
                      isValid={isValid(
                        errors,
                        touched,
                        "clienteNumeroDocumento"
                      )}
                    />
                  </FormGroup>
                </div>
                <div className="pf-l-grid__item">
                  <FormGroup
                    isRequired={true}
                    label="Cliente nombre/razón social"
                    fieldId="clienteNombre"
                    helperTextInvalid={errors.clienteNombre}
                    isValid={isValid(errors, touched, "clienteNombre")}
                  >
                    <TextInput
                      isRequired
                      type="text"
                      id="clienteNombre"
                      name="clienteNombre"
                      value={values.clienteNombre}
                      onChange={handleOnChange}
                      onBlur={handleBlur}
                      isValid={isValid(errors, touched, "clienteNombre")}
                    />
                  </FormGroup>
                </div>
              </div>

              <FieldArray
                name="friends"
                render={({ move, swap, push, insert, unshift, pop }) => (
                  <Form>{/*... use these however you want */}</Form>
                )}
              />

              <Flex>
                <Flex breakpointMods={[{ modifier: "grow", breakpoint: "md" }]}>
                  <FlexItem style={{ width: "100%" }}>
                    {this.renderDocumentLine(
                      values,
                      errors,
                      touched,
                      handleOnChange,
                      handleBlur,
                      setFieldValue
                    )}
                  </FlexItem>
                </Flex>
                <Flex>
                  <FlexItem>{this.renderDocumentSummary()}</FlexItem>
                </Flex>
              </Flex>
            </Form>
          );
        }}
      </Formik>
    );
  };

  render() {
    return (
      <React.Fragment>
        <PageSection variant={PageSectionVariants.light}>
          <TextContent>
            <Text component="h1">Crear Comprobante</Text>
            <Text component="p">
              Crea facturas, boletas, notas de crédito y notas de débito.
            </Text>
          </TextContent>
        </PageSection>
        <PageSection>{this.renderForm()}</PageSection>
      </React.Fragment>
    );
  }
}

export default StandardDocumentPage;
