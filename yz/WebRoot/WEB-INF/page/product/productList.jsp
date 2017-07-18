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
			title : '添加礼品',
			width : 700,
			height : 500,
			url : sy.contextPath + '/go.do?path=product/productEdit',
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
			title : '编辑礼品',
			width : 880,
			height : 550,
			url : sy.contextPath + '/go.do?path=product/productEdit&id=' + id,
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
			width : 800,
			height : 550,
			url : sy.contextPath + '/go.do?path=product/productEdit&id=' + id,
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
				$.post(sy.contextPath + '/product/delete.do', {
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
			url : sy.contextPath + '/product/list.do',
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
				title : '商品标题',
				field : 'title',
				align : 'center'
			},{
				field : 'img',title : '图片',width : $(this).width() * 0.1,align : 'center',
				formatter:function(value, row, index) {
					if('' != value && null != value){
						var strs = new Array(); //定义一数组   
						if(value.substr(value.length-1,1)==","){
							value=value.substr(0,value.length-1)  
						}
						strs = value.split(","); //字符分割   
						var rvalue ="";
						for (i=0;i<strs.length ;i++ ){
							rvalue += "<img onclick=zoomImg(\""+strs[i]+"\") style='width:50px; height:40px;margin-left:3px;' src='download?id=" + strs[i] + "&w=50&h=40' title='点击查看图片'/>";  
						}
						return rvalue;
					}
				}
			},{
				width : $(this).width() * 0.1,
				title : '所需积分',
				field : 'score',
				align : 'center',
			},{
				width : $(this).width() * 0.1,
				title : '库存',
				field : 'stock',
				align : 'center',
			},{
				width : $(this).width() * 0.2,
				title : '发布时间',
				field : 'createTime',
				align : 'center'
			},{
				width : $(this).width() * 0.1,
				title : '上架状态',
				field : 'state',
				align : 'center',
				formatter : function(value, row, index) {
					switch (value) {
						case 0: return '下架';
						case 1: return '上架';
					}
				}
			},{
				field:'opt',title:'操作',width:$(this).width() * 0.2,align:'center',
	            formatter:function(value,rec){
	                var btn = '<a class="viewcls" onclick="viewFun(\''+rec.id+'\')" href="javascript:void(0)">查看</a>';
	                btn += '<a class="editcls" onclick="editFun(\''+rec.id+'\')" href="javascript:void(0)">修改</a>';
	                btn += '<a class="delcls" onclick="delFun(\''+rec.id+'\')" href="javascript:void(0)">删除</a>';
	                return btn;
	            }
	        }
			] ],
			onLoadSuccess:function(data){
		        $('.viewcls').linkbutton({plain:true,iconCls:'icon-search'});
		        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});
		        $('.delcls').linkbutton({plain:true,iconCls:'icon-remove'});
		    }
		});
		
	});
function zoomImg(imgId){  
    var dialog = parent.sy.modalDialog({
		title : '查看图片',
		width : 600,
		height : 400,
		url : sy.contextPath + '/download?id='+ imgId,
		buttons : [ {
			text : '关闭',
			handler : function() {
				dialog.dialog('destroy');
			}
		} ]
	});
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
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_delete',plain:true" onclick="editFun();">修改</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_delete',plain:true" onclick="delFun();">删除</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
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