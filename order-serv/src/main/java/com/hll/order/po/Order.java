package com.hll.order.po;

import lombok.Data;

/**
 * Author: huangll
 * Written on 2018/11/12.
 */
@Data
public class Order {

  private String bookName;

  private Integer count;

  private Double totalMoney;
}
