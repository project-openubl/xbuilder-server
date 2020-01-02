import { RootState } from "../rootReducer";
import { stateKey } from "./reducer";

export const organizationState = (state: RootState) => state[stateKey];

export const organizations = (state: RootState) => {
  return organizationState(state).organizations;
};

export const status = (state: RootState) => organizationState(state).status;
export const error = (state: RootState) => organizationState(state).error;
