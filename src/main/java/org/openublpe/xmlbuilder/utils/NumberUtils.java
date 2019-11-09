package org.openublpe.xmlbuilder.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {

    public static BigDecimal format2Digists(BigDecimal number) {
        return number.setScale(2, RoundingMode.HALF_EVEN);
    }
}
