package com.hmall.hmgateway.routes;

import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

@Component
@Slf4j
@RequiredArgsConstructor
/**
 * 利用nacos实现动态路由，无需重启项目就可以改变路由配置
 */
public class DynamicRouterLoader {

    private final NacosConfigManager nacosConfigManager;  //nacos配置后监听配置

    private final RouteDefinitionWriter routeDefinitionWriter;  //nacos中改变路由方法的接口

    private final Set<String> routes=new HashSet<>();  //这个集合用来存放更新后的路由id方便下一次根据路由id删除路由

    private final String dataId="gateway-routes.json";

    private final String group="DEFAULT_GROUP";

    @PostConstruct  //这个注释是项目启动时初始化bean后马上执行该方法
    public void initRouterListen() throws NacosException {
        //项目启动先拉取一次配置后监听配置
        String configInfo = nacosConfigManager.getConfigService().getConfigAndSignListener(dataId, group, 5000, new Listener() {

                    @Override
                    public Executor getExecutor() {
                        return null;
                    }

                    @Override
                    public void receiveConfigInfo(String configInfo) {
                        //监听到变化更新路由表
                        updateConfigInfo(configInfo);
                    }
                }
        );
        //第一次获取配置页更新路由表
        updateConfigInfo(configInfo);
    }

    /**
     * 更新路由表
     * @param configInfo
     */
    public void updateConfigInfo(String configInfo){
        log.info("路由配置信息:{}",configInfo);
        //解析路由信息得到RouteDefinition类型的路由信息，因为yaml文件中配置的路由类型系统规定为RouteDefinition型
        List<RouteDefinition> routeDefinitions = JSONUtil.toList(configInfo, RouteDefinition.class);

        //首先删除旧的路由表，第一次删除中集合routes空，无效，第二次以及后面先删除在更新路由可以实现删除和更新的操作
        for (String route : routes) {
            routeDefinitionWriter.delete(Mono.just(route)).subscribe();
        }
        routes.clear();

        //一条条的更新
        for (RouteDefinition routeDefinition : routeDefinitions) {
            routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
            //记录路由id,以便下一次的更新删除操作
            routes.add(routeDefinition.getId());
        }
    }
}
