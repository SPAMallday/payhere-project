package com.spamallday.payhere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PayhereApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayhereApplication.class, args);
	}

}
