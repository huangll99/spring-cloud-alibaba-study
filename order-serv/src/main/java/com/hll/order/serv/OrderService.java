package com.hll.order.serv;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.hll.order.api.Result;
import com.hll.order.po.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: huangll
 * Written on 2018/11/13.
 */
@Service
public class OrderService {

  @Autowired
  private BookService bookService;


  @SentinelResource(value = "bookSearch",blockHandler = "bookSearchBlock")
  public Book bookSearch(String bookName) {
    Result<Book> result = bookService.book(bookName);
    return result.getData();
  }

  public Book bookSearchBlock(String bookName,BlockException ex){
    System.out.println("book search blocking...");
    ex.printStackTrace();
    return Book.builder().build();
  }
}
