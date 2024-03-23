package com.chouette.rankingWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RankingWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(RankingWebApplication.class, args);
	}

}
