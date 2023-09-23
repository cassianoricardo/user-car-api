package br.com.pitang.user.car.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans({@ComponentScan(basePackages = "br.com.pitang.user.car.api")})
public class ScansConfiguration {
}
