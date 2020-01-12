import { AxiosError, AxiosResponse } from "axios";
import { Dispatch } from "redux";
import { createAsyncAction } from "typesafe-actions";
import {
  OrganizationRepresentation,
  SearchResultsRepresentation
} from "../../models/xml-builder";
import { search } from "../../api/organizations";
import { alertFetchEndpoint } from "../alert/actions";

export const {
  request: fetchOrganizationListRequest,
  success: fetchOrganizationListSuccess,
  failure: fetchOrganizationListFailure
} = createAsyncAction(
  "organizationList/fetch/request",
  "organizationList/fetch/success",
  "organizationList/fetch/failure"
)<void, SearchResultsRepresentation<OrganizationRepresentation>, AxiosError>();

export const fetchOrganizations = (
  filterText: string,
  page: number,
  pageSize: number
) => {
  return (dispatch: Dispatch) => {
    dispatch(fetchOrganizationListRequest());

    return search(filterText, page, pageSize)
      .then((res: AxiosResponse) => {
        dispatch(fetchOrganizationListSuccess(res.data));
      })
      .catch((err: AxiosError) => {
        dispatch(fetchOrganizationListFailure(err));
        alertFetchEndpoint(err)(dispatch);
      });
  };
};
