import { connect } from "react-redux";
import KeyListPage from "./KeyListPage";

import { createMapStateToProps } from "../../../store/common";
import {
  organizationKeysActions,
  organizationKeysSelectors
} from "../../../store/organizationKeys";
import {
  organizationComponentsActions,
  organizationComponentsSelectors
} from "../../../store/organizationComponents";

const mapStateToProps = createMapStateToProps((state, ownProps: any) => {
  const organizationId = ownProps.match.params.organizationId;
  return {
    organizationKeys: organizationKeysSelectors.selectOrganizationKeys(
      state,
      organizationId
    ),
    organizationKeysFetchStatus: organizationKeysSelectors.selectOrganizationKeysFetchStatus(
      state,
      organizationId
    ),
    organizationKeysError: organizationKeysSelectors.selectOrganizationKeysError(
      state,
      organizationId
    ),

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
  fetchOrganizationKeys: organizationKeysActions.fetchOrganizationKeys,
  fetchOrganizationComponents:
    organizationComponentsActions.fetchOrganizationComponents
};

export default connect(mapStateToProps, mapDispatchToProps)(KeyListPage);
