package com.oyameen.commonservice.modle;


import lombok.Getter;


@Getter
public enum ExchangeType {

    DIRECT("direct"),
    FAN_OUT("fan_out"),
    TOPIC("topic"),
    HEADERS("headers");

    private final String value;

    ExchangeType(String value) {
        this.value = value;
    }

}
