package org.openublpe.xmlbuilder.utils;

import org.openublpe.xmlbuilder.models.ubl.Catalog1;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class XMLUtils {

    public static Document inputStreamToDocument(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException, TransformerConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(inputStream);
    }

    public static byte[] documentToBytes(Document document) throws TransformerException {
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = transFactory.newTransformer();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(bos);
        transformer.transform(new DOMSource(document), result);
        return bos.toByteArray();
    }

    public static void writeDocumentToFile(Document document, File file) throws IOException, TransformerException {
        DOMSource source = new DOMSource(document);
        FileWriter writer = new FileWriter(file);
        StreamResult result = new StreamResult(writer);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        transformer.transform(source, result);
    }

    public static String getInvoiceFileName(String ruc, String serie, Integer numero) {
        Catalog1 tipoInvoice;
        if (UBLUtils.FACTURA_SERIE_REGEX.matcher(serie.toUpperCase()).find()) {
            tipoInvoice = Catalog1.FACTURA;
        } else if (UBLUtils.BOLETA_SERIE_REGEX.matcher(serie.toUpperCase()).find()) {
            tipoInvoice = Catalog1.BOLETA;
        } else {
            throw new IllegalStateException("Invalid Serie, can not detect code");
        }

        return new StringBuilder()
                .append(ruc).append("-")
                .append(tipoInvoice.getCode()).append("-")
                .append(serie.toUpperCase()).append("-")
                .append(numero)
                .toString();
    }
}
