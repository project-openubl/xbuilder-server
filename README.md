# XML-Builder
[![Build Status](https://dev.azure.com/project-openubl/xml-builder/_apis/build/status/project-openubl.xml-builder?branchName=master)](https://dev.azure.com/project-openubl/xml-builder/_build/latest?definitionId=1&branchName=master)

Crea archivos XML basados en UBL 2.1 - Facturación electrónica Perú. Facturas, boletas, notas de cŕedito, notas de débito, etc.

# Documentación y ayuda

- [Documentación](https://project-openubl.gitbook.io/xml-builder/)
- [Videos](https://www.youtube.com/channel/UChq3xxjyDgjcU346rp0bbtA/)

# Empezar
El método más fácil de iniciar el servidor localmente es usando Docker:

```
docker run -p 8080:8080 projectopenubl/xml-builder
```

Podrás ver el servidor en: http://localhost:8080 

Para más información puedes leer: [Instalación y configuration del servidor](docs/INSTALACION_CONFIGURACION_SERVIDOR.md)

## Crear Factura UBL 2.1.
Para crear comprobantes debes de haber levantantado el servidor localmente.

Pasos:
1. Abrir un terminal
1. Ejecutar el comando:

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

Ejemplo:

![Swagger](docs/images/curl.gif)

# API
XML Builder está hecho para ser consumido a travéz de peticiones HTTP.

Puedes utilizar **XML Builder** desde aplicaciones front-end como:
- Angular
- Reactjs, etc.

También puedes utilizar **XML Builder** desde aplicaciones backend como:
- Java
- .Net, etc.

O simplemente puedes utilizar **XML Builder** a travéz de:
- terminal de tu sistema operativo
- Herramientas como Postman, etc.

Para saber saber más acerca de los endpoints visita: [API Docs](https://app.swaggerhub.com/apis-docs/project-openubl/xml-builder)

# Contribuye
Aún hay muchas cosas por mejorar, tu ayuda es siempre bienvenida; no necesitas ser programador para contribuir ya que existen muchas formas de hacerlo:

- Mejora y corrige la documentación
- Reporta defectos o solicita mejoras
- Dale una estrella y/o fork a este proyecto
- Usa el proyecto en tu empresa o software 

# Reporta un problema
Si crees que descubriste un defecto en XML Builder, por favor repórtalo en [Github issues](https://github.com/project-openubl/xml-builder/issues)

# Licencia
- [Eclipse Public License - v 2.0](./LICENSE)
