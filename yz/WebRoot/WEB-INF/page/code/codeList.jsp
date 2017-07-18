<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/base/jsp/include.jsp"%>
<script type="text/javascript">
	var grid;
	var addFun = function() {
		var dialog = parent.sy.modalDialog({
			title : '添加参数信息',
			width : 640,
			height : 480,
			url : sy.contextPath + '/go.do?path=code/codeEdit',
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
			parent.$.messager.alert('warning', '请选择一条记录进行编辑！','warning');
			return;
		}
		var dialog = parent.sy.modalDialog({
			title : '编辑参数信息',
			width : 640,
			height : 480,
			url : sy.contextPath + '/go.do?path=code/codeEdit&id=' + rows[0].code,
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
		parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
				var ids = [];
				for ( var i = 0, l = rows.length; i < l; i++) {
					var r = rows[i];
					ids.push(r.code);
				}
				var id = ids.join(',');
				
				$.post(sy.contextPath + '/code/del.do', {
					code : id
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
			url : sy.contextPath + '/code/list.do',
			toolbar : '#toolbar',
			singleSelect : false,
			frozenColumns : [ [ {
				width : $(this).width()*0.1,
				title : '编号',
				field : 'code',
				align : 'center'
			}] ],
			columns : [ [ {
				width : $(this).width()*0.65,
				title : '值',
				field : 'value',
				align : 'center',
				formatter:function(v,r,i){
					return "<span title='"+v+"'  >"+v+"</span>";
				}
			}, {
				width : $(this).width()*0.25,
				title : '描述',
				field : 'remark',
				align : 'center',
				formatter:function(v,r,i){
					return "<span title='"+v+"'  >"+v+"</span>";
				}
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
								<td>编号</td>
								<td><input name="code" class="easyui-textbox" style="width: 80px;" /></td>
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
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_edit',plain:true" onclick="editFun();">修改</a></td>
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