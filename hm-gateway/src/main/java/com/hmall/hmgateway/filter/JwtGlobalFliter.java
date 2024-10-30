package com.hmall.hmgateway.filter;

import com.hmall.common.exception.UnauthorizedException;
import com.hmall.hmgateway.config.AuthProperties;
import com.hmall.hmgateway.util.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component   //申明该类作为bean是为了让项目启动就扫描到然后借助该类的getOrder方法自定义该bean的实现顺序
@RequiredArgsConstructor
public class JwtGlobalFliter implements GlobalFilter, Ordered {

    private final AuthProperties authProperties;

    private final JwtTool jwtTool;

    private final AntPathMatcher antPathMatcher=new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取request
        ServerHttpRequest request = exchange.getRequest();
        //2.判断是否需要拦截
        if(isExist(request.getPath().toString())){
            //放行
            return chain.filter(exchange);
        }
        //3.从请求头中获取token
        String token=null;
        HttpHeaders headers = request.getHeaders();
        List<String> authorization = headers.get("authorization");
        if(authorization!=null&& !authorization.isEmpty()){
            token=authorization.get(0);
        }
        //4.解析token得到用户id
        Long userId=null;
        try{
            userId = jwtTool.parseToken(token);
        }catch (UnauthorizedException e){
            ServerHttpResponse response = exchange.getResponse();
            response.setRawStatusCode(401);  //设计401状态码一帮表示登入出错
            return  response.setComplete();
        }

        //5.在各服务之间传递userId
        String userInfo=userId.toString();
        ServerWebExchange swe = exchange.mutate().request(builder -> builder.header("userInfo", userInfo)).build();

        //放行
        return chain.filter(swe);
    }

    /**
     * 查看所拦截的路径是否是拦截的。在yaml文件中有不用拦截的路径
     * @param str
     * @return
     */
    private boolean isExist(String str) {
        /*
        使用spring提供的antPathMatcher路径匹配器
         */
        for (String path : authProperties.getExcludePaths()) {
            if(antPathMatcher.match(path,str)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
