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
	var creditsFun = function() {
		var rows = grid.datagrid('getSelections');
		if (rows.length != 1) {
			parent.$.messager.w('请选择一条记录进行查看！');
			return;
		}
		var nickName="";
		if(rows[0].nickName!=null&&rows[0].nickName!=""){
			nickName=rows[0].nickName;
		}
		var url = sy.contextPath + '/go.do?path=user/userCreditLogList&id=' + rows[0].id;
		var dialog = parent.sy.modalDialog({
			title : '积分日志——'+nickName,
			width : 800,
			height : 620,
			url : url
		});
	};
	var balanceFun = function() {
		var rows = grid.datagrid('getSelections');
		if (rows.length != 1) {
			parent.$.messager.w('请选择一条记录进行查看！');
			return;
		}
		var nickName="";
		if(rows[0].nickName!=null&&rows[0].nickName!=""){
			nickName=rows[0].nickName;
		}
		var url = sy.contextPath + '/go.do?path=user/userBalanceLog&id=' + rows[0].id;
		var dialog = parent.sy.modalDialog({
			title : '余额日志——'+nickName,
			width : 800,
			height : 620,
			url : url
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
			width : 400,
			height : 200,
			url : sy.contextPath + '/go.do?path=common/pwdEdit&type=1&id=' + rows[0].id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	}
	$(function() {
		
		var menu;
		menu={
				url : sy.contextPath + '/user/list.do',
				toolbar : '#toolbar',
				singleSelect : false,
				columns : [ [ {
					width : $(this).width() * 0.1,
					title : 'ID',
					field : 'id',
					align : 'center'
				},{
					width : $(this).width() * 0.1,
					title : '账号',
					field : 'account',
					align : 'center'
				},{
					width : $(this).width() * 0.1,
					title : '昵称',
					field : 'nickName',
					align : 'center'
				},{
					width : $(this).width() * 0.1,
					title : '头像',
					field : 'headImg',
					align : 'center',
					formatter : function(value, row, index) {
						if(value!=null&&value!=''){
							return po.showImg(value,20,20);
						}else{
							return null;
						}
					}
				},{
					width : $(this).width() * 0.05,
					title : '性别',
					field : 'sex',
					align : 'center',
					formatter : function(value, row, index) {
						if(value==1){
							return "男";
						}else if(value == 2){
							return "女";
						}else{
							return "未知";
						}
					}
				},{
					width : $(this).width() * 0.08,
					title : '当前积分',
					field : 'score',
					align : 'center'
				},{
					width : $(this).width() * 0.08,
					title : '总积分',
					field : 'totalScore',
					align : 'center'
				},{
					width : $(this).width() * 0.08,
					title : 'VIP等级',
					field : 'vip',
					align : 'center'
				},{
					width : $(this).width() * 0.08,
					title : '余额(￥)',
					field : 'balance',
					align : 'center'
				},{
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
					width : $(this).width() * 0.15,
					title : '创建时间',
					field : 'createTime',
					align : 'center'
				}
				] ]
			};
		
		grid = $('#grid').datagrid(menu);
	});
	
	function forbidden(state,id) {
		if (state == 1) {
			
			parent.$.messager.confirm('询问',"确定启用此账号吗？", function(r) {
				if (r) {
					
					var data = {
						id : id,
						state : 1
					};
					SaveData(data);
				}
			});
		} else if(state == 0){
			parent.$.messager.confirm('询问',"确定禁用此账号吗？", function(r) {
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
	function bannedPost(state,id) {
		if (state == 1) {
			parent.$.messager.confirm('询问',"确定将此账号禁言吗？", function(r) {
				if (r) {
					
					var data = {
						id : id,
						bannedPost : 1
					};
					SaveData(data);
				}
			});
		} else if(state == 0){
			parent.$.messager.confirm('询问',"确定将此账号取消禁言吗？", function(r) {
				if (r) {
					var data = {
						id : id,
						bannedPost : 0
					};
					SaveData(data);
				}
			});
		}
	}
	function SaveData(data) {
		var url = sy.contextPath + '/user/update.do';
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
								<td>昵称/手机号：</td>
								<td>
									<input type="text" class="easyui-textbox" name="name" style="width: 150px"  />
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
			<tr>
				<td>
					<table>
						<tr>
							<!-- <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-book',plain:true" onclick="creditsFun();">积分日志</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-book',plain:true" onclick="balanceFun();">余额日志</a></td>
							<td><div class="datagrid-btn-separator"></div></td> -->
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