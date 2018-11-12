spring-cloud-alibaba是spring-cloud规范的另一种实现，提供了注册服务与配置服务二合一的Nacos，提供熔断降级与系统保护的Sentinel.

## 系统整体架构：


## Nacos部署
  - 下载安装包：https://github.com/alibaba/nacos/releases
```bash
unzip nacos-server-0.4.0.zip
cd nacos/bin 
```
  -  部署：
sh startup.sh -m standalone
  -  访问：
http://10.3.10.131:8848/nacos
## Sentinel Dashboard部署
  -  下载dashboard源码，编译得到fat jar(https://github.com/alibaba/Sentinel/tree/master/sentinel-dashboard)
  -  运行：java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard.jar

## Spring-Cloud-Gateway搭建配置与部署
依赖包：
```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-dependencies</artifactId>
      <version>Finchley.SR2</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
<dependencies>
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
  </dependency>
</dependencies>
```

## 服务与调用服务示例工程
  -  服务注册

添加依赖：
```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
  <version>0.2.0.RELEASE</version>
</dependency>
```
配置地址（bootstrap.properties）：
spring.cloud.nacos.discovery.server-addr=10.3.10.131:8848
开启配置：
```java
@EnableDiscoveryClient
@SpringBootApplication
public class BookApplication {
  public static void main(String[] args) {
    SpringApplication.run(BookApplication.class, args);
  }
}
```

  -  调用服务：
添加依赖：
```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```
编写feign接口：
```java
@FeignClient("book-serv")
public interface BookService {
  @GetMapping("/book/{bookName}")
  Result<Book> book(@PathVariable(name = "bookName") String bookName);
}
```
调用：
```java
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
```
  -  动态配置
添加依赖：
```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
  <version>0.2.0.RELEASE</version>
</dependency>
```

配置地址（bootstrap.properties）：
spring.cloud.nacos.config.server-addr=10.3.10.131:8848

注入配置：
```java
@RefreshScope     //用来刷新配置
@RestController
public class BookController {

  @Value("${price}")  //注入配置
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

```
注意：需要在nacos控制台添加配置，dataid=${spring.application.name}.properties

  -  服务保护
添加依赖：
```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>
```
配置dashboard地址：
spring.cloud.sentinel.transport.dashboard=localhost:8080
