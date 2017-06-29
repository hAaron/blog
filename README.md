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

启动项目：
	先开启服务 nginx，redis