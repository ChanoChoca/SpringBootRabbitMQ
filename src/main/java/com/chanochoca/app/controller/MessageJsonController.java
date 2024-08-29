package com.chanochoca.app.controller;

import com.chanochoca.app.dto.User;
import com.chanochoca.app.publisher.RabbitMQJsonProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para enviar mensajes JSON a RabbitMQ.
 *
 * Esta clase expone un endpoint para enviar mensajes en formato JSON a RabbitMQ.
 * Utiliza `RabbitMQJsonProducer` para enviar los mensajes a un intercambio y
 * clave de enrutamiento configurados. Recibe los datos a través de una petición
 * POST con un cuerpo JSON que se convierte en un objeto `User`.
 */
@RestController
@RequestMapping("/api/v1")
public class MessageJsonController {

    // Productor de mensajes JSON que se encarga de enviar los mensajes a RabbitMQ.
    private final RabbitMQJsonProducer jsonProducer;

    /**
     * Constructor que inyecta la dependencia de RabbitMQJsonProducer.
     *
     * @param jsonProducer el productor de mensajes JSON utilizado para enviar mensajes a RabbitMQ.
     */
    public MessageJsonController(RabbitMQJsonProducer jsonProducer) {
        this.jsonProducer = jsonProducer;
    }

    /**
     * Endpoint para enviar un mensaje JSON a RabbitMQ.
     *
     * Este método expone un endpoint POST que recibe un objeto `User` en el cuerpo de la solicitud,
     * lo envía a RabbitMQ utilizando el productor de mensajes JSON y retorna una respuesta de éxito.
     *
     * URL de ejemplo: `POST http://localhost:8080/api/v1/publish`
     *
     * @param user el objeto `User` recibido como JSON en el cuerpo de la solicitud.
     * @return una respuesta HTTP 200 indicando que el mensaje se envió con éxito a RabbitMQ.
     */
    @PostMapping("/publish")
    public ResponseEntity<String> sendJsonMessage(@RequestBody User user) {
        // Enviar el mensaje JSON al productor para ser publicado en RabbitMQ.
        jsonProducer.sendJsonMessage(user);

        // Retorna una respuesta de éxito.
        return ResponseEntity.ok("Json message sent to RabbitMQ ...");
    }
}
