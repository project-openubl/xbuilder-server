# Crear Comprobante Electrónico
## Requisitos
- Tener el servidor XML Builder levantado. Puedes leer [Levantar servidor](LEVANTAR_SERVIDOR.md) para aprender cómo.

**XML Builder** está hecho para recibir peticiones HTTP por lo que podemos utilizar varios métodos para consumir sus servicios.
Veamos algunos de ellos

## 1. Utilizando el comando CURL en el terminal
> Si tienes windows puedes descargar CURL utilizando el siguiente enlace: https://curl.haxx.se/windows/

**El siguiente ejemplo creará una Factura Electrónica.**

Abra un terminal en su sistema operativo y ejecute el siguiente comando:

```
curl -X POST \
-H "Content-Type: application/json" \
-d '{
    "serie": "F001",
    "numero": 1,
    "fechaEmision": 1573247709344,
    "firmante": {
      "ruc": "12345678912",
      "razonSocial": "Razon Social Firmante"
    },
    "proveedor": {
      "ruc": "98765432198",
      "nombreComercial": "Nombre comercial proveedor",
      "razonSocial": "Razon social proveedor",
      "codigoPostal": "010101"
    },
    "cliente": {
      "tipoDocumentoIdentidad": "RUC",
      "numeroDocumentoIdentidad": "12312312312",
      "nombre": "Nombre o razon social cliente"
    },
    "detalle": [
      {
        "descripcion": "Descripcion del item en venta",
        "cantidad": 1,
        "precioUnitario": 100
      }
    ]
  }' \
http://localhost:8080/documents/invoice/create
```

- Debes de cambiar `http://localhost:8080` por ubicación de tu servidor.
- Si deseas creear otro tipo de documento debes de cambiar `/invoice` por credit-note, debit-note, etc. Vea el [API](https://app.swaggerhub.com/apis-docs/project-openubl/xml-builder) para más información.

El ejemplo anterior creará un comprobante con los datos mínimos. **XML Builder** se encargará de generar todos los datos por ti.

![terminal](images/curl.gif)


## 2. Utilizando [Postman](https://www.getpostman.com/)
**El siguiente ejemplo creará una Factura Electrónica.**

- Instale Postman
- Abra Postman y cree una nueva peticion HTTP. Utilize las siguientes imágenes como guía:

![Postman Headers](images/postman_headers.png)

![Postman Body](images/postman_body.png)

Deberá utilizar el siguiente body:

```
{
    "serie": "F001",
    "numero": 1,
    "fechaEmision": 1573247709344,
    "firmante": {
      "ruc": "12345678912",
      "razonSocial": "Razon Social Firmante"
    },
    "proveedor": {
      "ruc": "98765432198",
      "nombreComercial": "Nombre comercial proveedor",
      "razonSocial": "Razon social proveedor",
      "codigoPostal": "010101"
    },
    "cliente": {
      "tipoDocumentoIdentidad": "RUC",
      "numeroDocumentoIdentidad": "12312312312",
      "nombre": "Nombre o razon social cliente"
    },
    "detalle": [
      {
        "descripcion": "Descripcion del item en venta",
        "cantidad": 1,
        "precioUnitario": 100
      }
    ]
  }
```

Por último deberá hacer clic en el boton SEND.

## 3. Utilizando [Swagger](https://app.swaggerhub.com/apis-docs/project-openubl/xml-builder)
> Este método requiere que levantes el servidor en modo desarrollador, vea el manual [Levantar servidor para desarrolladores](./DEVELOPERS.md)  

- Abra en un navegador http://localhost:8080/swagger-ui
- Seleccione el tipo de documento que desea crear
- Click en el boton "Try out"
- Click en el boton "Execute"
- Descargue el archivo generado haciendo click en el botón "Downlad File"

![terminal](images/swagger_execute.png)

## API
Para conocer un poco más acerca del JSON que se envía a **XML Builder** vea la [documentación del API](https://app.swaggerhub.com/apis-docs/project-openubl/xml-builder)
