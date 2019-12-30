import { connect } from "react-redux";
import { createMapStateToProps } from "../../store/common";
import { allOrganizationsSelectors } from "../../store/allOrganizations";
import OrganizationContextSelector from "./OrganizationContextSelector";
import { withRouter } from "react-router-dom";

const mapStateToProps = createMapStateToProps(state => ({
  organizations: allOrganizationsSelectors.allOrganizations(state) || [],
  error: allOrganizationsSelectors.error(state),
  status: allOrganizationsSelectors.status(state)
}));

const mapDispatchToProps = {};

export default withRouter(
  connect(mapStateToProps, mapDispatchToProps)(OrganizationContextSelector)
);
