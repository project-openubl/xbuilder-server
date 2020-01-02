package org.openublpe.xmlbuilder.apisigner.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Models a set of beans returned as a result of a search.
 *
 * @param <T> the bean type
 */
public class SearchResultsModel<T> {

    private List<T> models = new ArrayList<>();
    private long totalSize;

    /**
     * Constructor.
     */
    public SearchResultsModel() {
    }

    public SearchResultsModel(long totalSize, List<T> models) {
        this.totalSize = totalSize;
        this.models = models;
    }

    /**
     * @return the beans
     */
    public List<T> getModels() {
        return models;
    }

    /**
     * @param beans the beans to set
     */
    public void setModels(List<T> beans) {
        this.models = beans;
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
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

}
