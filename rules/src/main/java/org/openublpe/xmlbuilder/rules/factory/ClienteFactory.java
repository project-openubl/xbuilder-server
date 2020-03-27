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
package org.openublpe.xmlbuilder.rules.factory;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.output.common.ClienteOutputModel;

public class ClienteFactory {

    public static ClienteOutputModel getCliente(ClienteInputModel input) {
        return ClienteOutputModel.Builder.aClienteOutputModel()
                .withNombre(input.getNombre())
                .withNumeroDocumentoIdentidad(input.getNumeroDocumentoIdentidad())
                .withTipoDocumentoIdentidad(Catalog.valueOfCode(Catalog6.class, input.getTipoDocumentoIdentidad()).orElseThrow(Catalog.invalidCatalogValue))
                .withContacto(input.getContacto() != null ? ContactoFactory.getContacto(input.getContacto()) : null)
                .withDireccion(input.getDireccion() != null ? DireccionFactory.getDireccion(input.getDireccion()) : null)
                .build();
    }


}
