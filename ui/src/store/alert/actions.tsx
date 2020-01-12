import { Dispatch } from "redux";
import { AlertModel } from "../../models/alert";
import { AxiosError } from "axios";

const frontendComponentsNotifications = require("@redhat-cloud-services/frontend-components-notifications");
const addNotification = frontendComponentsNotifications.addNotification;

export const alert = (alert: AlertModel) => {
  return (dispatch: Dispatch) => {
    dispatch(addNotification(alert));
  };
};

export const alertFetchEndpoint = (err: AxiosError) => {
  let errorDescription = "";
  if (err.response && err.response.data) {
    if (typeof err.response.data === "string") {
      errorDescription = err.response.data;
    } else if (err.response.data.error) {
      errorDescription = err.response.data.error;
    }
  }
  return (dispatch: Dispatch) => {
    dispatch(
      addNotification({
        variant: "danger",
        title: err.message,
        description: errorDescription,
        dismissable: true
      })
    );
  };
};
