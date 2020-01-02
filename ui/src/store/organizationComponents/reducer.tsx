import { AxiosError } from "axios";
import { ActionType, getType } from "typesafe-actions";
import { ComponentRepresentation } from "../../models/xml-builder";
import { FetchStatus } from "../common";
import {
  fetchOrganizationComponentsRequest,
  fetchOrganizationComponentsSuccess,
  fetchOrganizationComponentsFailure
} from "./actions";

export const stateKey = "organizationComponents";

export type OrganizationComponentsState = Readonly<{
  byOrganizationId: Map<string, ComponentRepresentation[]>;
  errors: Map<string, AxiosError | undefined>;
  fetchStatus: Map<string, FetchStatus>;
}>;

export const defaultState: OrganizationComponentsState = {
  byOrganizationId: new Map(),
  errors: new Map(),
  fetchStatus: new Map()
};

export type OrganizationComponentsAction = ActionType<
  | typeof fetchOrganizationComponentsRequest
  | typeof fetchOrganizationComponentsSuccess
  | typeof fetchOrganizationComponentsFailure
>;

export function organizationComponentsReducer(
  state = defaultState,
  action: OrganizationComponentsAction
): OrganizationComponentsState {
  switch (action.type) {
    case getType(fetchOrganizationComponentsRequest):
      return {
        ...state,
        fetchStatus: new Map(state.fetchStatus).set(
          action.payload.organizationId,
          "inProgress"
        )
      };
    case getType(fetchOrganizationComponentsSuccess):
      return {
        ...state,
        fetchStatus: new Map(state.fetchStatus).set(
          action.meta.organizationId,
          "complete"
        ),
        byOrganizationId: new Map(state.byOrganizationId).set(
          action.meta.organizationId,
          [
            ...action.payload
          ]
        ),
        errors: new Map(state.errors).set(action.meta.organizationId, undefined)
      };
    case getType(fetchOrganizationComponentsFailure):
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
