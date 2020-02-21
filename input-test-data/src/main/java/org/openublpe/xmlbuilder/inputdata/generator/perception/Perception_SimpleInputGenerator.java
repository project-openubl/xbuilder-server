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
package org.openublpe.xmlbuilder.inputdata.generator.perception;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog19;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog22;
import org.openublpe.xmlbuilder.core.models.input.sunat.PerceptionInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.PerceptionRetentionLineInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentLineInputModel;
import org.openublpe.xmlbuilder.inputdata.generator.GeneralData;
import org.openublpe.xmlbuilder.inputdata.generator.PerceptionInputGenerator;
import org.openublpe.xmlbuilder.inputdata.generator.SummaryDocumentInputGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class Perception_SimpleInputGenerator implements PerceptionInputGenerator {

    @Override
    public PerceptionInputModel getInput() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.NOVEMBER, 8);


        PerceptionInputModel input = new PerceptionInputModel();
        input.setSerie("P001");
        input.setNumero(1);
        input.setFechaEmision(calendar.getTimeInMillis());

        input.setFirmante(GeneralData.getFirmante());
        input.setProveedor(GeneralData.getProveedor());
        input.setCliente(GeneralData.getClienteConRUC());

        List<PerceptionRetentionLineInputModel> detalle = new ArrayList<>();
        input.setDetalle(detalle);

        PerceptionRetentionLineInputModel item1 = new PerceptionRetentionLineInputModel();
        detalle.add(item1);

        calendar.add(Calendar.DAY_OF_MONTH, -1);

        item1.setTipoComprobante(Catalog1.FACTURA.toString());
        item1.setSerieNumeroComprobante("F001-1");
        item1.setFechaEmisionComprobante(calendar.getTimeInMillis());
        item1.setImporteTotalComprobante(BigDecimal.valueOf(100));

        return input;
    }

    @Override
    public Optional<String> getSnapshot() {
        return Optional.of("xml/perception/Simple.xml");
    }
}
