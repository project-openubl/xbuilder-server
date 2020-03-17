package org.openubl.xmlbuilder.test;

public enum UBLDocumentType {
    INVOICE("invoice"),
    CREDIT_NOTE("credit-note"),
    DEBIT_NOTE("debit-note"),
    PERCEPTION("perception"),
    RETENTION("retention"),
    DISPATCH_ADVICE("retention");

    private final String type;

    UBLDocumentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
