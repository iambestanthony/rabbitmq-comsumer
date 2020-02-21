package com.kayakwise.rabbitmq.component;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;


@Component
public class RabbitReveive {

    /**
     *
     * @param message
     * @param channel
     * @throws Exception
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${com.kayakwise.rabbitmq.properties.queue.name}",
                    durable = "${com.kayakwise.rabbitmq.properties.queue.durable}"),
            exchange = @Exchange(name = "${com.kayakwise.rabbitmq.properties.exchange.name}",
                    durable = "${com.kayakwise.rabbitmq.properties.exchange.durable}",
                    type = "${com.kayakwise.rabbitmq.properties.exchange.type}",
                    ignoreDeclarationExceptions = "${com.kayakwise.rabbitmq.properties.exchange.ignoreDeclarationExceptions}"),
            key = "${com.kayakwise.rabbitmq.properties.key}"
    )
    )
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("-----------------------------");
        System.out.println("消费消息：" + message.getPayload());
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag, false);
    }
}
