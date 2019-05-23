package org.jboss.xavier.integrations.route.dataformat;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.dataformat.mime.multipart.MimeMultipartDataFormat;
import org.apache.camel.impl.DefaultAttachment;
import org.apache.camel.util.IOHelper;
import org.apache.camel.util.MessageHelper;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParseException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.UUID;

public class CustomizedMultipartDataFormat extends MimeMultipartDataFormat {
    private static final String MIME_VERSION = "MIME-Version";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    private static final String[] STANDARD_HEADERS = {"Message-ID", "MIME-Version", "Content-Type"};

    @Override
    public Object unmarshal(Exchange exchange, InputStream stream) throws IOException, MessagingException {
        MimeBodyPart mimeMessage;
        String contentType;
        Message camelMessage;
        Object content = null;

        // check if this a multipart at all. Otherwise do nothing
        contentType = exchange.getIn().getHeader(CONTENT_TYPE, String.class);
        if (contentType == null) {
            return stream;
        }

        try {
            ContentType ct = new ContentType(contentType);
            if (!ct.match("multipart/*")) {
                return stream;
            }
        } catch (ParseException e) {
            return stream;
        }

        camelMessage = exchange.getOut();
        MessageHelper.copyHeaders(exchange.getIn(), camelMessage, true);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        IOHelper.copyAndCloseInput(stream, bos);
        InternetHeaders headers = new InternetHeaders();
        extractHeader(CONTENT_TYPE, camelMessage, headers);
        extractHeader(MIME_VERSION, camelMessage, headers);
        mimeMessage = new MimeBodyPart(headers, bos.toByteArray());
        bos.close();

        DataHandler dh;
        try {
            dh = mimeMessage.getDataHandler();
            if (dh != null) {
                content = dh.getContent();
                contentType = dh.getContentType();
            }
        } catch (MessagingException e) {
        }

        if (content instanceof MimeMultipart) {
            MimeMultipart mp = (MimeMultipart) content;
            content = null; // CHANGED
            for (int i = 0; i < mp.getCount(); i++) { // CHANGED
                BodyPart bp = mp.getBodyPart(i);
                DefaultAttachment camelAttachment = new DefaultAttachment(bp.getDataHandler());

                @SuppressWarnings("unchecked")
                Enumeration<Header> bpAllHeaders = bp.getAllHeaders();
                while (bpAllHeaders.hasMoreElements()) {
                    Header header = bpAllHeaders.nextElement();
                    camelAttachment.addHeader(header.getName(), header.getValue());
                }

                camelMessage.addAttachmentObject(getAttachmentKey(bp), camelAttachment);
            }
        }

        if (content instanceof BodyPart) {
            BodyPart bp = (BodyPart) content;
            camelMessage.setBody(bp.getInputStream());
            contentType = bp.getContentType();
            if (contentType != null && !DEFAULT_CONTENT_TYPE.equals(contentType)) {
                camelMessage.setHeader(CONTENT_TYPE, contentType);
                ContentType ct = new ContentType(contentType);
                String charset = ct.getParameter("charset");
                if (charset != null) {
                    camelMessage.setHeader(Exchange.CONTENT_ENCODING, MimeUtility.javaCharset(charset));
                }
            }
        } else {
            // If we find no body part, try to leave the message alone
        }


        return camelMessage;
    }

    private String getAttachmentKey(BodyPart bp) throws MessagingException, UnsupportedEncodingException {
        // use the filename as key for the map
        String key = bp.getFileName();
        // if there is no file name we use the Content-ID header
        if (key == null && bp instanceof MimeBodyPart) {
            key = ((MimeBodyPart) bp).getContentID();
            if (key != null && key.startsWith("<") && key.length() > 2) {
                // strip <>
                key = key.substring(1, key.length() - 1);
            }
        }
        // or a generated content id
        if (key == null) {
            key = UUID.randomUUID().toString() + "@camel.apache.org";
        }
        return MimeUtility.decodeText(key);
    }

    private void extractHeader(String headerMame, Message camelMessage, InternetHeaders headers) {
        String h = camelMessage.getHeader(headerMame, String.class);
        if (h != null) {
            headers.addHeader(headerMame, h);
            camelMessage.removeHeader(headerMame);
        }
    }
}
