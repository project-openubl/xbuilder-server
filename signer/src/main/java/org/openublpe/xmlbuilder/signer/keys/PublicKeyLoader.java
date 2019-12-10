package org.openublpe.xmlbuilder.signer.keys;

import java.security.PublicKey;
import java.util.Map;

public interface PublicKeyLoader {

    Map<String, PublicKey> loadKeys() throws Exception;

}
