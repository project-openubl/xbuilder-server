import React from "react";
import {
  PageSection,
  Title,
  EmptyState,
  EmptyStateVariant,
  EmptyStateIcon,
  EmptyStateBody,
  Button,
  Bullseye,
  PageSectionVariants,
  TextContent,
  Text,
  Tabs,
  Tab
} from "@patternfly/react-core";
import { CubesIcon } from "@patternfly/react-icons";

const ActiveKeysPage: React.FC = () => {
  return (
    <React.Fragment>
      <PageSection variant={PageSectionVariants.light}>
        <TextContent>
          <Text component="h1">Administrar certificados</Text>
          <Text component="p">
            Acá podrás configurar los certificados de seguridad que el software
            usará para firmar los comprobantes de pago electrónicos.
          </Text>
        </TextContent>
        <br />
        <Tabs
          isFilled
          // activeKey={this.state.activeTabKey}
          // onSelect={this.handleTabClick}
        >
          <Tab eventKey={0} title="Certificados activos"></Tab>
          <Tab eventKey={1} title="Tab item 2"></Tab>
          <Tab eventKey={2} title="Tab item 3"></Tab>
        </Tabs>
      </PageSection>
      <PageSection>carlo</PageSection>
    </React.Fragment>
  );
};

export default ActiveKeysPage;
