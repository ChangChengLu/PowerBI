package com.cclu.powerbi.mq.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author ChangCheng Lu
 * @date 2023/9/13 14:46
 */
public class DirectProducer {

    private static final String EXCHANGE_NAME = "direct-exchange";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String userInput = scanner.nextLine();
                String[] strings = userInput.split(" ");
                if (strings.length < 1) {
                    continue;
                }
                String message = strings[0];
                String routingKey = strings[1];
                channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + " with routing:" + routingKey + "'");
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

}
