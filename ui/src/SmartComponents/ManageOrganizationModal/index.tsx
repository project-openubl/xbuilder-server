import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import ManageOrganizationModal from "./ManageOrganizationModal";
import { createMapStateToProps } from "../../store/common";
import {
  organizationActions,
  organizationSelectors
} from "../../store/organization";

const mapStateToProps = createMapStateToProps((state, ownProps: any) => {
  return {
    organization: organizationSelectors.selectOrganization(
      state,
      ownProps.organizationId
    ),
    error: organizationSelectors.selectOrganizationError(
      state,
      ownProps.organizationId
    ),
    status: organizationSelectors.selectOrganizationFetchStatus(
      state,
      ownProps.organizationId
    )
  };
});

const mapDispatchToProps = {
  fetchOrganization: organizationActions.fetchOrganization,
  createOrganization: organizationActions.createOrganization,
  updateOrganization: organizationActions.updateOrganization
};

export default withRouter(
  connect(mapStateToProps, mapDispatchToProps)(ManageOrganizationModal)
);
