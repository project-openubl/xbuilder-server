package org.openublpe.xmlbuilder.apicore.resources;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath(ApiApplication.API_BASE)
public class ApiApplication extends Application {
    public static final String API_BASE = "/api";
}
