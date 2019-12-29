import BasicLayout from "./BasicLayout";
import { connect } from "react-redux";
import { createMapStateToProps } from "../../store/common";
import { allOrganizationsSelectors } from "../../store/allOrganizations";

const mapStateToProps = createMapStateToProps(state => ({
  allOrganizations: allOrganizationsSelectors.allOrganizations(state) || []
}));

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(BasicLayout);
