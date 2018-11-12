package com.hll.book.model;

import lombok.Builder;
import lombok.Data;

/**
 * Author: huangll
 * Written on 2018/11/12.
 */
@Builder
@Data
public class Result<T> {

  private boolean success;

  private String msg;

  private T data;
}
