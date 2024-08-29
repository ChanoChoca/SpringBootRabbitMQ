package com.chanochoca.app.publisher;

import com.chanochoca.app.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Servicio productor de mensajes JSON para RabbitMQ.
 *
 * Esta clase se encarga de enviar mensajes en formato JSON a RabbitMQ. Utiliza `RabbitTemplate`
 * para enviar los mensajes a un intercambio (`exchange`) y una clave de enrutamiento (`routing key`)
 * específicos, configurados mediante propiedades. Los mensajes enviados se registran en los logs
 * para seguimiento y monitoreo.
 */
@Service
public class RabbitMQJsonProducer {

    // Nombre del intercambio de RabbitMQ configurado en la propiedad 'rabbitmq.exchange.name'.
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    // Clave de enrutamiento para mensajes JSON configurada en la propiedad 'rabbitmq.routing.json.key'.
    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    // Logger para registrar los mensajes JSON enviados a RabbitMQ.
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonProducer.class);

    // RabbitTemplate utilizado para enviar los mensajes a RabbitMQ.
    private final RabbitTemplate rabbitTemplate;

    /**
     * Constructor para inyectar la dependencia de RabbitTemplate.
     *
     * @param rabbitTemplate plantilla de RabbitMQ utilizada para enviar mensajes.
     */
    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Envía un mensaje JSON a RabbitMQ.
     *
     * Este método envía un objeto `User` convertido en JSON a la cola configurada
     * en RabbitMQ. Utiliza la configuración del intercambio y clave de enrutamiento
     * especificados en las propiedades de la aplicación.
     *
     * @param user el objeto `User` que se enviará a RabbitMQ como mensaje JSON.
     */
    public void sendJsonMessage(User user) {
        // Registra el mensaje JSON que se está enviando en los logs.
        LOGGER.info(String.format("Json message sent -> %s", user.toString()));

        // Envía el mensaje al intercambio de RabbitMQ usando la clave de enrutamiento.
        rabbitTemplate.convertAndSend(exchange, routingJsonKey, user);
    }
}
