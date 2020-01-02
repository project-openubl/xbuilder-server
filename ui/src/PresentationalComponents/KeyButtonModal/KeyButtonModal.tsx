import React, { useState } from "react";
import { Text, Button, TextVariants, Modal } from "@patternfly/react-core";
import { ApplicationsIcon } from "@patternfly/react-icons";

interface Props {
  title: string;
  buttonLabel: string;
  keyValue: string;
}

const KeyButtonModal: React.FC<Props> = ({ title, buttonLabel, keyValue }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleModalToggle = () => {
    setIsModalOpen(!isModalOpen);
  };

  return (
    <React.Fragment>
      <Button
        variant="link"
        icon={<ApplicationsIcon />}
        onClick={handleModalToggle}
      >
        {buttonLabel}
      </Button>
      <Modal
        isSmall
        title={title}
        isOpen={isModalOpen}
        onClose={handleModalToggle}
        actions={[
          <Button key="cancel" variant="link" onClick={handleModalToggle}>
            Close
          </Button>
        ]}
        isFooterLeftAligned
      >
        <Text component={TextVariants.blockquote}>{keyValue}</Text>
      </Modal>
    </React.Fragment>
  );
};

export default KeyButtonModal;
