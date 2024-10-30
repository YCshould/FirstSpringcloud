package com.hmall.hmapi.client;

import com.hmall.hmapi.client.fallback.ItemClientFallbackFactory;
import com.hmall.hmapi.config.DefaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service",configuration = DefaultFeignConfig.class,fallbackFactory = ItemClientFallbackFactory.class)
public interface UserClient {
    @PutMapping("/users/money/deduct")
    void deductMoney(@RequestParam("pw") String pw, @RequestParam("amount") Integer amount);
}
