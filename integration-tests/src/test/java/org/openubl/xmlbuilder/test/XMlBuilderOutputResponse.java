package org.openubl.xmlbuilder.test;

import io.restassured.response.Response;

public class XMlBuilderOutputResponse {
    private Response apiEnrichResponse;
    private Response apiCreateResponse;
    private Response apiSignerEnrichResponse;
    private Response apiSignerCreateResponse;

    public Response getApiEnrichResponse() {
        return apiEnrichResponse;
    }

    public void setApiEnrichResponse(Response apiEnrichResponse) {
        this.apiEnrichResponse = apiEnrichResponse;
    }

    public Response getApiCreateResponse() {
        return apiCreateResponse;
    }

    public void setApiCreateResponse(Response apiCreateResponse) {
        this.apiCreateResponse = apiCreateResponse;
    }

    public Response getApiSignerEnrichResponse() {
        return apiSignerEnrichResponse;
    }

    public void setApiSignerEnrichResponse(Response apiSignerEnrichResponse) {
        this.apiSignerEnrichResponse = apiSignerEnrichResponse;
    }

    public Response getApiSignerCreateResponse() {
        return apiSignerCreateResponse;
    }

    public void setApiSignerCreateResponse(Response apiSignerCreateResponse) {
        this.apiSignerCreateResponse = apiSignerCreateResponse;
    }

    public static final class Builder {
        private Response apiEnrichResponse;
        private Response apiCreateResponse;
        private Response apiSignerEnrichResponse;
        private Response apiSignerCreateResponse;

        private Builder() {
        }

        public static Builder aXMlBuilderOutputResponse() {
            return new Builder();
        }

        public Builder withApiEnrichResponse(Response apiEnrichResponse) {
            this.apiEnrichResponse = apiEnrichResponse;
            return this;
        }

        public Builder withApiCreateResponse(Response apiCreateResponse) {
            this.apiCreateResponse = apiCreateResponse;
            return this;
        }

        public Builder withApiSignerEnrichResponse(Response apiSignerEnrichResponse) {
            this.apiSignerEnrichResponse = apiSignerEnrichResponse;
            return this;
        }

        public Builder withApiSignerCreateResponse(Response apiSignerCreateResponse) {
            this.apiSignerCreateResponse = apiSignerCreateResponse;
            return this;
        }

        public XMlBuilderOutputResponse build() {
            XMlBuilderOutputResponse xMlBuilderOutputResponse = new XMlBuilderOutputResponse();
            xMlBuilderOutputResponse.setApiEnrichResponse(apiEnrichResponse);
            xMlBuilderOutputResponse.setApiCreateResponse(apiCreateResponse);
            xMlBuilderOutputResponse.setApiSignerEnrichResponse(apiSignerEnrichResponse);
            xMlBuilderOutputResponse.setApiSignerCreateResponse(apiSignerCreateResponse);
            return xMlBuilderOutputResponse;
        }
    }
}
