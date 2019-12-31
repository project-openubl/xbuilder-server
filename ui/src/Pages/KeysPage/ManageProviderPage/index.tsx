import { connect } from "react-redux";
import ManageProviderPage from "./ManageProviderPage";

import { createMapStateToProps } from "../../../store/common";
import { componentActions, componentSelectors } from "../../../store/component";

const mapStateToProps = createMapStateToProps((state, ownProps: any) => {
  const componentId = ownProps.match.params.componentId;
  return {
    component: componentSelectors.selectComponent(state, componentId),
    componentFetchStatus: componentSelectors.selectComponentFetchStatus(
      state,
      componentId
    ),
    componentError: componentSelectors.selectComponentError(state, componentId)
  };
});

const mapDispatchToProps = {
  fetchComponent: componentActions.fetchComponent
};

export default connect(mapStateToProps, mapDispatchToProps)(ManageProviderPage);
