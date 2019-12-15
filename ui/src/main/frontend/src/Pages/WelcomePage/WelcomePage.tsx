import React from "react";
import {
  PageSection,
  Title,
  EmptyState,
  EmptyStateVariant,
  EmptyStateIcon,
  EmptyStateBody,
  Button,
  Bullseye
} from "@patternfly/react-core";
import { CubesIcon } from "@patternfly/react-icons";

const WelcomePage: React.FC = () => {
  return (
    <React.Fragment>
      <PageSection>
        <Bullseye>
          <EmptyState variant={EmptyStateVariant.full}>
            <EmptyStateIcon icon={CubesIcon} />
            <Title headingLevel="h5" size="lg">
              Bienvenido
            </Title>
            <EmptyStateBody>
              Acá podras encontrar demostraciones básicas de como crear
              comprobantes electrónicos.
            </EmptyStateBody>
            <Button variant="primary">Documentación</Button>
          </EmptyState>
        </Bullseye>
      </PageSection>
    </React.Fragment>
  );
};

export default WelcomePage;
