package org.openublpe.xmlbuilder.core.models.inputoutput;

import org.openublpe.xmlbuilder.core.models.input.standard.DocumentLineInputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentLineOutputModel;

public class DocumentLineInputOutputModel {
    private DocumentLineInputModel input;
    private DocumentLineOutputModel output;

    public DocumentLineInputOutputModel() {
    }

    public DocumentLineInputOutputModel(DocumentLineInputModel input, DocumentLineOutputModel output) {
        this.input = input;
        this.output = output;
    }

    public DocumentLineInputModel getInput() {
        return input;
    }

    public void setInput(DocumentLineInputModel input) {
        this.input = input;
    }

    public DocumentLineOutputModel getOutput() {
        return output;
    }

    public void setOutput(DocumentLineOutputModel output) {
        this.output = output;
    }
}
