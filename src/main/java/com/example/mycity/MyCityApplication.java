package com.example.mycity;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyCityApplication {
    @Bean
    public CloseableHttpClient httpClient() {
        return HttpClients.createDefault();
    }

    public static void main(String[] args) {
        SpringApplication.run(MyCityApplication.class, "--debug");
    }

}
