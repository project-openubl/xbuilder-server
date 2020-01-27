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
package org.openublpe.xmlbuilder.core.models.catalogs;

public enum Catalog10 implements Catalog {
    INTERES_POR_MORA("01"),
    AUMENTO_EN_EL_VALOR("02"),
    PENALIDAD_OTROS_CONCEPTOR("03");

    private final String code;

    Catalog10(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

}
