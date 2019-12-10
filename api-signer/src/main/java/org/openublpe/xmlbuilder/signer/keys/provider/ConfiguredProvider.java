package org.openublpe.xmlbuilder.signer.keys.provider;

import java.util.List;

public interface ConfiguredProvider {
    String getHelpText();

    List<ProviderConfigProperty> getConfigProperties();
}
