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
	
	var delFun = function() {
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
				$.post(sy.contextPath + '/userCashLog/del.do', {
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
			url : sy.contextPath + '/userCashLog/list',
			toolbar : '#toolbar',
			singleSelect : false,
			frozenColumns : [ [ {
				width : '100',
				checkbox:true,
				field : 'id',
				align : 'center'
			}] ],
			columns : [ [ {
				width : $(this).width() * 0.1,
				title : '账号(昵称)',
				field : 'nickName',
				align : 'center',
				formatter : function(value, row, index){
					return row.userAccount+'('+value+')';
				}
			},{
				width : $(this).width() * 0.1,
				title : '金额',
				field : 'num',
				align : 'center'
			},{
				width : $(this).width() * 0.1,
				title : '转出平台',
				field : 'platform',
				align : 'center'
			},{
				width : $(this).width() * 0.1,
				title : '对应账号',
				field : 'account',
				align : 'center'
			},{
				width : $(this).width() * 0.1,
				title : '参考用户',
				field : 'username',
				align : 'center'
			},{
				width : $(this).width() * 0.1,
				title : '状态',
				field : 'state',
				align : 'center',
				formatter : function(value, row, index){
					switch(value){
					case 1:
						return '待审核[<a href="javascript:void(0);" onclick="isPass(1,\''+row.id+'\')" >成功</a>]'+
				   			         '[<a href="javascript:void(0);" onclick="isPass(0,\''+row.id+'\')" >失败</a>]';
					case 2:
						return UT.addSpanColor('失败','red');
					case 3:
						return UT.addSpanColor('成功','green');
					}
				}
			},{
				width : $(this).width() * 0.15,
				title : '创建时间',
				field : 'createTime',
				align : 'center'
			}
			] ]
		});
	});
	
	function isPass(type,id) {
		if (type == 1) {
			parent.$.messager.confirm("询问","确认审核成功？", function(r) {
				if (r) {
					var data = {
						id : id,
						state : 3
					};
					SaveData(data);
				}
			});
		} else{
			parent.$.messager.confirm("询问","确认审核失败？", function(r) {
				if (r) {
					var data = {
						id : id,
						state : 2
					};
					SaveData(data);
				}
			});
		}
	}
	
	function SaveData(data) {
		var url = sy.contextPath + '/userCashLog/save';
		$.post(url, data, function() {
			grid.datagrid('reload');
		}, 'json');
	}
		 
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
								<td>账号|昵称:</td>
								<td>
									<input type="text" class="easyui-textbox"  style="width:150px;"  name="name" />
								</td>
								<td>状态:</td>
								<td>
									<input type="text" class="easyui-combobox"  style="width:100px;"  name="state" editable="false" panelHeight="auto"
									data-options="valueField:'code',textField:'text',data:[{code:'1',text:'待审核'},{code:'2',text:'失败'},{code:'2',text:'成功'}]"/>
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
			<tr style="display: none;">
				<td>
					<table>
						<tr>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_delete',plain:true" onclick="delFun();">删除</a></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>