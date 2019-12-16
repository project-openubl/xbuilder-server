import React from "react";
import {
  PageSection,
  PageSectionVariants,
  TextContent,
  Text,
  Tabs,
  Tab
} from "@patternfly/react-core";

const KeysPage: React.FC = ({children}) => {
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
          <Tab eventKey={1} title="Certificados inactivos"></Tab>
          <Tab eventKey={2} title="Proveeedores de certificados"></Tab>
        </Tabs>
        <div>
          {
            children
          }
        </div>
      </PageSection>
      <PageSection>carlo</PageSection>
    </React.Fragment>
  );
};

export default KeysPage;
