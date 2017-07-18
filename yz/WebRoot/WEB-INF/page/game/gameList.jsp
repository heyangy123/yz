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
			title : '添加约战',
			width : 600,
			height : 400,
			url : sy.contextPath + '/go.do?path=game/gameEdit',
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var editFun = function(id) {
		if(!id){
			var rows = grid.datagrid('getSelections');
			if (rows.length != 1) {
				parent.$.messager.w('请选择一条记录进行编辑！');
				return;
			}
			id = rows[0].id;
		}
		var dialog = parent.sy.modalDialog({
			title : '编辑约战',
			width : 600,
			height : 400,
			url : sy.contextPath + '/go.do?path=game/gameEdit&id=' + id,
			buttons : [ {
				text : '保存',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var viewFun = function(id) {
		if(!id){
			var rows = grid.datagrid('getSelections');
			if (rows.length != 1) {
				parent.$.messager.w('请选择一条记录进行查看！');
				return;
			}
			id = rows[0].id;
		}
		var dialog = parent.sy.modalDialog({
			title : '查看详情',
			width : 600,
			height : 400,
			url : sy.contextPath + '/go.do?path=game/gameEdit&id=' + id,
			buttons : [ {
				text : '关闭',
				handler : function() {
					dialog.dialog('destroy');
				}
			} ]
		});
	};
	
	var delFun = function(id) {
		var rows = grid.datagrid('getSelections');
		if(!id){
			if (rows.length == 0) {
				parent.$.messager.w('请选择需要删除的记录！');
				return;
			}
			var ids = [];
			for ( var i = 0, l = rows.length; i < l; i++) {
				var r = rows[i];
				ids.push(r.id);
			}
			id = ids.join(',');
		}
		parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
				$.post(sy.contextPath + '/game/delete.do', {
					id : id
				}, function() {
					rows.length = 0;//必须，否则有bug
					grid.datagrid('reload');
				}, 'json');
			}
		});
	};
	
	var addGamePlayer = function(id){
		if(!id){
			var rows = grid.datagrid('getSelections');
			if (rows.length != 1) {
				parent.$.messager.w('请选择一条记录进行录入！');
				return;
			}
			id = rows[0].id;
		}
		var dialog = parent.sy.modalDialog({
			title : '比赛结果录入',
			width : 800,
			height : 400,
			url : sy.contextPath + '/go.do?path=game/gamePlayerEdit&id=' + id,
			buttons : [ {
				text : '保存',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	
	$(function() {
		grid = $('#grid').datagrid({
			url : sy.contextPath + '/game/list.do',
			toolbar : '#toolbar',
			singleSelect : false,
			frozenColumns : [ [ {
				width : '100',
				checkbox:true,
				field : 'id',
				align : 'center'
			}] ],
			columns : [ [{
				width : $(this).width() * 0.1,
				title : '主题',
				field : 'theme',
				align : 'center'
			},{
				width : $(this).width() * 0.1,
				title : '约战方',
				field : 'publishUserName',
				align : 'center'
			},{
				width : $(this).width() * 0.1,
				title : '应战方',
				field : 'acceptUserName',
				align : 'center',
			},{
				width : $(this).width() * 0.2,
				title : '发布时间',
				field : 'createTime',
				align : 'center'
			},{
				width : $(this).width() * 0.1,
				title : '状态',
				field : 'state',
				align : 'center',
				formatter : function(value, row, index) {
					switch (value) {
						case 0: return '待审核';
						case 1: return '待应战';
						case 2: return '约战中';
						case 3: return '已经约战';
						case 4: return '已经结算';
					}
				}
			},{
				width : $(this).width() * 0.1,
				title : '胜利方',
				field : 'winner',
				align : 'center'
				,formatter : function(value, row, index) {
					if(value!=undefined){
						if(value==0){
							return "约战方";
						}else if(value==1){
							return "应战方";
						}
					}
					return "";
				}
			},{
				field:'opt',title:'操作',width:$(this).width() * 0.2,align:'center',
	            formatter:function(value,rec){
	                var btn = '<a class="viewcls" onclick="viewFun(\''+rec.id+'\')" href="javascript:void(0)">查看</a>';
	                btn += '<a class="delcls" onclick="delFun(\''+rec.id+'\')" href="javascript:void(0)">删除</a>';
	                if(rec.state!=undefined && rec.state >= 3){
		                btn += '<a class="editcls" onclick="addGamePlayer(\''+rec.id+'\')" href="javascript:void(0)">录入结果</a>';
	                }
	                return btn;
	            }
	        }
			] ],
			onLoadSuccess:function(data){
		        $('.viewcls').linkbutton({text:'查看',plain:true,iconCls:'icon-search'});
		        $('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
		        $('.editcls').linkbutton({text:'录入结果',plain:true,iconCls:'icon-edit'});
		    }
		});
		
	});
	function SaveData(data) {
		var url = sy.contextPath + '/game/save.do';
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
					<table>
						<tr>
							<!-- <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_edit',plain:true" onclick="editFun();">修改</a></td>
							<td><div class="datagrid-btn-separator"></div></td> -->
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_edit',plain:true" onclick="viewFun();">查看</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_delete',plain:true" onclick="delFun();">删除</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<!-- <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_edit',plain:true" onclick="addGamePlayer();">结果录入</a></td>
							<td><div class="datagrid-btn-separator"></div></td> -->
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