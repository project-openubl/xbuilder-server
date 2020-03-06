package org.openublpe.xmlbuilder.core.models.inputoutput;

import org.openublpe.xmlbuilder.core.models.input.common.DireccionInputModel;
import org.openublpe.xmlbuilder.core.models.output.common.DireccionOutputModel;

public class DireccionInputOutputModel {
    private DireccionInputModel input;
    private DireccionOutputModel output;

    public DireccionInputOutputModel() {
    }

    public DireccionInputOutputModel(DireccionInputModel input, DireccionOutputModel output) {
        this.input = input;
        this.output = output;
    }

    public DireccionInputModel getInput() {
        return input;
    }

    public void setInput(DireccionInputModel input) {
        this.input = input;
    }

    public DireccionOutputModel getOutput() {
        return output;
    }

    public void setOutput(DireccionOutputModel output) {
        this.output = output;
    }
}
