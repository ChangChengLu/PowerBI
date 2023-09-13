package com.cclu.powerbi.mq.ttl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author ChangCheng Lu
 * @date 2023/9/13 20:34
 */
public class TtlProducer {

    private static final String QUEUE_NAME = "ttl-queue";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            String message = "hello, world!";
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .expiration("30000")
                    .build();
            channel.basicPublish("", QUEUE_NAME, properties, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

}
