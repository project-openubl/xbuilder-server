import { combineReducers } from "redux";
import { StateType } from "typesafe-actions";

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

export type RootState = StateType<typeof rootReducer>;

export const rootReducer = combineReducers({
  [organizationListStateKey]: organizationListReducer,
  [organizationContextStateKey]: organizationContextReducer,
  [organizationStateKey]: organizationReducer,
  [organizationKeysStateKey]: organizationKeysReducer,
  [organizationComponentsStateKey]: organizationComponentsReducer,
  [componentStateKey]: componentReducer,
  [serverInfoStateKey]: serverInfoReducer
});
