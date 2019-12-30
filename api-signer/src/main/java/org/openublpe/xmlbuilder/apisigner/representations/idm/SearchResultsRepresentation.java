package org.openublpe.xmlbuilder.apisigner.representations.idm;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsRepresentation<T> {

    private List<T> items = new ArrayList<>();
    private long totalSize;

    public SearchResultsRepresentation() {
    }

    public SearchResultsRepresentation(long totalSize, List<T> items) {
        this.totalSize = totalSize;
        this.items = items;
    }

    /**
     * @return the beans
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * @param items the beans to set
     */
    public void setItems(List<T> items) {
        this.items = items;
    }

    /**
     * @return the totalSize
     */
    public long getTotalSize() {
        return totalSize;
    }

    /**
     * @param totalSize the totalSize to set
     */
    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

}
