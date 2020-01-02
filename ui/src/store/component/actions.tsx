import { AxiosError, AxiosResponse } from "axios";
import { Dispatch } from "redux";
import { createAction } from "typesafe-actions";
import { ComponentRepresentation } from "../../models/xml-builder";
import { getComponent } from "../../api/organizations";

interface ComponentActionMeta {
  organizationId: string;
  componentId: string;
}

export const fetchComponentRequest = createAction("component/fetch/request")<
  ComponentActionMeta
>();
export const fetchComponentSuccess = createAction("component/fetch/success")<
  ComponentRepresentation,
  ComponentActionMeta
>();
export const fetchComponentFailure = createAction("component/fetch/failure")<
  AxiosError,
  ComponentActionMeta
>();

export const fetchComponent = (organizationId: string, componentId: string) => {
  return (dispatch: Dispatch) => {
    const meta: ComponentActionMeta = {
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
