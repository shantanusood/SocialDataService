package com.silpaservices.ml.data.utils;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    @Value("${app.name}")
    private String appName;

	/*
	 * @Value("${spark.home}") private String sparkHome;
	 */
    @Bean
    public SparkConf sparkConf() {
        SparkConf sparkConf = new SparkConf()
                .set("spark.app.name" ,appName)
                .set("spark.master", "local[*]");

        return sparkConf;
    }

    @Bean
    public SparkSession sparkSession() {
        return SparkSession
                .builder()
                .config(sparkConf())
                .appName("Java Spark SQL basic example")
                .getOrCreate();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}