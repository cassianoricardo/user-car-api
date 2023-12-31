package br.com.pitang.user.car.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class App {

	public static void main(String[] args) {

		var application = new SpringApplication(App.class);
		var env = application.run(args).getEnvironment();

		var port = env.getProperty("server.port");

		log.info("\n\n\t Swagger: http://localhost:{}/api/swagger-ui/index.html\n", port);

		log.info("\n\n\t Api Login: user, Password: 123\n");

		log.info("\n\n\t H2 Console: http://localhost:{}/api/h2-console\n", port);

		log.info("\n\n\t H2 Login: userdb, Password: userpass\n");


	}

}
