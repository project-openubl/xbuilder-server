import React from "react";
import {
  PageSection,
  Bullseye,
  EmptyState,
  EmptyStateVariant,
  Title,
  EmptyStateIcon
} from "@patternfly/react-core";
import { ErrorCircleOIcon } from "@patternfly/react-icons";

export const NotFound404: React.FC = () => {
  return (
    <React.Fragment>
      <PageSection>
        <Bullseye>
          <EmptyState variant={EmptyStateVariant.full}>
            <EmptyStateIcon icon={ErrorCircleOIcon} />
            <Title headingLevel="h5" size="lg">
              Error 404 Page not found!
            </Title>
          </EmptyState>
        </Bullseye>
      </PageSection>
    </React.Fragment>
  );
};
