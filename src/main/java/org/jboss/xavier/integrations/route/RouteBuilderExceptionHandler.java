package org.jboss.xavier.integrations.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.lang3.StringUtils;
import org.jboss.xavier.integrations.jpa.service.AnalysisService;

import javax.inject.Inject;
import java.util.Map;

public abstract class RouteBuilderExceptionHandler extends RouteBuilder {
    public static final String UPLOADFORMDATA = "uploadformdata";
    public static final String MA_METADATA = "MA_metadata";
    public static final String ANALYSIS_ID = "analysisId";
    public static final String USERNAME = "analysisUsername";
    public static final String CORRELATION_ID = "correlationId";
    public static final String WORKING_DIR = "workingFile";
    public static final String WORKING_FILE = "workingFile";
    public static final String FROM_DATE = "fromDate";
    public static final String TO_DATE = "toDate";
    @Inject
    protected AnalysisService analysisService;

    @Override
    public void configure() throws Exception {
        onException(Exception.class).routeId("exception-handler")
                .handled(true)
                .setHeader("exception", simple("${exception.stacktrace}"))
                .process(this::markAnalysisAsFailed)
                .stop();
    }

    public void markAnalysisAsFailed(Exchange e) {
        String analysisId = "";
        try {
             analysisId = e.getIn().getHeader(ANALYSIS_ID, "", String.class);
            if (analysisId.isEmpty()) {
                analysisId = (String) e.getIn().getHeader(MA_METADATA, Map.class).get(ANALYSIS_ID);
            }
            if (StringUtils.isNotEmpty(analysisId)) {
                analysisService.markAsFailedIfNotCreated(Long.parseLong(analysisId));
            }
            log.error("Exception occurred while running the Analysis [{}] \n {} ", analysisId, e.getIn().getHeader("exception", String.class));
            e.getIn().setHeader("exception", null);
        } catch (Exception ex) {
            log.error("Exception occurred while marking the Analysis [" + analysisId + "] as failed.", ex);
        }
    }
}
