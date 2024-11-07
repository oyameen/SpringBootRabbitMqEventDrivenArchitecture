package com.oyameen.commonservice.modle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OrderEvent implements Serializable {
    private Order order;
    private String message;
    private OrderEventStatus status;
}
