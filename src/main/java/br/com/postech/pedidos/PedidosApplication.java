package br.com.postech.pedidos;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(PedidosApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components())
				.info(new Info().title("Postech - Lanchonete do Bairro 🍔 - Pedidos")
						.description("Essencial para orquestrar o processo de pedidos, a API centraliza a criação, busca de produtos, pagamento, e notificações ao cliente, otimizando a eficiência operacional.")
						.contact(new Contact().name("Daniel Maria da Silva").url("https://github.com/postech-lanchonete"))
						.license(new License().name("MIT License").url("https://opensource.org/licenses/MIT"))
						.version("1.0.0-POC"));
	}
}
