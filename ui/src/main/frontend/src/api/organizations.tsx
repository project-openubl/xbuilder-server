import ApiClient from "./apiClient";
import { AxiosPromise } from "axios";
import { OrganizationRepresentation } from "../models/xml-builder";

const GET_ORGANIZATIONS_URL = "/organizations";

export const getAll = (
  filterText: string,
  page: number,
  pageSize: number
): AxiosPromise<OrganizationRepresentation[]> => {
  const params: any = {
    filterText,
    offset: (page - 1) * pageSize,
    limit: pageSize
  };
  const query: string[] = [];

  Object.keys(params).forEach((key: string) => {
    const value: any = params[key];
    if (value !== undefined) {
      query.push(`${key}=${value}`);
    }
  });

  return ApiClient.get<OrganizationRepresentation[]>(
    `${GET_ORGANIZATIONS_URL}?${query.join("&")}`
  );
};
