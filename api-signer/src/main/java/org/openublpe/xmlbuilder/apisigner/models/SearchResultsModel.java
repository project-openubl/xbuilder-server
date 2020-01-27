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
