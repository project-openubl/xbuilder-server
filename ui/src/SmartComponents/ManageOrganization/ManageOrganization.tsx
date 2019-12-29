import React from "react";
import { Button, Modal } from "@patternfly/react-core";
import OrganizationForm from "../../PresentationalComponents/OrganizationForm";
import { FormData } from "../../PresentationalComponents/OrganizationForm/OrganizationForm";
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
  organizationFormData: FormData | null;
}

class ManageOrganization extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = { organizationFormData: null };
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

    if (organizationId) {
      const { updateOrganization, history } = this.props;

      const payload: OrganizationRepresentation = {
        name: organizationFormData.name,
        description: organizationFormData.description,
        type: "",
        useMasterKeys: false
      };

      updateOrganization(organizationId, payload).then(() => {
        history.push("/list-organizations");
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
        history.push("/list-organizations");
      });
    }
  };

  handleModalClose = () => {
    const { history } = this.props;
    history.push("/list-organizations");
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
    const { organizationFormData } = this.state;
    const { organizationId, organization } = this.props;

    let form;
    if (organization) {
      const defaultFormValue: FormData = {
        name: organization.name,
        description: organization.description
      };

      form = (
        <div>
          <OrganizationForm
            defaultFormValue={defaultFormValue}
            onChange={this.handleOnFormChange}
          />
        </div>
      );
    } else {
      form = (
        <OrganizationForm
          defaultFormValue={undefined}
          onChange={this.handleOnFormChange}
        />
      );
    }

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
              isDisabled={!organizationFormData}
            >
              Guardar
            </Button>,
            <Button key="cancel" variant="link" onClick={this.handleModalClose}>
              Cancelar
            </Button>
          ]}
        >
          {form}
        </Modal>
      </React.Fragment>
    );
  }
}

export default ManageOrganization;
