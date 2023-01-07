package com.elibrary.rshelfstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.elibrary.rshelfstore.repository")
@EnableScheduling
public class RshelfstoreApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RshelfstoreApplication.class, args);
		
	}

}
