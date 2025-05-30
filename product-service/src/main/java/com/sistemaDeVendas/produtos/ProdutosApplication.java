package com.sistemaDeVendas.produtos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ProdutosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdutosApplication.class, args);
	}

}
