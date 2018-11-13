package com.hll.order.api;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.hll.order.po.Book;
import com.hll.order.po.Order;
import com.hll.order.serv.BookService;
import com.hll.order.serv.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: huangll
 * Written on 2018/11/12.
 */
@RestController
public class OrderController {

  @Autowired
  private OrderService orderService;


  @PostMapping("/orders")
  public Result order(@RequestBody Order order) {

    Book book = orderService.bookSearch(order.getBookName());
    if (book==null){
      return Result.builder().success(false).msg("flow error").build();
    }
    System.out.println(book);
    double money = order.getCount() * book.getPrice();
    order.setTotalMoney(money);
    System.out.println(order);
    return Result.builder().success(true).msg("ok").build();
  }

  /*@SentinelResource("bookSearch")
  public Book bookSearch(String bookName) {
    Result<Book> result = bookService.book(bookName);
    return result.getData();
   *//* Entry entry = null;
    try {
      entry = SphU.entry("bookSearch");
      Result<Book> result = bookService.book(bookName);
      return result.getData();
    } catch (BlockException e) {
      e.printStackTrace();
      return null;
    } finally {
      if (entry != null) {
        entry.exit();
      }
    }*//*
  }*/
}
