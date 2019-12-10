package org.openublpe.xmlbuilder.signer.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class XMLUtils {

    public static Document stringToDocument(String xml) throws IOException, SAXException, ParserConfigurationException {
        if (xml == null) {
            return null;
        }
        return inputStreamToDocument(new ByteArrayInputStream(xml.getBytes(StandardCharsets.ISO_8859_1)));
    }

    public static String getBytesFromDocument(Document document) throws TransformerException {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        TransformerFactory factory = TransformerFactory.newInstance();
//        Transformer transformer = factory.newTransformer();
//        transformer.setOutputProperty("omit-xml-declaration", "no");
//        transformer.setOutputProperty("encoding", "ISO-8859-1");
//        transformer.transform(new DOMSource(document), new StreamResult(out));
//        return out.toByteArray();
        return writeXmlDocumentToXmlFile(document);
    }

    private static String writeXmlDocumentToXmlFile(Document xmlDocument) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        transformer = tf.newTransformer();

        // Uncomment if you do not require XML declaration
        // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

        //A character stream that collects its output in a string buffer,
        //which can then be used to construct a string.
        StringWriter writer = new StringWriter();

        //transform document to string
        transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));

        return writer.getBuffer().toString();
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
