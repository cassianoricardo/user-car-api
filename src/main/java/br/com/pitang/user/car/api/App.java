package br.com.pitang.user.car.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@OpenAPIDefinition
@SpringBootApplication
public class App {

	public static void main(String[] args) {

		var application = new SpringApplication(App.class);
		var env = application.run(args).getEnvironment();

		var port = env.getProperty("server.port");

		log.info("\n\n\t Swagger: http://localhost:{}/swagger-ui/index.html\n", port);
	}

}
