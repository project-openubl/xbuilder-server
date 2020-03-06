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
package org.openublpe.xmlbuilder.core.models.output.common;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.core.models.input.common.ContactoInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.DireccionInputModel;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ClienteOutputModel {

    @NotNull
    private Catalog6 tipoDocumentoIdentidad;

    @NotBlank
    private String numeroDocumentoIdentidad;

    @NotBlank
    private String nombre;

    @Valid
    private DireccionOutputModel direccion;

    @Valid
    private ContactoOutputModel contacto;

    public Catalog6 getTipoDocumentoIdentidad() {
        return tipoDocumentoIdentidad;
    }

    public void setTipoDocumentoIdentidad(Catalog6 tipoDocumentoIdentidad) {
        this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
    }

    public String getNumeroDocumentoIdentidad() {
        return numeroDocumentoIdentidad;
    }

    public void setNumeroDocumentoIdentidad(String numeroDocumentoIdentidad) {
        this.numeroDocumentoIdentidad = numeroDocumentoIdentidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public DireccionOutputModel getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionOutputModel direccion) {
        this.direccion = direccion;
    }

    public ContactoOutputModel getContacto() {
        return contacto;
    }

    public void setContacto(ContactoOutputModel contacto) {
        this.contacto = contacto;
    }
}
