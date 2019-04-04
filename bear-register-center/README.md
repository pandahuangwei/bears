# 注册中心
默认端口号8761
目前是单注册中心，多注册中心待完善

### 20190404 增加访问权限
```xml
<!-- 添加注册中心权限依赖  -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

```
### bootstrop.yml 配置
```yaml
spring: 
  security:
    basic:
      enabled: true
    user:
      name: admin
      password: admin
```

### 客户端服务application.properties
```yaml
eureka:
   client:
      serviceUrl:
         defaultZone: http://admin:admin@${eureka.instance.hostname}:8761/eureka/
```


