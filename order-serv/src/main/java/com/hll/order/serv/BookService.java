package com.hll.order.serv;

import com.hll.order.api.Result;
import com.hll.order.po.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Author: huangll
 * Written on 2018/11/12.
 */
@FeignClient("book-serv")
public interface BookService {

  @GetMapping("/book/{bookName}")
  Result<Book> book(@PathVariable(name = "bookName") String bookName);
}
