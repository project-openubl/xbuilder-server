import { connect } from "react-redux";
import { withRouter } from "react-router-dom";
import ManageProviderModal from "./ManageProviderModal";
import { createMapStateToProps } from "../../store/common";
import { serverInfoSelectors, serverInfoActions } from "../../store/serverInfo";

const mapStateToProps = createMapStateToProps(state => ({
  serverInfo: serverInfoSelectors.selectServerInfo(state),
  serverInfoFetchStatus: serverInfoSelectors.selectServerInfoFetchStatus(state),
  serverInfoError: serverInfoSelectors.selectServerInfoError(state)
}));

const mapDispatchToProps = {
  fetchServerInfo: serverInfoActions.fetchServerInfo
};

export default withRouter(
  connect(mapStateToProps, mapDispatchToProps)(ManageProviderModal)
);
