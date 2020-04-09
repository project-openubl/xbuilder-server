/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openublpe.xmlbuilder.rules.factory;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog10;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog19;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog5;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog7_1;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog9;
import org.openublpe.xmlbuilder.core.models.input.standard.DocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.NoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.*;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentImpuestosOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentLineImpuestosOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentLineOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentMonetaryTotalOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.ImpuestoDetalladoOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.ImpuestoOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.ImpuestoTotalICBOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.ImpuestoTotalOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.invoice.InvoiceOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.note.NoteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.note.creditNote.CreditNoteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.note.debitNote.DebitNoteOutputModel;
import org.openublpe.xmlbuilder.core.models.output.sunat.*;
import org.openublpe.xmlbuilder.rules.EnvironmentVariables;
import org.openublpe.xmlbuilder.rules.datetime.DateTimeFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
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

    @ConfigProperty(name = EnvironmentVariables.DEFAULT_TIPO_NOTA_CREDITO)
    String defaultTipoNotaCredito;

    @ConfigProperty(name = EnvironmentVariables.DEFAULT_TIPO_NOTA_DEBITO)
    String defaultTipoNotaDebito;

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

    public CreditNoteOutputModel getCreditNoteOutput(CreditNoteInputModel input) {
        CreditNoteOutputModel.Builder builder = CreditNoteOutputModel.Builder.aCreditNoteOutputModel()
                .withTipoNota(
                        input.getTipoNota() != null
                                ? Catalog.valueOfCode(Catalog9.class, input.getTipoNota()).orElseThrow(Catalog.invalidCatalogValue)
                                : Catalog.valueOfCode(Catalog9.class, defaultTipoNotaCredito).orElseThrow(Catalog.invalidCatalogValue)
                );

        enrichNote(input, builder);
        enrichDocument(input, builder);
        return builder.build();
    }

    public DebitNoteOutputModel getDebitNoteOutput(DebitNoteInputModel input) {
        DebitNoteOutputModel.Builder builder = DebitNoteOutputModel.Builder.aDebitNoteOutputModel()
                .withTipoNota(
                        input.getTipoNota() != null
                                ? Catalog.valueOfCode(Catalog10.class, input.getTipoNota()).orElseThrow(Catalog.invalidCatalogValue)
                                : Catalog.valueOfCode(Catalog10.class, defaultTipoNotaDebito).orElseThrow(Catalog.invalidCatalogValue)
                );

        enrichNote(input, builder);
        enrichDocument(input, builder);
        return builder.build();
    }

    public VoidedDocumentOutputModel getVoidedDocument(VoidedDocumentInputModel input) {
        VoidedDocumentOutputModel.Builder builder = VoidedDocumentOutputModel.Builder.aVoidedDocumentOutputModel();

        // datos generales
        String fechaEmision = input.getFechaEmision() != null
                ? toGregorianCalendarDate(input.getFechaEmision())
                : toGregorianCalendarDate(dateTimeFactory.getCurrent().getTimeInMillis());

        builder.withFechaEmision(fechaEmision)
                .withDescripcionSustento(input.getDescripcionSustento())
                .withProveedor(ProveedorFactory.getProveedor(input.getProveedor()))
                .withFirmante(
                        input.getFirmante() != null
                                ? FirmanteFactory.getFirmante(input.getFirmante())
                                : FirmanteFactory.getFirmante(input.getProveedor())
                );

        // Comprobante
        VoidedDocumentLineInputModel comprobanteAfectado = input.getComprobante();

        String[] serieNumeroComprobanteAfectado = comprobanteAfectado.getSerieNumero().split("-");
        Catalog1 tipoComprobanteAfectado = Catalog.valueOfCode(Catalog1.class, comprobanteAfectado.getTipoComprobante()).orElseThrow(Catalog.invalidCatalogValue);

        builder.withComprobante(VoidedDocumentLineOutputModel.Builder.aVoidedDocumentLineOutputModel()
                .withFechaEmision(toGregorianCalendarDate(comprobanteAfectado.getFechaEmision()))
                .withTipoComprobante(tipoComprobanteAfectado)
                .withSerie(serieNumeroComprobanteAfectado[0])
                .withNumero(serieNumeroComprobanteAfectado[1])
                .build()
        );

        // Serie
        String codigo;
        switch (tipoComprobanteAfectado) {
            case PERCEPCION:
            case RETENCION:
            case GUIA_REMISION_REMITENTE:
                codigo = "RR";
                break;
            default:
                codigo = "RA";
        }

        builder.withSerieNumero(
                MessageFormat.format("{0}-{1}-{2}", codigo, fechaEmision.replaceAll("-", ""), input.getNumero())
        );

        return builder.build();
    }

    public SummaryDocumentOutputModel getSummaryDocument(SummaryDocumentInputModel input) {
        Function<SummaryDocumentImpuestosInputModel, SummaryDocumentImpuestosOutputModel> summaryLineComprobanteImpuestosFn = inputImpuestos -> {
            SummaryDocumentImpuestosOutputModel.Builder builder = SummaryDocumentImpuestosOutputModel.Builder.aSummaryDocumentImpuestosOutputModel()
                    .withIgv(ImpuestoTotalResumenDiarioOutputModel.Builder.anImpuestoTotalResumenDiarioOutputModel()
                            .withImporte(inputImpuestos.getIgv())
                            .withCategoria(Catalog5.IGV)
                            .build()
                    );
            if (inputImpuestos.getIcb() != null) {
                builder.withIcb(ImpuestoTotalResumenDiarioOutputModel.Builder.anImpuestoTotalResumenDiarioOutputModel()
                        .withImporte(inputImpuestos.getIcb())
                        .withCategoria(Catalog5.ICBPER)
                        .build()
                );
            }
            return builder.build();
        };

        Function<SummaryDocumentComprobanteValorVentaInputModel, SummaryDocumentComprobanteValorVentaOutputModel> summaryLineComprobanteValorVentaFn = inputValorVenta -> {
            SummaryDocumentComprobanteValorVentaOutputModel.Builder builder = SummaryDocumentComprobanteValorVentaOutputModel.Builder.aSummaryDocumentComprobanteValorVentaOutputModel()
                    .withImporteTotal(inputValorVenta.getImporteTotal())
                    .withOtrosCargos(inputValorVenta.getOtrosCargos());
            if (inputValorVenta.getGravado() != null) {
                builder.withGravado(TotalValorVentaOutputModel.Builder.aTotalValorVentaOutputModel()
                        .withTipo(Catalog7_1.GRAVADO)
                        .withImporte(inputValorVenta.getGravado())
                        .build()
                );
            }
            if (inputValorVenta.getExonerado() != null) {
                builder.withExonerado(TotalValorVentaOutputModel.Builder.aTotalValorVentaOutputModel()
                        .withTipo(Catalog7_1.EXONERADO)
                        .withImporte(inputValorVenta.getExonerado())
                        .build()
                );
            }
            if (inputValorVenta.getInafecto() != null) {
                builder.withInafecto(TotalValorVentaOutputModel.Builder.aTotalValorVentaOutputModel()
                        .withTipo(Catalog7_1.INAFECTO)
                        .withImporte(inputValorVenta.getInafecto())
                        .build()
                );
            }
            if (inputValorVenta.getGratuito() != null) {
                builder.withGratuito(TotalValorVentaOutputModel.Builder.aTotalValorVentaOutputModel()
                        .withTipo(Catalog7_1.GRATUITA)
                        .withImporte(inputValorVenta.getGratuito())
                        .build()
                );
            }
            return builder.build();
        };

        Function<SummaryDocumentComprobanteInputModel, SummaryDocumentComprobanteOutputModel> summaryLineComprobanteFn = inputComprobante -> SummaryDocumentComprobanteOutputModel.Builder.aSummaryDocumentComprobanteOutputModel()
                .withTipo(
                        Catalog.valueOfCode(Catalog1.class, inputComprobante.getTipo()).orElseThrow(Catalog.invalidCatalogValue)
                )
                .withSerieNumero(inputComprobante.getSerieNumero())
                .withCliente(ClienteFactory.getCliente(inputComprobante.getCliente()))
                .withImpuestos(summaryLineComprobanteImpuestosFn.apply(inputComprobante.getImpuestos()))
                .withValorVenta(summaryLineComprobanteValorVentaFn.apply(inputComprobante.getValorVenta()))
                .build();

        Function<SummaryDocumentLineInputModel, SummaryDocumentLineOutputModel> summaryLineFn = inputLineFn -> {
            SummaryDocumentLineOutputModel.Builder builder = SummaryDocumentLineOutputModel.Builder.aSummaryDocumentLineOutputModel()
                    .withTipoOperacion(
                            Catalog.valueOfCode(Catalog19.class, inputLineFn.getTipoOperacion()).orElseThrow(Catalog.invalidCatalogValue)
                    )
                    .withComprobante(summaryLineComprobanteFn.apply(inputLineFn.getComprobante()));
            if (inputLineFn.getComprobanteAfectado() != null) {
                builder.withComprobanteAfectado(SummaryDocumentComprobanteAfectadoOutputModel.Builder.aSummaryDocumentComprobanteAfectadoOutputModel()
                        .withSerieNumero(inputLineFn.getComprobanteAfectado().getSerieNumero())
                        .withTipo(
                                Catalog.valueOfCode(Catalog1.class, inputLineFn.getComprobanteAfectado().getTipo()).orElseThrow(Catalog.invalidCatalogValue)
                        )
                        .build()
                );
            }

            return builder.build();
        };


        //


        String fechaEmision = input.getFechaEmision() != null
                ? toGregorianCalendarDate(input.getFechaEmision())
                : toGregorianCalendarDate(dateTimeFactory.getCurrent().getTimeInMillis());
        String serieNumero = MessageFormat.format("RC-{0}-{1}", fechaEmision.replaceAll("-", ""), input.getNumero());

        return SummaryDocumentOutputModel.Builder.aSummaryDocumentOutputModel()
                .withMoneda(defaultMoneda)
                .withFechaEmision(fechaEmision)
                .withSerieNumero(serieNumero)
                .withFechaEmisionDeComprobantesAsociados(
                        toGregorianCalendarDate(input.getFechaEmisionDeComprobantesAsociados())
                )
                .withProveedor(ProveedorFactory.getProveedor(input.getProveedor()))
                .withFirmante(
                        input.getFirmante() != null
                                ? FirmanteFactory.getFirmante(input.getFirmante())
                                : FirmanteFactory.getFirmante(input.getProveedor())
                )
                .withDetalle(input.getDetalle().stream()
                        .map(summaryLineFn)
                        .collect(Collectors.toList())
                )
                .build();
    }

    public PerceptionOutputModel getPerception(PerceptionInputModel input) {
        return null;
    }

    public RetentionOutputModel getRetention(RetentionInputModel input) {
        return null;
    }

    // Enrich

    private void enrichDocument(DocumentInputModel input, DocumentOutputModel.Builder builder) {
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
        BigDecimal importeTotalImpuestosIgv = lineOutput.stream()
                .map(DocumentLineOutputModel::getImpuestos)
                .filter(p -> p.getIgv().getCategoria().equals(Catalog5.IGV) ||
                        p.getIgv().getCategoria().equals(Catalog5.IMPUESTO_ARROZ_PILADO)
                )
                .map(m -> m.getIgv().getImporte())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal importeTotalImpuestosIcb = lineOutput.stream()
                .map(DocumentLineOutputModel::getImpuestos)
                .map(DocumentLineImpuestosOutputModel::getIcb)
                .filter(Objects::nonNull)
                .map(ImpuestoOutputModel::getImporte)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        impuestosBuilder.withImporteTotal(
                importeTotalImpuestosIgv.add(importeTotalImpuestosIcb)
        );

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

        // ICB
        BigDecimal icbImporte = lineOutput.stream()
                .map(DocumentLineOutputModel::getImpuestos)
                .map(DocumentLineImpuestosOutputModel::getIcb)
                .filter(Objects::nonNull)
                .map(ImpuestoOutputModel::getImporte)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (icbImporte.compareTo(BigDecimal.ZERO) > 0) {
            impuestosBuilder.withIcb(ImpuestoTotalICBOutputModel.Builder.anImpuestoTotalICBOutputModel()
                    .withCategoria(Catalog5.ICBPER)
                    .withImporte(icbImporte)
                    .build()
            );
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

    private void enrichNote(NoteInputModel input, NoteOutputModel.Builder builder) {
        builder.withSerieNumeroComprobanteAfectado(input.getSerieNumeroComprobanteAfectado())
                .withDescripcionSustentoDeNota(input.getDescripcionSustentoDeNota());

        if (input.getSerieNumeroComprobanteAfectado().matches("^[F|b].*$")) {
            builder.withTipoDocumentoComprobanteAfectado(Catalog1.FACTURA);
        } else if (input.getSerie().matches("^[B|b].*$")) {
            builder.withTipoDocumentoComprobanteAfectado(Catalog1.BOLETA);
        } else {
            throw new IllegalStateException("Invalid Serie");
        }
    }

    private ImpuestoTotalOutputModel getImpuestoTotal(List<DocumentLineOutputModel> lineOutput, Catalog5 categoria) {
        java.util.function.Supplier<Stream<DocumentLineOutputModel>> stream = () -> lineOutput.stream()
                .filter(i -> i.getImpuestos().getIgv().getTipo().getTaxCategory().equals(categoria));

        BigDecimal importe = stream.get()
                .map(DocumentLineOutputModel::getImpuestos)
                .map(DocumentLineImpuestosOutputModel::getIgv)
                .map(ImpuestoOutputModel::getImporte)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal baseImponible = stream.get()
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
