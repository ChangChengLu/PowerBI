package com.cclu.powerbi.bimq;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author ChangCheng Lu
 * @date 2023/9/13 22:32
 */
@Slf4j
@Component
public class MyMessageConsumer {

    /**
     * 指定程序监听的消息队列和确认机制
     * @param message msg
     * @param channel channel
     * @param deliveryTag dt
     */
    @SneakyThrows
    @RabbitListener(queues = {"code_queue"}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receiveMessage message = {}", message);
        channel.basicAck(deliveryTag, false);
    }

}
