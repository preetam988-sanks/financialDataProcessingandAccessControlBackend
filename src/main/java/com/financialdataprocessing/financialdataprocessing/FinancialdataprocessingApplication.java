package com.financialdataprocessing.financialdataprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FinancialdataprocessingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialdataprocessingApplication.class, args);
	}

}
