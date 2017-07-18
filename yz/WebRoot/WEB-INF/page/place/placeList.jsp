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
			title : '添加比赛地址',
			width : 600,
			height : 400,
			url : sy.contextPath + '/go.do?path=place/placeEdit',
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
			title : '编辑比赛地址',
			width : 600,
			height : 400,
			url : sy.contextPath + '/go.do?path=place/placeEdit&id=' + id,
			buttons : [ {
				text : '保存',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
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
				$.post(sy.contextPath + '/place/delete.do', {
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
			url : sy.contextPath + '/place/list.do',
			toolbar : '#toolbar',
			singleSelect : false,
			frozenColumns : [ [ {
				width : '100',
				checkbox:true,
				field : 'id',
				align : 'center'
			}] ],
			columns : [ [ 
			{
				width : $(this).width() * 0.2, title : '球场名称', field : 'place', align : 'center'
			},{
				width : $(this).width() * 0.1, title : '省份', field : 'province', align : 'center'
			},{
				width : $(this).width() * 0.1, title : '城市', field : 'city', align : 'center'
			},{
				width : $(this).width() * 0.1, title : '详细地址', field : 'address', align : 'center'
			},{
				width : $(this).width() * 0.2, title : '创建时间', field : 'createTime', align : 'center'
			},{
				field : 'img',title : '场馆图',width : $(this).width() * 0.1,align : 'center',
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
				field:'opt',title:'操作',width:$(this).width() * 0.1,align:'center',
	            formatter:function(value,rec){
	                var btn = '<a class="editcls" onclick="editFun(\''+rec.id+'\')" href="javascript:void(0)">修改</a>';
	                btn += '<a class="delcls" onclick="delFun(\''+rec.id+'\')" href="javascript:void(0)">删除</a>';
	                return btn;
	            }
	        }
			] ],
			onLoadSuccess:function(data){
		        $('.editcls').linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
		        $('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
		    }
		});
		
	});
	function SaveData(data) {
		var url = sy.contextPath + '/place/save.do';
		$.post(url, data, function() {
			grid.datagrid('reload');
		}, 'json');
	}
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
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_edit',plain:true" onclick="editFun();">修改</a></td>
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