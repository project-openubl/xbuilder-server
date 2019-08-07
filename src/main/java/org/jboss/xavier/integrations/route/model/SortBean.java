package org.jboss.xavier.integrations.route.model;

import java.util.Objects;

public class SortBean {

    private String orderBy;
    private Boolean orderAsc;

    public SortBean(String orderBy, Boolean orderAsc) {
        this.orderBy = orderBy;
        this.orderAsc = orderAsc;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Boolean isOrderAsc() {
        return orderAsc;
    }

    public void setOrderAsc(Boolean orderAsc) {
        this.orderAsc = orderAsc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortBean sortBean = (SortBean) o;
        return Objects.equals(orderBy, sortBean.orderBy) &&
                Objects.equals(orderAsc, sortBean.orderAsc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderBy, orderAsc);
    }
}
