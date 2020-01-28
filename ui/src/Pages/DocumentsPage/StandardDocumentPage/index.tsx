import { connect } from "react-redux";
import StandardDocumentPage from "./StandardDocumentPage";
import { createMapStateToProps } from "../../../store/common";

const mapStateToProps = createMapStateToProps(state => ({}));

const mapDispatchToProps = {};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StandardDocumentPage);
