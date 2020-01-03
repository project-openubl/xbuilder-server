import { AxiosError } from "axios";
import { ActionType, getType } from "typesafe-actions";
import { KeysMetadataRepresentation } from "../../models/xml-builder";
import { FetchStatus } from "../common";
import {
  fetchOrganizationKeysRequest,
  fetchOrganizationKeysSuccess,
  fetchOrganizationKeysFailure
} from "./actions";

export const stateKey = "organizationKeys";

export type OrganizationKeyState = Readonly<{
  byOrganizationId: Map<string, KeysMetadataRepresentation>;
  errors: Map<string, AxiosError | undefined>;
  fetchStatus: Map<string, FetchStatus>;
}>;

export const defaultState: OrganizationKeyState = {
  byOrganizationId: new Map(),
  errors: new Map(),
  fetchStatus: new Map()
};

export type OrganizationKeysAction = ActionType<
  | typeof fetchOrganizationKeysRequest
  | typeof fetchOrganizationKeysSuccess
  | typeof fetchOrganizationKeysFailure
>;

export function organizationKeysReducer(
  state = defaultState,
  action: OrganizationKeysAction
): OrganizationKeyState {
  switch (action.type) {
    case getType(fetchOrganizationKeysRequest):
      return {
        ...state,
        fetchStatus: new Map(state.fetchStatus).set(
          action.payload.organizationId,
          "inProgress"
        )
      };
    case getType(fetchOrganizationKeysSuccess):
      return {
        ...state,
        fetchStatus: new Map(state.fetchStatus).set(
          action.meta.organizationId,
          "complete"
        ),
        byOrganizationId: new Map(state.byOrganizationId).set(
          action.meta.organizationId,
          {
            ...action.payload
          }
        ),
        errors: new Map(state.errors).set(action.meta.organizationId, undefined)
      };
    case getType(fetchOrganizationKeysFailure):
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
