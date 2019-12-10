package org.openublpe.xmlbuilder.apisigner.keys.component;

public class ComponentValidationException extends RuntimeException {

    private Object[] parameters;

    public ComponentValidationException() {
    }

    public ComponentValidationException(String message, Object... parameters) {
        super(message);
        this.parameters = parameters;
    }

    public ComponentValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComponentValidationException(Throwable cause) {
        super(cause);
    }

    public ComponentValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
