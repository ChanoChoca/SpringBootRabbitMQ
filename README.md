# Spring Boot With RabbitMQ

Este proyecto muestra cómo integrar Spring Boot con RabbitMQ, un broker de mensajería de código abierto ampliamente utilizado para la gestión y enrutamiento de mensajes entre aplicaciones y servicios. 
RabbitMQ implementa el protocolo AMQP (Advanced Message Queuing Protocol), lo que facilita la comunicación asíncrona mediante colas de mensajes.

![Main image](RabbitMQ.png)

Componentes Clave de RabbitMQ:
* Exchange: Es el componente que recibe los mensajes enviados por los productores y los dirige a las colas en función de las reglas de enrutamiento definidas. Existen diferentes tipos de exchanges (direct, topic, fanout y headers), cada uno con un comportamiento específico para el enrutamiento de mensajes.
* Routing key: Es una clave utilizada por los exchanges para determinar a qué cola o colas enviar un mensaje. Las claves de enrutamiento permiten establecer patrones de distribución de mensajes, facilitando la segmentación y el direccionamiento basado en reglas definidas.
* Cola: Es el lugar donde se almacenan los mensajes hasta que son consumidos por un receptor. Las colas son el núcleo del sistema de mensajería, garantizando que los mensajes sean entregados de manera ordenada y fiable.
* Binding: Define la relación entre un exchange y una cola utilizando una clave de enrutamiento. Los bindings permiten configurar el enrutamiento interno de mensajes dentro de RabbitMQ, asegurando que los mensajes sean entregados a las colas correctas según las reglas establecidas.
* Virtual Hosts: Son particiones lógicas dentro de RabbitMQ que permiten agrupar y aislar recursos (colas, exchanges, usuarios, etc.) de manera independiente. Los VHosts facilitan la gestión de permisos y la organización de las aplicaciones que interactúan con RabbitMQ.

## Tabla de Contenidos

1. [Herramientas](#herramientas-usadas)
2. [Testeo](#testeo)
3. [Endpoints](#endpoints)

## Herramientas usadas

- Spring Boot
    - Spring for RabbitMQ
    - Spring Web
    - Spring Boot DevTools
    - Lombok

## Testeo

Se puede hacer de dos formas, desde un instalable o desde imagen de Docker.

En mi caso lo hice con imagen de Docker, recuerda tener Docker ejecutandose antes de ejecutar el siguiente comando.

En cmd o PowerShell: `docker run -d --restart always --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management-alpine`

Para acceder a RabbitMQ del contenedor: Acceder a http://localhost:15672/, con Username `guest` y Password `guest`

## Endpoints

- **GET http://localhost:8080/api/v1/publish?message=tu%20mensaje**
    - **Descripción:** Permite crear un nuevo usuario.
    - **Respuesta:**
        - Estado 200 Ok si se envia el mensaje exitosamente.
        - Estado 400 Bad Request si hay un error en la solicitud.
        - Cuerpo de la respuesta: `{ Message sent to RabbitMQ ... }`

- **POST http://localhost:8080/api/v1/publish**
    - **Descripción:** Permite crear un nuevo usuario.
    - **Cuerpo de la solicitud:**
        - `{ "id": "ID del cliente", "firstName": "Su nombre", "lastName": "Su apellido" }`
    - **Respuesta:**
        - Estado 200 Ok si el usuario se crea exitosamente.
        - Estado 400 Bad Request si hay un error en la solicitud.
        - Cuerpo de la respuesta: `{ Json message sent to RabbitMQ ... }`

## Authors

- [@Juan Ignacio Caprioli (ChanoChoca)](https://github.com/ChanoChoca)

## Badges

[//]: # (Add badges from somewhere like: [shields.io]&#40;https://shields.io/&#41;)

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)

[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/)

[![AGPL License](https://img.shields.io/badge/license-AGPL-blue.svg)](http://www.gnu.org/licenses/agpl-3.0)
