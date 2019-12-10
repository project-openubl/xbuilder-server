package org.openublpe.xmlbuilder.inputData.homologacion;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog5;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog7;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog7_1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HomologacionUtils {

    public static final Random random = new Random();
    public static final List<String> TIPO_IGV_INAFECTA_O_EXONERADA = Stream.of(Catalog7.values())
            .filter(p -> p.getGrupo().equals(Catalog7_1.INAFECTO) || p.getGrupo().equals(Catalog7_1.EXONERADO))
            .map(Catalog7::toString)
            .collect(Collectors.toList());

    public static final List<String> TIPO_IGV_GRATUITA = Stream.of(Catalog7.values())
            .filter(p -> p.getTaxCategory().equals(Catalog5.GRATUITO))
            .filter(p -> !p.equals(Catalog7.GRAVADO_IVAP)) // IVAP is a special case, it should use IGV 17 instead of 18
            .map(Catalog7::toString)
            .collect(Collectors.toList());

    private HomologacionUtils() {
        // Just static classess
    }

    public static BigDecimal cantidadRandom() {
        int valor = random.nextInt(10_000) / 1000;
        return new BigDecimal(valor).abs()
                .add(BigDecimal.TEN)
                .setScale(3, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal precioUnitarioRandom() {
        int valor = random.nextInt(1_000) / 100;
        return new BigDecimal(valor).abs()
                .add(BigDecimal.TEN)
                .setScale(3, RoundingMode.HALF_EVEN);
    }

    public static String tipoIGVInafectaExoneradaRandom() {
        return TIPO_IGV_INAFECTA_O_EXONERADA.get(random.nextInt(TIPO_IGV_INAFECTA_O_EXONERADA.size()));
    }

    public static String tipoIGVGratuita() {
        return TIPO_IGV_GRATUITA.get(random.nextInt(TIPO_IGV_GRATUITA.size()));
    }
}
