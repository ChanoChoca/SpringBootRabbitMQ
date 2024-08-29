package com.chanochoca.app.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de RabbitMQ para la aplicación.
 *
 * Esta clase configura los componentes esenciales para la integración con RabbitMQ,
 * incluyendo colas, intercambios, y bindings (enlaces) necesarios para el envío y
 * recepción de mensajes. Utiliza la anotación @Configuration para indicar que esta
 * clase contiene definiciones de beans que serán manejadas por el contenedor de Spring.
 */
@Configuration
public class RabbitMQConfig {

    // Nombre de la cola básica que se obtiene del archivo de propiedades.
    @Value("${rabbitmq.queue.name}")
    private String queue;

    // Nombre de la cola que manejará mensajes en formato JSON.
    @Value("${rabbitmq.queue.json.name}")
    private String jsonQueue;

    // Nombre del intercambio que se utilizará para enrutar los mensajes.
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    // Clave de enrutamiento para los mensajes básicos.
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    // Clave de enrutamiento para los mensajes en formato JSON.
    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    /**
     * Declara una cola simple basada en el nombre proporcionado por la propiedad
     * 'rabbitmq.queue.name'. Esta cola se usará para mensajes genéricos.
     *
     * @return un bean de tipo Queue con el nombre configurado.
     */
    @Bean
    public Queue queue() {
        return new Queue(queue);
    }

    /**
     * Declara una cola adicional que maneja mensajes en formato JSON, basada en
     * el nombre especificado por 'rabbitmq.queue.json.name'.
     *
     * @return un bean de tipo Queue con el nombre configurado para mensajes JSON.
     */
    @Bean
    public Queue jsonQueue() {
        return new Queue(jsonQueue);
    }

    /**
     * Declara un intercambio de tipo TopicExchange, el cual se usa para enrutar
     * mensajes a las colas apropiadas basado en las claves de enrutamiento.
     *
     * @return un bean de tipo TopicExchange configurado con el nombre especificado.
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    /**
     * Crea un binding (enlace) entre la cola básica y el intercambio usando
     * la clave de enrutamiento proporcionada por 'rabbitmq.routing.key'.
     * Esto permite que los mensajes enviados al intercambio se dirijan a esta cola
     * si la clave de enrutamiento coincide.
     *
     * @return un bean de tipo Binding que enlaza la cola básica con el intercambio.
     */
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    /**
     * Crea un binding (enlace) entre la cola de mensajes JSON y el intercambio
     * usando la clave de enrutamiento 'rabbitmq.routing.json.key'. Esto permite
     * que los mensajes con dicha clave se enruten específicamente a la cola de JSON.
     *
     * @return un bean de tipo Binding que enlaza la cola JSON con el intercambio.
     */
    @Bean
    public Binding jsonBinding() {
        return BindingBuilder
                .bind(jsonQueue())
                .to(exchange())
                .with(routingJsonKey);
    }

    /**
     * Define un convertidor de mensajes para transformar mensajes en formato JSON.
     * Utiliza Jackson para la conversión, facilitando la serialización y
     * deserialización de objetos Java en formato JSON.
     *
     * @return un bean de tipo MessageConverter que maneja la conversión a JSON.
     */
    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Configura un template de mensajería RabbitMQ (RabbitTemplate) que se
     * utiliza para enviar y recibir mensajes. El template se configura con un
     * convertidor de mensajes JSON para que todos los mensajes enviados se
     * conviertan automáticamente al formato adecuado.
     *
     * @param connectionFactory la fábrica de conexiones que gestiona la conexión con RabbitMQ.
     * @return un bean de tipo AmqpTemplate configurado con el RabbitTemplate y el convertidor JSON.
     */
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
