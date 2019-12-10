package org.openublpe.xmlbuilder.signer.keys;

public class KeyMetadata {

    public enum Status {
        ACTIVE, PASSIVE, DISABLED
    }

    private String providerId;
    private long providerPriority;

    private String kid;

    private Status status;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public long getProviderPriority() {
        return providerPriority;
    }

    public void setProviderPriority(long providerPriority) {
        this.providerPriority = providerPriority;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
