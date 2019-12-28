import { RootState } from "../rootReducer";
import { stateKey } from "./reducer";

export const organizationKeysState = (state: RootState) => state[stateKey];

// Fetch migration project

export const selectOrganizationKey = (
  state: RootState,
  organizationId: string
) => organizationKeysState(state).byOrganizationId.get(organizationId);
export const selectMigrationProjectFetchStatus = (
  state: RootState,
  organizationId: string
) => organizationKeysState(state).fetchStatus.get(organizationId);
export const selectMigrationProjectError = (
  state: RootState,
  organizationId: string
) => organizationKeysState(state).errors.get(organizationId);
