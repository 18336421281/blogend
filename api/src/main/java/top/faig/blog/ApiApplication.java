package top.faig.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("top.faig.blog.code.*.mapper")
public class ApiApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
        System.out.println("=======================end========================");
    }
}