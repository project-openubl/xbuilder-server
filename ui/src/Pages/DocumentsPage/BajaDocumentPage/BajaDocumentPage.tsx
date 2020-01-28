import React from "react";
import DocumentsPageTabs from "../../../PresentationalComponents/DocumentsPageTabs";
import { XmlBuilderRouterProps } from "../../../models/routerProps";
import GenericDocument from "../../../SmartComponents/GenericDocument";
import BajaDocumentForm from "../../../PresentationalComponents/BajaDocumentForm";
import { BajaDocumentFormData } from "../../../PresentationalComponents/BajaDocumentForm/BajaDocumentForm";

interface StateToProps {}

interface DispatchToProps {}

interface Props extends StateToProps, DispatchToProps, XmlBuilderRouterProps {}

interface State {
  form: BajaDocumentFormData | null;
}

class BajaDocumentPage extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      form: null
    };
  }

  processFormAndGetInputDocument = (form: BajaDocumentFormData) => {
    const result = {
      numero: form.numero,
      fechaEmision: form.fechaEmision ? form.fechaEmision.getTime() : undefined,
      proveedor: {
        ruc: form.proveedorRuc,
        razonSocial: form.proveedorNombreComercial,
        codigoPostal: form.proveedorCodigoPostal
      },
      firmante:
        form.firmanteRuc && form.firmanteRazonSocial
          ? {
              ruc: form.firmanteRuc,
              razonSocial: form.firmanteRazonSocial
            }
          : undefined,
      tipoDocumentReference: form.tipoDocumentReference,
      fechaEmisionDocumentReference: form.fechaEmisionDocumentReference
        ? form.fechaEmisionDocumentReference.getTime()
        : undefined,
      serieNumeroDocumentReference: form.serieNumeroDocumentReference,
      motivoBajaDocumentReference: form.motivoBajaDocumentReference
    };

    return result;
  };

  onSubmit = (form: BajaDocumentFormData) => {
    this.setState({
      form
    });
  };

  render() {
    const { form } = this.state;

    let inputDocument = null;
    if (form) {
      inputDocument = this.processFormAndGetInputDocument(form);
    }

    return (
      <React.Fragment>
        <DocumentsPageTabs activeKey={1}>
          <GenericDocument
            documentType="voided-document"
            inputDocument={inputDocument}
          >
            <BajaDocumentForm onSubmit={this.onSubmit} />
          </GenericDocument>
        </DocumentsPageTabs>
      </React.Fragment>
    );
  }
}

export default BajaDocumentPage;
