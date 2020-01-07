import { AxiosError, AxiosResponse } from "axios";
import { Dispatch } from "redux";
import { createAction } from "typesafe-actions";
import { ComponentRepresentation } from "../../models/xml-builder";
import {
  getComponent,
  createComponent,
  updateComponent
} from "../../api/organizations";

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

export const fetchComponent = (organizationId: string, componentId: string) => {
  return (dispatch: Dispatch) => {
    const meta: ComponentItemActionMeta = {
      organizationId: organizationId,
      componentId: componentId
    };

    dispatch(fetchComponentRequest(meta));

    return getComponent(organizationId, componentId)
      .then((res: AxiosResponse<ComponentRepresentation>) => {
        const data: ComponentRepresentation = res.data;
        dispatch(fetchComponentSuccess(data, meta));
        return data;
      })
      .catch((err: AxiosError) => {
        dispatch(fetchComponentFailure(err, meta));
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
        const data: ComponentRepresentation = res.data;
        dispatch(createComponentSuccess(data, meta));
        return data;
      })
      .catch((err: AxiosError) => {
        dispatch(createComponentFailure(err, meta));
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
        const data: ComponentRepresentation = res.data;
        dispatch(updateComponentSuccess(data, meta));
        return data;
      })
      .catch((err: AxiosError) => {
        dispatch(updateComponentFailure(err, meta));
      });
  };
};
