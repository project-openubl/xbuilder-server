/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 * <p>
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.eclipse.org/legal/epl-2.0/
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openublpe.xmlbuilder.inputdata.generator.despatchadvice;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog18;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog20;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.despatchadvice.DespatchAdviceInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.despatchadvice.DespatchAdviceLineDetalleInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.despatchadvice.DespatchAdviceTrasladoInputModel;
import org.openublpe.xmlbuilder.inputdata.generator.DespatchAdviceInputGenerator;
import org.openublpe.xmlbuilder.inputdata.generator.GeneralData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class DespatchAdvice_SimpleInputGenerator implements DespatchAdviceInputGenerator {

    @Override
    public DespatchAdviceInputModel getInput() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.NOVEMBER, 8);


        DespatchAdviceInputModel input = new DespatchAdviceInputModel();
        input.setSerie("T001");
        input.setNumero(1);
        input.setFechaEmision(calendar.getTimeInMillis());

        input.setRemitente(GeneralData.getProveedor());

        ClienteInputModel clienteConRUC = GeneralData.getClienteConRUC();
        clienteConRUC.setNumeroDocumentoIdentidad("10467793549");
        input.setDestinatario(clienteConRUC);

        // Traslado
        DespatchAdviceTrasladoInputModel traslado = new DespatchAdviceTrasladoInputModel();
        input.setTraslado(traslado);

        traslado.setMotivo(Catalog20.VENTA.toString());
        traslado.setPesoBrutoTotal(BigDecimal.TEN);
        traslado.setPesoBrutoUnidadMedida("KGM");
        traslado.setModalidad(Catalog18.TRANSPORTE_PUBLICO.toString());
        traslado.setPuntoLlegada(
                new DespatchAdviceTrasladoInputModel.PuntoInputModel("010101", "direccion llegada")
        );
        traslado.setFechaInicio(calendar.getTimeInMillis());

        // Detalle
        List<DespatchAdviceLineDetalleInputModel> detalle = new ArrayList<>();
        input.setDetalle(detalle);

        DespatchAdviceLineDetalleInputModel item1 = new DespatchAdviceLineDetalleInputModel();
        detalle.add(item1);

        item1.setCantidad(BigDecimal.valueOf(20));
        item1.setDescripcion("producto descripcion");
        item1.setCodigo("123456");

        return input;
    }

    @Override
    public Optional<String> getSnapshot() {
        return Optional.of("xml/despatchadvice/Simple.xml");
    }

}
