package com.hll.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Author: huangll
 * Written on 2018/11/12.
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableAspectJAutoProxy
public class OrderApplication {

  public static void main(String[] args) {
    SpringApplication.run(OrderApplication.class, args);
  }
}
