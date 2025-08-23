package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@SpringBootApplication
@EntityScan("com.example.demo.modules")
@EnableJpaRepositories("com.example.demo.modules")
public class JavaPerformanceProblemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaPerformanceProblemsApplication.class, args);
		log.info("Java Performance Problems Demo Application Started!");
		log.info("Modules:");
		log.info("   N+1 Query Disaster Module: /api/nplusone");
		log.info("   H2 Console: /h2-console");
		log.info("Test endpoints:");
		log.info("   N+1 Problem: http://localhost:8080/api/nplusone/users/nplusone");
		log.info("   Optimized: http://localhost:8080/api/nplusone/users/optimized");
		log.info("   Info: http://localhost:8080/api/nplusone/info");
	}

}
