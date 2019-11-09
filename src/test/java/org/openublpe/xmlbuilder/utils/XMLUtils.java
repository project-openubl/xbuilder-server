package org.openublpe.xmlbuilder.utils;

import org.openublpe.xmlbuilder.models.ubl.Catalog1;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
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

    public static String documentToString(Document document) throws TransformerException {
        StringWriter sw = new StringWriter();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
//        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
//        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        transformer.transform(new DOMSource(document), new StreamResult(sw));
        return sw.toString();
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

    public static Document cloneDocument(Document document) throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Node originalRoot = document.getDocumentElement();
        Document copyDocument = db.newDocument();
        Node copiedRoot = copyDocument.importNode(originalRoot, true);
        copyDocument.appendChild(copiedRoot);
        return copyDocument;
    }

    public static String getInvoiceFileName(String ruc, String serie, Integer numero) {
        Catalog1 catalog1;
        if (UBLUtils.FACTURA_SERIE_REGEX.matcher(serie.toUpperCase()).find()) {
            catalog1 = Catalog1.FACTURA;
        } else if (UBLUtils.BOLETA_SERIE_REGEX.matcher(serie.toUpperCase()).find()) {
            catalog1 = Catalog1.BOLETA;
        } else {
            throw new IllegalStateException("Invalid Serie, can not detect code");
        }

        return new StringBuilder()
                .append(ruc).append("-")
                .append(catalog1.getCode()).append("-")
                .append(serie.toUpperCase()).append("-")
                .append(numero)
                .toString();
    }

    public static String getNotaCredito(String ruc, String serie, Integer numero) {
        Catalog1 catalog1 = Catalog1.NOTA_CREDITO;
        return new StringBuilder()
                .append(ruc).append("-")
                .append(catalog1.getCode()).append("-")
                .append(serie.toUpperCase()).append("-")
                .append(numero)
                .toString();
    }

    public static String getNotaDebito(String ruc, String serie, Integer numero) {
        Catalog1 catalog1 = Catalog1.NOTA_DEBITO;
        return new StringBuilder()
                .append(ruc).append("-")
                .append(catalog1.getCode()).append("-")
                .append(serie.toUpperCase()).append("-")
                .append(numero)
                .toString();
    }
}
