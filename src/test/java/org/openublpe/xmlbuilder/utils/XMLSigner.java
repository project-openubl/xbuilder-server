package org.openublpe.xmlbuilder.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XMLSigner {

    public static Document firmarXML(
            Document document,
            String referenceID,
            X509Certificate certificate,
            PrivateKey privateKey
    ) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, MarshalException, XMLSignatureException {
        addUBLExtensions(document);
        addUBLExtension(document);
        Node nodeExtensionContent = addExtensionContent(document);

        XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance();
        Reference reference = signatureFactory.newReference("",
                signatureFactory.newDigestMethod(DigestMethod.SHA1, null),
                Collections.singletonList(signatureFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)), null, null);

        SignedInfo signedInfo = signatureFactory.newSignedInfo(
                signatureFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
                signatureFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(reference));

        KeyInfoFactory keyInfoFactory = signatureFactory.getKeyInfoFactory();
        List<X509Certificate> x509Content = new ArrayList<>();

        // Certificate
        x509Content.add(certificate);

        X509Data xdata = keyInfoFactory.newX509Data(x509Content);
        KeyInfo keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(xdata));

        DOMSignContext signContext = new DOMSignContext(privateKey, document.getDocumentElement());
        signContext.setDefaultNamespacePrefix("ds");
        signContext.setParent(nodeExtensionContent);

        XMLSignature signature = signatureFactory.newXMLSignature(signedInfo, keyInfo);
        signature.sign(signContext);

        Element elementParent = (Element) signContext.getParent();
        if ((referenceID != null) && (elementParent.getElementsByTagName("ds:Signature") != null)) {
            Element elementSignature = (Element) elementParent.getElementsByTagName("ds:Signature").item(0);
            elementSignature.setAttribute("Id", referenceID);
        }

        return document;
    }

    private static void addUBLExtensions(Document document) {
        NodeList nodeListUBLExtensions = document.getDocumentElement().getElementsByTagName("ext:UBLExtensions");
        Node nodeUBLExtensions = nodeListUBLExtensions.item(0);
        if (nodeUBLExtensions == null) {
            Element documentElement = document.getDocumentElement();
            nodeUBLExtensions = document.createElement("ext:UBLExtensions");
            documentElement.insertBefore(nodeUBLExtensions, documentElement.getFirstChild());
        }
    }

    private static void addUBLExtension(Document document) {
        NodeList nodeListUBLExtensions = document.getDocumentElement().getElementsByTagName("ext:UBLExtensions");
        NodeList nodeListUBLExtension = document.getDocumentElement().getElementsByTagName("ext:UBLExtension");
        Node nodeUBLExtension = nodeListUBLExtension.item(0);
        if (nodeUBLExtension == null) {
            nodeUBLExtension = document.createElement("ext:UBLExtension");
            nodeListUBLExtensions.item(0).appendChild(nodeUBLExtension);
        }
    }

    private static Node addExtensionContent(Document document) {
        NodeList nodeListUBLExtension = document.getDocumentElement().getElementsByTagName("ext:UBLExtension");
        NodeList nodeListExtensionContent = document.getDocumentElement().getElementsByTagName("ext:ExtensionContent");
        Node nodeExtensionContent = nodeListExtensionContent.item(0);
        if (nodeExtensionContent == null) {
            nodeExtensionContent = document.createElement("ext:ExtensionContent");
            nodeListUBLExtension.item(0).appendChild(nodeExtensionContent);
        }
        return nodeExtensionContent;
    }

}
