package com.cclu.powerbi.bimq;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author ChangCheng Lu
 * @date 2023/9/13 22:40
 */
@SpringBootTest
public class MyMessageProducerTest {

    @Resource
    private MyMessageProducer myMessageProducer;

    @Test
    public void sendMessage() {
        myMessageProducer.sendMessage("code_exchange", "my_routingKey", "你好呀");
    }

}
