package com.kazemi.challenge.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ChallengeEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeEurekaServerApplication.class, args);
	}

}
