import { connect } from "react-redux";
import SunatDocumentPage from "./SunatDocumentPage";

import { createMapStateToProps } from "../../../store/common";

const mapStateToProps = createMapStateToProps(() => ({}));

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(SunatDocumentPage);
