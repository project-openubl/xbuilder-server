import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import ManageProviderModal from "./ManageProviderModal";
import { createMapStateToProps } from "../../store/common";
import { componentActions } from "../../store/component";

const mapStateToProps = createMapStateToProps(state => ({}));

const mapDispatchToProps = {
  requestCreateComponent: componentActions.requestCreateComponent,
  requestUpdateComponent: componentActions.requestUpdateComponent
};

export default withRouter(
  connect(mapStateToProps, mapDispatchToProps)(ManageProviderModal)
);
