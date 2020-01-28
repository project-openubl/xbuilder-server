import { connect } from "react-redux";
import BajaDocumentPage from "./BajaDocumentPage";
import { createMapStateToProps } from "../../../store/common";

const mapStateToProps = createMapStateToProps(state => ({}));

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(BajaDocumentPage);
