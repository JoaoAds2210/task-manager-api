package com.example.task_manager_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TaskManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApiApplication.class, args);
	}

}
