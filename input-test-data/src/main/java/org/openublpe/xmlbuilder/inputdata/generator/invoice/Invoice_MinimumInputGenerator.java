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

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.ProveedorInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.DocumentLineInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.inputdata.generator.GeneralData;
import org.openublpe.xmlbuilder.inputdata.generator.InvoiceInputGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Invoice_MinimumInputGenerator implements InvoiceInputGenerator {

    @Override
    public InvoiceInputModel getInput() {
        InvoiceInputModel input = new InvoiceInputModel();
        input.setSerie("F001");
        input.setNumero(1);

        // Proveedor
        ProveedorInputModel proveedor = new ProveedorInputModel();
        proveedor.setRuc("12345678912");
        proveedor.setRazonSocial("Softgreen S.A.C.");

        input.setProveedor(GeneralData.getProveedor());

        // Cliente
        ClienteInputModel cliente = new ClienteInputModel();
        cliente.setNombre("Carlos");
        cliente.setNumeroDocumentoIdentidad("12121212121");
        cliente.setTipoDocumentoIdentidad(Catalog6.RUC.toString());

        input.setCliente(cliente);

        // Detalle
        List<DocumentLineInputModel> detalle = new ArrayList<>();
        input.setDetalle(detalle);

        DocumentLineInputModel item1 = new DocumentLineInputModel();
        detalle.add(item1);
        item1.setDescripcion("Item");
        item1.setCantidad(BigDecimal.ONE);
        item1.setPrecioUnitario(new BigDecimal("100"));

        DocumentLineInputModel item2 = new DocumentLineInputModel();
        detalle.add(item2);
        item2.setDescripcion("Item");
        item2.setCantidad(BigDecimal.ONE);
        item2.setPrecioUnitario(new BigDecimal("100"));

        return input;
    }

}
