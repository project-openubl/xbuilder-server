package org.openublpe.xmlbuilder.core.models.inputoutput;

import org.openublpe.xmlbuilder.core.models.input.common.ContactoInputModel;
import org.openublpe.xmlbuilder.core.models.output.common.ContactoOutputModel;

public class ContactoInputOutputModel {
    private ContactoInputModel input;
    private ContactoOutputModel output;

    public ContactoInputOutputModel() {
    }

    public ContactoInputOutputModel(ContactoInputModel input, ContactoOutputModel output) {
        this.input = input;
        this.output = output;
    }

    public ContactoInputModel getInput() {
        return input;
    }

    public void setInput(ContactoInputModel input) {
        this.input = input;
    }

    public ContactoOutputModel getOutput() {
        return output;
    }

    public void setOutput(ContactoOutputModel output) {
        this.output = output;
    }
}
