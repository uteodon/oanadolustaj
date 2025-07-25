package com.emresahin.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.emresahin"})
@EntityScan(basePackages = {"com.emresahin"})
@EnableJpaRepositories(basePackages = {"com.emresahin"})
public class OrtaAnadoluApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrtaAnadoluApplication.class, args);
	}

}
