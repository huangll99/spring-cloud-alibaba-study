package com.hll.book.po;

import lombok.Builder;
import lombok.Data;

/**
 * Author: huangll
 * Written on 2018/11/12.
 */
@Builder
@Data
public class Book {

  private String bookName;

  private String author;

  private Double price;
}
