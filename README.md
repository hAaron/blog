http://localhost:8080/blog/login.jsp 后台首页

http://localhost:8080/blog/index.do 首页

字符集编码GBK

注意图片上传：imageUrlPrefix+imagePathFormat才是图片地址

	"imageUrlPrefix": "http://127.0.0.1:8080/blog", /* 图片访问路径前缀 */
	
	"imagePathFormat": "/static/userImages/{yyyy}{mm}{dd}/{time}{rand:6}", /* 上传保存路径,可以自定义保存路径和文件名格式 */

更改图片上传到nginx外部服务器

	config.properties 定义文件上传的地址
	
	FileUpLoadController.java FileUtils.java  文件上传的辅助工具类
	
	注意图片路径


注意：

> 1.如果遇到图片上传问题，先检查一下Nginx服务器是否开启

	
加载spring配置文件时

    如果Spring配置文件中所定义的Bean类实现了ApplicationContextAware 接口，那么在加载Spring配置文件时，会自动调用ApplicationContextAware 接口中的
    public void setApplicationContext(ApplicationContext context) throws BeansException 方法，获得ApplicationContext对象。
    前提必须在Spring配置文件中指定该类。 
	<bean id="springContextHolder" class=" com.aaron.util.SpringContextHolder" />  

日志记录
	SpringAop 处理日志，保存日志表中(注意如何获取request请求对象，AOP本身和Servlet无关)


技术栈

Spring

Springmvc

mybatis

博客前端 bootstrap

博客后端 jquery easyui

redis

更新日志

	2017年6月12日	spring整合redis
	2017年6月13日	图片上传到nginx外部服务器
	2017年6月14日	更改sping加载bean方式 SpringContextHolder
	2017年6月26日    新增日志记录
	2017年6月27日    新增日志管理界面
	2017年7月3日     新增处理dubbo接口
	2017年7月5日     新增nginx反向代理
	2017年7月17日    新增日志导出功能

启动项目：
	先开启服务 nginx，redis


nginx 代理两台服务器

upstream blog.aaron.com {
		server 192.168.1.161:8080;
		server 192.168.1.161:8888;
	}
server {
        listen       8188;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location / {
            root   E:\\temp\\blog\\images;
			
			#反向代理的地址
            index  index.html index.htm;     
            proxy_pass        http://blog.aaron.com;     
            proxy_set_header  X-Real-IP  $remote_addr;     
            client_max_body_size  100m;  
        }

nginx 使用sticky方式处理session共享

nginx安装

	解压：
	tar -xzvf nginx-1.8.0.tar.gz 
	进入安装目录：
	cd nginx-1.8.0 
	配置编译信息：
	./configure --prefix=/usr/local/nginx --add-module=/usr/local/nginx-sticky-module 
	编译： make（不要make install） 

nginx第三方模块安装 nginx-sticky-module 

	解压：nginx-goodies-nginx-sticky-module-ng-08a395c66e42.tar.gz 
	重命名：nginx-sticky-module 
	进入安装目录：cd nginx-sticky-module 
	配置编译信息：
	./configure --prefix=/usr/local/nginx --add-module=/usr/local/nginx-sticky-module

Eclipse maven 打包跳过测试类

pom.xml 配置

	<!-- 跳过测试 -->
	<plugin>
		<groupId>org.apache.maven.plugins</groupId>
		<artifactId>maven-surefire-plugin</artifactId>
		<configuration>
			<skip>true</skip>
		</configuration>
	</plugin> 