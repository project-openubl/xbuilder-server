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
  SkipToContent
} from "@patternfly/react-core";
import { css } from "@patternfly/react-styles";
import accessibleStyles from "@patternfly/react-styles/css/utilities/Accessibility/accessibility";
import { HelpIcon } from "@patternfly/react-icons";
import imgBrand from "../../../logo.png";
import { OrganizationRepresentation } from "../../../models/xml-builder";

interface Props {
  allOrganizations: OrganizationRepresentation[];
}

interface State {}

class BasicLayout extends React.Component<Props, State> {
  renderHeader = () => {
    const PageToolbar = (
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
            >
              <HelpIcon />
            </Button>
          </ToolbarItem>
        </ToolbarGroup>
      </Toolbar>
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
    const { allOrganizations } = this.props;

    const PageNav = (
      <Nav id="nav-primary-simple" aria-label="Nav" theme="dark">
        <NavList id="nav-list-simple" variant={NavVariants.default}>
          <NavItem key="home">
            <NavLink to="/home" activeClassName="pf-m-current">
              Home
            </NavLink>
          </NavItem>
          <NavItem key="organizations">
            <NavLink to="/organizations/list" activeClassName="pf-m-current">
              Organizaciones
            </NavLink>
          </NavItem>
          {allOrganizations && allOrganizations.length > 0 && (
            <NavItem key="keys">
              <NavLink
                to={`/organizations/manage/${allOrganizations[0].id}/keys`}
                activeClassName="pf-m-current"
              >
                Certificados digitales
              </NavLink>
            </NavItem>
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
