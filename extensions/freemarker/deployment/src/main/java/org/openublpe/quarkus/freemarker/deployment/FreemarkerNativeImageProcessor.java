package org.openublpe.quarkus.freemarker.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.nativeimage.NativeImageResourceBuildItem;
import org.openublpe.quarkus.freemarker.FreemarkerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class FreemarkerNativeImageProcessor {
    public static final String CLASSPATH_SCHEME = "classpath:";

    @BuildStep
    List<NativeImageResourceBuildItem> freemarkerResources(FreemarkerConfig config) {
        List<NativeImageResourceBuildItem> items = new ArrayList<>(config.sources.size());

        for (String source : config.sources) {
            String scheme = getScheme(source);

            if (Objects.isNull(scheme) || Objects.equals(scheme, CLASSPATH_SCHEME)) {
                if (Objects.equals(scheme, CLASSPATH_SCHEME)) {
                    source = source.substring(CLASSPATH_SCHEME.length() + 1);
                }

                items.add(new NativeImageResourceBuildItem(source));
            }
        }

        return items;
    }

    public static String getScheme(String uri) {
        if (hasScheme(uri)) {
            return uri.substring(0, uri.indexOf(":") + 1);
        } else {
            return null;
        }
    }

    public static boolean hasScheme(String uri) {
        if (uri == null) {
            return false;
        }

        return uri.startsWith("file:") || uri.startsWith("classpath:") || uri.startsWith("http:");
    }
}
