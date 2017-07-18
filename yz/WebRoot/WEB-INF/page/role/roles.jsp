<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/base/jsp/include.jsp"%>
<script type="text/javascript">
	var id = '${id}';
	var grid;
	var addFun = function() {
		var rows = grid.datagrid('getSelections');
		if (rows.length != 1) {
			parent.$.messager.alert('warning', '请选择一个父节点！','warning');
			return;
		}
		var dialog = parent.sy.modalDialog({
			title : '添加角色',
			width : 700,
			height : 500,
			url : sy.contextPath + '/go.do?path=role/roleEdit&parent='+rows[0].id+'&level='+(rows[0].level+1)+'&groupId='+id,
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$, parent.mainMenu);
				}
			} ]
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
			title : '编辑角色信息',
			width : 700,
			height : 500,
			url : sy.contextPath + '/go.do?path=role/roleEdit&id=' + rows[0].id,
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
				
				$.post(sy.contextPath + '/delrole.do', {
					id : id
				}, function() {
					grid.treegrid('reload');
				}, 'json');
			}
		});
	};

	$(function() {
		grid = $('#grid').treegrid({
			url : sy.contextPath + '/rolelist.do?id='+id,
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
				title : 'code',
				align : 'center',
				field : 'code'
			}, {
				width : '200',
				title : '描述',
				field : 'info',
				align : 'center'
			}, {
				width : '150',
				title : '更新时间',
				field : 'modifyTime',
				align : 'center'
			}, {
				width : '150',
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