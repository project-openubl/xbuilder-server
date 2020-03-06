package org.openublpe.xmlbuilder.core.models.inputoutput;

import org.openublpe.xmlbuilder.core.models.input.standard.GuiaRemisionRelacionadaInputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.GuiaRemisionRelacionadaOutputModel;

public class GuiaRemisionRelacionadaInputOutputModel {

    private GuiaRemisionRelacionadaInputModel input;
    private GuiaRemisionRelacionadaOutputModel output;

    public GuiaRemisionRelacionadaInputOutputModel() {
    }

    public GuiaRemisionRelacionadaInputOutputModel(GuiaRemisionRelacionadaInputModel input, GuiaRemisionRelacionadaOutputModel output) {
        this.input = input;
        this.output = output;
    }

    public GuiaRemisionRelacionadaInputModel getInput() {
        return input;
    }

    public void setInput(GuiaRemisionRelacionadaInputModel input) {
        this.input = input;
    }

    public GuiaRemisionRelacionadaOutputModel getOutput() {
        return output;
    }

    public void setOutput(GuiaRemisionRelacionadaOutputModel output) {
        this.output = output;
    }
}
