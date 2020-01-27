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
package org.openublpe.xmlbuilder.inputdata.generator;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.FirmanteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.ProveedorInputModel;

public class GeneralData {

    private GeneralData() {
        // Just static methods
    }

    public static FirmanteInputModel getFirmante() {
        FirmanteInputModel result = new FirmanteInputModel();
        result.setRuc("11111111111");
        result.setRazonSocial("Firmante");
        return result;
    }

    public static ProveedorInputModel getProveedor() {
        ProveedorInputModel proveedor = new ProveedorInputModel();
        proveedor.setRuc("22222222222");
        proveedor.setRazonSocial("Proveedor");
        proveedor.setCodigoPostal("010101");
        return proveedor;
    }

    public static ClienteInputModel getClienteConRUC() {
        ClienteInputModel cliente = new ClienteInputModel();
        cliente.setNombre("Cliente");
        cliente.setNumeroDocumentoIdentidad("33333333333");
        cliente.setTipoDocumentoIdentidad(Catalog6.RUC.toString());
        return cliente;
    }

    public static ClienteInputModel getClienteConDNI() {
        ClienteInputModel cliente = new ClienteInputModel();
        cliente.setNombre("Cliente");
        cliente.setNumeroDocumentoIdentidad("33333333");
        cliente.setTipoDocumentoIdentidad(Catalog6.DNI.toString());
        return cliente;
    }
}
