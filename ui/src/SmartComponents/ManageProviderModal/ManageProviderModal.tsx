import React from "react";
import { Button, Modal } from "@patternfly/react-core";
import {
  ComponentRepresentation,
  ServerInfoRepresentation,
  ComponentTypeRepresentation
} from "../../models/xml-builder";
import { XmlBuilderRouterProps } from "../../models/routerProps";
import ProviderForm from "../../PresentationalComponents/ProviderForm";

interface StateToProps {}

interface DispatchToProps {}

interface Props extends StateToProps, DispatchToProps, XmlBuilderRouterProps {
  component: ComponentRepresentation | undefined;
  provider: ComponentTypeRepresentation | undefined;
  redirectTo: string | undefined;
}

interface State {
  saving: boolean;
  formData: FormData | null;
}

class ManageProviderModal extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      saving: false,
      formData: null
    };
  }

  create = () => {};
  update = () => {};

  // Handlers

  handleModalClose = () => {
    const { redirectTo, history } = this.props;
    if (redirectTo) {
      history.push(redirectTo);
    }
  };

  handleModalSave = () => {
    const { component } = this.props;
    this.setState({ saving: true }, () => {
      if (component) {
        this.update();
      } else {
        this.create();
      }
    });
  };

  handleOnFormChange = (isValid: boolean, value: any): void => {
    if (isValid) {
      this.setState({ formData: value });
    } else {
      this.setState({ formData: null });
    }
  };

  render() {
    const { component, provider } = this.props;
    const { saving, formData } = this.state;

    return (
      <React.Fragment>
        <Modal
          title={(component ? "Editar" : "Crear") + " componente"}
          isOpen={true}
          isLarge={true}
          onClose={this.handleModalClose}
          actions={[
            <Button
              key="confirm"
              variant="primary"
              onClick={this.handleModalSave}
              isDisabled={!formData || saving}
            >
              {saving ? "Guardando" : "Guardar"}
            </Button>,
            <Button key="cancel" variant="link" onClick={this.handleModalClose}>
              Cancelar
            </Button>
          ]}
        >
          <ProviderForm
            key={component ? component.id : "create-component-modal-id"}
            component={component}
            provider={provider}
            onChange={this.handleOnFormChange}
          />
        </Modal>
      </React.Fragment>
    );
  }
}

export default ManageProviderModal;
