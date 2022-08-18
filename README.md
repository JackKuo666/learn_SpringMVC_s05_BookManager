# 基于ssm的图书管理系统
技术：
```
Spring
SpringMVC
SpringSecurity
Mybatis
mysql
html
js
```

## 启动

### 1.下载tomcat: [9.0.65](https://mirrors.cnnic.cn/apache/tomcat/tomcat-9/v9.0.65/bin/)

### 2.配置tomcat

然后配置中文：


```
tomcat 虚拟机选项: -Dfile.encoding=UTF-8
```

配置URL:
```
http://localhost:8011/mvc/page/auth/login
```
配置【部署-应用程序上下文】
```
应用程序上下文: /mvc
```
### 3.配置maven
maven版本：3.8.1

maven的.m2/settings.xml文件配置阿里源
```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                          https://maven.apache.org/xsd/settings-1.0.0.xsd">
      
      <mirrors>
    	<mirror>  
      		<id>alimaven</id>  
      		<name>aliyun maven</name>  
      		<url>http://maven.aliyun.com/nexus/content/groups/public/</url>  
      		<mirrorOf>central</mirrorOf>          
    	</mirror>  
      </mirrors>
</settings>
```

### 4.配置sql
使用本地mysql打开`study.sql`文件

### 5.运行
使用idea配置好tomcat,直接运行即可。