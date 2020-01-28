import { connect } from "react-redux";
import OrganizationListPage from "./OrganizationListPage";
import { createMapStateToProps } from "../../store/common";
import {
  organizationListSelectors,
  organizationListActions
} from "../../store/organizationList";
import { deleteDialogActions } from "../../store/deleteDialog";
import { organizationActions } from "../../store/organization";

const mapStateToProps = createMapStateToProps(state => ({
  organizations: organizationListSelectors.organizations(state),
  error: organizationListSelectors.error(state),
  fetchStatus: organizationListSelectors.status(state)
}));

const mapDispatchToProps = {
  fetchOrganizations: organizationListActions.fetchOrganizations,
  deleteOrganization: organizationActions.deleteOrganization,
  showDeleteDialog: deleteDialogActions.openModal,
  closeDeleteDialog: deleteDialogActions.closeModal
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrganizationListPage);
