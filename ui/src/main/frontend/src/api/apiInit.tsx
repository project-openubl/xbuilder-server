import axios from "axios";

export const API_BASE_URL = "/";

export const initApi = () => {
  axios.defaults.baseURL = `${API_BASE_URL}`;
};
