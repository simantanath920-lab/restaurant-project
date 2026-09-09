package com.simanta.restaurant_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
 
@SpringBootApplication 
@EnableAsync 
public class RestaurantBackendApplication {
 
	public static void main(String[] args) {
		SpringApplication.run(RestaurantBackendApplication.class, args);
		
		System.err.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		System.out.println("Website restaurant-backend running....");
		
	} 

}








