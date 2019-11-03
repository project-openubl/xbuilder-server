package org.openublpe.xmlbuilder.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.CreditNoteInputGenerator;
import org.openublpe.xmlbuilder.DebitNoteInputGenerator;
import org.openublpe.xmlbuilder.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.models.input.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.models.input.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.models.input.invoice.InvoiceInputModel;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class DocumentsResourceTest {

    static List<InvoiceInputModel> invoiceInputs = new ArrayList<>();
    static List<CreditNoteInputModel> creditNoteInputs = new ArrayList<>();
    static List<DebitNoteInputModel> debitNoteInputs = new ArrayList<>();

    @BeforeAll
    public static void beforeAll() {
        ServiceLoader<InvoiceInputGenerator> serviceLoader1 = ServiceLoader.load(InvoiceInputGenerator.class);
        for (InvoiceInputGenerator generator : serviceLoader1) {
            invoiceInputs.add(generator.getInvoice());
        }

        ServiceLoader<CreditNoteInputGenerator> serviceLoader2 = ServiceLoader.load(CreditNoteInputGenerator.class);
        for (CreditNoteInputGenerator generator : serviceLoader2) {
            creditNoteInputs.add(generator.getCreditNote());
        }

        ServiceLoader<DebitNoteInputGenerator> serviceLoader3 = ServiceLoader.load(DebitNoteInputGenerator.class);
        for (DebitNoteInputGenerator generator : serviceLoader3) {
            debitNoteInputs.add(generator.getDebitNote());
        }
    }

    @Test
    public void testCreateInvoice() throws JsonProcessingException {
        for (InvoiceInputModel input : invoiceInputs) {
            String body = new ObjectMapper().writeValueAsString(input);

            Response response = given()
                    .body(body)
                    .header("Content-Type", "application/json")
                    .when()
                    .post("/documents/invoice/create")
                    .thenReturn();

            assertEquals(200, response.getStatusCode());
        }

//        InvoiceType read = UBL21Reader.invoice().read(response.getBody().asInputStream());
//        assertNotNull(read);
    }

    @Test
    public void testCreateCreditNote() throws JsonProcessingException {
        for (CreditNoteInputModel input : creditNoteInputs) {
            String body = new ObjectMapper().writeValueAsString(input);

            Response response = given()
                    .body(body)
                        .header("Content-Type", "application/json")
                    .when()
                        .post("/documents/credit-note/create")
                    .thenReturn();

            assertEquals(200, response.getStatusCode());
        }
    }

    @Test
    public void testCreateDebitNote() throws JsonProcessingException {
        for (DebitNoteInputModel input : debitNoteInputs) {
            String body = new ObjectMapper().writeValueAsString(input);

            Response response = given()
                    .body(body)
                        .header("Content-Type", "application/json")
                    .when()
                        .post("/documents/debit-note/create")
                    .thenReturn();

            assertEquals(200, response.getStatusCode());
        }
    }

}