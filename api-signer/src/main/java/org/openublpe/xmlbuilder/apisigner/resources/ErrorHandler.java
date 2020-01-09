package org.openublpe.xmlbuilder.apisigner.resources;

import org.jboss.logging.Logger;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpResponse;
import org.openublpe.xmlbuilder.apisigner.representations.idm.ErrorRepresentation;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ErrorHandler implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = Logger.getLogger(ErrorHandler.class);

    public static final String UNCAUGHT_SERVER_ERROR_TEXT = "Uncaught server error";

    @Context
    private HttpHeaders headers;

    @Context
    private HttpResponse response;

    @Override
    public Response toResponse(Throwable throwable) {
        int statusCode = getStatusCode(throwable);

        if (statusCode >= 500 && statusCode <= 599) {
            LOGGER.error(UNCAUGHT_SERVER_ERROR_TEXT, throwable);
        }

        if (!MediaTypeMatcher.isHtmlRequest(headers)) {
            ErrorRepresentation error = new ErrorRepresentation();

            error.setError(getErrorCode(throwable));

            return Response.status(statusCode)
                    .header(HttpHeaders.CONTENT_TYPE, javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE.toString())
                    .entity(error)
                    .build();
        }

        LOGGER.error("Failed to create error page");
        return Response.serverError().build();
    }

    private int getStatusCode(Throwable throwable) {
        int status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        if (throwable instanceof WebApplicationException) {
            WebApplicationException ex = (WebApplicationException) throwable;
            status = ex.getResponse().getStatus();
        }
        if (throwable instanceof Failure) {
            Failure f = (Failure) throwable;
            status = f.getErrorCode();
        }
        return status;
    }

    private String getErrorCode(Throwable throwable) {
        String error = throwable.getMessage();

        if (error == null) {
            return "unknown_error";
        }

        return error;
    }
}
