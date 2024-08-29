package com.chanochoca.app.consumer;

import com.chanochoca.app.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Servicio consumidor de mensajes JSON de RabbitMQ.
 *
 * Esta clase actúa como un consumidor que escucha mensajes en formato JSON desde una cola de RabbitMQ.
 * Utiliza la anotación `@RabbitListener` para suscribirse a una cola específica y registra los mensajes
 * JSON recibidos utilizando un logger. Los mensajes se convierten automáticamente a un objeto `User`
 * gracias al soporte de conversión de mensajes configurado en RabbitMQ.
 */
@Service
public class RabbitMQJsonConsumer {

    // Logger para registrar los mensajes JSON recibidos de RabbitMQ.
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    /**
     * Método que consume mensajes JSON de la cola de RabbitMQ.
     *
     * Este método se ejecuta cada vez que se recibe un mensaje JSON en la cola especificada
     * por la propiedad `rabbitmq.queue.json.name`. El mensaje se convierte automáticamente
     * en un objeto `User`, y su contenido se registra utilizando `SLF4J Logger` para
     * seguimiento y depuración.
     *
     * @param user el objeto `User` recibido desde la cola de RabbitMQ, convertido desde el mensaje JSON.
     */
    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consumeJsonMessage(User user) {
        // Registra el contenido del mensaje JSON recibido en los logs.
        LOGGER.info(String.format("Received JSON message -> %s", user.toString()));
    }
}
