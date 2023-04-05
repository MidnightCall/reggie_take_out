package com.kojikoji;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableCaching // 开启springCache缓存功能
public class ReggieTakeOutApplication {

    public static void main(String[] args) {
        log.info("项目启动成功...");
        SpringApplication.run(ReggieTakeOutApplication.class, args);
    }

}
