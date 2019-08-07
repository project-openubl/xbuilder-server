package org.jboss.xavier.integrations.route.model;

import java.util.Objects;

public class PageBean {

    private Integer page;
    private Integer size;

    public PageBean(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageBean pageBean = (PageBean) o;
        return Objects.equals(page, pageBean.page) &&
                Objects.equals(size, pageBean.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, size);
    }
}
