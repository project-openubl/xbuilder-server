package org.jboss.xavier.integrations.route.filter;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultHeaderFilterStrategy;

import java.util.HashSet;
import java.util.Set;

public class RestHeaderFilterStrategy extends DefaultHeaderFilterStrategy {

    private Set<String> inAllowFilter;
    private Set<String> outAllowFilter;


    @Override
    public boolean applyFilterToCamelHeaders(String headerName, Object headerValue, Exchange exchange) {
        if (headerValue == null && !isAllowNullValues()) {
            return true;
        } else if (getOutAllowFilter().contains(headerName)) {
            return false;
        }

        return super.applyFilterToCamelHeaders(headerName, headerValue, exchange);
    }

    @Override
    public boolean applyFilterToExternalHeaders(String headerName, Object headerValue, Exchange exchange) {
        if (headerValue == null && !isAllowNullValues()) {
            return true;
        } else if (getInAllowFilter().contains(headerName)) {
            return false;
        }

        return super.applyFilterToExternalHeaders(headerName, headerValue, exchange);
    }

    public Set<String> getInAllowFilter() {
        if (inAllowFilter == null) {
            inAllowFilter = new HashSet<>();
        }

        return inAllowFilter;
    }

    public void setInAllowFilter(Set<String> inAllowFilter) {
        this.inAllowFilter = inAllowFilter;
    }

    public Set<String> getOutAllowFilter() {
        if (outAllowFilter == null) {
            outAllowFilter = new HashSet<>();
        }

        return outAllowFilter;
    }

    public void setOutAllowFilter(Set<String> outAllowFilter) {
        this.outAllowFilter = outAllowFilter;
    }
}
