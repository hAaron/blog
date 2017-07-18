<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<title>Excel导入</title>
<script language="javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.8.3.min.js" type=text/javascript></script>
<script language="javascript" src="${pageContext.request.contextPath}/static/layer/layer.js" type=text/javascript></script>
</head>
<script type=text/javascript>

$(document).ready(function(){
	var name = "${pageClass}";
	
    $("#importButton").click(function(){
        var tet = $("#selectedFileName").val();
        if(tet != ""){
            /* ajaxFileUpload(name,tet); */
            var index = tet.indexOf(".");
        	var suffix = tet.substring(index+1,tet.length);
        	if(suffix =='xlsx'||suffix == 'xls'){
            	$("#importForm").submit();
            	$("#selectedFileName").val("");
        	}
        	else{
        		layer.tips('请选择xlsx或xls文件','#selectedFileName',{tips:[1,'#87CEFA']});
                return false;
        	}
        }
        else{
            layer.tips('请选择文件','#selectedFileName',{tips:[1,'#87CEFA']});
            return false;
        }
        addOnChangeEvent();
    });
    addOnChangeEvent();
});

function freshParent(url){
	parent.window.location.href = url;
	parent.layer.close(parent.layer.getFrameIndex(window.name));
}


function writeUTF(str, isGetBytes) {
    var back = [];
    var byteSize = 0;
    for (var i = 0; i < str.length; i++) {
        var code = str.charCodeAt(i);
        if (0x00 <= code && code <= 0x7f) {
              byteSize += 1;
              back.push(code);
        } else if (0x80 <= code && code <= 0x7ff) {
              byteSize += 2;
              back.push((192 | (31 & (code >> 6))));
              back.push((128 | (63 & code)))
        } else if ((0x800 <= code && code <= 0xd7ff) 
                || (0xe000 <= code && code <= 0xffff)) {
              byteSize += 3;
              back.push((224 | (15 & (code >> 12))));
              back.push((128 | (63 & (code >> 6))));
              back.push((128 | (63 & code)))
        }
     }
     for (i = 0; i < back.length; i++) {
          back[i] &= 0xff;
     }
     if (isGetBytes) {
          return back;
     }
     if (byteSize <= 0xff) {
          return [0, byteSize].concat(back);
     } else {
          return [byteSize >> 8, byteSize & 0xff].concat(back);
      }
}
function addOnChangeEvent(){
    $("#fileChooser")[0].onchange = function(){
        var textvalue = this.value;
        textvalue = textvalue.substring(textvalue.lastIndexOf("\\")+1, textvalue.length);
        $("#selectedFileName")[0].value = textvalue;
        $("#fileName").val(textvalue);
    };
}
function readUTF(arr) {
    if (typeof arr === 'string') {
        return arr;
    }
    var UTF = '', _arr = this.init(arr);
    for (var i = 0; i < _arr.length; i++) {
        var one = _arr[i].toString(2),
                v = one.match(/^1+?(?=0)/);
        if (v && one.length == 8) {
            var bytesLength = v[0].length;
            var store = _arr[i].toString(2).slice(7 - bytesLength);
            for (var st = 1; st < bytesLength; st++) {
                store += _arr[st + i].toString(2).slice(2);
            }
            UTF += String.fromCharCode(parseInt(store, 2));
            i += bytesLength - 1;
        } else {
            UTF += String.fromCharCode(_arr[i]);
        }
    }
    return UTF;
}
</script>
<style type="text/css">
div.container {
	width:100%;
	margin:0px;
}

div.up {
	position: absolute;
	left: 1em;
	top:3em;
	width: 15em;
}

div.down {
	position: absolute;
	left: 1em;
	top:5em;
	width: 15em;
}

.red_white_style {
	background-color: rgb(255,0,0);
	color: rgb(255,255,255);
	padding: 0.2em;
	font: medium lighter;
}

.classic {
	background-color: rgb(255,255,255);
	color: rgb(128,128,128);
	/* padding: 0.2em; */
	font: medium lighter;
}

#fileCover {
	position: absolute;
	top: 0em;
	left: 0em;
	width: 2em;
	margin-left: 0.2em;
	border: solid 1px;
	border-color: rgb(128,128,128);
	z-index: 0;
	text-align: center;
}

input#fileChooser {
	max-width: 2em;
	z-index: 1;
	opacity: 0;
	margin-left: 0.2em;
	/* for ie */
	filter: progid:DXImageTransform.Microsoft.Alpha(opacity=0);
}

input#selectedFileName {
	position: absolute;
	top: 0em;
	left: 3.5em;
	z-index: 1;
	width: 13em;
	height: 1.6em;
	border: solid 1px grey;
	padding: 0px;
}

a {
	text-decoration: none;
	cursor: pointer;
	/* for ie */
	
}

button {
	border:0px none;
	padding: 0px;
	cursor: pointer;
}

ul {
	float:left;
	width:100%;
	padding:0;
	margin:0;
	list-style-type:none;
}

li {
	display: inline-block;
	width: 31%;
	/* for ie */
	*zoom: 1;
	*display: inline;
}
</style>

<body>
	<div class="container">
				<c:choose>
				<c:when test="${pageClass=='bank'}">
				<form id="importForm" name="importForm" method="post" action="${pageContext.request.contextPath}/bank/import.do" enctype="multipart/form-data">
				</c:when>
				<c:when test="${pageClass=='syslog'}">
				<form id="importForm" name="importForm" method="post" action="${pageContext.request.contextPath}/ei/syslog/import.do" enctype="multipart/form-data">
				</c:when>
				<c:when test="${pageClass=='industry'}">
				<form id="importForm" name="importForm" method="post" action="${pageContext.request.contextPath}/industry/import.do" enctype="multipart/form-data">
				</c:when>
				<c:when test="${pageClass=='industrySub'}">
				<form id="importForm" name="importForm" method="post" action="${pageContext.request.contextPath}/industrySub/import.do" enctype="multipart/form-data">
				</c:when>
				<c:when test="${pageClass=='busiArea'}">
				<form id="importForm" name="importForm" method="post" action="${pageContext.request.contextPath}/busiArea/import.do" enctype="multipart/form-data">
				</c:when>
				<c:when test="${pageClass=='brand'}">
				<form id="importForm" name="importForm" method="post" action="${pageContext.request.contextPath}/brand/import.do" enctype="multipart/form-data">
				</c:when>
				</c:choose>
		<div id="hidden_txt" style="display:none"></div>
		<div id="show_area" class="up">
			<span id="fileCover">...</span>
			<input type="file" name ="file" id="fileChooser"/>
			<input type="text" readonly="readonly" id="selectedFileName"/>
		</div>
		<div id="interaction" class="down">
			<ul>
				<c:set var="backurl" value=""></c:set>
				<li>
				<c:choose>
					<c:when test="${pageClass=='bank'}">
						<a class="classic" id="template" href="${pageContext.request.contextPath}/bank/getBankFormat.do">模版</a>
						<c:set var="backurl" value="/ump/bank.do"></c:set>
					</c:when>
					<c:when test="${pageClass=='syslog'}">
						<a class="classic" id="template" href="${pageContext.request.contextPath}/ei/getSysLogTemplate.do">模版</a>
						<c:set var="backurl" value="/blog/admin/main.jsp"></c:set>
					</c:when>
					<c:when test="${pageClass=='industry'}">
						<a class="classic" id="template" href="${pageContext.request.contextPath}/industry/getIndustryFormat.do">模版</a>
						<c:set var="backurl" value="/ump/industry.do"></c:set>
					</c:when>
					<c:when test="${pageClass=='industrySub'}">
						<a class="classic" id="template" href="${pageContext.request.contextPath}/industrySub/getIndustrySubFormat.do">模版</a>
						<c:set var="backurl" value="/ump/industrySub.do"></c:set>
					</c:when>
					<c:when test="${pageClass=='busiArea'}">
						<a class="classic" id="template" href="${pageContext.request.contextPath}/busiArea/getBusiAreaFormat.do">模版</a>
						<c:set var="backurl" value="/ump/busiArea.do"></c:set>
					</c:when>
					<c:when test="${pageClass=='brand'}">
						<a class="classic" id="template" href="${pageContext.request.contextPath}/brand/getBrandFormat.do">模版</a>
						<c:set var="backurl" value="/ump/brand.do"></c:set>
					</c:when>
				</c:choose>
				</li>
				<li>
				<input type="hidden" id="fileName" name="fileName">
				<button class="classic" id="importButton">导入</button>
<!-- 				<button class="classic" id="closeBtn" onclick = "window.opener=null;window.close(this);">关闭</button>
 -->
 				</li>
			</ul>
		</div>
		</form>
	</div>
</body>
</html>