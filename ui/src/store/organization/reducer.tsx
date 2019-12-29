import { AxiosError } from "axios";
import { ActionType, getType } from "typesafe-actions";
import { OrganizationRepresentation } from "../../models/xml-builder";
import { FetchStatus } from "../common";
import {
  fetchOrganizationRequest,
  fetchOrganizationSuccess,
  fetchOrganizationFailure
} from "./actions";

export const stateKey = "organization";

export type OrganizationState = Readonly<{
  byId: Map<string, OrganizationRepresentation>;
  errors: Map<string, AxiosError | undefined>;
  fetchStatus: Map<string, FetchStatus>;
}>;

export const defaultState: OrganizationState = {
  byId: new Map(),
  errors: new Map(),
  fetchStatus: new Map()
};

export type OrganizationAction = ActionType<
  | typeof fetchOrganizationRequest
  | typeof fetchOrganizationSuccess
  | typeof fetchOrganizationFailure
>;

export function organizationReducer(
  state = defaultState,
  action: OrganizationAction
): OrganizationState {
  switch (action.type) {
    case getType(fetchOrganizationRequest):
      return {
        ...state,
        fetchStatus: new Map(state.fetchStatus).set(
          action.payload.organizationId,
          "inProgress"
        )
      };
    case getType(fetchOrganizationSuccess):
      return {
        ...state,
        fetchStatus: new Map(state.fetchStatus).set(
          action.meta.organizationId,
          "complete"
        ),
        byId: new Map(state.byId).set(action.meta.organizationId, {
          ...action.payload
        }),
        errors: new Map(state.errors).set(action.meta.organizationId, undefined)
      };
    case getType(fetchOrganizationFailure):
      return {
        ...state,
        fetchStatus: new Map(state.fetchStatus).set(
          action.meta.organizationId,
          "complete"
        ),
        errors: new Map(state.errors).set(
          action.meta.organizationId,
          action.payload
        )
      };
    default:
      return state;
  }
}
