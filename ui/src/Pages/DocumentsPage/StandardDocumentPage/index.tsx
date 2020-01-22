import { connect } from "react-redux";
import StandardDocumentPage from "./StandardDocumentPage";
import { documentActions } from "../../../store/document";

import { createMapStateToProps } from "../../../store/common";

const mapStateToProps = createMapStateToProps(state => ({}));

const mapDispatchToProps = {
  requestEnrichDocument: documentActions.requestEnrichDocument,
  requestCreateDocument: documentActions.requestCreateDocument
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StandardDocumentPage);
