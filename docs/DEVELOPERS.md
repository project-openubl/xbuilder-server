# Levantar servidor para desarrollo

Pre requisitos:
- Maven
- Git
- Java

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
4. Arrancar servidor

    - Para XMl Builder:
    ```
    ./mvnw quarkus:dev -f api/ -DnoDeps
    ```
 
    - Para XML Builder Signer:
     ```
     ./mvnw quarkus:dev -f api-signer/ -DnoDeps
     ```
     
5. Abrir el navegador y dirigirte a http://localhost:8080/
