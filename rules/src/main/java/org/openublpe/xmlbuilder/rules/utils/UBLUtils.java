package org.openublpe.xmlbuilder.rules.utils;

import java.util.regex.Pattern;

public class UBLUtils {

    public static final Pattern FACTURA_SERIE_REGEX = Pattern.compile("^[F|f].*$");
    public static final Pattern BOLETA_SERIE_REGEX = Pattern.compile("^[B|b].*$");

    private UBLUtils() {
        // Just static methods
    }
}
