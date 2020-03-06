package org.openublpe.xmlbuilder.core.models.inputoutput;

import org.openublpe.xmlbuilder.core.models.input.common.FirmanteInputModel;
import org.openublpe.xmlbuilder.core.models.output.common.FirmanteOutputModel;

public class FirmanteInputOutputModel {
    private FirmanteInputModel input;
    private FirmanteOutputModel output;

    public FirmanteInputOutputModel() {
    }

    public FirmanteInputOutputModel(FirmanteInputModel input, FirmanteOutputModel output) {
        this.input = input;
        this.output = output;
    }

    public FirmanteInputModel getInput() {
        return input;
    }

    public void setInput(FirmanteInputModel input) {
        this.input = input;
    }

    public FirmanteOutputModel getOutput() {
        return output;
    }

    public void setOutput(FirmanteOutputModel output) {
        this.output = output;
    }
}
