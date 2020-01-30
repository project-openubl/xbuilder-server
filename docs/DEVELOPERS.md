# Levantar servidor para desarrollo

Pre requisitos:
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)
- Java - OpenJDK  (8, 11)
- [Nodejs](https://nodejs.org/en/download/) (>=12.14.1)
- [yarn](https://legacy.yarnpkg.com/en/docs/install) (>=1.21.1)

Para levantar el servidor en modo desarrollo necesitas:

1. Lo primero es hacer un fork al projecto https://github.com/project-openubl/xml-builder

2. Después debes de clonar el project que acabas de hacer fork:
    ```
    git clone https://github.com/carlosthe19916/xml-builder.git 
    ```
    recuerda cambiar `carlosthe19916` por el usuario con el que hiciste Fork en el paso anterior
3. Descargar dependencias
    ```
    ./mvnw install -DskipTests
    ```

## Levantar Xml Builder
Arrancar servidor:

```
./mvnw clean compile quarkus:dev -f api/ -DnoDeps
```

Abrir el navegador y dirigirte a http://localhost:8080/

 ## Levantar Xml Builder Signer
Para XML Builder Signer necesitamos levantar servidor pero tambien la UI

- Levantar servidor:
 ```
 ./mvnw clean compile quarkus:dev -f api-signer/ -DnoDeps
 ```

- Levantar la UI:
```
cd ui/
yarn install
yarn start
 ```

Abrir el navegador y dirigirte a http://localhost:3000/
