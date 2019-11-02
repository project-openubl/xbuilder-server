package org.openublpe.xmlbuilder.utils;

import org.openublpe.xmlbuilder.models.input.DetalleInputModel;
import org.openublpe.xmlbuilder.models.output.DetalleOutputModel;

import java.util.LinkedList;
import java.util.List;

public class UBLUtils {

    public static List<DetalleOutputModel> getDetalle(List<DetalleInputModel> list) {
        List<DetalleOutputModel> result = new LinkedList<>();

        for (DetalleInputModel item : list) {

        }
        for (int i = 0; i < list.size(); i++) {
            DetalleInputModel item = list.get(i);

            DetalleOutputModel output = new DetalleOutputModel();
            result.add(output);

            output.setIndex(i + 1);
            output.setDescripcion(item.getDescripcion());
            output.setUnidadMedida(item.getUnidadMedida() != null ? item.getUnidadMedida() : "NIU");
            output.setCantidad(item.getCantidad());

//            output.setValorUnitario(item.getValorUnitario());
            output.setPrecioUnitario(item.getPrecioUnitario());
//            output.setSubtotal(item.getSubtotal());
//            output.setTotal(item.getTotal());

            if (output.getSubtotal() == null){
                output.setSubtotal(output.getCantidad().multiply(output.getValorUnitario()));
            }
            if (output.getTotal() == null){
                output.setTotal(output.getCantidad().multiply(output.getPrecioUnitario()));
            }
        }

        return result;
    }

}
