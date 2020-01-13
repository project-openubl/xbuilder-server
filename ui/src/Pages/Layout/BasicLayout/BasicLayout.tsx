import React from "react";
import { NavLink } from "react-router-dom";
import {
  Page,
  PageSidebar,
  Nav,
  NavList,
  NavItem,
  NavVariants,
  PageHeader,
  Brand,
  Toolbar,
  ToolbarGroup,
  ToolbarItem,
  Button,
  ButtonVariant,
  SkipToContent,
  AboutModal,
  TextContent,
  TextList,
  TextListItem
} from "@patternfly/react-core";
import { css } from "@patternfly/react-styles";
import accessibleStyles from "@patternfly/react-styles/css/utilities/Accessibility/accessibility";
import { HelpIcon } from "@patternfly/react-icons";
import imgBrand from "../../../logo.png";
import { OrganizationRepresentation } from "../../../models/xml-builder";
import brandImg from "../../../logo.png";

interface Props {
  organizations: OrganizationRepresentation[];
  selectedOrganization: OrganizationRepresentation | null;
}

interface State {
  isAboutModalOpen: boolean;
}

class BasicLayout extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      isAboutModalOpen: false
    };
  }

  handleModalToggle = () => {
    this.setState(({ isAboutModalOpen }) => ({
      isAboutModalOpen: !isAboutModalOpen
    }));
  };

  renderHeader = () => {
    const { isAboutModalOpen } = this.state;

    const aboutModal = (
      <AboutModal
        isOpen={isAboutModalOpen}
        onClose={this.handleModalToggle}
        trademark="COPYRIGHT © 2020. PROJECT OPENUBL"
        brandImageSrc={brandImg}
        brandImageAlt="Patternfly Logo"
        productName="Project OpenUBL - XML Builder"
      >
        <TextContent>
          <TextList component="dl">
            <TextListItem component="dt">Version</TextListItem>
            <TextListItem component="dd">1.0.0.Final</TextListItem>
            <TextListItem component="dt">Source code</TextListItem>
            <TextListItem component="dd">
              <a
                href="https://github.com/project-openubl/xml-builder"
                target="_blank"
                rel="noopener noreferrer"
              >
                Github
              </a>
            </TextListItem>
            <TextListItem component="dt">Report issues</TextListItem>
            <TextListItem component="dd">
              <a
                href="https://github.com/project-openubl/xml-builder/issues"
                target="_blank"
                rel="noopener noreferrer"
              >
                Github issues
              </a>
            </TextListItem>
            <TextListItem component="dt">Documentation</TextListItem>
            <TextListItem component="dd">
              <a
                href="https://project-openubl.gitbook.io/xml-builder/"
                target="_blank"
                rel="noopener noreferrer"
              >
                Gitbook
              </a>
            </TextListItem>
            <TextListItem component="dt">Videos</TextListItem>
            <TextListItem component="dd">
              <a
                href="https://www.youtube.com/channel/UChq3xxjyDgjcU346rp0bbtA/"
                target="_blank"
                rel="noopener noreferrer"
              >
                Youtube
              </a>
            </TextListItem>
            <TextListItem component="dt">Rest API documentation</TextListItem>
            <TextListItem component="dd">
              <a
                href="https://app.swaggerhub.com/apis-docs/project-openubl/xml-builder"
                target="_blank"
                rel="noopener noreferrer"
              >
                Swaggerhub
              </a>
            </TextListItem>
            <TextListItem component="dt">Licence</TextListItem>
            <TextListItem component="dd">
              <a
                href="https://github.com/project-openubl/xml-builder/blob/master/LICENSE"
                target="_blank"
                rel="noopener noreferrer"
              >
                Eclipse Public License - v 2.0
              </a>
            </TextListItem>
          </TextList>
        </TextContent>
      </AboutModal>
    );

    const PageToolbar = (
      <React.Fragment>
        <Toolbar>
          <ToolbarGroup
            className={css(
              accessibleStyles.screenReader,
              accessibleStyles.visibleOnLg
            )}
          >
            <ToolbarItem>
              <Button
                id="simple-example-uid-01"
                aria-label="Notifications actions"
                variant={ButtonVariant.plain}
                onClick={this.handleModalToggle}
              >
                <HelpIcon />
              </Button>
            </ToolbarItem>
          </ToolbarGroup>
        </Toolbar>
        {aboutModal}
      </React.Fragment>
    );

    return (
      <PageHeader
        logo={
          <React.Fragment>
            <Brand src={imgBrand} alt="Project OpenUBL" />
            <span>PROJECT OPENUBL</span>
          </React.Fragment>
        }
        toolbar={PageToolbar}
        showNavToggle
      />
    );
  };

  renderSidebar = () => {
    const { organizations, selectedOrganization } = this.props;

    let org;
    if (selectedOrganization) {
      org = selectedOrganization;
    } else if (organizations.length > 0) {
      org = organizations[0];
    }

    const PageNav = (
      <Nav id="nav-primary-simple" aria-label="Nav" theme="dark">
        <NavList id="nav-list-simple" variant={NavVariants.default}>
          {/* <NavItem key="home">
            <NavLink to="/home" activeClassName="pf-m-current">
              Home
            </NavLink>
          </NavItem> */}
          <NavItem key="organizations">
            <NavLink to="/organizations/list" activeClassName="pf-m-current">
              Organizaciones
            </NavLink>
          </NavItem>

          {org && (
            <React.Fragment>
              <NavItem key="keys">
                <NavLink
                  to={`/organizations/manage/${org.id}/keys`}
                  activeClassName="pf-m-current"
                >
                  Certificados digitales
                </NavLink>
              </NavItem>
              <NavItem key="documents">
                <NavLink
                  to={`/organizations/documents/${org.id}/create`}
                  activeClassName="pf-m-current"
                >
                  Comprobantes electrónicos
                </NavLink>
              </NavItem>
            </React.Fragment>
          )}
        </NavList>
      </Nav>
    );

    return <PageSidebar nav={PageNav} theme="dark" />;
  };

  renderPageSkipToContent = () => {
    return (
      <SkipToContent href="#primary-app-container">
        Skip to Content
      </SkipToContent>
    );
  };

  render() {
    const { children } = this.props;

    return (
      <React.Fragment>
        <Page
          header={this.renderHeader()}
          sidebar={this.renderSidebar()}
          isManagedSidebar
          skipToContent={this.renderPageSkipToContent()}
        >
          {children}
        </Page>
      </React.Fragment>
    );
  }
}

export default BasicLayout;
