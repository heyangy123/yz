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
					width : $(this).width() * 0.08,
					title : '当前积分',
					field : 'score',
					align : 'center'
				},{
					width : $(this).width() * 0.08,
					title : '总积分',
					field : 'totalScore',
					align : 'center'
				}/* ,{
					field:'opt',title:'操作',width:50,align:'center',
		            formatter:function(value,rec){
		                var btn = '<a class="editcls" onclick="viewRow(\''+rec.id+'\')" href="javascript:void(0)">编辑</a>';  
		                return btn;
		            }
		        } */
				] ],
				/* onLoadSuccess:function(data){
			        $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
			    } */
			};
		grid = $('#grid').datagrid(menu);
	});
	
	/* var viewRow = function(id){
		if(id){
			var dialog = parent.sy.modalDialog({
				title : '用户信息',
				width : 400,
				height : 200,
				url : sy.contextPath + '/go.do?path=common/pwdEdit&type=1&id=' + id,
				buttons : [ {
					text : '关闭',
					handler : function() {
						dialog.dialog('destroy');
					}
				} ]
			});
		}
	} */
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
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>