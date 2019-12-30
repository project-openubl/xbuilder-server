import { RootState } from "../rootReducer";
import { stateKey } from "./reducer";

export const organizationKeysState = (state: RootState) => state[stateKey];

// Fetch migration project

export const selectOrganizationKeys = (
  state: RootState,
  organizationId: string
) => organizationKeysState(state).byOrganizationId.get(organizationId);
export const selectOrganizationKeysFetchStatus = (
  state: RootState,
  organizationId: string
) => organizationKeysState(state).fetchStatus.get(organizationId);
export const selectOrganizationKeysError = (
  state: RootState,
  organizationId: string
) => organizationKeysState(state).errors.get(organizationId);
