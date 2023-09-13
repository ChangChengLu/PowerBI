package com.cclu.powerbi.mq.multi;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/**
 * @author ChangCheng Lu
 * @date 2023/9/12 13:48
 */
public class MultiConsumer {

    private static final String TASK_QUEUE_NAME = "multi_queue";

    private static final int CHANNEL_NUMBER = 2;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        for (int i = 1; i <= CHANNEL_NUMBER; i++) {
            final Channel channel = connection.createChannel();
            channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);

            channel.basicQos(1);

            int finalI = i;
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                synchronized (""+delivery.getEnvelope().getDeliveryTag()) {
                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    try {
                        // 处理工作
                        System.out.println(" [x] Received '" + "编号:" + finalI + ":" + message + "'");
                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                        // 停 20 秒，模拟机器处理能力有限
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
                    } finally {
                        System.out.println(" [x] Done");
                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    }
                }
            };
            channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> {});
        }
    }

//    public static void main(String[] args) throws IOException, TimeoutException {
//        ConnectionFactory factory = new ConnectionFactory();
//        Connection connection = factory.newConnection();
//
//        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
//        Channel channel = connection.createChannel();
//        channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
//        channel.basicQos(1);
//
//        Set<Long> processedTags = new HashSet<>();
//        for (int i = 1; i <= CHANNEL_NUMBER; i++) {
//            int finalI = i;
//            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//                long deliveryTag = delivery.getEnvelope().getDeliveryTag();
//                if (!processedTags.contains(deliveryTag)) {
//                    processedTags.add(deliveryTag);
//
//                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//                    try {
//                        // 处理工作
//                        System.out.println(" [x] Received '" + "编号:" + finalI + ":" + message + "'");
//                        Thread.sleep(1000);
//                        System.out.println(" [x] Done");
//                        channel.basicAck(deliveryTag, false);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        channel.basicNack(deliveryTag, false, false);
//                    } finally {
//                        processedTags.remove(deliveryTag);
//                    }
//                } else {
//                    channel.basicAck(deliveryTag, false);
//                }
//            };
//            channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> {});
//        }
//    }

}
