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

const NotFoundPage: React.FC = () => {
  return (
    <React.Fragment>
      <PageSection>
        <Bullseye>
          <EmptyState variant={EmptyStateVariant.full}>
            <EmptyStateIcon icon={ErrorCircleOIcon} />
            <Title headingLevel="h5" size="lg">
              Page not found!
            </Title>
          </EmptyState>
        </Bullseye>
      </PageSection>
    </React.Fragment>
  );
};

export default NotFoundPage;
