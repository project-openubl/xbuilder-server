import { RootState } from "../rootReducer";
import { stateKey } from "./reducer";

export const allOrganizationsState = (state: RootState) => state[stateKey];

export const allOrganizations = (state: RootState) => {
  const srcs = allOrganizationsState(state).organizations;
  if (srcs) {
    return srcs;
  }
  return [];
};

export const status = (state: RootState) => allOrganizationsState(state).status;
export const error = (state: RootState) => allOrganizationsState(state).error;
