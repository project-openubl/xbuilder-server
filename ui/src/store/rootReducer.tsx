import { combineReducers } from "redux";
import { StateType } from "typesafe-actions";

import { deleteDialogStateKey, deleteDialogReducer } from "./deleteDialog";
import {
  organizationListStateKey,
  organizationListReducer
} from "./organizationList";
import {
  organizationContextReducer,
  organizationContextStateKey
} from "./organizationContext";
import { organizationStateKey, organizationReducer } from "./organization";
import {
  organizationKeysStateKey,
  organizationKeysReducer
} from "./organizationKeys";
import {
  organizationComponentsStateKey,
  organizationComponentsReducer
} from "./organizationComponents";
import { componentStateKey, componentReducer } from "./component";
import { serverInfoStateKey, serverInfoReducer } from "./serverInfo";
const frontendComponentsNotifications = require("@redhat-cloud-services/frontend-components-notifications");

export type RootState = StateType<typeof rootReducer>;

export const rootReducer = combineReducers({
  notifications: frontendComponentsNotifications.notifications,
  [deleteDialogStateKey]: deleteDialogReducer,
  [organizationListStateKey]: organizationListReducer,
  [organizationContextStateKey]: organizationContextReducer,
  [organizationStateKey]: organizationReducer,
  [organizationKeysStateKey]: organizationKeysReducer,
  [organizationComponentsStateKey]: organizationComponentsReducer,
  [componentStateKey]: componentReducer,
  [serverInfoStateKey]: serverInfoReducer
});
