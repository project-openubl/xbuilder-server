import React from "react";
import {
  PageSection,
  PageSectionVariants,
  TextContent,
  Text,
  Tabs,
  Tab,
  Form,
  FormGroup} from "@patternfly/react-core";
import OrganizationContextSelector from "../../SmartComponents/OrganizationContextSelector";

interface Props {
  match: any;
  history: any;
  location: any;
  activeKey: number;
}
const KeysPageTabs: React.FC<Props> = ({ history, activeKey, children }) => {
  const handleTabClick = (
    event: React.MouseEvent<HTMLElement, MouseEvent>,
    eventKey: number | string
  ) => {
    if (eventKey === 0) {
      history.push("/keys");
    } else if (eventKey === 1) {
      history.push("/keys/list");
    } else if (eventKey === 2) {
      history.push("/keys/providers");
    }
  };

  return (
    <React.Fragment>
      <PageSection variant={PageSectionVariants.light}>
        <div style={{ marginBottom: 20 }}>
          <Form isHorizontal>
            <FormGroup label="Organización" fieldId="organizacion">
              <OrganizationContextSelector />
            </FormGroup>
          </Form>
        </div>

        <TextContent>
          <Text component="h1">Administrar certificados</Text>
          <Text component="p">
            Acá podrás configurar los certificados de seguridad que el software
            usará para firmar los comprobantes de pago electrónicos.
          </Text>
        </TextContent>
        <br />
        <Tabs isFilled activeKey={activeKey} onSelect={handleTabClick}>
          <Tab eventKey={0} title="Certificados activos"></Tab>
          <Tab eventKey={1} title="Certificados inactivos"></Tab>
          <Tab eventKey={2} title="Proveeedores de certificados"></Tab>
        </Tabs>
      </PageSection>
      <PageSection>{children}</PageSection>
    </React.Fragment>
  );
};

export default KeysPageTabs;
