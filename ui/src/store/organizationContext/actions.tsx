import { AxiosError, AxiosResponse } from "axios";
import { Dispatch } from "redux";
import { createAction } from "typesafe-actions";
import { OrganizationRepresentation } from "../../models/xml-builder";
import { getAll } from "../../api/organizations";
import { alertFetchEndpoint } from "../alert/actions";

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

export const fetchOrganizations = () => {
  return (dispatch: Dispatch) => {
    dispatch(fetchOrganizationsRequest());

    return getAll()
      .then((res: AxiosResponse<OrganizationRepresentation[]>) => {
        dispatch(fetchOrganizationsSuccess(res.data));
      })
      .catch((err: AxiosError) => {
        dispatch(fetchOrganizationsFailure(err));
        alertFetchEndpoint(err)(dispatch);
      });
  };
};
