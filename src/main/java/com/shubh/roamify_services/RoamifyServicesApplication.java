package com.shubh.roamify_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {ReactiveSecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@Import({com.shubh.roamify_services.ConfigFiles.AppConfig.class})
public class RoamifyServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoamifyServicesApplication.class, args);
	}

}
