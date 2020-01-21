import { AxiosError, AxiosResponse } from "axios";
import { Dispatch } from "redux";
import { createAction } from "typesafe-actions";
import { enrichDocument } from "../../api/organizations";
import { alert, alertFetchEndpoint } from "../alert/actions";
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
export const createComponentSuccess = createAction("document/enrich/success")<
  any,
  OrganizationComponentActionMeta
>();
export const createComponentFailure = createAction("document/enrich/failure")<
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
        dispatch(createComponentSuccess(res.data, meta));
        return res.data;
      })
      .catch((err: AxiosError) => {
        dispatch(createComponentFailure(err, meta));
        alertFetchEndpoint(err)(dispatch);
      });
  };
};
