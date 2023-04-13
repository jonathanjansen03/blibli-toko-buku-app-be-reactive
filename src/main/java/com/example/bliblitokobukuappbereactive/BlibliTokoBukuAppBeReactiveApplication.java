package com.example.bliblitokobukuappbereactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing
public class BlibliTokoBukuAppBeReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlibliTokoBukuAppBeReactiveApplication.class, args);
	}

}
