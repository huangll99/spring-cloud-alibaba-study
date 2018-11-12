package com.hll.order.api;

import com.hll.order.po.Book;
import com.hll.order.po.Order;
import com.hll.order.serv.BookService;
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
  private BookService bookService;

  @PostMapping("/order")
  public Result order(@RequestBody Order order) {
    Result<Book> result = bookService.book(order.getBookName());
    Book book = result.getData();
    System.out.println(book);
    double money = order.getCount() * book.getPrice();
    order.setTotalMoney(money);
    System.out.println(order);
    return Result.builder().success(true).msg("ok").build();
  }
}
