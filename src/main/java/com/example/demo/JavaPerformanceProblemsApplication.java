package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@SpringBootApplication
@EntityScan({"com.example.demo.modules.cache", "com.example.demo.modules.nplusone"})
@EnableJpaRepositories({"com.example.demo.modules.cache", "com.example.demo.modules.nplusone"})
public class JavaPerformanceProblemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaPerformanceProblemsApplication.class, args);
		log.info("Java Performance Problems Demo Application Started!");
		log.info("Available modules:");
		log.info("   - N+1 Query Problem Module");
		log.info("   - Cache Key Generation Problems Module");
		log.info("   - H2 Console: /h2-console");
		log.info("Application is ready for testing!");
	}

}
