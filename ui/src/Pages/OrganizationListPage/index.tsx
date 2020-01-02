import { connect } from "react-redux";
import OrganizationListPage from "./OrganizationListPage";
import { createMapStateToProps } from "../../store/common";
import {
  organizationListSelectors,
  organizationListActions
} from "../../store/organizationList";

const mapStateToProps = createMapStateToProps(state => ({
  organizations: organizationListSelectors.organizations(state),
  error: organizationListSelectors.error(state),
  fetchStatus: organizationListSelectors.status(state)
}));

const mapDispatchToProps = {
  fetchOrganizations: organizationListActions.fetchOrganizations
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrganizationListPage);
