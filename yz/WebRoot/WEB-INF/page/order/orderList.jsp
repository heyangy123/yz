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
	/* var addFun = function() {
		var dialog = parent.sy.modalDialog({
			title : '添加比赛地址',
			width : 600,
			height : 400,
			url : sy.contextPath + '/go.do?path=order/orderEdit',
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
			url : sy.contextPath + '/go.do?path=order/orderEdit&id=' + id,
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
				$.post(sy.contextPath + '/order/delete.do', {
					id : id
				}, function() {
					rows.length = 0;//必须，否则有bug
					grid.datagrid('reload');
				}, 'json');
			}
		});
	}; */
	
	$(function() {
		grid = $('#grid').datagrid({
			url : sy.contextPath + '/order/list.do',
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
				width : $(this).width() * 0.15, title : '商品名称', field : 'title', align : 'center'
			},{
				width : $(this).width() * 0.1, title : '用户', field : 'userName', align : 'center'
			},{
				width : $(this).width() * 0.1, title : '联系电话', field : 'tel', align : 'center'
			},{
				width : $(this).width() * 0.2, title : '收获地址', field : 'address', align : 'center'
			},{
				width : $(this).width() * 0.05, title : '积分', field : 'score', align : 'center'
			},{
				width : $(this).width() * 0.1, title : '订单状态', field : 'state', align : 'center'
				,formatter:function(value, row, index) {
					if(value==0){
						return "待审核";
					}else if(value==1){
						return "兑换成功";
					}else{
						return "";
					}
				}
			},{
				width : $(this).width() * 0.1, title : '备注', field : 'remark', align : 'center'
			},{
				width : $(this).width() * 0.15, title : '创建时间', field : 'createTime', align : 'center'
			},{
				width : $(this).width() * 0.1, title : '发货', field : 'deliveryStatus', align : 'center'
				,formatter:function(value, row, index) {
					if(value==1){
						return "已发货";
					}else{
						return '<a href="${path}/order/deliveryConfirm.do?id='+row.id+'" onclick="if(confirm(\'确定已发货?\')==false)return false;" title="点击标识为已发货">待发货</a>';
					}
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
		var url = sy.contextPath + '/order/save.do';
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
						<!-- <tr>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_edit',plain:true" onclick="editFun();">修改</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_delete',plain:true" onclick="delFun();">删除</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
						</tr> -->
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