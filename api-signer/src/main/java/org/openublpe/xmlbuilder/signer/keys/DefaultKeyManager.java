package org.openublpe.xmlbuilder.signer.keys;

import org.jboss.logging.Logger;
import org.keycloak.jose.jws.AlgorithmType;
import org.openublpe.xmlbuilder.signer.keys.component.ComponentModel;
import org.openublpe.xmlbuilder.signer.keys.component.utils.ComponentProviderLiteral;
import org.openublpe.xmlbuilder.signer.keys.component.utils.ComponentUtil;
import org.openublpe.xmlbuilder.signer.keys.component.utils.RsaKeyProviderLiteral;
import org.openublpe.xmlbuilder.signer.keys.qualifiers.RsaKeyType;
import org.openublpe.xmlbuilder.signer.models.ComponentProvider;
import org.openublpe.xmlbuilder.signer.models.KeyManager;
import org.openublpe.xmlbuilder.signer.models.OrganizationModel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DefaultKeyManager implements KeyManager {

    private static final Logger logger = Logger.getLogger(DefaultKeyManager.class);

    @Inject
    @Any
    Instance<KeyProviderFactory> keyProviderFactories;

    @Inject
    ComponentProvider componentProvider;

    @Inject
    ComponentUtil componentUtil;

    @Inject
    @Any
    Instance<KeyProviderFactory> getKeyProviderFactories;

    @Override
    public ActiveRsaKey getActiveRsaKey(OrganizationModel organization) {
        for (KeyProvider p : getProviders(organization)) {
            if (p.getType().equals(AlgorithmType.RSA)) {
                RsaKeyProvider r = (RsaKeyProvider) p;
                if (r.getKid() != null && r.getPrivateKey() != null) {
                    if (logger.isTraceEnabled()) {
                        logger.tracev("Active key organization={0} kid={1}", organization.getName(), p.getKid());
                    }
                    String kid = p.getKid();
                    return new ActiveRsaKey(kid, r.getPrivateKey(), r.getPublicKey(kid), r.getCertificate(kid));
                }
            }
        }
        throw new RuntimeException("Failed to get RSA keys");
    }

    @Override
    public PublicKey getRsaPublicKey(OrganizationModel realm, String kid) {
        if (kid == null) {
            logger.warnv("KID is null, can't find public key", realm.getName(), kid);
            return null;
        }

        for (KeyProvider p : getProviders(realm)) {
            if (p.getType().equals(AlgorithmType.RSA)) {
                RsaKeyProvider r = (RsaKeyProvider) p;
                PublicKey publicKey = r.getPublicKey(kid);
                if (publicKey != null) {
                    if (logger.isTraceEnabled()) {
                        logger.tracev("Found public key realm={0} kid={1}", realm.getName(), kid);
                    }
                    return publicKey;
                }
            }
        }
        if (logger.isTraceEnabled()) {
            logger.tracev("Failed to find public key realm={0} kid={1}", realm.getName(), kid);
        }
        return null;
    }

    @Override
    public Certificate getRsaCertificate(OrganizationModel realm, String kid) {
        if (kid == null) {
            logger.warnv("KID is null, can't find public key", realm.getName(), kid);
            return null;
        }

        for (KeyProvider p : getProviders(realm)) {
            if (p.getType().equals(AlgorithmType.RSA)) {
                RsaKeyProvider r = (RsaKeyProvider) p;
                Certificate certificate = r.getCertificate(kid);
                if (certificate != null) {
                    if (logger.isTraceEnabled()) {
                        logger.tracev("Found certificate realm={0} kid={1}", realm.getName(), kid);
                    }
                    return certificate;
                }
            }
        }
        if (logger.isTraceEnabled()) {
            logger.tracev("Failed to find certificate realm={0} kid={1}", realm.getName(), kid);
        }
        return null;
    }

    @Override
    public List<RsaKeyMetadata> getRsaKeys(OrganizationModel organization, boolean includeDisabled) {
        List<RsaKeyMetadata> keys = new LinkedList<>();
        for (KeyProvider p : getProviders(organization)) {
            if (p instanceof RsaKeyProvider) {
                if (includeDisabled) {
                    keys.addAll(p.getKeyMetadata());
                } else {
                    List<RsaKeyMetadata> metadata = p.getKeyMetadata();
                    metadata.stream().filter(k -> k.getStatus() != KeyMetadata.Status.DISABLED).forEach(k -> keys.add(k));
                }
            }
        }
        return keys;
    }

    private List<KeyProvider> getProviders(OrganizationModel organization) {
        List<KeyProvider> providers = new LinkedList<>();

        List<ComponentModel> components = new LinkedList<>(componentProvider.getComponents(organization, organization.getId(), KeyProvider.class.getName()));
        components.sort(new ProviderComparator());

        boolean activeRsa = false;

        for (ComponentModel c : components) {
            try {

                Optional<RsaKeyType> op = RsaKeyType.findByProviderId(c.getProviderId());
                if (!op.isPresent()) {
                    return null;
                }
                Annotation componentProviderLiteral = new ComponentProviderLiteral(KeyProvider.class);
                Annotation rsaKeyProviderLiteral = new RsaKeyProviderLiteral(op.get());

                KeyProviderFactory factory = getKeyProviderFactories.select(componentProviderLiteral, rsaKeyProviderLiteral).get();
                KeyProvider provider = factory.create(organization, c);
                providers.add(provider);
                if (provider.getType().equals(AlgorithmType.RSA)) {
                    RsaKeyProvider r = (RsaKeyProvider) provider;
                    if (r.getKid() != null && r.getPrivateKey() != null) {
                        activeRsa = true;
                    }
                }
            } catch (Throwable t) {
                logger.errorv(t, "Failed to load provider {0}", c.getId());
            }
        }

        if (!activeRsa) {
            providers.add(new FailsafeRsaKeyProvider());
        }

        return providers;
    }

    private class ProviderComparator implements Comparator<ComponentModel> {
        @Override
        public int compare(ComponentModel o1, ComponentModel o2) {
            int i = Long.compare(o2.get("priority", 0l), o1.get("priority", 0l));
            return i != 0 ? i : o1.getId().compareTo(o2.getId());
        }
    }
}
