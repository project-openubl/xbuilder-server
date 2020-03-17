package org.openublpe.xmlbuilder.rules.factory;

import org.openublpe.xmlbuilder.core.models.input.common.ContactoInputModel;
import org.openublpe.xmlbuilder.core.models.output.common.ContactoOutputModel;

public class ContactoFactory {

    public static ContactoOutputModel getContacto(ContactoInputModel input) {
        return ContactoOutputModel.Builder.aContactoOutputModel()
                .withEmail(input.getEmail())
                .withTelefono(input.getTelefono())
                .build();
    }

}
