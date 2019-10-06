package com.gmail.bishoybasily.cdc;

import com.gmail.bishoybasily.cdc.model.debezium.Change;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class DebeziumEventDeserializer extends JsonDeserializer<Change> {

    public DebeziumEventDeserializer() {
        super(Change.class);
    }
}
