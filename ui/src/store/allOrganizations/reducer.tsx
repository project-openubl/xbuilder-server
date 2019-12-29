import { AxiosError } from "axios";
import { ActionType, getType } from "typesafe-actions";
import { OrganizationRepresentation } from "../../models/xml-builder";
import { FetchStatus } from "../common";
import {
  fetchAllOrganizationsRequest,
  fetchAllOrganizationsSuccess,
  fetchAllOrganizationsFailure
} from "./actions";

export const stateKey = "allOrganizations";

export type AllOrganizationsState = Readonly<{
  organizations: OrganizationRepresentation[] | null;
  error: AxiosError<any> | null;
  status: FetchStatus;
}>;

export const defaultState: AllOrganizationsState = {
  organizations: null,
  error: null,
  status: "none"
};

export type AllOrganizationsAction = ActionType<
  | typeof fetchAllOrganizationsRequest
  | typeof fetchAllOrganizationsSuccess
  | typeof fetchAllOrganizationsFailure
>;

export function allOrganizationsReducer(
  state = defaultState,
  action: AllOrganizationsAction
): AllOrganizationsState {
  switch (action.type) {
    case getType(fetchAllOrganizationsRequest):
      return {
        ...state,
        status: "inProgress"
      };
    case getType(fetchAllOrganizationsSuccess):
      return {
        ...state,
        status: "complete",
        error: null,
        organizations: action.payload
      };
    case getType(fetchAllOrganizationsFailure):
      return {
        ...state,
        status: "complete",
        error: action.payload
      };
    default:
      return state;
  }
}
