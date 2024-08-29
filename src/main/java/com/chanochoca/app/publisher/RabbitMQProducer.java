package com.chanochoca.app.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Servicio productor para enviar mensajes a RabbitMQ.
 *
 * Esta clase actúa como un productor de mensajes, enviando mensajes a un intercambio de RabbitMQ
 * con una clave de enrutamiento específica. Utiliza `RabbitTemplate` para interactuar con RabbitMQ
 * y `SLF4J Logger` para registrar las actividades de envío de mensajes.
 */
@Service
public class RabbitMQProducer {

    // Nombre del intercambio que se obtiene del archivo de propiedades.
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    // Clave de enrutamiento que se usará para enviar los mensajes a la cola apropiada.
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    // Logger para registrar la información de los mensajes enviados.
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    // RabbitTemplate se utiliza para enviar mensajes a RabbitMQ.
    private final RabbitTemplate rabbitTemplate;

    /**
     * Constructor que inyecta el `RabbitTemplate` necesario para enviar mensajes a RabbitMQ.
     *
     * @param rabbitTemplate el template de mensajería configurado para enviar mensajes a RabbitMQ.
     */
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Envía un mensaje al intercambio de RabbitMQ con la clave de enrutamiento configurada.
     *
     * Este método utiliza el `RabbitTemplate` para enviar el mensaje al intercambio
     * especificado por la propiedad `rabbitmq.exchange.name` y la clave de enrutamiento
     * definida en `rabbitmq.routing.key`. Registra el mensaje enviado para seguimiento.
     *
     * @param message el mensaje que se desea enviar al intercambio.
     */
    public void sendMessage(String message) {
        // Registra el mensaje que se va a enviar.
        LOGGER.info(String.format("Message sent -> %s", message));
        // Envía el mensaje al intercambio utilizando la clave de enrutamiento configurada.
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
