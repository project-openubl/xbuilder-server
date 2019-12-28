import { RootState } from '../rootReducer';
import { stateKey } from './reducer';

export const organizationState = (state: RootState) => state[stateKey];

export const organizations = (state: RootState) => {
  const srcs = organizationState(state).organizations;
  if (srcs) {
    return srcs;
  }
  return [];
};

export const status = (state: RootState) => organizationState(state).status;
export const error = (state: RootState) => organizationState(state).error;
