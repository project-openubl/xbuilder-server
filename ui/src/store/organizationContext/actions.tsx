import { AxiosError, AxiosResponse } from "axios";
import { Dispatch } from "redux";
import { ThunkAction } from "redux-thunk";
import { createAction } from "typesafe-actions";
import { OrganizationRepresentation } from "../../models/xml-builder";
import { getAll } from "../../api/organizations";
import { RootState } from "../rootReducer";

export const fetchOrganizationsRequest = createAction(
  "organizationContext/organizations/fetch/request"
)();
export const fetchOrganizationsSuccess = createAction(
  "organizationContext/organizations/fetch/success"
)<OrganizationRepresentation[]>();
export const fetchOrganizationsFailure = createAction(
  "organizationContext/organizations/fetch/failure"
)<AxiosError>();

export const selectOrganizationContext = createAction(
  "organizationContext/organizations/select"
)<OrganizationRepresentation>();

export const fetchOrganizations = (): ThunkAction<
  void,
  RootState,
  void,
  any
> => {
  return (dispatch: Dispatch) => {
    dispatch(fetchOrganizationsRequest());

    return getAll()
      .then((res: AxiosResponse<OrganizationRepresentation[]>) => {
        const data: OrganizationRepresentation[] = res.data;
        dispatch(fetchOrganizationsSuccess(data));
        return data;
      })
      .catch((err: AxiosError) => {
        dispatch(fetchOrganizationsFailure(err));
      });
  };
};
