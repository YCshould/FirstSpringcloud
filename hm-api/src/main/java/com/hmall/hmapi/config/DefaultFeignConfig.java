package com.hmall.hmapi.config;

import com.hmall.common.utils.UserContext;
import com.hmall.hmapi.client.fallback.ItemClientFallbackFactory;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfig {
    @Bean
    public Logger.Level feignloggerconfig(){
        return Logger.Level.FULL;
    }

    /**
     * 定义feign中的一个拦截器用于在不同的微服务如cart和item中传递userid
     * @return
     */
    @Bean
    public RequestInterceptor requestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                Long userid = UserContext.getUser();
                if(userid!=null){
                    requestTemplate.header("userInfo",userid.toString());
                }
            }
        };
    }

    @Bean
    public ItemClientFallbackFactory itemClientFallbackFactory(){
        return new ItemClientFallbackFactory();
    }
}
