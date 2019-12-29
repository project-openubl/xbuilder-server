import { AxiosError, AxiosResponse } from "axios";
import { Dispatch } from "redux";
import { ThunkAction } from "redux-thunk";
import { createAction } from "typesafe-actions";
import { OrganizationRepresentation } from "../../models/xml-builder";
import { getAll } from "../../api/organizations";
import { RootState } from "../rootReducer";

export const fetchAllOrganizationsRequest = createAction(
  "allOrganizations/fetch/request"
)();
export const fetchAllOrganizationsSuccess = createAction(
  "allOrganizations/fetch/success"
)<OrganizationRepresentation[]>();
export const fetchAllOrganizationsFailure = createAction(
  "allOrganizations/fetch/failure"
)<AxiosError>();

export const fetchAllOrganizations = (): ThunkAction<
  void,
  RootState,
  void,
  any
> => {
  return (dispatch: Dispatch) => {
    dispatch(fetchAllOrganizationsRequest());

    return getAll()
      .then((res: AxiosResponse<OrganizationRepresentation[]>) => {
        const data: OrganizationRepresentation[] = res.data;
        dispatch(fetchAllOrganizationsSuccess(data));
        return data;
      })
      .catch((err: AxiosError) => {
        dispatch(fetchAllOrganizationsFailure(err));
      });
  };
};
