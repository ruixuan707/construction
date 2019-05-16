package com.monco;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

/**
 * @Auther: monco
 * @Date: 2019/5/16 20:55
 * @Description: 工地建筑启动类
 */
@Slf4j
@SpringBootApplication
public class ConstructionApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConstructionApplication.class);
        Environment env = app.run(args).getEnvironment();
        log.info("\n----------------------------------------------------------\n\tEnvironment '{}' is running! Access URLs:\n\tLocal: \t\thttp://localhost:{}\n\t\n----------------------------------------------------------", env.getProperty("spring.application.name"), env.getProperty("server.port"));
    }
}
