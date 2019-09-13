package org.jboss.xavier.integrations.util;

import org.apache.camel.CamelContext;

import java.util.Base64;

public class TestUtil
{
    public static final String HEADER_RH_IDENTITY = "x-rh-identity";

    public static void startUsernameRoutes(CamelContext camelContext) throws Exception
    {
        camelContext.startRoute("request-forbidden");
        camelContext.startRoute("add-username-header");
        camelContext.startRoute("check-authenticated-request");
    }

    public static String getBase64RHIdentity()
    {
        return  Base64.getEncoder().encodeToString("{\"entitlements\":{\"insights\":{\"is_entitled\":true},\"openshift\":{\"is_entitled\":true},\"smart_management\":{\"is_entitled\":false},\"hybrid_cloud\":{\"is_entitled\":true}},\"identity\":{\"internal\":{\"auth_time\":0,\"auth_type\":\"jwt-auth\",\"org_id\":\"6340056\"},\"account_number\":\"1460290\",\"user\":{\"first_name\":\"Marco\",\"is_active\":true,\"is_internal\":true,\"last_name\":\"Rizzi\",\"locale\":\"en_US\",\"is_org_admin\":false,\"username\":\"mrizzi@redhat.com\",\"email\":\"mrizzi+qa@redhat.com\"},\"type\":\"User\"}}".getBytes());
    }
}
