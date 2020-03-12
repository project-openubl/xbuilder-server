package org.openublpe.xmlbuilder.core.models.inputoutput;

import org.openublpe.xmlbuilder.core.models.input.standard.DocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.invoice.InvoiceOutputModel;

public class DocumentInputOutputModel {

    private DocumentInputModel input;
    private DocumentOutputModel output;

    public DocumentInputOutputModel() {
    }

    public DocumentInputOutputModel(InvoiceInputModel input, InvoiceOutputModel output) {
        this.input = input;
        this.output = output;
    }

    public DocumentInputModel getInput() {
        return input;
    }

    public void setInput(DocumentInputModel input) {
        this.input = input;
    }

    public DocumentOutputModel getOutput() {
        return output;
    }

    public void setOutput(DocumentOutputModel output) {
        this.output = output;
    }
}
