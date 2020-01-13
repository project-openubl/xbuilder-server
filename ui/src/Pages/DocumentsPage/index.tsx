import DocumentsPage from "./DocumentsPage";

import { connect } from "react-redux";
import { createMapStateToProps } from "../../store/common";
import {
  organizationContextActions,
  organizationContextSelectors
} from "../../store/organizationContext";

const mapStateToProps = createMapStateToProps(state => ({
  contextOrganizations: organizationContextSelectors.organizations(state)
}));

const mapDispatchToProps = {
  selectOrganizationContext: organizationContextActions.selectOrganizationContext
};

export default connect(mapStateToProps, mapDispatchToProps)(DocumentsPage);
