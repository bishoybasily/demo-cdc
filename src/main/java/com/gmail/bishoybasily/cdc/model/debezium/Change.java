/*
 * Copyright (C) 2018 Joumen Harzli
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */

package com.gmail.bishoybasily.cdc.model.debezium;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
public class Change implements Comparable<Change> {

    private Payload payload;

    @Override
    public int compareTo(Change change) {
        return payload.date.compareTo(change.payload.date);
    }

    public Payload.Operation getOperation() {
        return payload.operation;
    }

    public String getTable() {
        return payload.source.table;
    }

    @Data
    public static class Payload {

        private Map<String, Object> before;
        private Map<String, Object> after;
        private Source source;
        @JsonSetter("op")
        private Operation operation;
        @JsonSetter("ts_ms")
        private Date date;

        @RequiredArgsConstructor
        public enum Operation {

            CREATE("c"),
            UPDATE("u"),
            DELETE("d");

            private final String value;

            @JsonCreator
            public static Operation fromValue(String value) {
                for (Operation operation :
                        values()) {
                    if (operation.value.equals(value))
                        return operation;
                }
                return null;
            }

        }

        @Data
        public static class Source {

            private String name;
            private String connector;
            @JsonSetter("db")
            private String database;
            private String table;

        }

    }

}
