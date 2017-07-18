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
	var addFun = function() {
		var dialog = parent.sy.modalDialog({
			title : '添加定时任务信息',
			width : 600,
			height : 400,
			url : sy.contextPath + '/go.do?path=task/taskEdit',
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var editFun = function() {
		var rows = grid.datagrid('getSelections');
		if (rows.length != 1) {
			parent.$.messager.w('请选择一条记录进行编辑！');
			return;
		}
		var dialog = parent.sy.modalDialog({
			title : '编辑定时任务信息',
			width : 600,
			height : 400,
			url : sy.contextPath + '/go.do?path=task/taskEdit&id=' + rows[0].id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	
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
				
				$.post(sy.contextPath + '/task/del.do', {
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
			url : sy.contextPath + '/task/list.do',
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
				title : '任务ID',
				field : 'jobId',
				align : 'center'
			},{
				width : $(this).width() * 0.1,
				title : '任务名称',
				field : 'jobName',
				align : 'center'
			},{
				width : $(this).width() * 0.1,
				title : '任务组',
				field : 'jobGroup',
				align : 'center'
			},{
				width : $(this).width() * 0.2,
				title : '表达式',
				field : 'cronExpression',
				align : 'center'
			}, {
				width : $(this).width() * 0.1,
				title : '状态',
				field : 'state',
				align : 'center',
				formatter : function(value, row, index) {
					switch (value) {
					case 0:
						return '<a href="javascript:void(0);" onclick="forbidden(1,\''+row.id+'\')" ><img src="base/images/no.png" title="点击启用" > </img></a>';
					case 1:
						return '<a href="javascript:void(0);" onclick="forbidden(0,\''+row.id+'\')" ><img src="base/images/yes.png" title="点击禁用" > </img></a>';
					}
				}
			},
			{
				width : $(this).width() * 0.3,
				title : '描述',
				field : 'info',
				align : 'center',
				formatter : function(value, row, index) {
					return UT.addTitle(value);
				}
			}
			] ]
		});
		
	});
	
	function forbidden(state,id) {
		if (state == 1) {
			parent.$.messager.confirm('询问',"确定启用此任务吗？", function(r) {
				if (r) {
					
					var data = {
						id : id,
						state : 1
					};
					SaveData(data);
				}
			});
		} else if(state == 0){
			parent.$.messager.confirm('询问',"确定禁用此任务吗？", function(r) {
				if (r) {
					var data = {
						id : id,
						state : 0
					};
					SaveData(data);
				}
			});
		}
	}
	function SaveData(data) {
		var url = sy.contextPath + '/task/save.do';
		$.post(url, data, function() {
			grid.datagrid('reload');
		}, 'json');
	}
	
	function reflushJob(){
		parent.$.messager.progress({
			text : '任务重载中....'
		});
		$.post(sy.contextPath + '/task/reflushJob.do', {
		}, function(result) {
			parent.$.messager.progress('close');
			if (result.code == 0) {
				parent.$.messager.i('重载成功！');
			}else{
				parent.$.messager.w(result.msg);
			}
		}, 'json');
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<table>
			<tr>
				<td>
					<table>
						<tr>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_edit',plain:true" onclick="editFun();">修改</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_delete',plain:true" onclick="delFun();">删除</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-arrow_refresh'" onclick="reflushJob()">重载任务</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td style="color: gray;">操作完毕后请点击[重载任务]对任务进行重新部署</td>
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