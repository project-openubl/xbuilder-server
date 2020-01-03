import ApiClient from "./apiClient";
import { AxiosPromise } from "axios";
import {
  ServerInfoRepresentation
} from "../models/xml-builder";

const SERVER_INFO_URL = "/server-info";

export const get = (): AxiosPromise<ServerInfoRepresentation> => {
  return ApiClient.get<ServerInfoRepresentation>(SERVER_INFO_URL);
};
