package org.openublpe.xmlbuilder.apisigner.models;

public class ModelException extends Exception {

    private static final long serialVersionUID = 1L;

    private Object[] parameters;

    public ModelException() {
    }

    public ModelException(String message) {
        super(message);
    }

    public ModelException(String message, Object... parameters) {
        super(message);
        this.parameters = parameters;
    }

    public ModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelException(Throwable cause) {
        super(cause);
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}

