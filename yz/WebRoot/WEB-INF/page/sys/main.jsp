<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>篮球约战</title>
<%@ include file="/base/jsp/include.jsp"%>
<style type="text/css">
.easyui-accordion ul{list-style-type:none;margin:0px; padding:10px;}
.easyui-accordion ul li{ padding:0px;}
.easyui-accordion ul li a{line-height:24px;text-decoration: none;color:black;font-size: 12px;
font-family: Tahoma,Verdana,微软雅黑,新宋体;}
.easyui-accordion ul li div{margin:2px 0px;padding-left:10px;padding-top:2px;}
.easyui-accordion ul li div.hover{border:1px dashed #99BBE8; background:#E0ECFF;cursor:pointer;}
.easyui-accordion ul li div.hover a{color:#416AA3;}
.easyui-accordion ul li div.selected{border:1px solid #99BBE8; background:#E0ECFF;cursor:default;}
.easyui-accordion ul li div.selected a{color:#416AA3; font-weight:bold;}
.icon { width: 18px; line-height: 18px; display: inline-block;margin-right: 5px; }
</style>
<script type="text/javascript">
	var _menus = ${sessionUser.menu}['_menus'];
</script>
<script type="text/javascript" src="${path}/base/js/main.js"></script>
<script type="text/javascript">
</script>
</head>
<body id="mainLayout" class="easyui-layout">
	<!--  顶部 -->
	<div data-options="region:'north',href:'${path}/base/jsp/north.jsp'" style="height: 70px; overflow: hidden;">
	</div>

	<!--  左侧导航 -->
	<div region="west" split="true" title="导航菜单" style="width:200px;" >
		<div class="easyui-accordion" data-options="fit:true,region:'west',border:false" id="mainMenu">
			<!--  导航内容 -->
		</div>
	</div>
	
	<!--  tab页 -->
	<div data-options="region:'center'" style="overflow: hidden;">
		<div id="mainTabs">
			<div title="首页" data-options="iconCls:'ext-icon-heart'">
				<iframe src="${ path }${sessionUser.groupWelcome}" allowTransparency="true" style="border: 0; width: 100%; height: 99%;" frameBorder="0"></iframe>
			</div>
		</div>
	</div>
	
	<!--  底部 
	<div data-options="region:'south',href:'${path}/base/jsp/south.jsp',border:false" style="height: 30px; overflow: hidden;"></div>
	-->
	<!--  修改密码 -->
	<div id="passwordDialog" title="修改密码" style="display: none;">
		<form method="post" class="form" onsubmit="return false;">
			<table class="table">
				<tr>
					<th>旧密码</th>
					<td><input id="oldpwd" name="oldpwd" type="password" class="easyui-textbox" data-options="required:true" prompt="" missingMessage="请输入旧密码"/></td>
				</tr>
				<tr>
					<th>新密码</th>
					<td><input id="newpwd" name="newpwd" type="password" class="easyui-textbox" data-options="required:true" prompt="" missingMessage="请输入新密码" /></td>
				</tr>
				<tr>
					<th>重复密码</th>
					<td><input type="password" class="easyui-textbox" data-options="required:true,validType:'eqPwd[\'#newpwd\']'" prompt="" missingMessage="请输入确认密码" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>