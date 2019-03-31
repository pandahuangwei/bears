
### 1.架构简单说明
#### 1.1 架构
基于`Spring Cloud`微`服务`化`开发平台`，具有统一授权、认证后台管理系统，其中包含具备用户管理、资源权限管理、网关API管理等多个模块，支持多业务系统并行开发。
后端核心采用`Spring Boot2`以及`Spring Cloud`相关核心组件，
前端采用`vue-element-admin`组件。

#### 1.2 服务鉴权
`JWT`的方式来加强服务之间调度的权限验证，保证内部服务的安全性
#### 1.3 监控
利用Spring Boot Admin 来监控各个独立Service的运行状态；

#### 1.4 负载均衡
将服务保留的rest进行代理和网关控制，Spring Cloud系列的zuul和ribbon，可以帮我们进行正常的网关管控和负载均衡。
其中扩展和借鉴国外项目的扩展基于JWT的`Zuul限流插件`，方面进行限流。

#### 1.15 服务注册与调用
基于Eureka来实现的服务注册与调用，在Spring Cloud中使用Feign, 我们可以做到使用HTTP请求远程服务时能与调用本地方法一样的编码体验。

#### 熔断机制
因为采取了服务的分布，为了避免服务之间的调用“雪崩”，采用了`Hystrix`的作为熔断器，避免了服务之间的“雪崩”。

### 2. 环境准备
- 2.1 Centos7安装Mysql[详细](https://juejin.im/post/5b9bd3f3e51d450e5071d789)
- 2.2 Rabbitmq 安装 [详细](https://juejin.im/post/5b97e9956fb9a05d37617a79)
- 2.3 Rabbitmq 使用[详细](https://juejin.im/post/5ba4b23de51d450e4d2fdaba)
- 2.4 Mysql 安装[详细](https://juejin.im/post/5b9bd3f3e51d450e5071d789)


### 3. 项目结构
```
├─bears
  │  
  |─bear-auth-center--------认证中心
  |─bear-common-------------通用模块
  |─bear-config-center------配置中心 
  |─bear-file-center--------文件中心 
  |─bear-gateway-zuul-------网关
  |─bear-log-center-------日志中心
  |─bear-log-starter--------日志组件，别的项目直接引入该依赖即可
  |─bear-monitor-center-------监控中心
  |─bear-notification-center-------通知中心
  |─bear-registry-center-----服务注册中心
  |  
  |─bear-system-admin--------后端权限管理模块等
  |─bear-user-center--------用户中心、用户、角色、权限
  |─
  |─

```
### 3. 配置说明
#### 3.1 注册中心(bear-registry-center)
```yaml
server:
  port: 8989 #启动端口
#更详细信息查看bear-registry-center/resources/application.yml
```

#### 3.2 授权服务(bear-auth-server)
```yaml
server:
  port: 8989 #启动端口
# mysql 数据库用户密码
# redis
# rabbitmq   
#更详细信息查看bear-auth-server/resources/application.yml
```



#### 运行步骤
- 运行数据库脚本：
 ```text
  bear/init.sql
```  
- 修改配置数据库配置：
```text
   bear-system-admin/src/main/resources/application.yml、
   bear-gateway-zuul/src/main/resources/application.yml
```
- 按顺序运行main类：
```text
  1.注册中心: bear-registry-center : RegistryCenterApp.java
  2.配置中心: bear-config-center   : ConfigCenterApp.java
  2.鉴权服务: bear-auth-center     : AuthCenterApp.java
  3.后台服务: bear-system-admin    : SystemAdminpp.java
  4.网关服务: bear-gatewayzuul     : GatewayApp.java
  
```


