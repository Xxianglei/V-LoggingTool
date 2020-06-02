package com.xianglei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @Auther: Xianglei
 * @Company: venusgroup
 * @Date: 2020/6/2 14:04
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.xianglei.mapper")
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
