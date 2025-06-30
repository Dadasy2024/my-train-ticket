package tech.luckyyi.trainticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动主类
 * @author sy
 * @date 2025-06-30
 * @description Spring Boot 启动入口，自动扫描本包及子包下所有组件
 */
@SpringBootApplication // 这是Spring Boot的核心注解
public class TrainTicketBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrainTicketBackendApplication.class, args); // 启动 Spring Boot 应用
    }
}
