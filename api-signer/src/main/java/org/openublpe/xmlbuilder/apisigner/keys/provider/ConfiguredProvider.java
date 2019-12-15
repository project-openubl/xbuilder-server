package org.openublpe.xmlbuilder.apisigner.keys.provider;

import java.util.List;

public interface ConfiguredProvider {
    String getHelpText();

    List<ProviderConfigProperty> getConfigProperties();
}
