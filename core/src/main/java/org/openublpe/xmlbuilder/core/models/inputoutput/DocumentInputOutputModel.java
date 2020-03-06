package org.openublpe.xmlbuilder.core.models.inputoutput;

import org.openublpe.xmlbuilder.core.models.input.standard.DocumentInputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentOutputModel;

public class DocumentInputOutputModel {
    private DocumentInputModel input;
    private DocumentOutputModel output;

    public DocumentInputOutputModel() {
    }

    public DocumentInputOutputModel(DocumentInputModel input, DocumentOutputModel output) {
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
