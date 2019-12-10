package org.openublpe.xmlbuilder.api.resources;

import org.jboss.resteasy.api.validation.ResteasyConstraintViolation;
import org.jboss.resteasy.api.validation.ResteasyViolationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;

@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ResteasyViolationException>
{
    @Override
    public Response toResponse(ResteasyViolationException e) {
        ArrayList<String> violationMessages = new ArrayList<>();
        for (ResteasyConstraintViolation violation : e.getViolations()) {
            String[] pathParts = violation.getPath().split("\\.");
            violationMessages.add(pathParts[pathParts.length - 1] + " " + violation.getMessage());
        }
        return Response.status(400).entity(String.join(", ", violationMessages)).build();
    }
}
