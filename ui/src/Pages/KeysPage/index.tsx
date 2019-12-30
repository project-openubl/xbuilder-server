import KeysPage from "./KeysPage";

import { connect } from "react-redux";
import { createMapStateToProps } from "../../store/common";
import {
  organizationContextActions,
  organizationContextSelectors
} from "../../store/organizationContext";

const mapStateToProps = createMapStateToProps(state => ({
  organizations: organizationContextSelectors.organizations(state) || null
}));

const mapDispatchToProps = {
  selectOrganizationContext: organizationContextActions.selectOrganizationContext
};

export default connect(mapStateToProps, mapDispatchToProps)(KeysPage);
