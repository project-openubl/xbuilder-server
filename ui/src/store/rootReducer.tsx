import { combineReducers } from "redux";
import { StateType } from "typesafe-actions";

import {
  organizationListStateKey,
  organizationListReducer
} from "./organizationList";
import {
  organizationStateKey,
  organizationReducer
} from "./organization";
import {
  organizationKeysStateKey,
  organizationKeysReducer
} from "./organizationKeys";

export type RootState = StateType<typeof rootReducer>;

export const rootReducer = combineReducers({
  [organizationListStateKey]: organizationListReducer,
  [organizationStateKey]: organizationReducer,
  [organizationKeysStateKey]: organizationKeysReducer
});
