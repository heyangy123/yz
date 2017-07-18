<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/base/jsp/include.jsp"%>
<script type="text/javascript">
	var grid;
	var addFun = function() {
		var rows = grid.datagrid('getSelections');
		var dialog = parent.sy.modalDialog({
			title : '添加城市信息',
			width : 500,
			height : 400,
			url : sy.contextPath + '/go.do?path=city/cityEdit',
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var editFun = function(id) {
		var rows = grid.datagrid('getSelections');
		if (rows.length != 1) {
			parent.$.messager.alert('请选择一条记录进行编辑！');
			return;
		}
		if(rows[0].code == '0'){
			parent.$.messager.w('该记录无法编辑！');
			return;
		}
		var dialog = parent.sy.modalDialog({
			title : '编辑城市信息',
			width : 500,
			height : 400,
			url : sy.contextPath + '/go.do?path=city/cityEdit&code=' + rows[0].code,
			buttons : [ {
				text : '编辑',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var delFun = function(id) {
		var rows = grid.datagrid('getSelections');
		if (rows.length == 0) {
			parent.$.messager.w('请选择需要删除的记录！');
			return;
		}
		if(rows[0].code == '0'){
			parent.$.messager.w('该记录无法删除！');
			return;
		}
		parent.$.messager.confirm('询问', '您确定要删除此城市[请先迁移该城市的商家]？', function(r) {
			if (r) {
				var ids = [];
				for ( var i = 0, l = rows.length; i < l; i++) {
					var r = rows[i];
					ids.push(r.code);
				}
				var id = ids.join(',');
				
				$.post(sy.contextPath + '/city/del.do', {
					id : id
				}, function() {
					grid.datagrid('reload');
				}, 'json');
			}
		});
	};
	var pwdFun = function(){
		var rows = grid.datagrid('getSelections');
		if (rows.length != 1) {
			parent.$.messager.alert('请选择一条记录进行编辑！');
			return;
		}
		var dialog = parent.sy.modalDialog({
			title : '修改密码',
			width : 640,
			height : 340,
			url : sy.contextPath + '/go.do?path=common/pwdEdit&type=2&id=' + rows[0].code,
			buttons : [ {
				text : '编辑',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	}
	
	
	
	$(function() {
		grid = $('#grid').datagrid({
			url : sy.contextPath + '/city/list.do',
			toolbar : '#toolbar',
			pagination : true,
			columns : [ [{
				width : $(this).width() * 0.2,
				title : '名称',
				align : 'center',
				field : 'name',
				formatter : function(value, row, index) {
					return value+'[<a href="city/fastLogin?code='+row.code+'" target="_top">快速登录</a>]';
				}
			},{
				width : $(this).width() * 0.1,
				title : '编号',
				align : 'center',
				field : 'code'
			},{
				width : $(this).width() * 0.2,
				title : '账号',
				align : 'center',
				field : 'account'
			},{
				width : $(this).width() * 0.1,
				title : '热门',
				field : 'isHot',
				align : 'center',
				formatter : function(value, row, index) {
					if(row.code !='0'){
						switch (value) {
							case 0:
								return '<a href="javascript:void(0);" onclick="forbiddenHot(1,\''+row.code+'\')" ><img src="base/images/no.png" title="点击启用" > </img></a>';
							case 1:
								return '<a href="javascript:void(0);" onclick="forbiddenHot(0,\''+row.code+'\')" ><img src="base/images/yes.png" title="点击禁用" > </img></a>';
						}
					}else{
						return "";
					}
					
				}
			}
			,{
				width : $(this).width() * 0.1,
				title : '状态',
				field : 'status',
				align : 'center',
				formatter : function(value, row, index) {
					if(row.code !='0'){
						switch (value) {
							case 0:
								return '<a href="javascript:void(0);" onclick="forbidden(1,\''+row.code+'\')" ><img src="base/images/no.png" alt="点击启用" > </img></a>';
							case 1:
								return '<a href="javascript:void(0);" onclick="forbidden(0,\''+row.code+'\')" ><img src="base/images/yes.png"  alt="点击禁用" > </img></a>';
						}
					}else{
						return "";
					}
				}
			}
			, {
				width : $(this).width() * 0.15,
				title : '创建时间',
				field : 'createTime',
				align : 'center'
			}
			]]
		});
		
	});
	
	function forbidden(type,id) {
		if (type == 1) {
			parent.$.messager.confirm('询问',"确定启用此地区吗？", function(r) {
				if (r) {
					var data = {
						code : id,
						status : 1
					};
					SaveData(data);
				}
			});
		} else {
			parent.$.messager.confirm('询问',"确定禁用此地区吗？", function(r) {
				if (r) {
					var data = {
						code : id,
						status : 0
					};
					SaveData(data);
				}
			});
		}		
	}
	function forbiddenHot(type,id) {
		if (type == 1) {
			parent.$.messager.confirm('询问',"确定热门？", function(r) {
				if (r) {
					var data = {
						code : id,
						isHot : 1
					};
					SaveData(data);
				}
			});
		} else {
			parent.$.messager.confirm('询问',"确定不热门吗？", function(r) {
				if (r) {
					var data = {
						code : id,
						isHot : 0
					};
					SaveData(data);
				}
			});
		}		
	}
	function SaveData(data) {
		$.post(sy.contextPath + '/city/save.do', data, function() {
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
								<td>名称:</td>
								<td><input name="name" class="easyui-textbox" style="width: 200px;" /></td>
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
				<td>
					<table>
						<tr>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_edit',plain:true" onclick="editFun();">修改</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_delete',plain:true" onclick="delFun();">删除</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_edit',plain:true" onclick="pwdFun();">修改密码</a></td>
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