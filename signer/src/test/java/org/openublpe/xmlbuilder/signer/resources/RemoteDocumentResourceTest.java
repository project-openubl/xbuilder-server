package org.openublpe.xmlbuilder.signer.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.models.input.common.FirmanteInputModel;
import org.openublpe.xmlbuilder.models.input.common.ProveedorInputModel;
import org.openublpe.xmlbuilder.models.input.standard.DetalleInputModel;
import org.openublpe.xmlbuilder.models.input.standard.invoice.InvoiceInputModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class RemoteDocumentResourceTest {

    public FirmanteInputModel getFirmante() {
        FirmanteInputModel result = new FirmanteInputModel();
        result.setRuc("11111111111");
        result.setRazonSocial("Firmante");
        return result;
    }

    public ProveedorInputModel getProveedor() {
        ProveedorInputModel proveedor = new ProveedorInputModel();
        proveedor.setRuc("22222222222");
        proveedor.setRazonSocial("Proveedor");
        proveedor.setCodigoPostal("010101");
        return proveedor;
    }

    public ClienteInputModel getClienteConRUC() {
        ClienteInputModel cliente = new ClienteInputModel();
        cliente.setNombre("Cliente");
        cliente.setNumeroDocumentoIdentidad("33333333333");
        cliente.setTipoDocumentoIdentidad(Catalog6.RUC.toString());
        return cliente;
    }

    @Test
    public void testCreateInvoice() throws Exception {
        // GIVEN
        InvoiceInputModel input = new InvoiceInputModel();
        input.setSerie("F001");
        input.setNumero(1);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.NOVEMBER, 8);
        input.setFechaEmision(calendar.getTimeInMillis());

        input.setFirmante(getFirmante());
        input.setProveedor(getProveedor());
        input.setCliente(getClienteConRUC());

        List<DetalleInputModel> detalle = new ArrayList<>();
        input.setDetalle(detalle);

        DetalleInputModel item1 = new DetalleInputModel();
        detalle.add(item1);
        item1.setDescripcion("Item");
        item1.setCantidad(BigDecimal.ONE);
        item1.setPrecioUnitario(new BigDecimal("100"));

        String body = new ObjectMapper().writeValueAsString(input);

        // WHEN
        Response response = given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post("/documents/invoice/create")
                .thenReturn();

        // THEN
        assertEquals(200, response.getStatusCode());
    }
}
