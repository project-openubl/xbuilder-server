package org.openublpe.xmlbuilder.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {

    private NumberUtils() {
        //TODO just static methods
    }

    public static BigDecimal format2Digists(BigDecimal number) {
        return number.setScale(2, RoundingMode.HALF_EVEN);
    }
}
