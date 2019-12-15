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
import { IAppRoute } from "../../Routes";
import imgBrand from "../../logo.png";

interface Props {
  sidebar: IAppRoute[];
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
    const { sidebar } = this.props;

    const PageNav = (
      <Nav id="nav-primary-simple" aria-label="Nav" theme="dark">
        <NavList id="nav-list-simple" variant={NavVariants.default}>
          {sidebar.map(
            (route: IAppRoute, idx: number) =>
              route.sidebarLabel && (
                <NavItem
                  key={`${route.sidebarLabel}-${idx}`}
                  id={`${route.sidebarLabel}-${idx}`}
                >
                  <NavLink exact to={route.path} activeClassName="pf-m-current">
                    {route.sidebarLabel}
                  </NavLink>
                </NavItem>
              )
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
