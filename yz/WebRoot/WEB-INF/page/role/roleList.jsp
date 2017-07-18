<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/base/jsp/include.jsp"%>
<script type="text/javascript">
	var grid;
	var addFun = function() {
		var rows = grid.datagrid('getSelections');
		if (rows.length != 1) {
			parent.$.messager.alert('warning', '请选择一个父节点！','warning');
			return;
		}
		var dialog = parent.sy.modalDialog({
			title : '添加用户组',
			width : '600',
			height : '400',
			url : sy.contextPath + '/go.do?path=role/groupEdit&parent='+rows[0].id+'&level='+(rows[0].level+1),
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$, parent.mainMenu);
				}
			} ]
		});
	};
	
	var roleFun = function() {
		var rows = grid.datagrid('getSelections');
		if (rows.length != 1) {
			parent.$.messager.alert('warning', '请选择一个父节点！','warning');
			return;
		}
		if(rows[0].id == '0'){
			parent.$.messager.alert('warning', '根节点无法编辑！','warning');
			return;
		}
		var dialog = parent.sy.modalDialog({
			title : '用户组角色',
			url : sy.contextPath + '/go.do?path=role/roles&id='+rows[0].id,
			width : '900',
			height : '600'
		});
	};
	
	var menuFun = function() {
		var dialog = parent.sy.modalDialog({
			title : '菜单',
			url : sy.contextPath + '/go.do?path=role/menus',
			width : '900',
			height : '600'
		});
	};
	
	var editFun = function() {
		var rows = grid.datagrid('getSelections');
		if (rows.length != 1) {
			parent.$.messager.alert('warning', '请选择一条记录进行编辑！','warning');
			return;
		}
		if(rows[0].id == '0'){
			parent.$.messager.alert('warning', '根节点无法编辑！','warning');
			return;
		}
		var dialog = parent.sy.modalDialog({
			title : '编辑用户信息',
			width : '600',
			height : '400',
			url : sy.contextPath + '/go.do?path=role/groupEdit&id=' + rows[0].id,
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
			parent.$.messager.alert('warning', '请选择需要删除的记录！','warning');
			return;
		}
		if(rows[0].id == '0'){
			parent.$.messager.alert('warning', '根节点无法编辑！','warning');
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
				
				$.post(sy.contextPath + '/delgroup.do', {
					id : id
				}, function() {
					grid.treegrid('reload');
				}, 'json');
			}
		});
	};

	$(function() {	
		grid = $('#grid').treegrid({
			url : sy.contextPath + '/grouplist.do',
			idField : 'id',
			treeField : 'name',
			parentField : 'parentId',
			toolbar : '#toolbar',
			pagination : false,
			frozenColumns : [ [ {
				width : '250',
				title : '名称',
				align : 'left',
				field : 'name'
			} ] ],
			columns : [ [{
				width : '150',
				title : '编号',
				align : 'center',
				field : 'code'
			}, {
				width : '200',
				title : '映射类',
				field : 'className',
				align : 'center'
			},{
				width : '200',
				title : '跳转',
				field : 'url',
				align : 'center'
			}, {
				width : '200',
				title : '描述',
				field : 'info',
				align : 'center'
			}, {
				width : '200',
				title : '更新时间',
				field : 'modifyTime',
				align : 'center'
			},{
				width : '200',
				title : '创建时间',
				field : 'createTime',
				align : 'center'
			}
			]]
		});
	});
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar">
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
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_delete',plain:true" onclick="roleFun();">用户组角色</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_delete',plain:true" onclick="menuFun();">菜单</a></td>
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