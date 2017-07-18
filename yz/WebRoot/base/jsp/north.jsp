<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" charset="utf-8">
	var logoutFun = function() {
		location.replace(sy.contextPath + '/manager/logout');
	};
	var sys_ = "${sessionUser.sysname}";
	$("#sysName").html(sys_);
</script>
<marquee width="300"  behavior="slide" ><h1 style="margin-left: 10px;" id="sysName"></h1></marquee>
</h1>
<div style="position: absolute; right: 0px; bottom: 0px;">
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'ext-icon-rainbow'">更换皮肤</a> 
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'ext-icon-cog'">控制面板</a> 
</div>
<div id="layout_north_pfMenu" style="width: 120px; display: none;">
	<div onclick="sy.changeTheme('default');" title="default">经典蓝</div>
	<div onclick="sy.changeTheme('gray');" title="gray">经典灰</div>
	<div onclick="sy.changeTheme('black');" title="black">经典黑</div>
	<div onclick="sy.changeTheme('bootstrap');" title="bootstrap">推特风格</div>
	<div class="menu-sep"></div>
	<div onclick="sy.changeTheme('metro');" title="metro">美俏白</div>
	<div onclick="sy.changeTheme('metro-blue');" title="metro-blue">美俏蓝(默认)</div>
	<div onclick="sy.changeTheme('metro-gray');" title="metro-gray">美俏灰</div>
	<div onclick="sy.changeTheme('metro-green');" title="metro-green">美俏绿</div>
	<div onclick="sy.changeTheme('metro-orange');" title="metro-orange">美俏橙</div>
	<div onclick="sy.changeTheme('metro-red');" title="metro-red">美俏红</div>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'ext-icon-user_edit'" onclick="$('#passwordDialog').dialog('open');">修改密码</div>
	<div class="menu-sep"></div>
	<div data-options="iconCls:'ext-icon-door_out'" onclick="logoutFun();">退出系统</div>
	<!--<div data-options="iconCls:'ext-icon-user'" onclick="showMyInfoFun();">我的信息</div>-->
</div>
