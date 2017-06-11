http://localhost:8080/blog/blogger/login.do 
http://localhost:8080/blog/login.jsp 后台首页

http://localhost:8080/blog/index.do 首页

字符集编码GBK

注意图片上传：imageUrlPrefix+imagePathFormat才是图片地址

	"imageUrlPrefix": "http://127.0.0.1:8080/blog", /* 图片访问路径前缀 */
	
	"imagePathFormat": "/static/userImages/{yyyy}{mm}{dd}/{time}{rand:6}", /* 上传保存路径,可以自定义保存路径和文件名格式 */