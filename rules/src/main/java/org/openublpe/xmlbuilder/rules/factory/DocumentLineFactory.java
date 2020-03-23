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
import org.openublpe.xmlbuilder.core.models.output.standard.ImpuestoDetalladoICBOutputModel;
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

    @ConfigProperty(name = EnvironmentVariables.ICB_KEY)
    BigDecimal defaultIcb;

    public DocumentLineOutputModel getDocumentLineOutput(DocumentLineInputModel input) {
        DocumentLineOutputModel.Builder builder = DocumentLineOutputModel.Builder.aDocumentLineOutputModel()
                .withDescripcion(input.getDescripcion())
                .withUnidadMedida(input.getUnidadMedida() != null ? input.getUnidadMedida() : defaultUnidadMedida)
                .withCantidad(input.getCantidad())
                .withPrecioUnitario(input.getPrecioUnitario())
                .withPrecioConIgv(input.getPrecioConIgv());

        // Impuestos
        DocumentLineImpuestosOutputModel impuestosOutput;
        if (input.getPrecioUnitario() != null) {
            impuestosOutput = getDocumentLineImpuestosOutput_LeftRight(input);
        } else if (input.getPrecioConIgv() != null) {
            impuestosOutput = getDocumentLineImpuestosOutput_RightLeft(input);
        } else {
            throw new IllegalStateException("Precio con impuestos y/o sin impuestos no encontrado, no se pueden calcular los impuestos");
        }
        builder.withImpuestos(impuestosOutput);

        // Precio con/sin impuestos
        BigDecimal precioUnitario;
        BigDecimal precioConIgv;

        if (input.getPrecioUnitario() != null) {
            precioUnitario = input.getPrecioUnitario();
            precioConIgv = input.getPrecioUnitario().multiply(input.getCantidad())
                    .add(impuestosOutput.getIgv().getImporte())
                    .divide(input.getCantidad(), 2, BigDecimal.ROUND_HALF_EVEN);
        } else if (input.getPrecioConIgv() != null) {
            precioUnitario = input.getPrecioConIgv().multiply(input.getCantidad())
                    .subtract(impuestosOutput.getIgv().getImporte())
                    .divide(input.getCantidad(), 2, BigDecimal.ROUND_HALF_EVEN);
            precioConIgv = input.getPrecioConIgv();
        } else {
            throw new IllegalStateException("Precio con impuestos y/o sin impuestos no encontrado, no se pueden calcular el precion con impuestos");
        }

        builder.withPrecioConIgv(precioConIgv)
                .withPrecioUnitario(
                        // Trick to make <cbc:PriceAmount>0</cbc:PriceAmount>
                        impuestosOutput.getIgv().getTipo().isOperacionOnerosa() ? precioUnitario : BigDecimal.ZERO
                );


        // Precio de referencia
        builder.withPrecioDeReferencia(DocumentLinePrecioReferenciaOutputModel.Builder.aDetallePrecioReferenciaOutputModel()
                .withPrecio(precioConIgv)
                .withTipoPrecio(
                        impuestosOutput.getIgv().getTipo().isOperacionOnerosa()
                                ? Catalog16.PRECIO_UNITARIO_INCLUYE_IGV
                                : Catalog16.VALOR_FERENCIAL_UNITARIO_EN_OPERACIONES_NO_ONEROSAS
                )
                .build()
        );

        // Valor de venta (sin impuestos)
        BigDecimal valorVentaSinImpuestos = input.getCantidad().multiply(precioConIgv).setScale(2, RoundingMode.HALF_EVEN);

        if (impuestosOutput.getIgv().getTipo().isOperacionOnerosa()) {
            valorVentaSinImpuestos = valorVentaSinImpuestos.subtract(impuestosOutput.getIgv().getImporte());
        }
        builder.withValorVentaSinImpuestos(valorVentaSinImpuestos);

        return builder.build();
    }

    private DocumentLineImpuestosOutputModel getDocumentLineImpuestosOutput_LeftRight(DocumentLineInputModel input) {
        DocumentLineImpuestosOutputModel.Builder builder = DocumentLineImpuestosOutputModel.Builder.aDocumentLineImpuestosOutputModel();


        BigDecimal subtotal = input.getCantidad().multiply(input.getPrecioUnitario()).setScale(2, RoundingMode.HALF_EVEN);

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

        BigDecimal igvBaseImponible = subtotal.add(BigDecimal.ZERO); // Just to copy value
        BigDecimal igvImporte = igvBaseImponible.multiply(igvValor).setScale(2, RoundingMode.HALF_EVEN);

        builder.withIgv(ImpuestoDetalladoIGVOutputModel.Builder.anImpuestoDetalladoIGVOutputModel()
                .withTipo(igvTipo)
                .withCategoria(igvTipo.getTaxCategory())
                .withBaseImponible(igvBaseImponible)
                .withImporte(igvImporte)
                .withPorcentaje(igvValor.multiply(new BigDecimal("100")))
                .build()
        );

        // ICB
        BigDecimal icbImporte = BigDecimal.ZERO;
        if (input.isIcb()) {
            BigDecimal icbValor = defaultIcb;
            icbImporte = input.getCantidad().multiply(icbValor).setScale(2, RoundingMode.HALF_EVEN);

            builder.withIcb(ImpuestoDetalladoICBOutputModel.Builder.anImpuestoDetalladoICBOutputModel()
                    .withCategoria(Catalog5.ICBPER)
                    .withIcbValor(icbValor)
                    .withImporte(icbImporte)
                    .build());
        }

        return builder.withImporteTotal(
                igvImporte.add(icbImporte)
        ).build();
    }

    private DocumentLineImpuestosOutputModel getDocumentLineImpuestosOutput_RightLeft(DocumentLineInputModel input) {
        DocumentLineImpuestosOutputModel.Builder builder = DocumentLineImpuestosOutputModel.Builder.aDocumentLineImpuestosOutputModel();


        BigDecimal total = input.getCantidad().multiply(input.getPrecioConIgv()).setScale(2, RoundingMode.HALF_EVEN);

        // ICB
        BigDecimal icbImporte = BigDecimal.ZERO;
        if (input.isIcb()) {
            BigDecimal icbValor = defaultIcb;
            icbImporte = input.getCantidad().multiply(icbValor).setScale(2, RoundingMode.HALF_EVEN);

            builder.withIcb(ImpuestoDetalladoICBOutputModel.Builder.anImpuestoDetalladoICBOutputModel()
                    .withCategoria(Catalog5.ICBPER)
                    .withIcbValor(icbValor)
                    .withImporte(icbImporte)
                    .build());
        }

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

        builder.withIgv(ImpuestoDetalladoIGVOutputModel.Builder.anImpuestoDetalladoIGVOutputModel()
                .withTipo(igvTipo)
                .withCategoria(igvTipo.getTaxCategory())
                .withBaseImponible(igvBaseImponible)
                .withImporte(igvImporte)
                .withPorcentaje(igvValor.multiply(new BigDecimal("100")))
                .build()
        );

        return builder.withImporteTotal(
                igvImporte.add(icbImporte)
        ).build();
    }
}
