package org.openublpe.xmlbuilder.apisigner.keys;

import org.openublpe.xmlbuilder.apisigner.keys.provider.ProviderConfigProperty;

public interface Attributes {

    String PRIORITY_KEY = "priority";
    ProviderConfigProperty PRIORITY_PROPERTY = new ProviderConfigProperty(PRIORITY_KEY, "Priority", "Priority for the provider", ProviderConfigProperty.STRING_TYPE, "0");

    String ENABLED_KEY = "enabled";
    ProviderConfigProperty ENABLED_PROPERTY = new ProviderConfigProperty(ENABLED_KEY, "Enabled", "Set if the keys are enabled", ProviderConfigProperty.BOOLEAN_TYPE, "true");

    String ACTIVE_KEY = "active";
    ProviderConfigProperty ACTIVE_PROPERTY = new ProviderConfigProperty(ACTIVE_KEY, "Active", "Set if the keys can be used for signing", ProviderConfigProperty.BOOLEAN_TYPE, "true");

    String PRIVATE_KEY_KEY = "privateKey";
    ProviderConfigProperty PRIVATE_KEY_PROPERTY = new ProviderConfigProperty(PRIVATE_KEY_KEY, "Private RSA Key", "Private RSA Key encoded in PEM format", ProviderConfigProperty.FILE_TYPE, null, true);

    String CERTIFICATE_KEY = "certificate";
    ProviderConfigProperty CERTIFICATE_PROPERTY = new ProviderConfigProperty(CERTIFICATE_KEY, "X509 Certificate", "X509 Certificate encoded in PEM format", ProviderConfigProperty.FILE_TYPE, null);

    String KEY_SIZE_KEY = "keySize";
    ProviderConfigProperty KEY_SIZE_PROPERTY = new ProviderConfigProperty(KEY_SIZE_KEY, "Key size", "Size for the generated keys", ProviderConfigProperty.LIST_TYPE, "2048", "1024", "2048", "4096");

    String KID_KEY = "kid";

    String SECRET_KEY = "secret";

    String SECRET_SIZE_KEY = "secretSize";
    ProviderConfigProperty SECRET_SIZE_PROPERTY = new ProviderConfigProperty(SECRET_SIZE_KEY, "Secret size", "Size in bytes for the generated secret", ProviderConfigProperty.LIST_TYPE, "32", "32", "64", "128", "256", "512");

}
