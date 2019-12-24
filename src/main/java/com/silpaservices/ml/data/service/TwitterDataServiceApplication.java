package com.silpaservices.ml.data.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.silpaservices.ml.data.controllers","com.silpaservices.ml.data.utils"})
@EnableAutoConfiguration(exclude= {DataSourceAutoConfiguration.class})
public class TwitterDataServiceApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(TwitterDataServiceApplication.class, args);
	}

}

