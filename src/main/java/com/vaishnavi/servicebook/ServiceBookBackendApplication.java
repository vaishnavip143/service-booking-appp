package com.vaishnavi.servicebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ServiceBookBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceBookBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner printDatasource(Environment env) {
		return args -> {
			System.out.println("[DEBUG] spring.datasource.url=" + env.getProperty("spring.datasource.url"));
			System.out.println("[DEBUG] spring.datasource.driver-class-name=" + env.getProperty("spring.datasource.driver-class-name"));
		};
	}

}
