package com.example.pagamento_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PagamentoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagamentoBackendApplication.class, args);
	}

}
