package org.openublpe.xmlbuilder.core.models.utils;

import java.util.regex.Pattern;

public class RegexUtils {

    public static final Pattern FACTURA_SERIE_REGEX = Pattern.compile("^[F|f].*$");
    public static final Pattern BOLETA_SERIE_REGEX = Pattern.compile("^[B|b].*$");

    private RegexUtils() {
        // Just static methods
    }
}
