import { AxiosError } from "axios";
import { ActionType, getType } from "typesafe-actions";
import { ComponentRepresentation } from "../../models/xml-builder";
import { FetchStatus } from "../common";
import {
  fetchComponentRequest,
  fetchComponentSuccess,
  fetchComponentFailure
} from "./actions";

export const stateKey = "component";

export type ComponentState = Readonly<{
  byId: Map<string, ComponentRepresentation>;
  errors: Map<string, AxiosError | undefined>;
  fetchStatus: Map<string, FetchStatus>;
}>;

export const defaultState: ComponentState = {
  byId: new Map(),
  errors: new Map(),
  fetchStatus: new Map()
};

export type ComponentAction = ActionType<
  | typeof fetchComponentRequest
  | typeof fetchComponentSuccess
  | typeof fetchComponentFailure
>;

export function componentReducer(
  state = defaultState,
  action: ComponentAction
): ComponentState {
  switch (action.type) {
    case getType(fetchComponentRequest):
      return {
        ...state,
        fetchStatus: new Map(state.fetchStatus).set(
          action.payload.componentId,
          "inProgress"
        )
      };
    case getType(fetchComponentSuccess):
      return {
        ...state,
        fetchStatus: new Map(state.fetchStatus).set(
          action.meta.componentId,
          "complete"
        ),
        byId: new Map(state.byId).set(action.meta.componentId, {
          ...action.payload
        }),
        errors: new Map(state.errors).set(action.meta.componentId, undefined)
      };
    case getType(fetchComponentFailure):
      return {
        ...state,
        fetchStatus: new Map(state.fetchStatus).set(
          action.meta.componentId,
          "complete"
        ),
        errors: new Map(state.errors).set(
          action.meta.componentId,
          action.payload
        )
      };
    default:
      return state;
  }
}
