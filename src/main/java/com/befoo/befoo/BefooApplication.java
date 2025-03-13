package com.befoo.befoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BefooApplication {

	public static void main(String[] args) {
		SpringApplication.run(BefooApplication.class, args);
	}

}
