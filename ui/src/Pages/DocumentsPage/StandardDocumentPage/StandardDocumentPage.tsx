import React from "react";
import { DocumentType } from "../../../models/xml-builder";
import { XmlBuilderRouterProps } from "../../../models/routerProps";
import GenericDocument from "../../../SmartComponents/GenericDocument";
import DocumentsPageTabs from "../../../PresentationalComponents/DocumentsPageTabs";
import StandardDocumentForm from "../../../PresentationalComponents/StandardDocumentForm";
import { StandardDocumentFormData } from "../../../PresentationalComponents/StandardDocumentForm/StandardDocumentForm";

interface StateToProps {}

interface DispatchToProps {}

interface Props extends StateToProps, DispatchToProps, XmlBuilderRouterProps {}

interface State {
  form: StandardDocumentFormData | null;
  documentType: DocumentType;
}

class StandardDocumentPage extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      form: null,
      documentType: "invoice"
    };
  }

  processFormAndGetInputDocument = (form: StandardDocumentFormData) => {
    const result: any = {
      serie: form.serie,
      numero: form.numero,
      fechaEmision: form.fechaEmision ? form.fechaEmision.getTime() : undefined,
      totalDescuentos: form.totalDescuentos,
      totalOtrosCargos: form.totalOtrosCargos,
      proveedor: {
        ruc: form.proveedorRuc,
        razonSocial: form.proveedorNombreComercial,
        codigoPostal: form.proveedorCodigoPostal
      },
      cliente: {
        tipoDocumentoIdentidad: form.clienteTipoDocumento,
        numeroDocumentoIdentidad: form.clienteNumeroDocumento,
        nombre: form.clienteNombre
      },
      firmante:
        form.firmanteRuc && form.firmanteRazonSocial
          ? {
              ruc: form.firmanteRuc,
              razonSocial: form.firmanteRazonSocial
            }
          : undefined,
      detalle: form.detalle.map((item: any) => ({
        descripcion: item.descripcion,
        precioUnitario: item.precioUnitario,
        cantidad: item.cantidad,
        unidadMedida: item.unidadMedida ? item.unidadMedida : undefined,
        tipoIGV: item.tipoIgv,
        icb: item.icb
      }))
    };

    if (form.comprobanteAfectado) {
      result.serieNumeroInvoiceReference = form.comprobanteAfectado;
    }
    if (form.comprobanteAfectadoSustento) {
      result.descripcionSustentoInvoiceReference =
        form.comprobanteAfectadoSustento;
    }
    if (form.tipoNota) {
      result.tipoNota = form.tipoNota;
    }

    return result;
  };

  onSubmit = (form: any) => {
    this.setState({
      form,
      documentType: form.tipoComprobante
    });
  };

  render() {
    const { documentType, form } = this.state;

    let inputDocument = null;
    if (form) {
      inputDocument = this.processFormAndGetInputDocument(form);
    }

    return (
      <React.Fragment>
        <DocumentsPageTabs activeKey={0}>
          <GenericDocument
            documentType={documentType}
            inputDocument={inputDocument}
          >
            <StandardDocumentForm onSubmit={this.onSubmit} />
          </GenericDocument>
        </DocumentsPageTabs>
      </React.Fragment>
    );
  }
}

export default StandardDocumentPage;
