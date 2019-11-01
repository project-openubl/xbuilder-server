package org.openublpe.xmlbuilder.utils;

import org.openublpe.xmlbuilder.models.ubl.Catalog1;
import org.openublpe.xmlbuilder.models.ubl.Catalog6;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class UBLUtils {

    public static String getInvoiceTypeCode(String serie) {
        String result = null;

        String upperCase = serie.toUpperCase();
        if (upperCase.startsWith("F")) {
            result = Catalog1.FACTURA.getCode();
        } else if (upperCase.startsWith("B")) {
            result = Catalog1.BOLETA.getCode();
        }

        return result;
    }

    public static String getCodigoTipoDocumentoIdentidad(String tipoDocumentoIdentidad) {
        try {
            Catalog6 catalog6 = Catalog6.valueOf(tipoDocumentoIdentidad.toUpperCase());
            return catalog6.getCode();
        } catch (Exception e) {
            // TODO use the user defined Code
            return tipoDocumentoIdentidad;
        }
    }

}
