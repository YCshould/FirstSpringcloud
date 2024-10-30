package com.hmall.hmapi.client;


import com.hmall.hmapi.client.fallback.ItemClientFallbackFactory;
import com.hmall.hmapi.config.DefaultFeignConfig;
import com.hmall.hmapi.dto.ItemDTO;
import com.hmall.hmapi.dto.OrderDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/*
再加入openfeign和对应负载均衡的依赖后可用这个代替nacos服务，代码更简明，只需调用该接口即可
 */
@FeignClient(value = "item-service",configuration = DefaultFeignConfig.class,fallbackFactory = ItemClientFallbackFactory.class)
public interface ItemClient {

    @GetMapping("/items")
    List<ItemDTO> queryItemByIds(@RequestParam("ids") Collection<Long> ids);

    @PutMapping("/items/stock/deduct")
    void deductStock(@RequestBody List<OrderDetailDTO> items);
}
