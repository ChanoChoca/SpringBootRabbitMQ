package com.chanochoca.app.controller;

import com.chanochoca.app.publisher.RabbitMQProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para manejar la publicación de mensajes a RabbitMQ.
 *
 * Esta clase expone un endpoint HTTP que permite a los clientes enviar mensajes a RabbitMQ
 * a través de la capa de servicio `RabbitMQProducer`. Proporciona una API sencilla
 * para interactuar con el sistema de mensajería desde una interfaz HTTP.
 */
@RestController
@RequestMapping("/api/v1")
public class MessageController {

    // Servicio productor de mensajes de RabbitMQ.
    private final RabbitMQProducer producer;

    /**
     * Constructor que inyecta el servicio `RabbitMQProducer` utilizado para enviar mensajes.
     *
     * @param producer el servicio productor de RabbitMQ que gestiona el envío de mensajes.
     */
    public MessageController(RabbitMQProducer producer) {
        this.producer = producer;
    }

    /**
     * Endpoint para enviar mensajes a RabbitMQ.
     *
     * Este método expone un endpoint GET que permite enviar un mensaje a RabbitMQ.
     * Se accede a este endpoint utilizando la URL `/api/v1/publish` y pasando el
     * mensaje a enviar como parámetro de consulta (`message`). El mensaje es enviado
     * a RabbitMQ a través del servicio `RabbitMQProducer`.
     *
     * Ejemplo de uso: `http://localhost:8080/api/v1/publish?message=hello%20world`
     *
     * @param message el mensaje que se enviará a RabbitMQ.
     * @return un `ResponseEntity` con una respuesta de éxito si el mensaje se envía correctamente.
     */
    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message) {
        // Envía el mensaje al servicio RabbitMQProducer.
        producer.sendMessage(message);
        // Retorna una respuesta de éxito indicando que el mensaje fue enviado.
        return ResponseEntity.ok("Message sent to RabbitMQ ...");
    }
}
