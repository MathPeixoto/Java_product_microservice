package com.mpeixoto.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * The main class.
 *
 * @author mpeixoto
 */
@SpringBootApplication(scanBasePackages = "com.mpeixoto")
public class MainProduct {

    /**
     * The entry point.
     *
     * @param args Type: Array os strings
     */
    public static void main(String[] args) {
        SpringApplication.run(MainProduct.class);
    }

    /**
     * provide a default instance of RestTemplate.
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
