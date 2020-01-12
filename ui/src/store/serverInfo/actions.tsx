import { AxiosError, AxiosResponse } from "axios";
import { Dispatch } from "redux";
import { createAction } from "typesafe-actions";
import { ServerInfoRepresentation } from "../../models/xml-builder";
import { get } from "../../api/serverInfo";
import { alertFetchEndpoint } from "../alert/actions";

export const fetchServerInfoRequest = createAction("serverInfoFetch/request")();
export const fetchServerInfoSuccess = createAction("serverInfoFetch/success")<
  ServerInfoRepresentation
>();
export const fetchServerInfoFailure = createAction("serverInfoFetch/failure")<
  AxiosError
>();

export const fetchServerInfo = () => {
  return (dispatch: Dispatch) => {
    dispatch(fetchServerInfoRequest());

    return get()
      .then((res: AxiosResponse<ServerInfoRepresentation>) => {
        dispatch(fetchServerInfoSuccess(res.data));
      })
      .catch((err: AxiosError) => {
        dispatch(fetchServerInfoFailure(err));
        alertFetchEndpoint(err)(dispatch);
      });
  };
};
