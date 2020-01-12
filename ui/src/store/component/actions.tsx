import { AxiosError, AxiosResponse } from "axios";
import { Dispatch } from "redux";
import { createAction } from "typesafe-actions";
import { ComponentRepresentation } from "../../models/xml-builder";
import {
  getComponent,
  createComponent,
  updateComponent,
  deleteComponent
} from "../../api/organizations";
import { alert, alertFetchEndpoint } from "../alert/actions";

interface OrganizationComponentActionMeta {
  organizationId: string;
}

interface ComponentItemActionMeta extends OrganizationComponentActionMeta {
  componentId: string;
}

export const fetchComponentRequest = createAction("component/fetch/request")<
  ComponentItemActionMeta
>();
export const fetchComponentSuccess = createAction("component/fetch/success")<
  ComponentRepresentation,
  ComponentItemActionMeta
>();
export const fetchComponentFailure = createAction("component/fetch/failure")<
  AxiosError,
  ComponentItemActionMeta
>();

export const createComponentRequest = createAction("component/create/request")<
  OrganizationComponentActionMeta
>();
export const createComponentSuccess = createAction("component/create/success")<
  ComponentRepresentation,
  OrganizationComponentActionMeta
>();
export const createComponentFailure = createAction("component/create/failure")<
  AxiosError,
  OrganizationComponentActionMeta
>();

export const updateComponentRequest = createAction("component/update/request")<
  ComponentItemActionMeta
>();
export const updateComponentSuccess = createAction("component/update/success")<
  ComponentRepresentation,
  ComponentItemActionMeta
>();
export const updateComponentFailure = createAction("component/update/failure")<
  AxiosError,
  ComponentItemActionMeta
>();

export const deleteComponentRequest = createAction("component/delete/request")<
  ComponentItemActionMeta
>();
export const deleteComponentSuccess = createAction("component/delete/success")<
  string,
  ComponentItemActionMeta
>();
export const deleteComponentFailure = createAction("component/delete/failure")<
  AxiosError,
  ComponentItemActionMeta
>();

export const fetchComponent = (organizationId: string, componentId: string) => {
  return (dispatch: Dispatch) => {
    const meta: ComponentItemActionMeta = {
      organizationId: organizationId,
      componentId: componentId
    };

    dispatch(fetchComponentRequest(meta));

    return getComponent(organizationId, componentId)
      .then((res: AxiosResponse<ComponentRepresentation>) => {
        dispatch(fetchComponentSuccess(res.data, meta));
      })
      .catch((err: AxiosError) => {
        dispatch(fetchComponentFailure(err, meta));
        alertFetchEndpoint(err)(dispatch);
      });
  };
};

export const requestCreateComponent = (
  organizationId: string,
  component: ComponentRepresentation
) => {
  return (dispatch: Dispatch) => {
    const meta: OrganizationComponentActionMeta = {
      organizationId: organizationId
    };

    dispatch(createComponentRequest(meta));
    return createComponent(organizationId, component)
      .then((res: AxiosResponse<ComponentRepresentation>) => {
        dispatch(createComponentSuccess(res.data, meta));
        alert({
          title: `Creado satisfactoriamente`,
          variant: "success",
          description: `Componente ${component.name} creado`
        })(dispatch);
      })
      .catch((err: AxiosError) => {
        dispatch(createComponentFailure(err, meta));
        alertFetchEndpoint(err)(dispatch);
      });
  };
};

export const requestUpdateComponent = (
  organizationId: string,
  component: ComponentRepresentation
) => {
  return (dispatch: Dispatch) => {
    const meta: ComponentItemActionMeta = {
      organizationId: organizationId,
      componentId: component.id
    };

    dispatch(updateComponentRequest(meta));
    return updateComponent(organizationId, component)
      .then((res: AxiosResponse<ComponentRepresentation>) => {
        dispatch(updateComponentSuccess(res.data, meta));
        alert({
          title: `Actualizado satisfactoriamante`,
          description: `Componente ${component.id} actualizado`,
          variant: "success"
        })(dispatch);
      })
      .catch((err: AxiosError) => {
        dispatch(updateComponentFailure(err, meta));
        alertFetchEndpoint(err)(dispatch);
      });
  };
};

export const requestDeleteComponent = (
  organizationId: string,
  componentId: string
) => {
  return (dispatch: Dispatch) => {
    const meta: ComponentItemActionMeta = {
      organizationId: organizationId,
      componentId: componentId
    };

    dispatch(deleteComponentRequest(meta));
    return deleteComponent(organizationId, componentId)
      .then((res: AxiosResponse) => {
        dispatch(deleteComponentSuccess(res.data, meta));
        alert({
          title: `Eliminado satisfactoriamente`,
          description: `Componente ${componentId} eliminado`,
          variant: "success"
        })(dispatch);
      })
      .catch((err: AxiosError) => {
        dispatch(updateComponentFailure(err, meta));
        alertFetchEndpoint(err)(dispatch);
      });
  };
};
