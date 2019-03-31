package com.bear.gateway.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author panda.
 * @version 1.0.
 * @since 2018-11-26 11:20.
 */
@RestController
@RequestMapping("/service-instances")
public class ServiceInstanceController {

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 获取各个服务的信息
     *
     * @return
     */
    @GetMapping
    public Map<String, Object> map() {
        Map<String, Object> map = new HashMap<>();
        List<String> services = discoveryClient.getServices();
        services.forEach(serviceId -> {
            List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
            map.put(serviceId, instances);
        });

        return map;
    }
}
