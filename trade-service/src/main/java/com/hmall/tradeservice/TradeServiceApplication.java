package com.hmall.tradeservice;

import com.hmall.hmapi.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.hmall.hmapi.client",defaultConfiguration = DefaultFeignConfig.class)
@SpringBootApplication
@MapperScan("com.hmall.tradeservice.mapper")
public class TradeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeServiceApplication.class, args);
    }

}
