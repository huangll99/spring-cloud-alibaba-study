package com.hll.book.api;

import com.hll.book.model.Result;
import com.hll.book.po.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * Author: huangll
 * Written on 2018/11/12.
 */
@RefreshScope
@RestController
public class BookController {

  @Value("${price}")
  private Double price;

  @PostMapping("/bookAdd")
  public Result bookAdd(@RequestBody Book book) {
    System.out.println(book.toString());
    return Result.builder().success(true).msg("ok").build();
  }

  @GetMapping("/book/{bookName}")
  public Result<Book> book(@PathVariable(name = "bookName") String bookName) {
    Book book = Book.builder().bookName(bookName).author("海明威").price(price).build();
    return Result.<Book>builder().success(true).msg("ok").data(book).build();
  }
}
