# XML-Builder
[![Build Status](https://dev.azure.com/project-openubl/xml-builder/_apis/build/status/project-openubl.xml-builder?branchName=master)](https://dev.azure.com/project-openubl/xml-builder/_build/latest?definitionId=1&branchName=master)

Crea archivos XML basados en UBL 2.1 - Facturación electrónica Perú. Facturas, boletas, notas de cŕedito, notas de débito, etc.

# Mailing list
Puedes hacer preguntas y recibir noticias a travéz de nuestro mailing list.
Únete al grupo:
- Enviando un correo a [projectopenubl+subscribe@googlegroups.com](mailto:projectopenubl+subscribe@googlegroups.com) o
- Uniéndote al grupo [https://groups.google.com/d/forum/projectopenubl](https://groups.google.com/d/forum/projectopenubl)

# Documentación y ayuda

- [Documentación](https://project-openubl.gitbook.io/xml-builder/)
- [Videos](https://www.youtube.com/channel/UChq3xxjyDgjcU346rp0bbtA/)

# Distribuciones
El proyecto XML Builder distribuye dos versiones:
- ***XML Builder***.- Crea XMLs sin firma digital. Útil para empresas que ya cuentan con algun software para firmar sus XMLs.
- ***XML Builder Signer***.- Incluye todas las funcionalidades de ***XML Builder*** pero además permite firmar electrónicamente los archivos XMLs; permite administrar los certificados digitales de varias empresas en un solo lugar.

# XML Builder
## Empezar
El método más fácil de iniciar el servidor localmente es usando Docker:

```
docker run -p 8080:8080 projectopenubl/xml-builder
```

Podrás ver el servidor en: http://localhost:8080 

Para más información puedes leer: [Instalación y configuration del servidor](docs/XB_INSTALAR_CONFIGURAR.md)

## Crear Factura electrónica
Para crear comprobantes debes de tener levantantado el servidor localmente, puedes seguir las instrucciones del paso anterior.

Pasos para crear factura electrónica:
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
http://localhost:8080/api/documents/invoice/create
```

Ejemplo:

![Swagger](docs/images/curl.gif)

# XML Builder Signer
## Empezar
El método más fácil de iniciar el servidor localmente es usando Docker:

Crear un network:
```
docker network create xml-builder-network
```

Iniciar una instancia de PostgreSQL:
```
docker run -d --name postgres --net xml-builder-network -e POSTGRES_DB=db_name -e POSTGRES_USER=db_username -e POSTGRES_PASSWORD=db_password postgres
```

Iniciar una instancia de XML Builder Signer:
```
docker run --name xml-builder --net xml-builder-network -p 8080:8080 -e QUARKUS_DATASOURCE_URL=jdbc:postgresql://postgres:5432/db_name -e QUARKUS_DATASOURCE_USERNAME=db_username -e QUARKUS_DATASOURCE_PASSWORD=db_password -e QUARKUS_DATASOURCE_DRIVER=org.postgresql.Driver projectopenubl/xml-builder-signer
```

Podrás ver el servidor en: http://localhost:8080 

Para más información puedes leer: [Instalación y configuration del servidor](docs/XB_INSTALAR_CONFIGURAR.md)

![Swagger](docs/images/api_signer_screenshot.png)

## Crear Factura electrónica
Para crear comprobantes debes de tener levantantado el servidor localmente, puedes seguir las instrucciones del paso anterior.

XML Builder Signer crea por defecto una organización llamada `master`; para el presente ejemplo vamos a utilizar la organización `master`.

Pasos para crear factura electrónica:
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
http://localhost:8080/api/organizations/master/documents/invoice/create
```

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

Para saber saber más acerca de los endpoints visita:
- XML Builder [API Docs](https://app.swaggerhub.com/apis-docs/project-openubl/xml-builder)
- XML Builder Signer: [API Docs](https://app.swaggerhub.com/apis-docs/project-openubl/xml-builder-signer)

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
