package org.jboss.xavier.integrations.migrationanalytics.business.versioning;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Named
public class ManifestVersionService {
    protected static final String CLOUDFORMS_MANIFEST = "cloudforms.manifest.";
    protected static final String CLOUDFORMS_MANIFEST_PATTERN = CLOUDFORMS_MANIFEST + "[0-9_]*.";
    @Inject
    private Environment env;

    @Getter
    @Setter
    private Map<String, String> properties;

    @PostConstruct
    public void init() {
        properties = getAllProperties();
    }

    public String getPropertyWithFallbackVersion(String payloadVersion, String path) {
        String fallbackVersion = getFallbackVersionPath(payloadVersion, path);

        return properties.get(CLOUDFORMS_MANIFEST + fallbackVersion + "." + path);
    }

    String getFallbackVersionPath(String payloadVersion, String path) {
        ManifestVersion fallbackversion = properties.keySet()
                .stream()
                .filter(e -> e.matches(CLOUDFORMS_MANIFEST_PATTERN + path))
                .map(e -> e.split("\\.")[2])
                .map(this::expandVersion)
                .map(ManifestVersion::new)
                .sorted(Comparator.reverseOrder())
                .filter(e -> isVersionEqualOrLower(payloadVersion, e))
                .findFirst()
                .orElse(new ManifestVersion("0_0_0"));

        return fallbackversion.getFullVersion();
    }

    private boolean isVersionEqualOrLower(String payloadVersion, ManifestVersion e) {
        return e.compareTo(expandVersion(payloadVersion)) <= 0;
    }

    private Map<String, String> getAllProperties() {
        return StreamSupport.stream(((AbstractEnvironment) env).getPropertySources().spliterator(), false)
                    .filter(ps -> ps instanceof EnumerablePropertySource)
                    .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
                    .flatMap(Arrays::stream)
                .filter(e -> e.matches(CLOUDFORMS_MANIFEST_PATTERN + "*"))
                .collect(Collectors.toMap( this::getKey, e-> env.getProperty(e)));
    }

    private String getKey(String property) {
        String[] elements = property.split("\\.");
        return property.replace(elements[2], expandVersion(elements[2]));
    }

    String expandVersion(String e) {
        return e.concat(IntStream.range(1, 4 - e.split("_").length).mapToObj(f -> "_0").collect(Collectors.joining()));
    }
}
