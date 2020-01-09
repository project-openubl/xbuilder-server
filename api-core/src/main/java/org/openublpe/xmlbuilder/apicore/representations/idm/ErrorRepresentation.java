package org.openublpe.xmlbuilder.apicore.representations.idm;

public class ErrorRepresentation {

    private String error;
    private String errorDescription;

    public ErrorRepresentation() {
    }

    public ErrorRepresentation(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

}
