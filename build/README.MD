####1、环境变量 
JAVA_HOME,MAVEN_HOME,PATH,CLASSPATH需配置正确；

####2、打包配置
将需要发布的服务配置到build.properties 文件中，以逗号分隔开，中间不要留空格；
    服务
 ```text
   bear.server=bear-register-center,bear-auth-center,bear-config-center,bear-file-center,bear-gateway-zuul,bear-log-center,bear-monitor-center,bear-user-center,bear-notification-center,bear-system-admin
```
 web服务
```text
    bear.web=
```
   
####3、打包与拷贝
执行(双击)build-all.bat脚本,将会执行maven打包与拷贝等动作；

####4、结构说明
当前目录新建目录结构
```text
bears 
--server  服务列表
  eg:
  --bear-register-center
   --bin 
         启动脚本(linux下，需要授权(chmod 755 *.sh),如果出现 _bash^M,说明该文件是windows格式)
         需要执行步骤（编辑-修改文件格式)如： vi start.sh -> Esc ->:set ff=unix ->Esc ->:wq
   --conf   私有配置文件
   --lib    依赖jar
   --bear-register-center.jar   后续如果没有引入新的依赖，发布代码只要上传该jar即可。
--web   待续... 计划放前端服务
```    
####5、其他
5.1、如果出现构建中断，先把fms目录手动删除，再重新构建.

5.2、需要检查一下linux部署环境是否安装了 nc，如果没有的话可以从rpm 目录下取安装包。
