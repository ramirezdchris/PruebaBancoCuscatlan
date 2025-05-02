package com.bankcuscatlan.productservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Arrays;


@SpringBootApplication
@EnableFeignClients(basePackages = "com.bankcuscatlan.productservice")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(ApplicationContext ctx) {
		return args -> {
			Arrays.stream(ctx.getBeanDefinitionNames())
					.filter(name -> name.contains("fake") || name.contains("product"))
					.forEach(System.out::println);
		};
	}
}
