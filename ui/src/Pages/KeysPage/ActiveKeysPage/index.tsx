import { connect } from 'react-redux';
import ActiveKeysPage from './ActiveKeysPage';

import { createMapStateToProps } from '../../../store/common';

const mapStateToProps = createMapStateToProps(state => ({
//   projects: organizationKeysSelectors.projects(state) || [],
//   error: organizationKeysSelectors.error(state),
//   status: organizationKeysSelectors.status(state)
}));

// const mapDispatchToProps = createMapDispatchToProps(() =>({
//   fetchMigrationProjects: migrationProjectListActions.fetchMigrationProjects,
//   showDeleteDialog: deleteDialogActions.openModal,
//   closeDeleteDialog: deleteDialogActions.closeModal
// }));
const mapDispatchToProps = {
//   fetchMigrationProjects: migrationProjectListActions.fetchMigrationProjects,
//   showDeleteDialog: deleteDialogActions.openModal,
//   closeDeleteDialog: deleteDialogActions.closeModal
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ActiveKeysPage);
