package it.kafka.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;



@SpringBootApplication

public class Application extends SpringBootServletInitializer {


    public static void main(String[] args){

        System.setProperty("server.servlet.context-path", "/kafka/api/v1");
        System.setProperty("allowChunking", "false");
        System.setProperty("allowChunking", "false");
        System.setProperty("bper.connectionTimeout", "2000");
        System.setProperty("bper.socketTimeout", "2000");
        System.setProperty("spring.kafka.bootstrap-servers", "localhost:9092");




        SpringApplication.run(Application.class);

    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }



}
