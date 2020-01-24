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

public class Invoice_ComplexInputGenerator extends AbstractInvoiceInputGenerator implements InvoiceInputGenerator {

    @Override
    public InvoiceInputModel getInput() {
        InvoiceInputModel input = getInvoiceTemplate();

        DetalleInputModel item1 = new DetalleInputModel();
        input.getDetalle().add(item1);
        item1.setDescripcion("item");
        item1.setCantidad(new BigDecimal("1"));
        item1.setPrecioUnitario(new BigDecimal("118"));
        item1.setTipoIGV(Catalog7.GRAVADO_OPERACION_ONEROSA.toString());

        DetalleInputModel item2 = new DetalleInputModel();
        input.getDetalle().add(item2);
        item2.setDescripcion("item");
        item2.setCantidad(new BigDecimal("1"));
        item2.setPrecioUnitario(new BigDecimal("100"));
        item2.setTipoIGV(Catalog7.GRAVADO_RETIRO_POR_PREMIO.toString());

        DetalleInputModel item3 = new DetalleInputModel();
        input.getDetalle().add(item3);
        item3.setDescripcion("item");
        item3.setCantidad(new BigDecimal("1"));
        item3.setPrecioUnitario(new BigDecimal("100"));
        item3.setTipoIGV(Catalog7.EXONERADO_OPERACION_ONEROSA.toString());

        DetalleInputModel item4 = new DetalleInputModel();
        input.getDetalle().add(item4);
        item4.setDescripcion("item");
        item4.setCantidad(new BigDecimal("1"));
        item4.setPrecioUnitario(new BigDecimal("100"));
        item4.setTipoIGV(Catalog7.INAFECTO_OPERACION_ONEROSA.toString());

        DetalleInputModel item5 = new DetalleInputModel();
        input.getDetalle().add(item5);
        item5.setDescripcion("item");
        item5.setCantidad(new BigDecimal("1"));
        item5.setPrecioUnitario(new BigDecimal("100"));
        item5.setTipoIGV(Catalog7.INAFECTO_RETIRO_POR_BONIFICACION.toString());


        return input;
    }

    @Override
    public Optional<String> getSnapshot() {
        return Optional.of("xml/invoice/Complex.xml");
    }

}
