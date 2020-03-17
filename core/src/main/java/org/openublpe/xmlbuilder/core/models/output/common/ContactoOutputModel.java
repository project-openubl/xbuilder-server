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

    public static final class Builder {
        private String telefono;
        private String email;

        private Builder() {
        }

        public static Builder aContactoOutputModel() {
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

        public ContactoOutputModel build() {
            ContactoOutputModel contactoOutputModel = new ContactoOutputModel();
            contactoOutputModel.setTelefono(telefono);
            contactoOutputModel.setEmail(email);
            return contactoOutputModel;
        }
    }
}
