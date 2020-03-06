package org.openublpe.xmlbuilder.core.models.output.common;

import javax.validation.constraints.Email;

public class ContactoOutputModel {

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
