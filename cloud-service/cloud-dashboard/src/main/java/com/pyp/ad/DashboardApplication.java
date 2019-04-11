package com.pyp.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/11 22:20
 * @modifier:
 * @version: V1.0
 */
@EnableEurekaClient
@SpringBootApplication
@EnableHystrixDashboard
public class DashboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(DashboardApplication.class, args);
    }
}
