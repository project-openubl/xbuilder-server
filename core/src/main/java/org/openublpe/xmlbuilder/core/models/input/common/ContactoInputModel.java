package org.openublpe.xmlbuilder.core.models.input.common;

import javax.validation.constraints.Email;

public class ContactoInputModel {

    private String telefono;

    @Email
    private String email;

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
