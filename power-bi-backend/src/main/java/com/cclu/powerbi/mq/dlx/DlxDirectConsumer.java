package com.cclu.powerbi.mq.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author ChangCheng Lu
 * @date 2023/9/13 21:20
 */
public class DlxDirectConsumer {

    private static final String DEAD_EXCHANGE_NAME = "dead-exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(DEAD_EXCHANGE_NAME, "topic");

        String queueName1 = "policy-dead-letter-queue";
        channel.queueDeclare(queueName1, true, false, false, null);
        channel.queueBind(queueName1, DEAD_EXCHANGE_NAME, "policy");


        String queueName2 = "hospital-dead-letter-queue";
        channel.queueDeclare(queueName2, true, false, false, null);
        channel.queueBind(queueName2, DEAD_EXCHANGE_NAME, "hospital");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback policyDeliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [policy] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        DeliverCallback hospitalDeliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [hospital] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        channel.basicConsume(queueName1, false, policyDeliverCallback, consumerTag -> {
        });
        channel.basicConsume(queueName2, false, hospitalDeliverCallback, consumerTag -> {
        });
    }

}
