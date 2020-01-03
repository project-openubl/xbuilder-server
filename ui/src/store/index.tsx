import { createStore, applyMiddleware } from "redux";
import thunk from "redux-thunk";
import logger from "redux-logger";
import { composeWithDevTools } from "redux-devtools-extension";
import { rootReducer } from "./rootReducer";

export default function configureStore() {
  return createStore(
    rootReducer,
    composeWithDevTools(applyMiddleware(logger, thunk))
  );
}
