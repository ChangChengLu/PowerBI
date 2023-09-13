package com.cclu.powerbi.mq.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author ChangCheng Lu
 * @date 2023/9/13 22:05
 */
public class NormalConsumer {

    private static final String DEAD_EXCHANGE_NAME = "dead-exchange";

    private static final String WORK_EXCHANGE_NAME = "topic-dead-change";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(WORK_EXCHANGE_NAME, "topic");

        Map<String, Object> deadQueueArgs1 = new HashMap<>(2);
        deadQueueArgs1.put("x-dead-letter-exchange", DEAD_EXCHANGE_NAME);
        deadQueueArgs1.put("x-dead-letter-routing-key", "policy");

        String queueName1 = "lyt";
        channel.queueDeclare(queueName1, true, false, false, deadQueueArgs1);
        channel.queueBind(queueName1, WORK_EXCHANGE_NAME, "#.lyt.#");

        Map<String, Object> deadQueueArgs2 = new HashMap<>(2);
        deadQueueArgs2.put("x-dead-letter-exchange", DEAD_EXCHANGE_NAME);
        deadQueueArgs2.put("x-dead-letter-routing-key", "hospital");

        String queueName2 = "yxy";
        channel.queueDeclare(queueName2, true, false, false, deadQueueArgs2);
        channel.queueBind(queueName2, WORK_EXCHANGE_NAME, "#.yxy.#");

        DeliverCallback lytDeliverCallback = (consumerTag, delivery) -> {
            channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
        };

        DeliverCallback yxyDeliverCallback = (consumerTag, delivery) -> {
            channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
        };

        channel.basicConsume(queueName1, false, lytDeliverCallback, consumerTag -> {
        });
        channel.basicConsume(queueName2, false, yxyDeliverCallback, consumerTag -> {
        });
    }

}
