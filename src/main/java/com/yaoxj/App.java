package com.yaoxj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.joy.*")
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	//@SpringBootApplication底层也是使用spring的@Configuration，使用这个封装的好处就是可以直接在main
	//函数这个启动类里面，直接加载第三方的依赖到ioc容器中。
	@Bean("string")
	public String  getString(){
		return new  String();
	}

}
