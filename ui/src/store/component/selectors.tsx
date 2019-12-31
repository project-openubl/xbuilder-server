import { RootState } from "../rootReducer";
import { stateKey } from "./reducer";

export const organizationComponentsState = (state: RootState) =>
  state[stateKey];

//

export const selectComponent = (state: RootState, componentId: string) =>
  organizationComponentsState(state).byId.get(componentId);
export const selectComponentFetchStatus = (
  state: RootState,
  componentId: string
) => organizationComponentsState(state).fetchStatus.get(componentId);
export const selectComponentError = (state: RootState, componentId: string) =>
  organizationComponentsState(state).errors.get(componentId);
