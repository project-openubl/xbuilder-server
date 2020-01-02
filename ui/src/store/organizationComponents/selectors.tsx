import { RootState } from "../rootReducer";
import { stateKey } from "./reducer";

export const organizationComponentsState = (state: RootState) =>
  state[stateKey];

// Fetch migration project

export const selectOrganizationComponents = (
  state: RootState,
  organizationId: string
) => organizationComponentsState(state).byOrganizationId.get(organizationId) || [];
export const selectOrganizationComponentsFetchStatus = (
  state: RootState,
  organizationId: string
) => organizationComponentsState(state).fetchStatus.get(organizationId);
export const selectOrganizationComponentsError = (
  state: RootState,
  organizationId: string
) => organizationComponentsState(state).errors.get(organizationId);
