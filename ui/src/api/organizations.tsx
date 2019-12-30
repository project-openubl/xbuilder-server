import ApiClient from "./apiClient";
import { AxiosPromise } from "axios";
import {
  OrganizationRepresentation,
  SearchResultsRepresentation
} from "../models/xml-builder";

const ORGANIZATIONS_URL = "/organizations";
const ORGANIZATIONS_SEARCH_URL = "/organizations/search";
const ALL_ORGANIZATIONS_URL = "/organizations/all";
const GET_ID_BY_NAME_URL = "/organizations/id-by-name";

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

export const getIdByName = (name: string): AxiosPromise<string | null> => {
  return ApiClient.get(GET_ID_BY_NAME_URL + "/" + encodeURIComponent(name));
};
