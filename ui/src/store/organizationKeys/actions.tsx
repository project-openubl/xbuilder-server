import { AxiosError, AxiosResponse } from "axios";
import { Dispatch } from "redux";
import { createAction } from "typesafe-actions";
import { KeysMetadataRepresentation } from "../../models/xml-builder";
import { getOrganizationKeys } from "../../api/organizations";
import { alertFetchEndpoint } from "../alert/actions";

interface OrganizationKeysActionMeta {
  organizationId: string;
}

export const fetchOrganizationKeysRequest = createAction(
  "organizationKeys/fetch/request"
)<OrganizationKeysActionMeta>();
export const fetchOrganizationKeysSuccess = createAction(
  "organizationKeys/fetch/success"
)<KeysMetadataRepresentation, OrganizationKeysActionMeta>();
export const fetchOrganizationKeysFailure = createAction(
  "organizationKeys/fetch/failure"
)<AxiosError, OrganizationKeysActionMeta>();

export const fetchOrganizationKeys = (organizationId: string) => {
  return (dispatch: Dispatch) => {
    const meta: OrganizationKeysActionMeta = {
      organizationId: organizationId
    };

    dispatch(fetchOrganizationKeysRequest(meta));

    return getOrganizationKeys(organizationId)
      .then((res: AxiosResponse<KeysMetadataRepresentation>) => {
        const keysMetadata: KeysMetadataRepresentation = res.data;
        dispatch(fetchOrganizationKeysSuccess(keysMetadata, meta));
        return keysMetadata;
      })
      .catch((err: AxiosError) => {
        dispatch(fetchOrganizationKeysFailure(err, meta));
        alertFetchEndpoint(err)(dispatch);
      });
  };
};
