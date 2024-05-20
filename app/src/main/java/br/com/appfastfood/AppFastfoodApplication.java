package br.com.appfastfood;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableRabbit
@OpenAPIDefinition(info = @Info(
		title = "API - Fast Food",
		version = "0.0.3-SNAPSHOT",
		description = "Seja bem vindo(a) a especificação da API de Fast Food! O sistema possui tudo sobre Clientes, Produtos, Pedidos."
))
public class AppFastfoodApplication {
	public static void main(String[] args) {
		SpringApplication.run(AppFastfoodApplication.class, args);
	}

}
