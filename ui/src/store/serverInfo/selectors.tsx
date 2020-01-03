import { RootState } from "../rootReducer";
import { stateKey } from "./reducer";

export const serverInfoState = (state: RootState) => state[stateKey];

export const selectServerInfo = (state: RootState) =>
  serverInfoState(state).serverInfo;
export const selectServerInfoFetchStatus = (state: RootState) =>
  serverInfoState(state).status;
export const selectServerInfoError = (state: RootState) =>
  serverInfoState(state).error;
