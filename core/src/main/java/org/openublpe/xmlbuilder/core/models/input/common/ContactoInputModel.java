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

    public static final class Builder {
        private String telefono;
        private String email;

        private Builder() {
        }

        public static Builder aContactoInputModel() {
            return new Builder();
        }

        public Builder withTelefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public ContactoInputModel build() {
            ContactoInputModel contactoInputModel = new ContactoInputModel();
            contactoInputModel.setTelefono(telefono);
            contactoInputModel.setEmail(email);
            return contactoInputModel;
        }
    }
}
