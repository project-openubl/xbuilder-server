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
import { XmlBuilderRouterProps } from "../../models/routerProps";
import { OrganizationRepresentation } from "../../models/xml-builder";

interface Props extends XmlBuilderRouterProps {
  activeKey: number;
}

const DocumentsPageTabs: React.FC<Props> = ({
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
    const url = `/organizations/documents/${organizationId}/create`;
    if (eventKey === 0) {
      history.push(url + "/");
    } else if (eventKey === 1) {
      history.push(url + "/baja");
    }
  };

  const onOrganizationContextSelect = (
    organization: OrganizationRepresentation
  ) => {
    history.push(`/organizations/documents/${organization.id}/create`);
  };

  return (
    <React.Fragment>
      <PageSection variant={PageSectionVariants.light}>
        <div style={{ marginBottom: 20 }}>
          <div className="pf-c-form pf-m-horizontal">
            <FormGroup label="Organización" fieldId="organizacion">
              <OrganizationContextSelector
                onSelect={onOrganizationContextSelect}
              />
            </FormGroup>
          </div>
        </div>

        <TextContent>
          <Text component="h1">Comprobantes electrónicos</Text>
          <Text component="small">
            Acá podras simular la creación de comprobantes electrónicos.
          </Text>
        </TextContent>
        <br />
        <Tabs isFilled activeKey={activeKey} onSelect={handleTabClick}>
          <Tab
            eventKey={0}
            title="Documentos básicos (boleta, factura, nota crédito/débito)"
          ></Tab>
          <Tab eventKey={1} title="Baja"></Tab>
        </Tabs>
      </PageSection>
      <PageSection>{children}</PageSection>
    </React.Fragment>
  );
};

export default DocumentsPageTabs;
