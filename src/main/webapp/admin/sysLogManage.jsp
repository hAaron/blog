<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日志管理页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/static/layer/layer.js" type=text/javascript></script>

<script type="text/javascript">

	var url;

	function deleteSysLog(){
		var selectedRows=$("#dg").datagrid("getSelections");
		if(selectedRows.length==0){
			 $.messager.alert("系统提示","请选择要删除的数据！");
			 return;
		 }
		 var strIds=[];
		 for(var i=0;i<selectedRows.length;i++){
			 strIds.push(selectedRows[i].id);
		 }
		 var ids=strIds.join(",");
		 $.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
				if(r){
					$.post("${pageContext.request.contextPath}/admin/syslog/delete.do",{ids:ids},function(result){
						if(result.success){
							 $.messager.alert("系统提示","数据已成功删除！");
							 $("#dg").datagrid("reload");
						}else{
							$.messager.alert("系统提示","数据删除失败！");
						}
					},"json");
				} 
	   });
	}
	
	function exportSysLog(){
		var selectedRows=$("#dg").datagrid("getSelections");
		if(selectedRows.length==0){
			// $.messager.alert("系统提示","默认导出日志全部信息");
		 }
		 var strIds=[];
		 for(var i=0;i<selectedRows.length;i++){
			 strIds.push(selectedRows[i].id);
		 }
		 var ids=strIds.join(",");
		 $.messager.confirm("系统提示","您确定要导出日志信息吗？",function(r){
				if(r){
					 window.location.href="${pageContext.request.contextPath}/ei/syslog/export.do";
				} 
	   }); 
	}
	
	$(document).ready(function(){
		$("#importButton").click(function(){
			layer.open({
		        type: 2,
		        title: '从Excel文件导入日志信息',
		        shadeClose: true, //点击遮罩关闭层
		        area : ['23em' , '18em'],
		        content: '${pageContext.request.contextPath}/ei/getExcelPage.do?parent=syslog'
		    });
		});
	});

	function openSysLogDetailDialog(){
		var selectedRows=$("#dg").datagrid("getSelections");
		 if(selectedRows.length!=1){
			 $.messager.alert("系统提示","请选择一条要查看的数据！");
			 return;
		 }
		 var row=selectedRows[0];
		 $("#dlg").dialog("open").dialog("setTitle","日志详情信息");
		 $("#fm").form("load",row);
	 }
	
	 function closeSysLogDialog(){
		 $("#dlg").dialog("close");
	 }
</script>
</head>
<body style="margin: 1px">
<table id="dg" title="日志管理" class="easyui-datagrid"
   fitColumns="true" pagination="true" rownumbers="true"
   url="${pageContext.request.contextPath}/admin/sysLog/list.do" fit="true" toolbar="#tb">
   <thead>
   	<tr>
   		<th field="cb" checkbox="true" align="center"></th>
   		<th field="id" width="20" align="center">编号</th>
   		<th field="remoteAddr" width="200" align="center">IP地址</th>
   		<th field="method" width="200" align="center">操作方式</th>
   		<th field="params" width="100" align="center">数据参数</th>
   		<th field="requestUri" width="200" align="center">请求地址</th>
   		<th field="createDate" width="50" align="center">创建时间</th>
   	</tr>
   </thead>
 </table>
 <div id="tb">
 	<div>
 		<a href="javascript:openSysLogDetailDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">查看</a>
 		<a href="javascript:deleteSysLog()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
 		<a href="javascript:exportSysLog()" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
 		<!-- <a href="javascript:importSysLog()" id="importButton" class="easyui-linkbutton" iconCls="icon-undo" plain="true">导入</a> -->
 		<input class="button1" type="button" id="importButton" value="从Excel导入" style="cursor:hand;"></input>
 	</div>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
 </div>
 
 
 <div id="dlg" class="easyui-dialog" style="width:500px;height:400px;padding: 10px 20px"
   closed="true" buttons="#dlg-buttons">
   
   <form id="fm" method="post">
   	<table cellspacing="8px">
   		<tr>
   			<td>日志类型：</td>
   			<td><input type="text" id="type" name="type" class="easyui-validatebox" readonly style="width: 300px"/></td>
   		</tr>
   		<tr>
   			<td>IP地址：</td>
   			<td><input type="text" id="remoteAddr" name="remoteAddr" class="easyui-validatebox" readonly style="width: 300px"/></td>
   		</tr>
   		<tr>
   			<td>操作方式：</td>
   			<td><input type="text" id="method" name="method" class="easyui-validatebox" readonly style="width: 300px"/></td>
   		</tr>
   		<tr>
   			<td>数据参数：</td>
   			<td><input type="text" id="params" name="params" class="easyui-validatebox" readonly style="width: 300px"/></td>
   		</tr>
   		<tr>
   			<td>创建时间：</td>
   			<td><input type="text" id="createDate" name="createDate" class="easyui-validatebox" readonly style="width: 300px"/></td>
   		</tr>
   		<tr>
   			<td>用户代理：</td>
   			<td><input type="text" id="userAgent" name="userAgent" class="easyui-validatebox" readonly style="width: 300px"/></td>
   		</tr>
   		<tr>
   			<td>请求URI：</td>
   			<td><input type="text" id="requestUri" name="requestUri" class="easyui-validatebox" readonly style="width: 300px"/></td>
   		</tr>
   		<tr>
   			<td>异常信息：</td>
   			<td><input type="text" id="exception" name="exception" class="easyui-validatebox" readonly style="width: 300px"/></td>
   		</tr>
   	</table>
   </form>
 </div>
 
 <div id="dlg-buttons">
 	<a href="javascript:closeSysLogDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
 </div>
</body>
</html>