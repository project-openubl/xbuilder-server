import { connect } from "react-redux";
import { createMapStateToProps } from "../../store/common";
import {
  allOrganizationsSelectors,
  allOrganizationsActions
} from "../../store/allOrganizations";
import AllOrganizations from "./AllOrganizations";

const mapStateToProps = createMapStateToProps(state => ({
  organizations: allOrganizationsSelectors.allOrganizations(state) || [],
  error: allOrganizationsSelectors.error(state),
  status: allOrganizationsSelectors.status(state)
}));

const mapDispatchToProps = {
  fetchAllOrganizations: allOrganizationsActions.fetchAllOrganizations
};

export default connect(mapStateToProps, mapDispatchToProps)(AllOrganizations);
