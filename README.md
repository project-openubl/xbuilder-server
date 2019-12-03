# XML-Builder
[![Build Status](https://dev.azure.com/project-openubl/xml-builder/_apis/build/status/project-openubl.xml-builder?branchName=master)](https://dev.azure.com/project-openubl/xml-builder/_build/latest?definitionId=1&branchName=master)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=org.openublpe%3Axml-builder&metric=alert_status)](https://sonarcloud.io/dashboard?id=org.openublpe%3Axml-builder)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=org.openublpe%3Axml-builder&metric=coverage)](https://sonarcloud.io/dashboard?id=org.openublpe%3Axml-builder)

Crea archivos XML basados en UBL 2.1 - Facturación electrónica Perú. Facturas, boletas, notas de cŕedito, notas de débito, etc.

# Recursos

- [Documentación](https://project-openubl.gitbook.io/xml-builder/)
- [Videos](https://www.youtube.com/channel/UChq3xxjyDgjcU346rp0bbtA/)

# Levantar el servidor localmente
Siga las siguientes instrucciones: [LEVANTAR SERVIDOR](docs/LEVANTAR_SERVIDOR.md)

**¡Felicitaciones! pudiste arrancar el proyecto localmente**

# Crear Factura UBL 2.1.
Para crear comprobantes debes de haber levantantado el servidor localmente.

XML Builder es muy sencillo de usar. En este ejemplo voy a crear una Factura.

Pasos:
1. Abrir un terminal
1. Ejecutar el comando de abajo
1. **¡Eso es todo! ¿Que fácil verdad?**

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

![Swagger](docs/images/curl.gif)

# Rest Endpoints (API)
XML Builder está hecho para ser consumido a travéz de peticiones HTTP.

Puedes utilizar **XML Builder** desde aplicaciones front-end como:
- Angular
- Reactjs, etc.

También puedes utilizar **XML Builder** desde aplicaciones backend como:
- Java
- .Net, etc.

O simplemente puedes utilizar **XML Builder** a travéz de:
- terminal de tu sistema operativo
- Herramientas como Postman.

Para saber saber más acerca de los endpoints visita: 

## [swagger](https://app.swaggerhub.com/apis-docs/project-openubl/xml-builder)

![Swagger](docs/images/swagger.png)

