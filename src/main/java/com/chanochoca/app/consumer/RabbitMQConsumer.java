package com.chanochoca.app.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Servicio consumidor de mensajes de RabbitMQ.
 *
 * Esta clase actúa como un consumidor que escucha mensajes de una cola de RabbitMQ.
 * Utiliza la anotación `@RabbitListener` para suscribirse a una cola específica y
 * registrar los mensajes recibidos mediante el uso de un logger.
 */
@Service
public class RabbitMQConsumer {

    // Logger para registrar los mensajes recibidos de RabbitMQ.
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    /**
     * Método que consume mensajes de la cola de RabbitMQ.
     *
     * Este método se ejecuta cada vez que se recibe un mensaje en la cola especificada
     * por la propiedad `rabbitmq.queue.name`. Registra el contenido del mensaje recibido
     * utilizando `SLF4J Logger` para seguimiento y depuración.
     *
     * @param message el mensaje recibido de la cola de RabbitMQ.
     */
    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String message) {
        // Registra el mensaje recibido en los logs.
        LOGGER.info(String.format("Received message -> %s", message));
    }
}
