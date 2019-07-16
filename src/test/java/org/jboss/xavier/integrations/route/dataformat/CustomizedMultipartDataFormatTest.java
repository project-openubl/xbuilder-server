package org.jboss.xavier.integrations.route.dataformat;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomizedMultipartDataFormatTest {
    
    @Test
    public void customizedMultipartDataFormat_unmarshal_MultipartMessage5FilesGiven_ShouldReturn5Attachments() throws IOException, MessagingException {
        // Given
        CustomizedMultipartDataFormat customizedMultipartDataFormat = new CustomizedMultipartDataFormat();
        Exchange exchange = new DefaultExchange(new DefaultCamelContext());
        exchange.getIn().setHeader("Content-Type", "multipart/text");
        
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("mime-message-several-files-sample.txt");
        
        // When
        Message message = (Message) customizedMultipartDataFormat.unmarshal(exchange, stream);
        
        // Then
        assertThat(message.getAttachments().size()).isEqualTo(5);
    }    
    
    @Test
    public void customizedMultipartDataFormat_unmarshal_TextHtmlMessageGiven_ShouldReturnSameInput() throws IOException, MessagingException {
        // Given
        CustomizedMultipartDataFormat customizedMultipartDataFormat = new CustomizedMultipartDataFormat();
        Exchange exchange = new DefaultExchange(new DefaultCamelContext());
        exchange.getIn().setHeader("Content-Type", "text/html");
        
        InputStream stream = new ByteArrayInputStream("This is a text".getBytes());
        
        // When
        Object message = customizedMultipartDataFormat.unmarshal(exchange, stream);
        
        // Then
        assertThat(message).isEqualTo(stream);
    }
}