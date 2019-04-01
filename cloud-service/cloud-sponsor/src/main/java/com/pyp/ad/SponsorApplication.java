package com.pyp.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/1 22:19
 * @modifier:
 * @version: V1.0
 */
@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableEurekaClient
public class SponsorApplication {
    public static void main(String[] args) {
        SpringApplication.run(SponsorApplication.class, args);
    }
}
