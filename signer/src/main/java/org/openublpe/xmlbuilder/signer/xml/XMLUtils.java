package org.openublpe.xmlbuilder.signer.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class XMLUtils {

    public static Document stringToDocument(String xml) throws IOException, SAXException, ParserConfigurationException {
        if (xml == null) {
            return null;
        }
        return inputStreamToDocument(new ByteArrayInputStream(xml.getBytes(StandardCharsets.ISO_8859_1)));
    }

    public static Document inputStreamToDocument(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(inputStream);
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
}
