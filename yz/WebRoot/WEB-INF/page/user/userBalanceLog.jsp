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
	var userId='${id}';

	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : sy.contextPath + '/userBalanceLog/listByUserId.do?id='+userId,
			toolbar : '#toolbar',
			singleSelect : false,
			columns : [ [{
				width : $(this).width() * 0.2,
				title : '项目',
				field : 'item',
				align : 'center',
				formatter : function(value, row, index) {
					if(value!=null&&value!="")
				     return "<span  title='"+value+"' >"+value+"</span>";
					
				}
			},{
				width : $(this).width() * 0.1,
				title : '收支变化',
				field : 'amount',
				align : 'center',
				formatter : function(value, row, index) {
					if(value!=null){
						return row.type==1?"<span style='color: green;' >+"+value+"</span>":"<span style='color: red;' >-"+value+"</span>";
					}
					}
			},{
				width : $(this).width() * 0.3,
				title : '说明',
				field : 'info',
				align : 'center',
				formatter : function(value, row, index) {
					if(value!=null&&value!="")
				     return "<span  title='"+value+"' >"+value+"</span>";
					
				}
			}, 
			{
				width : $(this).width() * 0.2,
				title : '创建时间',
				field : 'createTime',
				align : 'center'
			}
			] ]
		});
		
		$('#type').combobox({   
			onSelect: function(rec){   
				grid.datagrid('load',sy.serializeObject($('#searchForm')));
	        } 
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
								<td>类型：</td>
								<td>
									<select id="type" name="type" editable="false" style="width:150px;" panelHeight='auto' data-options="valueField: 'value',textField: 'name',data: [{name: '收入',value: '1'},{name: '支出',value: '2'}]"/>
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