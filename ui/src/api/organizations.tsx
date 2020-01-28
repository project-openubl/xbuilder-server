import ApiClient from "./apiClient";
import { AxiosPromise } from "axios";
import {
  OrganizationRepresentation,
  SearchResultsRepresentation,
  KeysMetadataRepresentation,
  ComponentRepresentation,
  DocumentType
} from "../models/xml-builder";

const ORGANIZATIONS_URL = "/organizations";
const ORGANIZATIONS_SEARCH_URL = "/organizations/search";
const ALL_ORGANIZATIONS_URL = "/organizations/all";
const GET_ID_BY_NAME_URL = "/organizations/id-by-name";
const GET_ORGANIZATION_KEYS_URL = "/organizations/{organizationId}/keys";
const ORGANIZATION_COMPONENTS_URL =
  "/organizations/{organizationId}/components";
const GET_ORGANIZATION_COMPONENT_URL =
  "/organizations/{organizationId}/components/{componentId}";
const ORGANIZATION_ENRICH_DOCUMENTS_URL =
  "/organizations/{organizationId}/documents/{documentType}/enrich";
const ORGANIZATION_CREATE_DOCUMENTS_URL =
  "/organizations/{organizationId}/documents/{documentType}/create";

export const search = (
  filterText: string,
  page: number,
  pageSize: number
): AxiosPromise<SearchResultsRepresentation<OrganizationRepresentation>> => {
  const params: any = {
    filterText,
    page: page - 1,
    pageSize: pageSize
  };
  const query: string[] = [];

  Object.keys(params).forEach((key: string) => {
    const value: any = params[key];
    if (value !== undefined) {
      query.push(`${key}=${value}`);
    }
  });

  return ApiClient.get<SearchResultsRepresentation<OrganizationRepresentation>>(
    `${ORGANIZATIONS_SEARCH_URL}?${query.join("&")}`
  );
};

export const getAll = (): AxiosPromise<OrganizationRepresentation[]> => {
  return ApiClient.get<OrganizationRepresentation[]>(ALL_ORGANIZATIONS_URL);
};

export const create = (organization: OrganizationRepresentation) => {
  return ApiClient.post<OrganizationRepresentation>(
    ORGANIZATIONS_URL,
    organization
  );
};

export const getById = (organizationId: string) => {
  return ApiClient.get<OrganizationRepresentation>(
    `${ORGANIZATIONS_URL}/${organizationId}`
  );
};

export const update = (
  organizationId: string,
  organization: OrganizationRepresentation
) => {
  return ApiClient.put<OrganizationRepresentation>(
    `${ORGANIZATIONS_URL}/${organizationId}`,
    organization
  );
};

export const remove = (organizationId: string) => {
  return ApiClient.delete(`${ORGANIZATIONS_URL}/${organizationId}`);
};

export const getIdByName = (name: string): AxiosPromise<string | null> => {
  return ApiClient.get(GET_ID_BY_NAME_URL + "/" + encodeURIComponent(name));
};

export const getOrganizationKeys = (
  organizationId: string
): AxiosPromise<KeysMetadataRepresentation> => {
  return ApiClient.get<KeysMetadataRepresentation>(
    GET_ORGANIZATION_KEYS_URL.replace("{organizationId}", organizationId)
  );
};

export const getOrganizationComponents = (
  organizationId: string
): AxiosPromise<ComponentRepresentation[]> => {
  return ApiClient.get<ComponentRepresentation[]>(
    ORGANIZATION_COMPONENTS_URL.replace("{organizationId}", organizationId)
  );
};

export const getComponent = (
  organizationId: string,
  componentId: string
): AxiosPromise<ComponentRepresentation> => {
  return ApiClient.get<ComponentRepresentation>(
    GET_ORGANIZATION_COMPONENT_URL.replace(
      "{organizationId}",
      organizationId
    ).replace("{componentId}", componentId)
  );
};

export const createComponent = (
  organizationId: string,
  component: ComponentRepresentation
): AxiosPromise<ComponentRepresentation> => {
  return ApiClient.post<ComponentRepresentation>(
    ORGANIZATION_COMPONENTS_URL.replace("{organizationId}", organizationId),
    component
  );
};

export const updateComponent = (
  organizationId: string,
  component: ComponentRepresentation
): AxiosPromise<ComponentRepresentation> => {
  return ApiClient.put<ComponentRepresentation>(
    GET_ORGANIZATION_COMPONENT_URL.replace(
      "{organizationId}",
      organizationId
    ).replace("{componentId}", component.id),
    component
  );
};

export const deleteComponent = (
  organizationId: string,
  componentId: string
): AxiosPromise => {
  return ApiClient.delete(
    GET_ORGANIZATION_COMPONENT_URL.replace(
      "{organizationId}",
      organizationId
    ).replace("{componentId}", componentId)
  );
};

export const enrichDocument = (
  organizationId: string,
  documentType: DocumentType,
  document: any
): AxiosPromise<any> => {
  return ApiClient.post<any>(
    ORGANIZATION_ENRICH_DOCUMENTS_URL.replace(
      "{organizationId}",
      organizationId
    ).replace("{documentType}", documentType),
    document
  );
};

export const createDocument = (
  organizationId: string,
  documentType: DocumentType,
  document: any
): AxiosPromise<any> => {
  return ApiClient.post<any>(
    ORGANIZATION_CREATE_DOCUMENTS_URL.replace(
      "{organizationId}",
      organizationId
    ).replace("{documentType}", documentType),
    document
  );
};
