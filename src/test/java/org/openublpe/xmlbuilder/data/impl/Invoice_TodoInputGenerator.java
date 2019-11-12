package org.openublpe.xmlbuilder.data.impl;

import org.openublpe.xmlbuilder.data.AbstractInvoiceInputGenerator;
import org.openublpe.xmlbuilder.data.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.models.input.general.DetalleInputModel;
import org.openublpe.xmlbuilder.models.input.general.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.ubl.Catalog7;

import java.math.BigDecimal;
import java.util.Optional;

public class Invoice_TodoInputGenerator extends AbstractInvoiceInputGenerator implements InvoiceInputGenerator {

    @Override
    public InvoiceInputModel getInput() {
        InvoiceInputModel input = getInvoiceTemplate();


        DetalleInputModel item9 = new DetalleInputModel();
        input.getDetalle().add(item9);
        item9.setDescripcion("item9");
        item9.setCantidad(new BigDecimal("10"));
        item9.setPrecioUnitario(new BigDecimal("20"));
        item9.setTipoIGV(Catalog7.GRAVADO_OPERACION_ONEROSA.toString());

        DetalleInputModel item10 = new DetalleInputModel();
        input.getDetalle().add(item10);
        item10.setDescripcion("item");
        item10.setCantidad(new BigDecimal("10"));
        item10.setPrecioUnitario(new BigDecimal("20"));
        item10.setTipoIGV(Catalog7.GRAVADO_RETIRO_POR_PREMIO.toString());
//
//        DetalleInputModel item11 = new DetalleInputModel();
//        input.getDetalle().add(item11);
//        item11.setDescripcion("item11");
//        item11.setCantidad(new BigDecimal("10"));
//        item11.setPrecioUnitario(new BigDecimal("20"));
//        item11.setTipoIGV(Catalog7.GRAVADO_RETIRO_POR_DONACION.toString());
//
//        DetalleInputModel item12 = new DetalleInputModel();
//        input.getDetalle().add(item12);
//        item12.setDescripcion("item");
//        item12.setCantidad(new BigDecimal("10"));
//        item12.setPrecioUnitario(new BigDecimal("20"));
//        item12.setTipoIGV(Catalog7.GRAVADO_RETIRO.toString());
//
//        DetalleInputModel item13 = new DetalleInputModel();
//        input.getDetalle().add(item13);
//        item13.setDescripcion("item");
//        item13.setCantidad(new BigDecimal("10"));
//        item13.setPrecioUnitario(new BigDecimal("20"));
//        item13.setTipoIGV(Catalog7.GRAVADO_RETIRO_POR_PUBLICIDAD.toString());
//
//        DetalleInputModel item14 = new DetalleInputModel();
//        input.getDetalle().add(item14);
//        item14.setDescripcion("item");
//        item14.setCantidad(new BigDecimal("10"));
//        item14.setPrecioUnitario(new BigDecimal("20"));
//        item14.setTipoIGV(Catalog7.GRAVADO_BONIFICACIONES.toString());

//        DetalleInputModel item15 = new DetalleInputModel();
//        input.getDetalle().add(item15);
//        item15.setDescripcion("item");
//        item15.setCantidad(new BigDecimal("10"));
//        item15.setPrecioUnitario(new BigDecimal("20"));
//        item15.setTipoIGV(Catalog7.GRAVADO_RETIRO_POR_ENTREGA_A_TRABAJADORES.toString());

//        DetalleInputModel item16 = new DetalleInputModel();
//        input.getDetalle().add(item16);
//        item16.setDescripcion("item");
//        item16.setCantidad(new BigDecimal("10"));
//        item16.setPrecioUnitario(new BigDecimal("20"));
//        item16.setTipoIGV(Catalog7.GRAVADO_IVAP.toString());



//        DetalleInputModel item7 = new DetalleInputModel();
//        input.getDetalle().add(item7);
//        item7.setDescripcion("item");
//        item7.setCantidad(new BigDecimal("10"));
//        item7.setPrecioUnitario(new BigDecimal("20"));
//        item7.setTipoIGV(Catalog7.EXONERADO_OPERACION_ONEROSA.toString());
//
//        DetalleInputModel item8 = new DetalleInputModel();
//        input.getDetalle().add(item8);
//        item8.setDescripcion("item");
//        item8.setCantidad(new BigDecimal("10"));
//        item8.setPrecioUnitario(new BigDecimal("20"));
//        item8.setTipoIGV(Catalog7.EXONERADO_TRANSFERENCIA_GRATUITA.toString());
//
//
//
//        DetalleInputModel item0 = new DetalleInputModel();
//        input.getDetalle().add(item0);
//        item0.setDescripcion("item0");
//        item0.setCantidad(new BigDecimal("10"));
//        item0.setPrecioUnitario(new BigDecimal("20"));
//        item0.setTipoIGV(Catalog7.INAFECTO_OPERACION_ONEROSA.toString());
//
//        DetalleInputModel item1 = new DetalleInputModel();
//        input.getDetalle().add(item1);
//        item1.setDescripcion("item1");
//        item1.setCantidad(new BigDecimal("10"));
//        item1.setPrecioUnitario(new BigDecimal("20"));
//        item1.setTipoIGV(Catalog7.INAFECTO_RETIRO_POR_BONIFICACION.toString());
//
//        DetalleInputModel item2 = new DetalleInputModel();
//        input.getDetalle().add(item2);
//        item2.setDescripcion("item2");
//        item2.setCantidad(new BigDecimal("10"));
//        item2.setPrecioUnitario(new BigDecimal("20"));
//        item2.setTipoIGV(Catalog7.INAFECTO_RETIRO.toString());
//
//        DetalleInputModel item3 = new DetalleInputModel();
//        input.getDetalle().add(item3);
//        item3.setDescripcion("item3");
//        item3.setCantidad(new BigDecimal("10"));
//        item3.setPrecioUnitario(new BigDecimal("20"));
//        item3.setTipoIGV(Catalog7.INAFECTO_RETIRO_POR_MUESTRAS_MEDICAS.toString());
//
//        DetalleInputModel item4 = new DetalleInputModel();
//        input.getDetalle().add(item4);
//        item4.setDescripcion("item4");
//        item4.setCantidad(new BigDecimal("10"));
//        item4.setPrecioUnitario(new BigDecimal("20"));
//        item4.setTipoIGV(Catalog7.INAFECTO_RETIRO_POR_CONVENIO_COLECTIVO.toString());
//
//        DetalleInputModel item5 = new DetalleInputModel();
//        input.getDetalle().add(item5);
//        item5.setDescripcion("item5");
//        item5.setCantidad(new BigDecimal("10"));
//        item5.setPrecioUnitario(new BigDecimal("20"));
//        item5.setTipoIGV(Catalog7.INAFECTO_RETIRO_POR_PREMIO.toString());
//
//        DetalleInputModel item6 = new DetalleInputModel();
//        input.getDetalle().add(item6);
//        item6.setDescripcion("item6");
//        item6.setCantidad(new BigDecimal("10"));
//        item6.setPrecioUnitario(new BigDecimal("20"));
//        item6.setTipoIGV(Catalog7.INAFECTO_RETIRO_POR_PUBLICIDAD.toString());

        return input;
    }

    @Override
    public Optional<String> getSnapshot() {
        return Optional.empty();
    }

}
