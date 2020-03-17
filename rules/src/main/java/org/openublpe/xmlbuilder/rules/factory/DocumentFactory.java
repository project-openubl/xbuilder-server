package org.openublpe.xmlbuilder.rules.factory;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog5;
import org.openublpe.xmlbuilder.core.models.input.standard.DocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentImpuestosOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentLineImpuestosOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentLineOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentMonetaryTotalOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.ImpuestoDetalladoOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.ImpuestoOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.ImpuestoTotalOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.invoice.InvoiceOutputModel;
import org.openublpe.xmlbuilder.rules.EnvironmentVariables;
import org.openublpe.xmlbuilder.rules.datetime.DateTimeFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.openublpe.xmlbuilder.rules.utils.DateUtils.toGregorianCalendarDate;
import static org.openublpe.xmlbuilder.rules.utils.DateUtils.toGregorianCalendarTime;

@ApplicationScoped
public class DocumentFactory {

    @ConfigProperty(name = EnvironmentVariables.IGV_KEY)
    BigDecimal igv;

    @ConfigProperty(name = EnvironmentVariables.DEFAULT_MONEDA)
    String defaultMoneda;

    @Inject
    DocumentLineFactory documentLineFactory;

    @Inject
    DateTimeFactory dateTimeFactory;

    public InvoiceOutputModel getInvoiceOutput(InvoiceInputModel input) {
        InvoiceOutputModel.Builder builder = InvoiceOutputModel.Builder.anInvoiceOutputModel();

        if (input.getSerie().matches("^[F|b].*$")) {
            builder.withTipoInvoice(Catalog1.FACTURA);
        } else if (input.getSerie().matches("^[B|b].*$")) {
            builder.withTipoInvoice(Catalog1.BOLETA);
        } else {
            throw new IllegalStateException("Invalid Serie");
        }

        enrichDocument(input, builder);
        return builder.build();
    }

    private void enrichDocument(DocumentInputModel input, InvoiceOutputModel.Builder builder) {
        builder.withMoneda(defaultMoneda)
                .withSerieNumero(input.getSerie().toUpperCase() + "-" + input.getNumero());

        // Fecha y hora de emision
        long fechaEmision = input.getFechaEmision() != null ? input.getFechaEmision() : dateTimeFactory.getCurrent().getTimeInMillis();
        builder.withFechaEmision(toGregorianCalendarDate(fechaEmision))
                .withHoraEmision(toGregorianCalendarTime(fechaEmision));

        // Proveedor
        builder.withProveedor(ProveedorFactory.getProveedor(input.getProveedor()));

        // Cliente
        builder.withCliente(ClienteFactory.getCliente(input.getCliente()));

        // Firmante
        builder.withFirmante(
                input.getFirmante() != null
                        ? FirmanteFactory.getFirmante(input.getFirmante())
                        : FirmanteFactory.getFirmante(input.getProveedor())
        );

        // Detalle
        List<DocumentLineOutputModel> lineOutput = input.getDetalle().stream()
                .map(f -> documentLineFactory.getDocumentLineOutput(f))
                .collect(Collectors.toList());
        builder.withDetalle(lineOutput);


        DocumentImpuestosOutputModel.Builder impuestosBuilder = DocumentImpuestosOutputModel.Builder.aDocumentImpuestosOutputModel();

        // Importe total de impuestos
        BigDecimal importeTotalImpuestos = lineOutput.stream()
                .map(DocumentLineOutputModel::getImpuestos)
                .filter(p -> p.getIgv().getCategoria().equals(Catalog5.IGV) ||
                        p.getIgv().getCategoria().equals(Catalog5.IMPUESTO_ARROZ_PILADO)
                )
                .map(m -> m.getIgv().getImporte())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        impuestosBuilder.withImporteTotal(importeTotalImpuestos);

        // Gravado
        ImpuestoTotalOutputModel gravado = getImpuestoTotal(lineOutput, Catalog5.IGV);
        if (gravado.getBaseImponible().compareTo(BigDecimal.ZERO) > 0) {
            impuestosBuilder.withGravadas(gravado);
        }

        // Exonerado
        ImpuestoTotalOutputModel exonerado = getImpuestoTotal(lineOutput, Catalog5.EXONERADO);
        if (exonerado.getBaseImponible().compareTo(BigDecimal.ZERO) > 0) {
            impuestosBuilder.withExoneradas(exonerado);
        }

        // Inafecto
        ImpuestoTotalOutputModel inafecto = getImpuestoTotal(lineOutput, Catalog5.INAFECTO);
        if (inafecto.getBaseImponible().compareTo(BigDecimal.ZERO) > 0) {
            impuestosBuilder.withInafectas(inafecto);
        }

        // Gratuito
        ImpuestoTotalOutputModel gratuito = getImpuestoTotal(lineOutput, Catalog5.GRATUITO);
        if (gratuito.getBaseImponible().compareTo(BigDecimal.ZERO) > 0) {
            gratuito.setImporte(BigDecimal.ZERO);
            impuestosBuilder.withInafectas(gratuito);
        }

        // IVAP
        java.util.function.Supplier<Stream<DocumentLineOutputModel>> ivapStream = () -> lineOutput.stream()
                .filter(i -> i.getImpuestos().getIgv().getTipo().getTaxCategory().equals(Catalog5.IMPUESTO_ARROZ_PILADO));

        BigDecimal ivapImporte = ivapStream.get()
                .map(DocumentLineOutputModel::getImpuestos)
                .map(DocumentLineImpuestosOutputModel::getIgv)
                .map(ImpuestoOutputModel::getImporte)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal ivapBaseImponible = ivapStream.get()
                .map(DocumentLineOutputModel::getImpuestos)
                .map(DocumentLineImpuestosOutputModel::getIgv)
                .map(ImpuestoDetalladoOutputModel::getBaseImponible)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (ivapImporte.compareTo(BigDecimal.ZERO) > 0) {
            impuestosBuilder.withIvap(ImpuestoTotalOutputModel.Builder.anImpuestoTotalOutputModel()
                    .withCategoria(Catalog5.IMPUESTO_ARROZ_PILADO)
                    .withImporte(ivapImporte)
                    .withBaseImponible(ivapBaseImponible)
                    .build());
        }

        builder.withImpuestos(impuestosBuilder.build());

        // Importe total
        BigDecimal valorVentaSinImpuestos = lineOutput.stream()
                .filter(p -> !p.getImpuestos().getIgv().getTipo().getTaxCategory().equals(Catalog5.GRATUITO))
                .map(DocumentLineOutputModel::getValorVentaSinImpuestos)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal valorVentaConImpuestos = lineOutput.stream()
                .filter(p -> !p.getImpuestos().getIgv().getTipo().getTaxCategory().equals(Catalog5.GRATUITO))
                .map(f -> f.getImpuestos().getImporteTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(valorVentaSinImpuestos);

        builder.withTotales(DocumentMonetaryTotalOutputModel.Builder.aDocumentMonetaryTotalOutputModel()
                .withValorVentaSinImpuestos(valorVentaSinImpuestos)
                .withValorVentaConImpuestos(valorVentaConImpuestos)
                .withImporteTotal(valorVentaConImpuestos)
                .build()
        );
    }

    private ImpuestoTotalOutputModel getImpuestoTotal(List<DocumentLineOutputModel> lineOutput, Catalog5 categoria){
        java.util.function.Supplier<Stream<DocumentLineOutputModel>> gravadoStream = () -> lineOutput.stream()
                .filter(i -> i.getImpuestos().getIgv().getTipo().getTaxCategory().equals(categoria));

        BigDecimal importe = gravadoStream.get()
                .map(DocumentLineOutputModel::getImpuestos)
                .map(DocumentLineImpuestosOutputModel::getIgv)
                .map(ImpuestoOutputModel::getImporte)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal baseImponible = gravadoStream.get()
                .map(DocumentLineOutputModel::getImpuestos)
                .map(DocumentLineImpuestosOutputModel::getIgv)
                .map(ImpuestoDetalladoOutputModel::getBaseImponible)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return ImpuestoTotalOutputModel.Builder.anImpuestoTotalOutputModel()
                .withCategoria(categoria)
                .withImporte(importe)
                .withBaseImponible(baseImponible)
                .build();
    }

}
