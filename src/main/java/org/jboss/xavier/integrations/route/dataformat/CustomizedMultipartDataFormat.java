package org.jboss.xavier.integrations.route.dataformat;

import org.apache.camel.Attachment;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.dataformat.mime.multipart.MimeMultipartDataFormat;
import org.apache.camel.impl.DefaultAttachment;
import org.apache.camel.util.IOHelper;
import org.apache.camel.util.MessageHelper;
import org.jboss.xavier.integrations.route.RouteBuilderExceptionHandler;

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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomizedMultipartDataFormat extends MimeMultipartDataFormat {
    public static final String MIME_VERSION = "MIME-Version";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";

    @Override
    public Object unmarshal(Exchange exchange, InputStream stream) throws IOException, MessagingException {
        // check if this a multipart at all. Otherwise do nothing
        String contentType = exchange.getIn().getHeader(CONTENT_TYPE, String.class);
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

        Message camelMessage = exchange.getOut();
        MessageHelper.copyHeaders(exchange.getIn(), camelMessage, true);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        IOHelper.copyAndCloseInput(stream, bos);

        InternetHeaders headers = new InternetHeaders();
        extractHeader(CONTENT_TYPE, camelMessage, headers);
        extractHeader(MIME_VERSION, camelMessage, headers);

        MimeBodyPart mimeMessage = new MimeBodyPart(headers, bos.toByteArray());
        bos.close();

        Object content = mimeMessage.getDataHandler().getContent();

        if (content instanceof MimeMultipart) {
            MimeMultipart mp = (MimeMultipart) content;
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart bp = mp.getBodyPart(i);
                DefaultAttachment camelAttachment = new DefaultAttachment(bp.getDataHandler());

                @SuppressWarnings("unchecked")
                Enumeration<Header> bpAllHeaders = bp.getAllHeaders();
                while (bpAllHeaders.hasMoreElements()) {
                    Header header = bpAllHeaders.nextElement();
                    camelAttachment.addHeader(header.getName(), header.getValue());
                }
                // All non file parts are considered parameters and set as headers of the whole message
                if (!camelAttachment.getHeader(CONTENT_DISPOSITION).contains("filename")) {
                    Map ma_metadata = camelMessage.getHeader(RouteBuilderExceptionHandler.MA_METADATA, new HashMap<String,String>(), java.util.Map.class);
                    ma_metadata.put(getFieldNameFromMultipart(camelAttachment), camelAttachment.getDataHandler().getContent());
                    camelMessage.setHeader(RouteBuilderExceptionHandler.MA_METADATA, ma_metadata);
                }

                camelMessage.addAttachmentObject(getAttachmentKey(bp), camelAttachment);
            }
        }

        return camelMessage;
    }
    /**
     * With multipartform data , info will come in the Content-Disposition header
     * in the form of : form-data;name="myname"
     * @param body
     * @return
     */
    private String getFieldNameFromMultipart(Attachment body) {
        String header = body.getHeader(CONTENT_DISPOSITION);
        Matcher matcher = Pattern.compile("=\"(.*?)\"").matcher(header);
        matcher.find();
        return matcher.group(1);
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
