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

public enum Catalog12 implements Catalog {
    FACTURA_EMITIDA_PARA_CORREGIR_ERROR_EN_EL_RUC("01"),
    FACTURA_EMITIDA_POR_ANTICIPOS("02"),
    BOLETA_DE_VENTA_EMITIDA_POR_ANTICIPOS("03"),
    TICKET_DE_SALIDA("04"),
    CODIGO_SCOP("05"),
    OTROS("99");

    private final String code;

    Catalog12(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

}
