package com.hmall.hmapi.client.fallback;


import com.hmall.hmapi.client.ItemClient;
import com.hmall.hmapi.dto.ItemDTO;
import com.hmall.hmapi.dto.OrderDetailDTO;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.openfeign.FallbackFactory;

import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ItemClientFallbackFactory implements FallbackFactory {

    @Override
    public Object create(Throwable cause) {
        return new ItemClient() {
            @Override
            public List<ItemDTO> queryItemByIds(Collection<Long> ids) {
                log.info("查询购物车失败:{}",cause);
                return Collections.emptyList();
            }

            @Override
            public void deductStock(List<OrderDetailDTO> items) {
                log.info("扣减库存失败:{}",cause);
                throw new RuntimeException(cause);
            }
        };
    }
}
