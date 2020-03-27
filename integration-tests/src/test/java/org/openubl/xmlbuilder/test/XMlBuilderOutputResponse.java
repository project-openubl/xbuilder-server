/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
