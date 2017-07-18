<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
	<%@ include file="/base/jsp/include.jsp"%>
  </head>
  <body>
  <div style="font-size: 12px">
  	<table style="width: 100%" id="showTable">
  		<tr>
    		<td>
    			<a class="easyui-linkbutton" onclick="$('#showTable').hide();$('#editTable').show();" data-options="iconCls:'ext-icon-database_edit'">编辑</a>  
    		</td>
    	</tr>
		<tr>
			<td style="width: 100%" id="show">
			
			</td>
		</tr>
     </table>
    <form id="form" method="post">
    <table style="width: 100%;display: none;" id="editTable" >
    	<tr>
    		<td>
    			<a class="easyui-linkbutton" onclick="save()" data-options="iconCls:'ext-icon-database_save'">保存</a>  
    			<a class="easyui-linkbutton" onclick="back()" data-options="iconCls:'ext-icon-arrow_undo'">返回</a> 
    		</td>
    	</tr>
		<tr>
			<td style="width: 100%"><textarea id="content" style="width:98%;height: 550px"></textarea></td>
		</tr>
     </table>
    </form>
  </div>
    <script type="text/javascript">
    var editor = new HtmlEditor('#content');
	var code = "${code}";
    $(function(){
    	$.post(sy.contextPath + '/sp/findByCode.do', {
    		code : code
		}, function(result) {
			$("#show").html(result.content);
			editor.setHtml(result.content);
		}, 'json');
    });
    
   	function save(){
   		if ($('form').form('validate')) {
   			parent.$.messager.progress({
   				text : '发送中....'
   			});
    		$.ajax({
    			type:"post",
    			data:{code:code,content:editor.getHtml()},
    			url:"sp/save.do",
    			dataType:"json",
    			success:function(data){
    				parent.$.messager.progress('close');
    				if(data.code == 0){
    					back();
    				}else{
    					parent.$.messager.w(data.msg);
    				}
    			}
    		});
   		}
   	}
   	
   	function back(){
   		location.href = "go.do?path=common/single&code="+code;
   	}
   </script>	
  </body>
</html>
