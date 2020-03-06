package org.openublpe.xmlbuilder.core.models.inputoutput;

import org.openublpe.xmlbuilder.core.models.input.standard.DocumentoTributarioRelacionadoInputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentoTributarioRelacionadoOutputModel;

public class DocumentoTributarioRelacionadoInputOutputModel {
    private DocumentoTributarioRelacionadoInputModel input;
    private DocumentoTributarioRelacionadoOutputModel output;

    public DocumentoTributarioRelacionadoInputOutputModel() {
    }

    public DocumentoTributarioRelacionadoInputOutputModel(DocumentoTributarioRelacionadoInputModel input, DocumentoTributarioRelacionadoOutputModel output) {
        this.output = output;
    }

    public DocumentoTributarioRelacionadoInputModel getInput() {
        return input;
    }

    public void setInput(DocumentoTributarioRelacionadoInputModel input) {
        this.input = input;
    }

    public DocumentoTributarioRelacionadoOutputModel getOutput() {
        return output;
    }

    public void setOutput(DocumentoTributarioRelacionadoOutputModel output) {
        this.output = output;
    }
}
