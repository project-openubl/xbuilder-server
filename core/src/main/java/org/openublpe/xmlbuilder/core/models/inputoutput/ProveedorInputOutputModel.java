package org.openublpe.xmlbuilder.core.models.inputoutput;

import org.openublpe.xmlbuilder.core.models.input.common.ProveedorInputModel;
import org.openublpe.xmlbuilder.core.models.output.common.ProveedorOutputModel;

public class ProveedorInputOutputModel {
    private ProveedorInputModel input;
    private ProveedorOutputModel output;

    public ProveedorInputOutputModel() {
    }

    public ProveedorInputOutputModel(ProveedorInputModel input, ProveedorOutputModel output) {
        this.input = input;
        this.output = output;
    }

    public ProveedorInputModel getInput() {
        return input;
    }

    public void setInput(ProveedorInputModel input) {
        this.input = input;
    }

    public ProveedorOutputModel getOutput() {
        return output;
    }

    public void setOutput(ProveedorOutputModel output) {
        this.output = output;
    }
}
