package org.openublpe.xmlbuilder.data.homologacion;

import org.openublpe.xmlbuilder.models.ubl.Catalog7;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HomologacionUtils {

    public static final Random random = new Random();
    public static final List<String> TIPO_IGV_INAFECTA_O_EXONERADA = Stream.of(
            Catalog7.EXONERADO_OPERACION_ONEROSA, // Onerosa
            Catalog7.EXONERADO_TRANSFERENCIA_GRATUITA,
            Catalog7.INAFECTO_OPERACION_ONEROSA, // Onerosa
            Catalog7.INAFECTO_RETIRO_POR_BONIFICACION,
            Catalog7.INAFECTO_RETIRO,
            Catalog7.INAFECTO_RETIRO_POR_MUESTRAS_MEDICAS,
            Catalog7.INAFECTO_RETIRO_POR_CONVENIO_COLECTIVO,
            Catalog7.INAFECTO_RETIRO_POR_PREMIO,
            Catalog7.INAFECTO_RETIRO_POR_PUBLICIDAD
    )
            .map(Catalog7::toString)
            .collect(Collectors.toList());

    private HomologacionUtils() {
        //TODO just static classess
    }

    public static BigDecimal cantidadRandom() {
        int valor = random.nextInt(10_000) / 1000;
        return new BigDecimal(valor).abs()
                .add(BigDecimal.ONE)
                .setScale(3, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal precioUnitarioRandom() {
        int valor = random.nextInt(1_000) / 100;
        return new BigDecimal(valor).abs()
                .add(BigDecimal.ONE)
                .setScale(3, RoundingMode.HALF_EVEN);
    }

    public static String tipoIGVInafectaExoneradaRandom() {
        return TIPO_IGV_INAFECTA_O_EXONERADA.get(random.nextInt(TIPO_IGV_INAFECTA_O_EXONERADA.size()));
    }
}
