package com.stackroute.productwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping("/react-app/")
@CrossOrigin("*")
public class ProductWebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductWebappApplication.class, args);
	}

	@GetMapping("/frontend")
	public String getIndex(){
		return "index";
	}
}
