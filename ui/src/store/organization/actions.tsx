import { AxiosError, AxiosResponse } from "axios";
import { Dispatch } from "redux";
import { ThunkAction } from "redux-thunk";
import { createAction } from "typesafe-actions";
import { OrganizationRepresentation } from "../../models/xml-builder";
import { getById, create, update } from "../../api/organizations";
import { RootState } from "../rootReducer";

interface OrganizationActionMeta {
  organizationId: string;
}

export const fetchOrganizationRequest = createAction(
  "organization/fetch/request"
)<OrganizationActionMeta>();
export const fetchOrganizationSuccess = createAction(
  "organization/fetch/success"
)<OrganizationRepresentation, OrganizationActionMeta>();
export const fetchOrganizationFailure = createAction(
  "organization/fetch/failure"
)<AxiosError, OrganizationActionMeta>();

export const createOrganizationRequest = createAction(
  "createOrganization/fetch/request"
)();
export const createOrganizationSuccess = createAction(
  "createOrganization/fetch/success"
)<OrganizationRepresentation>();
export const createOrganizationFailure = createAction(
  "createOrganization/fetch/failure"
)<AxiosError>();

export const updateOrganizationRequest = createAction(
  "updateOrganization/fetch/request"
)<OrganizationActionMeta>();
export const updateOrganizationSuccess = createAction(
  "updateOrganization/fetch/success"
)<OrganizationRepresentation, OrganizationActionMeta>();
export const updateOrganizationFailure = createAction(
  "updateOrganization/fetch/failure"
)<AxiosError, OrganizationActionMeta>();

export const fetchOrganization = (
  organizationId: string
): ThunkAction<void, RootState, void, any> => {
  return (dispatch: Dispatch) => {
    const meta: OrganizationActionMeta = {
      organizationId
    };

    dispatch(fetchOrganizationRequest(meta));

    return getById(organizationId)
      .then((res: AxiosResponse<OrganizationRepresentation>) => {
        const data: OrganizationRepresentation = res.data;
        dispatch(fetchOrganizationSuccess(data, meta));
        return data;
      })
      .catch((err: AxiosError) => {
        dispatch(fetchOrganizationFailure(err, meta));
      });
  };
};

export const createOrganization = (
  organization: OrganizationRepresentation
): ThunkAction<void, RootState, void, any> => {
  return (dispatch: Dispatch) => {
    dispatch(createOrganizationRequest());

    return create(organization)
      .then((res: AxiosResponse<OrganizationRepresentation>) => {
        const data: OrganizationRepresentation = res.data;
        dispatch(createOrganizationSuccess(data));
        return data;
      })
      .catch((err: AxiosError) => {
        dispatch(createOrganizationFailure(err));
      });
  };
};

export const updateOrganization = (
  organizationId: string,
  organization: OrganizationRepresentation
): ThunkAction<void, RootState, void, any> => {
  return (dispatch: Dispatch) => {
    const meta: OrganizationActionMeta = {
      organizationId
    };

    dispatch(updateOrganizationRequest(meta));

    return update(organizationId, organization)
      .then((res: AxiosResponse<OrganizationRepresentation>) => {
        const data: OrganizationRepresentation = res.data;
        dispatch(updateOrganizationSuccess(data, meta));
        return data;
      })
      .catch((err: AxiosError) => {
        dispatch(updateOrganizationFailure(err, meta));
      });
  };
};
