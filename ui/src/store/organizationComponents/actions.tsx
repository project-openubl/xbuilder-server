import { AxiosError, AxiosResponse } from "axios";
import { Dispatch } from "redux";
import { createAction } from "typesafe-actions";
import { ComponentRepresentation } from "../../models/xml-builder";
import { getOrganizationComponents } from "../../api/organizations";
import { alertFetchEndpoint } from "../alert/actions";

interface OrganizationComponentsActionMeta {
  organizationId: string;
}

export const fetchOrganizationComponentsRequest = createAction(
  "organizationComponents/fetch/request"
)<OrganizationComponentsActionMeta>();
export const fetchOrganizationComponentsSuccess = createAction(
  "organizationComponents/fetch/success"
)<ComponentRepresentation[], OrganizationComponentsActionMeta>();
export const fetchOrganizationComponentsFailure = createAction(
  "organizationComponents/fetch/failure"
)<AxiosError, OrganizationComponentsActionMeta>();

export const fetchOrganizationComponents = (organizationId: string) => {
  return (dispatch: Dispatch) => {
    const meta: OrganizationComponentsActionMeta = {
      organizationId: organizationId
    };

    dispatch(fetchOrganizationComponentsRequest(meta));

    return getOrganizationComponents(organizationId)
      .then((res: AxiosResponse<ComponentRepresentation[]>) => {
        dispatch(fetchOrganizationComponentsSuccess(res.data, meta));
      })
      .catch((err: AxiosError) => {
        dispatch(fetchOrganizationComponentsFailure(err, meta));
        alertFetchEndpoint(err)(dispatch);
      });
  };
};
