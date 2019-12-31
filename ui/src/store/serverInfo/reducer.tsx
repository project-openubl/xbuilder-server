import { AxiosError } from "axios";
import { ActionType, getType } from "typesafe-actions";
import { ServerInfoRepresentation } from "../../models/xml-builder";
import { FetchStatus } from "../common";
import {
  fetchServerInfoRequest,
  fetchServerInfoSuccess,
  fetchServerInfoFailure
} from "./actions";

export const stateKey = "serverInfo";

export type ServerInfoState = Readonly<{
  serverInfo: ServerInfoRepresentation | undefined;
  error: AxiosError<any> | undefined;
  status: FetchStatus;
}>;

export const defaultState: ServerInfoState = {
  serverInfo: undefined,
  error: undefined,
  status: "none"
};

export type OrganizationKeysAction = ActionType<
  | typeof fetchServerInfoRequest
  | typeof fetchServerInfoSuccess
  | typeof fetchServerInfoFailure
>;

export function serverInfoReducer(
  state = defaultState,
  action: OrganizationKeysAction
): ServerInfoState {
  switch (action.type) {
    case getType(fetchServerInfoRequest):
      return {
        ...state,
        status: "inProgress"
      };
    case getType(fetchServerInfoSuccess):
      return {
        ...state,
        status: "complete",
        error: undefined,
        serverInfo: action.payload
      };
    case getType(fetchServerInfoFailure):
      return {
        ...state,
        status: "complete",
        error: action.payload
      };
    default:
      return state;
  }
}
