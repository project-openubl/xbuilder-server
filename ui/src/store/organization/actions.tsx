import { AxiosError, AxiosResponse } from "axios";
import { Dispatch } from "redux";
import { createAction } from "typesafe-actions";
import { OrganizationRepresentation } from "../../models/xml-builder";
import {
  getById,
  create,
  update,
  getIdByName,
  remove
} from "../../api/organizations";
import { fetchOrganizations } from "../organizationContext/actions";
import { alert, alertFetchEndpoint } from "../alert/actions";

interface OrganizationActionMeta {
  organizationId: string;
}

interface OrganizationNameActionMeta {
  organizationName: string;
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
  "organization/create/request"
)();
export const createOrganizationSuccess = createAction(
  "organization/create/success"
)<OrganizationRepresentation>();
export const createOrganizationFailure = createAction(
  "organization/create/failure"
)<AxiosError>();

export const updateOrganizationRequest = createAction(
  "organization/update/request"
)<OrganizationActionMeta>();
export const updateOrganizationSuccess = createAction(
  "organization/update/success"
)<OrganizationRepresentation, OrganizationActionMeta>();
export const updateOrganizationFailure = createAction(
  "organization/update/failure"
)<AxiosError, OrganizationActionMeta>();

export const deleteOrganizationRequest = createAction(
  "organization/delete/request"
)<OrganizationActionMeta>();
export const deleteOrganizationSuccess = createAction(
  "organization/delete/success"
)<OrganizationRepresentation, OrganizationActionMeta>();
export const deleteOrganizationFailure = createAction(
  "organization/delete/failure"
)<AxiosError, OrganizationActionMeta>();

export const fetchOrganizationIdByNameRequest = createAction(
  "organization/fetchIdByName/request"
)<OrganizationNameActionMeta>();
export const fetchOrganizationIdByNameSuccess = createAction(
  "organization/fetchIdByName/success"
)<string | null, OrganizationNameActionMeta>();
export const fetchOrganizationIdByNameFailure = createAction(
  "organization/fetchIdByName/failure"
)<AxiosError, OrganizationNameActionMeta>();

export const fetchOrganization = (organizationId: string) => {
  return (dispatch: Dispatch) => {
    const meta: OrganizationActionMeta = {
      organizationId
    };

    dispatch(fetchOrganizationRequest(meta));

    return getById(organizationId)
      .then((res: AxiosResponse<OrganizationRepresentation>) => {
        dispatch(fetchOrganizationSuccess(res.data, meta));
      })
      .catch((err: AxiosError) => {
        dispatch(fetchOrganizationFailure(err, meta));
        alertFetchEndpoint(err)(dispatch);
      });
  };
};

export const createOrganization = (
  organization: OrganizationRepresentation
) => {
  return (dispatch: Dispatch) => {
    dispatch(createOrganizationRequest());
    return create(organization)
      .then((res: AxiosResponse<OrganizationRepresentation>) => {
        dispatch(createOrganizationSuccess(res.data));
        fetchOrganizations()(dispatch);
        alert({
          title: `Creado satisfactoriamente`,
          description: `Organización ${organization.name} creada`,
          variant: "success"
        })(dispatch);
      })
      .catch((err: AxiosError) => {
        dispatch(createOrganizationFailure(err));
        alertFetchEndpoint(err)(dispatch);
      });
  };
};

export const updateOrganization = (
  organizationId: string,
  organization: OrganizationRepresentation
) => {
  return (dispatch: Dispatch) => {
    const meta: OrganizationActionMeta = {
      organizationId
    };

    dispatch(updateOrganizationRequest(meta));

    return update(organizationId, organization)
      .then((res: AxiosResponse<OrganizationRepresentation>) => {
        dispatch(updateOrganizationSuccess(res.data, meta));
        fetchOrganizations()(dispatch);
        alert({
          title: `Actualizado satisfactoriamente`,
          description: `Organización ${organization.name} actualizada`,
          variant: "success"
        })(dispatch);
      })
      .catch((err: AxiosError) => {
        dispatch(updateOrganizationFailure(err, meta));
        alertFetchEndpoint(err)(dispatch);
      });
  };
};

export const deleteOrganization = (organizationId: string) => {
  return (dispatch: Dispatch) => {
    const meta: OrganizationActionMeta = {
      organizationId
    };

    dispatch(deleteOrganizationRequest(meta));

    return remove(organizationId)
      .then((res: AxiosResponse) => {
        dispatch(deleteOrganizationSuccess(res.data, meta));
        fetchOrganizations()(dispatch);
        alert({
          title: `Eliminado satisfactoriamente`,
          description: `Organización ${organizationId} eliminada`,
          variant: "success"
        })(dispatch);
      })
      .catch((err: AxiosError) => {
        dispatch(deleteOrganizationFailure(err, meta));
        alertFetchEndpoint(err)(dispatch);
      });
  };
};

//

export const fetchOrganizationIdByName = (organizationName: string) => {
  return (dispatch: Dispatch) => {
    const meta: OrganizationNameActionMeta = {
      organizationName: organizationName
    };

    dispatch(fetchOrganizationIdByNameRequest(meta));

    return getIdByName(organizationName)
      .then((res: AxiosResponse<string | null>) => {
        const data = res.data;
        dispatch(fetchOrganizationIdByNameSuccess(data, meta));
        return data;
      })
      .catch((err: AxiosError) => {
        dispatch(fetchOrganizationIdByNameFailure(err, meta));
      });
  };
};
