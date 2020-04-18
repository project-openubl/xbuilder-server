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
package org.openubl.xmlbuilder.test.ubl.perception;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.openubl.xmlbuilder.test.AbstractUBLTest;
import org.openubl.xmlbuilder.test.UBLDocumentType;
import org.openubl.xmlbuilder.test.XMlBuilderOutputResponse;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog19;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog22;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.ProveedorInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

@QuarkusTest
public class PerceptionTest extends AbstractUBLTest {

    @Test
    void testPerception_minData() throws Exception {
        // Given
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.DECEMBER, 1, 20, 30, 59);

        PerceptionInputModel input = PerceptionInputModel.Builder.aPerceptionInputModel()
                .withSerie("P001")
                .withNumero(1)
                .withRegimen(Catalog22.VENTA_INTERNA.toString())
                .withProveedor(ProveedorInputModel.Builder.aProveedorInputModel()
                        .withRuc("12345678912")
                        .withRazonSocial("Softgreen S.A.C.")
                        .build()
                )
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withNombre("Carlos Feria")
                        .withNumeroDocumentoIdentidad("12121212121")
                        .withTipoDocumentoIdentidad(Catalog6.RUC.toString())
                        .build()
                )
                .withDetalle(Arrays.asList(
                        PerceptionRetentionLineInputModel.Builder.aPerceptionRetentionLineInputModel()
                                .withComprobante(PerceptionRetentionComprobanteInputModel.Builder.aPerceptionRetentionComprobanteInputModel()
                                        .withMoneda("PEN")
                                        .withTipo(Catalog1.FACTURA.toString())
                                        .withSerieNumero("F001-1")
                                        .withFechaEmision(calendar.getTimeInMillis())
                                        .withImporteTotal(new BigDecimal("100"))
                                        .build()
                                )
                                .build(),
                        PerceptionRetentionLineInputModel.Builder.aPerceptionRetentionLineInputModel()
                                .withComprobante(PerceptionRetentionComprobanteInputModel.Builder.aPerceptionRetentionComprobanteInputModel()
                                        .withMoneda("PEN")
                                        .withTipo(Catalog1.FACTURA.toString())
                                        .withSerieNumero("F001-1")
                                        .withFechaEmision(calendar.getTimeInMillis())
                                        .withImporteTotal(new BigDecimal("100"))
                                        .build()
                                )
                                .build()
                ))
                .build();

        String body = new ObjectMapper().writeValueAsString(input);

        // When
        XMlBuilderOutputResponse response = requestAllEdpoints(UBLDocumentType.PERCEPTION, body);

        // Then
        assertSnapshot(response.getApiCreateResponse(), "xml/perception/perception_min.xml");
        assertEqualsXMLExcerptSignature(
                response.getApiCreateResponse(),
                response.getApiSignerCreateResponse()
        );
//        assertSendSunat(UBLDocumentType.PERCEPTION, response.getApiSignerCreateResponse());
    }

}
