package com.gmail.bishoybasily.cdc;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Listener {

    @KafkaListener(topics = "dbserver1.cdc.users", groupId = "2")
    public void consume(String msg) {
        System.out.println(msg);
    }

}
