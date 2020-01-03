import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import { createMapStateToProps } from "../../store/common";
import {
  organizationContextSelectors
} from "../../store/organizationContext";
import OrganizationContextSelector from "./OrganizationContextSelector";

const mapStateToProps = createMapStateToProps(state => ({
  selectedOrganization: organizationContextSelectors.selectedOrganization(state) || null,
  organizations: organizationContextSelectors.organizations(state) || [],
  error: organizationContextSelectors.error(state),
  status: organizationContextSelectors.status(state)
}));

const mapDispatchToProps = {
  
};

export default withRouter(
  connect(mapStateToProps, mapDispatchToProps)(OrganizationContextSelector)
);
