import { AxiosError, AxiosResponse } from "axios";
import { Dispatch } from "redux";
import { createAction } from "typesafe-actions";
import { enrichDocument, createDocument } from "../../api/organizations";
import { alertFetchEndpoint } from "../alert/actions";
import { DocumentType } from "../../models/xml-builder";

interface OrganizationComponentActionMeta {
  organizationId: string;
}

interface ComponentItemActionMeta extends OrganizationComponentActionMeta {
  componentId: string;
}

export const createEnrichDocumentRequest = createAction(
  "document/enrich/request"
)<OrganizationComponentActionMeta>();
export const createEnrichDocumentSuccess = createAction(
  "document/enrich/success"
)<any, OrganizationComponentActionMeta>();
export const createEnrichDocumentFailure = createAction(
  "document/enrich/failure"
)<AxiosError, OrganizationComponentActionMeta>();

export const createDocumentRequest = createAction("document/create/request")<
  OrganizationComponentActionMeta
>();
export const createDocumentSuccess = createAction("document/create/success")<
  any,
  OrganizationComponentActionMeta
>();
export const createDocumentFailure = createAction("document/create/failure")<
  AxiosError,
  OrganizationComponentActionMeta
>();

export const requestEnrichDocument = (
  organizationId: string,
  documentType: DocumentType,
  document: any
) => {
  return (dispatch: Dispatch) => {
    const meta: OrganizationComponentActionMeta = {
      organizationId: organizationId
    };

    dispatch(createEnrichDocumentRequest(meta));
    return enrichDocument(organizationId, documentType, document)
      .then((res: AxiosResponse<any>) => {
        dispatch(createEnrichDocumentSuccess(res.data, meta));
        return res.data;
      })
      .catch((err: AxiosError) => {
        dispatch(createEnrichDocumentFailure(err, meta));
        alertFetchEndpoint(err)(dispatch);
      });
  };
};

export const requestCreateDocument = (
  organizationId: string,
  documentType: DocumentType,
  document: any
) => {
  return (dispatch: Dispatch) => {
    const meta: OrganizationComponentActionMeta = {
      organizationId: organizationId
    };

    dispatch(createDocumentRequest(meta));
    return createDocument(organizationId, documentType, document)
      .then((res: AxiosResponse<any>) => {
        dispatch(createDocumentSuccess(res.data, meta));
        return res;
      })
      .catch((err: AxiosError) => {
        dispatch(createDocumentFailure(err, meta));
        alertFetchEndpoint(err)(dispatch);
      });
  };
};
