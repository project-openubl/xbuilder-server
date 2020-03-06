package org.openublpe.xmlbuilder.core.models.inputoutput;

import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.output.common.ClienteOutputModel;

public class ClienteInputOutputModel {
    private ClienteInputModel input;
    private ClienteOutputModel output;

    public ClienteInputOutputModel() {
    }

    public ClienteInputOutputModel(ClienteInputModel input, ClienteOutputModel output) {
        this.input = input;
        this.output = output;
    }

    public ClienteInputModel getInput() {
        return input;
    }

    public void setInput(ClienteInputModel input) {
        this.input = input;
    }

    public ClienteOutputModel getOutput() {
        return output;
    }

    public void setOutput(ClienteOutputModel output) {
        this.output = output;
    }
}
