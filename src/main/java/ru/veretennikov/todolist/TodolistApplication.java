package ru.veretennikov.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TodolistApplication {

	/**
	 * для кодирования пароля (чтобы не хранить в открытом виде в БД)
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

}
