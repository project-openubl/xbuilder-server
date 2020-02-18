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
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.VoidedDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.output.sunat.VoidedDocumentOutputModel;
import org.openublpe.xmlbuilder.inputdata.generator.AbstractInvoiceInputGenerator;
import org.openublpe.xmlbuilder.inputdata.generator.GeneralData;
import org.openublpe.xmlbuilder.inputdata.generator.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.inputdata.generator.VoidedDocumentInputGenerator;

import java.util.Calendar;
import java.util.Optional;

public class VoidedDocument_ProveedorSinCodigoPostalGenerator implements VoidedDocumentInputGenerator {

    @Override
    public VoidedDocumentInputModel getInput() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.NOVEMBER, 8);


        VoidedDocumentInputModel input = new VoidedDocumentInputModel();
        input.setNumero(1);
        input.setFechaEmision(calendar.getTimeInMillis());

        input.setProveedor(Utils.getProveedor());

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        input.setSerieNumeroDocumentReference("F001-1");
        input.setFechaEmisionDocumentReference(calendar.getTimeInMillis());
        input.setMotivoBajaDocumentReference("motivo baja");
        input.setTipoDocumentReference(Catalog1.FACTURA.toString());

        return input;
    }

    @Override
    public Optional<String> getSnapshot() {
        return Optional.of("xml/cases/proveedorsincodigopostal/VoidedDocument.xml");
    }

}
