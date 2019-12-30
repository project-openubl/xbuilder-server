import { RootState } from "../rootReducer";
import { stateKey } from "./reducer";

export const organizationsState = (state: RootState) => state[stateKey];

export const organizations = (state: RootState) => {
  const srcs = organizationsState(state).organizations;
  if (srcs) {
    return srcs;
  }
  return [];
};

export const status = (state: RootState) => organizationsState(state).status;
export const error = (state: RootState) => organizationsState(state).error;

//

export const selectedOrganization = (state: RootState) =>
  organizationsState(state).selected;
