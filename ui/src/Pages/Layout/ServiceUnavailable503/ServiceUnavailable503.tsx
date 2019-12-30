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

export const ServiceUnavailable503: React.FC = () => {
  return (
    <React.Fragment>
      <PageSection>
        <Bullseye>
          <EmptyState variant={EmptyStateVariant.full}>
            <EmptyStateIcon icon={ErrorCircleOIcon} />
            <Title headingLevel="h5" size="lg">
              Error 503 Service Unavailable! Server is temporarily unable to
              handle the request. This may be due to the server being overloaded
              or down for maintenance.
            </Title>
          </EmptyState>
        </Bullseye>
      </PageSection>
    </React.Fragment>
  );
};
