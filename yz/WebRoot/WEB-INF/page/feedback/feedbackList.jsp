<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/base/jsp/include.jsp"%>
<script type="text/javascript">
	var grid;
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
					ids.push(r.id);
				}
				var id = ids.join(',');
				
				$.post(sy.contextPath + '/feedback/del.do', {
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
			title : '',
			url : sy.contextPath + '/feedback/list.do',
			toolbar : '#toolbar',
			singleSelect : false,
			frozenColumns : [ [ {
				width : '100',
				checkbox:true,
				field : 'id',
				align : 'center'
			}] ],
			
			columns : [ [ {
				width : $(this).width()*0.8,
				title : '内容',
				field : 'info',
				align : 'center',
				formatter:function(v,r,i){
					if(v!=null){
						return "<span title='"+v+"' >"+v+"</span>";
					}
				}
			}, {
                width : $(this).width() * 0.1,
                title : '图片',
                field : 'img',
                align : 'center',
                formatter : function(value, row, index) {
                    if(value!=null&&value!=''){
                        return po.showImg(value,30,30);
                    }else{
                        return null;
                    }
                }
            },
				{
				width :  $(this).width()*0.15,
				title : '创建时间',
				field : 'createTime',
				align : 'center'
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
							<!-- 
								<td>编号</td>
								<td><input name="code" style="width: 80px;" /></td>
								<td>
									<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="grid.datagrid('load',sy.serializeObject($('#searchForm')));">过滤</a>
									<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchForm input').val('');grid.datagrid('load',{});">重置过滤</a>
								</td> -->
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
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