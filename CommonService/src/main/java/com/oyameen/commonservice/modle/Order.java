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
public class Order implements Serializable {
    private String id;
    private String name;
    private int quantity;
    private double price;
}
