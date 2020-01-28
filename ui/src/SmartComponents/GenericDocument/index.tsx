import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import GenericDocument from "./GenericDocument";
import { documentActions } from "../../store/document";
import { createMapStateToProps } from "../../store/common";

const mapStateToProps = createMapStateToProps(state => ({}));

const mapDispatchToProps = {
  requestEnrichDocument: documentActions.requestEnrichDocument,
  requestCreateDocument: documentActions.requestCreateDocument
};

export default withRouter(
  connect(mapStateToProps, mapDispatchToProps)(GenericDocument)
);
