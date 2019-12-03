# Levantar el servidor XML Builder
El servidor **XML Builder** puede ser levandando utilizando diferentes métodos. Se recomienda utilizar Docker, Podman, Openshift, Kubernetes para produción; la lista de imágenes pueden ser encontradas acá https://hub.docker.com/r/projectopenubl/xml-builder

## Docker
Requisitos:
1. Tener instalado [Docker](https://www.docker.com/). Puedes utilizar la siguiente [guía de instalación]([Docker](https://docs.docker.com/install/))

Levantar el servidor **XML Builder** es muy sencillo, solamente tienes que abrir un terminal y ejecutar el siguiente comando:

```
docker run -p 8080:8080 projectopenubl/xml-builder
```

Luego debes de abrir un navegador y dirigirte a http://localhost:8080

## Podman
Requisitos:
1. Tener instalado [Podman](https://podman.io/)

El comando es muy similar a Docker (sección anterior):

```
podman run -p 8080:8080 projectopenubl/xml-builder
```

## Java
Requisitos:
1. Tener instalado Java

Pasos para levantar el servidor
1. Descargar el servidor desde https://github.com/project-openubl/xml-builder/releases
1. Descomprimir el archivo descargado en el paso anterior
1. Abrir un terminal y ubicarse en la carpeta donde el servidor fue extraido
1. Ejecutar el siguiente comando: 

```
java -jar *-runner.jar
```

Luego debes de abrir un navegador y dirigirte a http://localhost:8080

Despues de seguir cualquiera de los métodos anteriores, deberás ser capaz de ver la siguiente página:

![XML Builder](images/welcome.png)

# Container Registry
Las imágenes de **XML Builder** se encuentran en el registry de Docker:

https://hub.docker.com/r/projectopenubl/xml-builder
