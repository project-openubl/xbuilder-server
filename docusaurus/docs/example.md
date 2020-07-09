---
id: example
title: Example
---

Once you started _XBuilder Server_ following any of the methods described in [installation](./installation) you should be able to:

- Create XML files based on UBL
- Sign XML files

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
