package org.openublpe.xmlbuilder.signer.keys.provider;

/**
 * At boot time, openfact discovers all factories.  For each discovered factory, the init() method is called.  After
 * all factories have been initialized, the postInit() method is called.  close() is called when the server shuts down.
 * <p>
 * Only one instance of a factory exists per server.
 */
public interface ProviderFactory<T> {

    T create();

    String getId();

}
