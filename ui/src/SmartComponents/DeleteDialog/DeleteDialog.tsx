import { Button, Modal, ButtonVariant } from "@patternfly/react-core";
import React from "react";
import "./DeleteDialog.scss";
import { deleteDialogActions } from "../../store/deleteDialog";

interface Props {
  onDelete: () => void;
  onCancel: typeof deleteDialogActions.closeModal;
  isOpen: boolean;
  isProcessing: boolean;
  isError: boolean;
  name: string;
  type: string;
}

interface State {}

class DeleteDialogBase extends React.Component<Props, State> {
  public render() {
    const {
      type,
      name,
      onDelete,
      onCancel,
      isOpen,
      isProcessing,
      isError
    } = this.props;

    return (
      <Modal
        isSmall={true}
        title={`Delete ${name}?`}
        onClose={() => {
          onCancel();
        }}
        isOpen={isOpen}
        actions={[
          <Button
            key="confirm"
            isDisabled={isProcessing}
            variant={ButtonVariant.danger}
            onClick={onDelete}
          >
            Delete {`${type}`}
          </Button>,
          <Button
            key="cancel"
            isDisabled={isProcessing}
            variant={ButtonVariant.link}
            onClick={() => {
              onCancel();
            }}
          >
            Cancel
          </Button>
        ]}
      >
        {isError
          ? `Ops! There was a problem while deleting the ${type}.`
          : `Are you sure you want to delete this ${type}? This action will remove any data related to this ${type} permanently.`}
      </Modal>
    );
  }
}

export default DeleteDialogBase;
