<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<base href="<%=basePath%>">
<%@ include file="/base/jsp/include.jsp"%>
<script type="text/javascript">
	var grid;
	
	var delFun = function(id) {
		var rows = grid.datagrid('getSelections');
		if (rows.length == 0) {
			parent.$.messager.w('请选择需要删除的记录！');
			return;
		}
		parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
				var ids = [];
				for ( var i = 0, l = rows.length; i < l; i++) {
					var r = rows[i];
					ids.push(r.id);
				}
				var id = ids.join(',');
				$.post(sy.contextPath + '/smsSendLog/del', {
					id : id
				}, function() {
					rows.length = 0;//必须，否则有bug
					grid.datagrid('reload');
				}, 'json');
			}
		});
	};

	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : sy.contextPath + '/smsSendLog/list',
			toolbar : '#toolbar',
			singleSelect : false,
			columns : [ [{
				width : '100',
				checkbox:true,
				field : 'id',
				align : 'center'
			},{
				width : $(this).width() * 0.2,
				title : '手机号',
				field : 'phone',
				align : 'center',
				formatter : function(value, row, index) {
					if (value)
						return UT.addTitle(value);
					
				}
			},{
				width : $(this).width() * 0.2,
				title : '设备id',
				field : 'deviceId',
				align : 'center'
			}, 
			{
				width : $(this).width() * 0.1,
				title : 'ip地址',
				field : 'ip',
				align : 'center'
			},{
				width : $(this).width() * 0.3,
				title : '短信发送情况',
				field : 'errorName',
				align : 'center',
				formatter : function(value, row, index) {
					if (value)
						return UT.addTitle(value);
				}
			}, 
			{
				width : $(this).width() * 0.2,
				title : '创建时间',
				field : 'createTime',
				align : 'center'
			}
			] ]
		});
	});
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<table>
			<tr>
				<td>
					<form id="searchForm">
						<table>
							<tr>
								<td>手机号：</td>
								<td>
									<input id="type" name="phone" style="width:150px;" class="easyui-textbox"/>
								</td>
								<td>
									<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="grid.datagrid('load',sy.serializeObject($('#searchForm')));">过滤</a>
									<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchForm input').val('');grid.datagrid('load',{});">重置过滤</a>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-book',plain:true" onclick="delFun();">删除记录</a></td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>