import React from "react";
import {
  PageSection,
  PageSectionVariants,
  TextContent,
  Text,
  Tabs,
  Tab,
  FormGroup
} from "@patternfly/react-core";
import OrganizationContextSelector from "../../SmartComponents/OrganizationContextSelector";

interface Props {
  match: any;
  history: any;
  location: any;
  activeKey: number;
}
const KeysPageTabs: React.FC<Props> = ({
  match,
  history,
  activeKey,
  children
}) => {
  const handleTabClick = (
    event: React.MouseEvent<HTMLElement, MouseEvent>,
    eventKey: number | string
  ) => {
    const organizationId = match.params.organizationId;
    const url = `/organizations/manage/${organizationId}`;
    if (eventKey === 0) {
      history.push(url + "/keys");
    } else if (eventKey === 1) {
      history.push(url + "/keys/list");
    } else if (eventKey === 2) {
      history.push(url + "/keys/providers");
    }
  };

  return (
    <React.Fragment>
      <PageSection variant={PageSectionVariants.light}>
        <div style={{ marginBottom: 20 }}>
          <div className="pf-c-form pf-m-horizontal">
            <FormGroup label="Organización" fieldId="organizacion">
              <OrganizationContextSelector />
            </FormGroup>
          </div>
        </div>

        <TextContent>
          <Text component="h1">Certificados digitales</Text>
          <Text component="small">
            Acá podrás configurar los certificados de seguridad que el software
            usará para firmar los comprobantes de pago electrónicos.
          </Text>
        </TextContent>
        <br />
        <Tabs isFilled activeKey={activeKey} onSelect={handleTabClick}>
          <Tab eventKey={0} title="Certificados activos"></Tab>
          <Tab eventKey={1} title="Todos los certificados"></Tab>
          <Tab eventKey={2} title="Proveeedores de certificados"></Tab>
        </Tabs>
      </PageSection>
      <PageSection>{children}</PageSection>
    </React.Fragment>
  );
};

export default KeysPageTabs;
