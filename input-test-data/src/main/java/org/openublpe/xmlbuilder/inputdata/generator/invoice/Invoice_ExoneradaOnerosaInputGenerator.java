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
package org.openublpe.xmlbuilder.inputdata.generator.invoice;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog7;
import org.openublpe.xmlbuilder.core.models.input.standard.DetalleInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.inputdata.generator.AbstractInvoiceInputGenerator;
import org.openublpe.xmlbuilder.inputdata.generator.InvoiceInputGenerator;

import java.math.BigDecimal;
import java.util.Optional;

public class Invoice_ExoneradaOnerosaInputGenerator extends AbstractInvoiceInputGenerator implements InvoiceInputGenerator {

    @Override
    public InvoiceInputModel getInput() {
        InvoiceInputModel input = getInvoiceTemplate();

        DetalleInputModel item1 = new DetalleInputModel();
        input.getDetalle().add(item1);

        item1.setDescripcion("item");
        item1.setCantidad(new BigDecimal("1"));
        item1.setPrecioUnitario(new BigDecimal("100"));
        item1.setTipoIGV(Catalog7.EXONERADO_OPERACION_ONEROSA.toString());

        return input;
    }

    @Override
    public Optional<String> getSnapshot() {
        return Optional.of("xml/invoice/Exonerada_Onerosa.xml");
    }

}
