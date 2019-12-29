import { combineReducers } from "redux";
import { StateType } from "typesafe-actions";

import {
  organizationListStateKey,
  organizationListReducer
} from "./organizationList";
import {
  allOrganizationsStateKey,
  allOrganizationsReducer
} from "./allOrganizations";
import { organizationStateKey, organizationReducer } from "./organization";
import {
  organizationKeysStateKey,
  organizationKeysReducer
} from "./organizationKeys";

export type RootState = StateType<typeof rootReducer>;

export const rootReducer = combineReducers({
  [organizationListStateKey]: organizationListReducer,
  [allOrganizationsStateKey]: allOrganizationsReducer,
  [organizationStateKey]: organizationReducer,
  [organizationKeysStateKey]: organizationKeysReducer
});
