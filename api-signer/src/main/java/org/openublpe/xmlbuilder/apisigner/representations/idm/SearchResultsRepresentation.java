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
