import OrganizationForm from "./OrganizationForm";

import { connect } from "react-redux";
import { createMapStateToProps } from "../../store/common";
import { organizationActions } from "../../store/organization";

const mapStateToProps = createMapStateToProps(state => ({}));

const mapDispatchToProps = {
  fetchOrganizationIdByName: organizationActions.fetchOrganizationIdByName
};

export default connect(mapStateToProps, mapDispatchToProps)(OrganizationForm);
