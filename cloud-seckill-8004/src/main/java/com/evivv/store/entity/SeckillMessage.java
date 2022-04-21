package com.evivv.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillMessage implements Serializable {
    public Integer uid;
    private Integer pid;
    private Long orderId;
}
