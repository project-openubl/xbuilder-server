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
package org.openublpe.xmlbuilder.inputdata.generator.cases.proveedorsincodigopostal;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog19;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentLineInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.VoidedDocumentInputModel;
import org.openublpe.xmlbuilder.inputdata.generator.GeneralData;
import org.openublpe.xmlbuilder.inputdata.generator.SummaryDocumentInputGenerator;
import org.openublpe.xmlbuilder.inputdata.generator.VoidedDocumentInputGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SummaryDocument_ProveedorSinCodigoPostalGenerator implements SummaryDocumentInputGenerator {

    @Override
    public SummaryDocumentInputModel getInput() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.NOVEMBER, 8);


        SummaryDocumentInputModel input = new SummaryDocumentInputModel();
        input.setNumero(1);
        input.setFechaEmision(calendar.getTimeInMillis());

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        input.setFechaEmisionDocumentReference(calendar.getTimeInMillis());

        // Proveedor
        input.setProveedor(Utils.getProveedor());

        List<SummaryDocumentLineInputModel> detalle = new ArrayList<>();
        input.setDetalle(detalle);

        SummaryDocumentLineInputModel item = new SummaryDocumentLineInputModel();
        detalle.add(item);

        item.setTipoComprobante(Catalog1.BOLETA.toString());
        item.setSerieNumero("B001-1");
        item.setCliente(Utils.getClienteConDNI()); // Cliente
        item.setTipoOperacion(Catalog19.ADICIONAR.toString());
        item.setImporteTotal(new BigDecimal("100"));
        item.setTotalOperacionesGravadas(new BigDecimal("100"));
        item.setIgv(new BigDecimal("18"));

        return input;
    }

}
