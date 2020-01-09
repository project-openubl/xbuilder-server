import { connect } from "react-redux";
import KeyProvidersPage from "./KeyProvidersPage";

import { createMapStateToProps } from "../../../store/common";
import {
  organizationComponentsActions,
  organizationComponentsSelectors
} from "../../../store/organizationComponents";
import {
  serverInfoSelectors,
  serverInfoActions
} from "../../../store/serverInfo";
import { deleteDialogActions } from "../../../store/deleteDialog";
import { componentActions } from "../../../store/component";

const mapStateToProps = createMapStateToProps((state, ownProps: any) => {
  const organizationId = ownProps.match.params.organizationId;
  return {
    serverInfo: serverInfoSelectors.selectServerInfo(state),
    serverInfoFetchStatus: serverInfoSelectors.selectServerInfoFetchStatus(
      state
    ),
    serverInfoError: serverInfoSelectors.selectServerInfoError(state),

    organizationComponents: organizationComponentsSelectors.selectOrganizationComponents(
      state,
      organizationId
    ),
    organizationComponentsFetchStatus: organizationComponentsSelectors.selectOrganizationComponentsFetchStatus(
      state,
      organizationId
    ),
    organizationComponentsError: organizationComponentsSelectors.selectOrganizationComponentsError(
      state,
      organizationId
    )
  };
});

const mapDispatchToProps = {
  fetchServerInfo: serverInfoActions.fetchServerInfo,
  fetchOrganizationComponents:
    organizationComponentsActions.fetchOrganizationComponents,
  requestDeleteComponent: componentActions.requestDeleteComponent,
  showDeleteDialog: deleteDialogActions.openModal,
  closeDeleteDialog: deleteDialogActions.closeModal
};

export default connect(mapStateToProps, mapDispatchToProps)(KeyProvidersPage);
