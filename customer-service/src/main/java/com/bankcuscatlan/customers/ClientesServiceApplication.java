package com.bankcuscatlan.customers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ClientesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientesServiceApplication.class, args);
	}

}
