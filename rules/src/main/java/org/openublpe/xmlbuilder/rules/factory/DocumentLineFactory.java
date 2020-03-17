package org.openublpe.xmlbuilder.rules.factory;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog16;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog5;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog7;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog7_1;
import org.openublpe.xmlbuilder.core.models.input.standard.DocumentLineInputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentLineImpuestosOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentLineOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentLinePrecioReferenciaOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.ImpuestoDetalladoIGVOutputModel;
import org.openublpe.xmlbuilder.rules.EnvironmentVariables;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.math.RoundingMode;

@ApplicationScoped
public class DocumentLineFactory {

    @ConfigProperty(name = EnvironmentVariables.IGV_KEY)
    BigDecimal igv;

    @ConfigProperty(name = EnvironmentVariables.IVAP_KEY)
    BigDecimal ivap;

    @ConfigProperty(name = EnvironmentVariables.DEFAULT_UNIDAD_MEDIDA)
    String defaultUnidadMedida;

    @ConfigProperty(name = EnvironmentVariables.DEFAULT_TIPO_IGV)
    String defaultTipoIgv;

    public DocumentLineOutputModel getDocumentLineOutput(DocumentLineInputModel input) {
        DocumentLineOutputModel.Builder builder = DocumentLineOutputModel.Builder.aDocumentLineOutputModel()
                .withDescripcion(input.getDescripcion())
                .withUnidadMedida(input.getUnidadMedida() != null ? input.getUnidadMedida() : defaultUnidadMedida)
                .withCantidad(input.getCantidad())
                .withPrecioSinImpuestos(input.getPrecioSinImpuestos())
                .withPrecioConImpuestos(input.getPrecioConImpuestos());

        // Impuestos
        DocumentLineImpuestosOutputModel impuestosOutput;
        if (input.getPrecioSinImpuestos() != null) {
            impuestosOutput = getDocumentLineImpuestosOutput_LeftRight(input);
        } else if (input.getPrecioConImpuestos() != null) {
            impuestosOutput = getDocumentLineImpuestosOutput_RightLeft(input);
        } else {
            throw new IllegalStateException("Precio con impuestos y/o sin impuestos no encontrado, no se pueden calcular los impuestos");
        }
        builder.withImpuestos(impuestosOutput);

        // Precio con/sin impuestos
        BigDecimal precioSinImpuestos;
        BigDecimal precioConImpuestos;

        if (input.getPrecioSinImpuestos() != null) {
            precioSinImpuestos = input.getPrecioSinImpuestos();
            precioConImpuestos = input.getPrecioSinImpuestos().multiply(input.getCantidad())
                    .add(impuestosOutput.getImporteTotal())
                    .divide(input.getCantidad(), 2, BigDecimal.ROUND_HALF_EVEN);
        } else if (input.getPrecioConImpuestos() != null) {
            precioSinImpuestos = input.getPrecioConImpuestos().multiply(input.getCantidad())
                    .subtract(impuestosOutput.getImporteTotal())
                    .divide(input.getCantidad(), 2, BigDecimal.ROUND_HALF_EVEN);
            precioConImpuestos = input.getPrecioConImpuestos();
        } else {
            throw new IllegalStateException("Precio con impuestos y/o sin impuestos no encontrado, no se pueden calcular el precion con impuestos");
        }

        builder.withPrecioConImpuestos(precioConImpuestos)
                .withPrecioSinImpuestos(
                        // Trick to make <cbc:PriceAmount>0</cbc:PriceAmount>
                        impuestosOutput.getIgv().getTipo().isOperacionOnerosa() ? precioSinImpuestos : BigDecimal.ZERO
                );


        // Precio de referencia
        builder.withPrecioDeReferencia(DocumentLinePrecioReferenciaOutputModel.Builder.aDetallePrecioReferenciaOutputModel()
                .withPrecio(precioConImpuestos)
                .withTipoPrecio(
                        impuestosOutput.getIgv().getTipo().isOperacionOnerosa()
                                ? Catalog16.PRECIO_UNITARIO_INCLUYE_IGV
                                : Catalog16.VALOR_FERENCIAL_UNITARIO_EN_OPERACIONES_NO_ONEROSAS
                )
                .build()
        );

        // Valor de venta (sin impuestos)
        BigDecimal valorVenta = input.getCantidad().multiply(precioConImpuestos).setScale(2, RoundingMode.HALF_EVEN);
        ;
        if (impuestosOutput.getIgv().getTipo().isOperacionOnerosa()) {
            valorVenta = valorVenta.subtract(impuestosOutput.getImporteTotal());
        }
        builder.withValorVentaSinImpuestos(valorVenta);

        return builder.build();
    }

    private DocumentLineImpuestosOutputModel getDocumentLineImpuestosOutput_LeftRight(DocumentLineInputModel input) {
        BigDecimal subtotal = input.getCantidad().multiply(input.getPrecioSinImpuestos()).setScale(2, RoundingMode.HALF_EVEN);

        // IGV
        Catalog7 igvTipo = input.getTipoIgv() != null
                ? Catalog.valueOfCode(Catalog7.class, input.getTipoIgv()).orElseThrow(Catalog.invalidCatalogValue)
                : Catalog.valueOfCode(Catalog7.class, defaultTipoIgv).orElseThrow(Catalog.invalidCatalogValue);

        BigDecimal igvValor;
        if (igvTipo.getGrupo().equals(Catalog7_1.GRAVADO)) {
            igvValor = igvTipo.equals(Catalog7.GRAVADO_IVAP) ? ivap : igv;
        } else {
            igvValor = BigDecimal.ZERO;
        }

        BigDecimal igvBaseImponible = subtotal.add(BigDecimal.ZERO);
        BigDecimal igvImporte = igvBaseImponible.multiply(igvValor).setScale(2, RoundingMode.HALF_EVEN);

        return DocumentLineImpuestosOutputModel.Builder.aDocumentLineImpuestosOutputModel()
                .withIgv(ImpuestoDetalladoIGVOutputModel.Builder.anImpuestoDetalladoIGVOutputModel()
                        .withTipo(igvTipo)
                        .withCategoria(igvTipo.getTaxCategory())
                        .withBaseImponible(igvBaseImponible)
                        .withImporte(igvImporte)
                        .withPorcentaje(igvValor.multiply(new BigDecimal("100")))
                        .build()
                )
                .withImporteTotal(igvImporte)
                .build();
    }

    private DocumentLineImpuestosOutputModel getDocumentLineImpuestosOutput_RightLeft(DocumentLineInputModel input) {
        BigDecimal total = input.getCantidad().multiply(input.getPrecioConImpuestos()).setScale(2, RoundingMode.HALF_EVEN);

        // IGV
        Catalog7 igvTipo = input.getTipoIgv() != null
                ? Catalog.valueOfCode(Catalog7.class, input.getTipoIgv()).orElseThrow(Catalog.invalidCatalogValue)
                : Catalog.valueOfCode(Catalog7.class, defaultTipoIgv).orElseThrow(Catalog.invalidCatalogValue);

        BigDecimal igvValor;
        if (igvTipo.getGrupo().equals(Catalog7_1.GRAVADO)) {
            igvValor = igvTipo.equals(Catalog7.GRAVADO_IVAP) ? ivap : igv;
        } else {
            igvValor = BigDecimal.ZERO;
        }

        BigDecimal igvBaseImponible = total.divide(igvValor.add(BigDecimal.ONE), 2, RoundingMode.HALF_EVEN);
        BigDecimal igvImporte = total.subtract(igvBaseImponible);

        return DocumentLineImpuestosOutputModel.Builder.aDocumentLineImpuestosOutputModel()
                .withIgv(ImpuestoDetalladoIGVOutputModel.Builder.anImpuestoDetalladoIGVOutputModel()
                        .withTipo(igvTipo)
                        .withCategoria(igvTipo.getTaxCategory())
                        .withBaseImponible(igvBaseImponible)
                        .withImporte(igvImporte)
                        .withPorcentaje(igvValor.multiply(new BigDecimal("100")))
                        .build())
                .withImporteTotal(igvImporte)
                .build();
    }
}
