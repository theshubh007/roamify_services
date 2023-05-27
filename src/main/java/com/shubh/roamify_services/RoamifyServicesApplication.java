package com.shubh.roamify_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Import;

import com.shubh.roamify_services.JwtFiles.SecurityConfiguration;

// @SpringBootApplication(exclude = {ReactiveSecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@SpringBootApplication
// @Import({ com.shubh.roamify_services.ConfigFiles.AppConfig.class,
// com.shubh.roamify_services.JwtFiles.SecurityConfiguration.class,})
@Import(com.shubh.roamify_services.JwtFiles.SecurityConfiguration.class)
public class RoamifyServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoamifyServicesApplication.class, args);
	}

}
