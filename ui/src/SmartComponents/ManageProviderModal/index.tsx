import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import ManageProviderModal from "./ManageProviderModal";
import { createMapStateToProps } from "../../store/common";

const mapStateToProps = createMapStateToProps(state => ({}));

const mapDispatchToProps = {};

export default withRouter(
  connect(mapStateToProps, mapDispatchToProps)(ManageProviderModal)
);
