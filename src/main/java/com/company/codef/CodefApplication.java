package com.company.codef;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodefApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodefApplication.class, args);
		StartBot.start();
	}
}
