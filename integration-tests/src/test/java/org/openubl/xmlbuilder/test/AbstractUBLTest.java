package org.openubl.xmlbuilder.test;

import io.github.carlosthe19916.webservices.managers.BillServiceManager;
import io.github.carlosthe19916.webservices.providers.BillServiceModel;
import io.github.carlosthe19916.webservices.wrappers.ServiceConfig;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.hamcrest.MatcherAssert;
import org.openublpe.xmlbuilder.apicore.resources.ApiApplication;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.rules.utils.UBLUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonListener;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.DOMDifferenceEngine;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.DifferenceEngine;
import org.xmlunit.matchers.CompareMatcher;
import org.xmlunit.matchers.HasXPathMatcher;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import static io.restassured.RestAssured.given;
//import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
//import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.xmlunit.matchers.HasXPathMatcher.hasXPath;

public abstract class AbstractUBLTest {

    private static final String ORGANIZATION_ID = "master";

    private static final String SUNAT_BETA_URL = "https://e-beta.sunat.gob.pe/ol-ti-itcpfegem-beta/billService";
    private static final String SUNAT_PERCEPTION_RETENTION_BETA_URL = "https://e-beta.sunat.gob.pe/ol-ti-itemision-otroscpe-gem-beta/billService";
    private static final String SUNAT_DISPATCH_ADVICE_BETA_URL = "https://e-beta.sunat.gob.pe/ol-ti-itemision-guia-gem-beta/billService";
    private static final String SUNAT_BETA_USERNAME = "MODDATOS";
    private static final String SUNAT_BETA_PASSWORD = "MODDATOS";

    private XPath xPath;

    public AbstractUBLTest() {
        xPath = XPathFactory.newInstance().newXPath();
        xPath.setNamespaceContext(SunatNamespacesSingleton.getInstance());
    }

    public void assertSnapshot(Response expected, String snapshotFile) {
        InputStream snapshotInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(snapshotFile);
        assertNotNull(snapshotInputStream, "Could not find snapshot file " + snapshotFile);

        Diff myDiff = DiffBuilder
                .compare(snapshotInputStream)
                .withTest(expected.asInputStream())
                .ignoreComments()
                .ignoreWhitespace()
                .build();

        assertFalse(myDiff.hasDifferences(), expected.asString() + "\n" + myDiff.toString());
    }

    public void assertEqualsXMLExcerptSignature(Response xmlWithoutSignature, Response xmlWithSignature) throws IOException, SAXException {
        assertThat(
                xmlWithSignature.asInputStream(),
                hasXPath("//ext:UBLExtensions/ext:UBLExtension/ext:ExtensionContent/ds:Signature")
                        .withNamespaceContext(SunatNamespacesSingleton.getInstance().getPrefixes())
        );

        Diff myDiff = DiffBuilder
                .compare(xmlWithoutSignature.asInputStream())
                .withTest(xmlWithSignature.asInputStream())
                .ignoreComments()
                .ignoreWhitespace()
                .withNodeFilter(node -> node.getNodeName().equals("ExtensionContent"))
                .build();

        assertFalse(myDiff.hasDifferences(), myDiff.toString());
    }

    public void assertSendSunat(UBLDocumentType type, Response xmlWithSignature) throws TransformerException, ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        String skipSunat = System.getProperty("skipSunat", "true");
        if (skipSunat != null && skipSunat.equals("true")) {
            sendFileToSunat(type, xmlWithSignature);
        }
    }

    private void sendFileToSunat(UBLDocumentType type, Response xmlWithSignature) throws TransformerException, IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        String ruc = getProveedorRuc(type, xmlWithSignature.asInputStream());
        String documentID = getDocumentID(type, xmlWithSignature.asInputStream());
        String xmlFileName = getFileName(type, ruc, documentID);

        ServiceConfig config = new ServiceConfig.Builder()
                .url(getServerUrl(type))
                .username(ruc + SUNAT_BETA_USERNAME)
                .password(SUNAT_BETA_PASSWORD)
                .build();

        Document xmlDocument = inputStreamToDocument(xmlWithSignature.asInputStream());
        BillServiceModel billServiceModel = BillServiceManager.sendBill(xmlFileName, documentToBytes(xmlDocument), config);
        assertEquals(
                BillServiceModel.Status.ACEPTADO,
                billServiceModel.getStatus(),
                xmlWithSignature.asString() + " \n sunat [codigo=" + billServiceModel.getCode() + "], [descripcion=" + billServiceModel.getDescription() + "]"
        );
    }

    private String getProveedorRuc(UBLDocumentType type, InputStream xmlDocument) throws XPathExpressionException {
        InputSource inputSource = new InputSource(xmlDocument);
        switch (type) {
            case INVOICE:
            case CREDIT_NOTE:
            case DEBIT_NOTE:
                return (String) xPath
                        .compile("//cac:AccountingSupplierParty/cac:Party/cac:PartyIdentification/cbc:ID/text()")
                        .evaluate(inputSource, XPathConstants.STRING);
            default:
                throw new IllegalStateException("Invalid type of UBL Document, can not extract Proveedor RUC");
        }
    }

    private String getDocumentID(UBLDocumentType type, InputStream xmlDocument) throws XPathExpressionException {
        InputSource inputSource = new InputSource(xmlDocument);
        switch (type) {
            case INVOICE:
            case CREDIT_NOTE:
            case DEBIT_NOTE:
                return (String) xPath
                        .compile("//cbc:ID/text()")
                        .evaluate(inputSource, XPathConstants.STRING);
            default:
                throw new IllegalStateException("Invalid type of UBL Document, can not extract Serie Numero");
        }
    }

    private String getFileName(UBLDocumentType type, String ruc, String documentID) throws XPathExpressionException {
        String codigo;
        switch (type) {
            case INVOICE:
                if (UBLUtils.FACTURA_SERIE_REGEX.matcher(documentID).find()) {
                    codigo = Catalog1.FACTURA.getCode();
                } else if (UBLUtils.BOLETA_SERIE_REGEX.matcher(documentID).find()) {
                    codigo = Catalog1.BOLETA.getCode();
                } else {
                    throw new IllegalStateException("Invalid Serie, can not detect code");
                }
                break;
            case CREDIT_NOTE:
                codigo = Catalog1.NOTA_CREDITO.getCode();
                break;
            case DEBIT_NOTE:
                codigo = Catalog1.NOTA_DEBITO.getCode();
                break;
            default:
                throw new IllegalStateException("Invalid type of UBL Document, can not extract Serie Numero");
        }

        return MessageFormat.format("{0}-{1}-{2}.xml", ruc, codigo, documentID);
    }

    private String getServerUrl(UBLDocumentType type) {
        switch (type) {
            case INVOICE:
                return SUNAT_BETA_URL;
            case CREDIT_NOTE:
                return SUNAT_BETA_URL;
            case DEBIT_NOTE:
                return SUNAT_BETA_URL;
            case PERCEPTION:
                return SUNAT_PERCEPTION_RETENTION_BETA_URL;
            case RETENTION:
                return SUNAT_PERCEPTION_RETENTION_BETA_URL;
            case DISPATCH_ADVICE:
                return SUNAT_DISPATCH_ADVICE_BETA_URL;
            default:
                throw new IllegalStateException("Invalid type of UBL Document, can not create Sunat Server URL");
        }
    }

    private byte[] documentToBytes(Document document) throws TransformerException {
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = transFactory.newTransformer();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(bos);
        transformer.transform(new DOMSource(document), result);
        return bos.toByteArray();
    }

    private Document inputStreamToDocument(InputStream in) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(in));
    }

    //

    public XMlBuilderOutputResponse requestAllEdpoints(UBLDocumentType type, String body) {
        return XMlBuilderOutputResponse.Builder.aXMlBuilderOutputResponse()
                .withApiEnrichResponse(requestApiEnrich(type, body))
                .withApiCreateResponse(requestApiCreate(type, body))
                .withApiSignerEnrichResponse(requestApiSignerEnrich(type, body))
                .withApiSignerCreateResponse(requestApiSignerCreate(type, body))
                .build();
    }

    private Response requestApiEnrich(UBLDocumentType type, String body) {
        Response response = given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post(ApiApplication.API_BASE + "/documents/" + type.getType() + "/enrich")
                .thenReturn();

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody().asString());

        return response;
    }

    private Response requestApiCreate(UBLDocumentType type, String body) {
        Response response = given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post(ApiApplication.API_BASE + "/documents/" + type.getType() + "/create")
                .thenReturn();

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody().asString());

        return response;
    }

    private Response requestApiSignerEnrich(UBLDocumentType documentType, String body) {
        Response response = given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post(ApiApplication.API_BASE + "/organizations/" + ORGANIZATION_ID + "/documents/" + documentType.getType() + "/enrich")
                .thenReturn();

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody().asString());

        return response;
    }

    private Response requestApiSignerCreate(UBLDocumentType documentType, String body) {
        Response response = given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post(ApiApplication.API_BASE + "/organizations/" + ORGANIZATION_ID + "/documents/" + documentType.getType() + "/create")
                .thenReturn();

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody().asString());

        return response;
    }

}
