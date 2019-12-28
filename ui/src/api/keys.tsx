import ApiClient from "./apiClient";
import { AxiosPromise } from "axios";
import { KeysMetadataRepresentation } from "../models/xml-builder";

const GET_ORGANIZATION_KEYS_URL = "/organizations/{organizationId}/keys";

export const getOrganizationKeys = (
  organizationId: string
): AxiosPromise<KeysMetadataRepresentation> => {
  return ApiClient.get<KeysMetadataRepresentation>(
    GET_ORGANIZATION_KEYS_URL.replace("{organizationId}", organizationId)
  );
};
