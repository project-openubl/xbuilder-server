![CI](https://github.com/project-openubl/xbuilder-server/workflows/CI/badge.svg)
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fproject-openubl%2Fxbuilder-server.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fproject-openubl%2Fxbuilder-server?ref=badge_shield)

## **https://project-openubl.github.io/xbuilder-server-docs/**

# xbuilder-server
Microservice for creating and signing XML files based on Universal Bussiness Language (UBL)

# Getting started
## Docker

The easiest way of starting _XBuilder Server_ is using Docker.

```shell script
docker run -p 8080:8080 docker.io/projectopenubl/xbuilder-server
```

Then open [http://localhost:8080](http://localhost:8080) and verify that the server is running.

## Create XML

Open a terminal or the tool of your preference and execute:

```shell script
curl -X POST \
-H "Content-Type: application/json" \
-d '{
    "serie": "F001",
    "numero": 1,
    "proveedor": {
        "ruc": "12345678912",
        "razonSocial": "Project OpenUBL"
    },
    "cliente": {
        "tipoDocumentoIdentidad": "RUC",
        "numeroDocumentoIdentidad": "12312312312",
        "nombre": "Nombre de mi cliente"
    },
    "detalle": [
        {
        "descripcion": "Nombre de producto o servicio",
        "precioUnitario": 1,
        "cantidad": 1,
        "tipoIgv": "GRAVADO_OPERACION_ONEROSA"
        }
    ]
}' \
http://localhost:8080/api/documents/invoice/create
```

# Resources
- [Documentation](https://project-openubl.github.io/xbuilder-server-docs/)
- [Videos](https://www.youtube.com/channel/UChq3xxjyDgjcU346rp0bbtA/)

# Mailing list
You can ask questions and get news through our mailing list:

- Send an email to [projectopenubl+subscribe@googlegroups.com](mailto:projectopenubl+subscribe@googlegroups.com) o
- Join the Group [https://groups.google.com/d/forum/projectopenubl](https://groups.google.com/d/forum/projectopenubl)

# License
- [Eclipse Public License - v 2.0](./LICENSE)


[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fproject-openubl%2Fxbuilder-server.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fproject-openubl%2Fxbuilder-server?ref=badge_large)
