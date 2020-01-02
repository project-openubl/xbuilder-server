import { AxiosError } from "axios";
import { ActionType, getType } from "typesafe-actions";
import { OrganizationRepresentation } from "../../models/xml-builder";
import { FetchStatus } from "../common";
import {
  fetchOrganizationsRequest,
  fetchOrganizationsSuccess,
  fetchOrganizationsFailure,
  selectOrganizationContext
} from "./actions";

export const stateKey = "organizationContext";

export type OrganizationContextState = Readonly<{
  selected: OrganizationRepresentation | null;
  organizations: OrganizationRepresentation[] | null;
  error: AxiosError<any> | null;
  status: FetchStatus;
}>;

export const defaultState: OrganizationContextState = {
  selected: null,
  organizations: null,
  error: null,
  status: "none"
};

export type OrganizationContextAction = ActionType<
  | typeof fetchOrganizationsRequest
  | typeof fetchOrganizationsSuccess
  | typeof fetchOrganizationsFailure
  | typeof selectOrganizationContext
>;

export function organizationContextReducer(
  state = defaultState,
  action: OrganizationContextAction
): OrganizationContextState {
  switch (action.type) {
    case getType(fetchOrganizationsRequest):
      return {
        ...state,
        status: "inProgress"
      };
    case getType(fetchOrganizationsSuccess):
      return {
        ...state,
        status: "complete",
        error: null,
        organizations: action.payload
      };
    case getType(fetchOrganizationsFailure):
      return {
        ...state,
        status: "complete",
        error: action.payload
      };

    case getType(selectOrganizationContext):
      return {
        ...state,
        selected: action.payload
      };

    default:
      return state;
  }
}
