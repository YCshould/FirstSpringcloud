package com.hmall.hmapi.client.fallback;

import com.hmall.hmapi.client.PayClient;
import com.hmall.hmapi.dto.PayOrderDTO;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FallbackFactory;


@Slf4j
public class PayClientFallbackFactory implements FallbackFactory<PayClient> {

    @Override
    public PayClient create(Throwable cause) {
        return new PayClient() {
            @Override
            public PayOrderDTO queryPayOrderByBizOrderNo(Long id) {
                return null;
            }
        };
    }
}
