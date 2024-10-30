package com.hmall.hmapi.client;

import com.hmall.hmapi.client.fallback.PayClientFallbackFactory;
import com.hmall.hmapi.dto.PayOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "pay-service",fallbackFactory = PayClientFallbackFactory.class)
public interface PayClient {
    @GetMapping("/pay-orders/biz/{id}")
    PayOrderDTO queryPayOrderByBizOrderNo(@PathVariable("id") Long id);
}
