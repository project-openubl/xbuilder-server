import React from "react";
import { Button, Modal } from "@patternfly/react-core";
import OrganizationForm from "../OrganizationForm";
import { FormData } from "../OrganizationForm/OrganizationForm";
import { OrganizationRepresentation } from "../../models/xml-builder";
import { AxiosError } from "axios";
import { FetchStatus } from "../../store/common";

interface Props {
  match: any;
  history: any;
  location: any;

  organizationId: string | null;

  organization: OrganizationRepresentation | undefined;
  error: AxiosError<any> | undefined;
  status: FetchStatus | undefined;

  fetchOrganization: (organizationId: string) => any;
  createOrganization: (organization: OrganizationRepresentation) => any;
  updateOrganization: (
    organizationId: string,
    organization: OrganizationRepresentation
  ) => any;
}

interface State {
  saving: boolean;
  organizationFormData: FormData | null;
}

class ManageOrganizationModal extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = { saving: false, organizationFormData: null };
  }

  componentDidMount() {
    const { organizationId, fetchOrganization } = this.props;
    if (organizationId) {
      fetchOrganization(organizationId);
    }
  }

  handleModalSave = () => {
    const { organizationFormData } = this.state;
    const { organizationId } = this.props;

    if (!organizationFormData) {
      return;
    }

    this.setState({ saving: true }, () => {
      if (organizationId) {
        const { updateOrganization, history } = this.props;

        const payload: OrganizationRepresentation = {
          name: organizationFormData.name,
          description: organizationFormData.description,
          type: "",
          useMasterKeys: false
        };

        updateOrganization(organizationId, payload).then(() => {
          history.push("/organizations/list");
        });
      } else {
        const { createOrganization, history } = this.props;

        const payload: OrganizationRepresentation = {
          name: organizationFormData.name,
          description: organizationFormData.description,
          type: "",
          useMasterKeys: false
        };

        createOrganization(payload).then(() => {
          history.push("/organizations/list");
        });
      }
    });
  };

  handleModalClose = () => {
    const { history } = this.props;
    history.push("/organizations/list");
  };

  handleOnFormChange = (isValid: boolean, value: FormData): void => {
    if (isValid) {
      this.setState({
        organizationFormData: value
      });
    } else {
      this.setState({
        organizationFormData: null
      });
    }
  };

  render() {
    const { saving, organizationFormData } = this.state;
    const { organizationId, organization } = this.props;

    return (
      <React.Fragment>
        <Modal
          title={(organizationId ? "Editar" : "Crear") + " organización"}
          isOpen={true}
          isSmall={true}
          onClose={this.handleModalClose}
          actions={[
            <Button
              key="confirm"
              variant="primary"
              onClick={this.handleModalSave}
              isDisabled={!organizationFormData || saving}
            >
              {saving ? "Guardando" : "Guardar"}
            </Button>,
            <Button key="cancel" variant="link" onClick={this.handleModalClose}>
              Cancelar
            </Button>
          ]}
        >
          <OrganizationForm
            key={organization ? organization.id : "create-organization"}
            organization={organization}
            onChange={this.handleOnFormChange}
          />
        </Modal>
      </React.Fragment>
    );
  }
}

export default ManageOrganizationModal;
