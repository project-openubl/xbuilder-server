import { AxiosError, AxiosResponse } from "axios";
import { Dispatch } from "redux";
import { ThunkAction } from "redux-thunk";
import { createAction } from "typesafe-actions";
import { OrganizationRepresentation, SearchResultsRepresentation } from "../../models/xml-builder";
import { search } from "../../api/organizations";
import { RootState } from "../rootReducer";

export const fetchOrganizationListRequest = createAction(
  "organizationList/fetch/request"
)();
export const fetchOrganizationListSuccess = createAction(
  "organizationList/fetch/success"
)<SearchResultsRepresentation<OrganizationRepresentation>>();
export const fetchOrganizationListFailure = createAction(
  "organizationList/fetch/failure"
)<AxiosError>();

export const fetchOrganizations = (
  filterText: string,
  page: number,
  pageSize: number
): ThunkAction<void, RootState, void, any> => {
  return (dispatch: Dispatch) => {
    dispatch(fetchOrganizationListRequest());

    return search(filterText, page, pageSize)
      .then((res: AxiosResponse<SearchResultsRepresentation<OrganizationRepresentation>>) => {
        const data: SearchResultsRepresentation<OrganizationRepresentation> = res.data;
        dispatch(fetchOrganizationListSuccess(data));
        return data;
      })
      .catch((err: AxiosError) => {
        dispatch(fetchOrganizationListFailure(err));
      });
  };
};


