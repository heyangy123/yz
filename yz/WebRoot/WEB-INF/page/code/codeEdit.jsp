<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/base/jsp/include.jsp"%>
<style>
</style>
<script type="text/javascript">
	var id = "${id}";

	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			var url = sy.contextPath + '/code/save.do';
			$.post(url, sy.serializeObject($('form')), function(result) {
				if (result.code==0) {
					$grid.datagrid('reload');
					$dialog.dialog('destroy');
				} else {
					$pjq.messager.alert('提示', '操作失败', 'error');
				}
			}, 'json');
		}
	};
	
	$(function() {
		if (id != '') {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(sy.contextPath + '/code/findById.do', {
				code : id
			}, function(result) {
				parent.$.messager.progress('close');
				if (result) {
					$('form').form('load', result);
				}
			}, 'json');
		
		}
	});
</script>
</head>
<body>
	<form id="form" method="post">
        <div style="padding:15px;font-size: 12px">
            <table style="table-layout:fixed;" border="0" cellspacing="0" class="formtable">
            	<tr>
                    <th style="width:100px;">名称：</th>
                    <td>   
                    	 <input id="code" name="code" style="width: 90%;border: 0px" readonly="readonly"   />
                    </td>
                </tr>
                <tr>
                    <th style="width:100px;">值：</th>
                    <td>   
                    	 <input id="value" name="value" style="height:100px;width: 90%" class="easyui-textbox" data-options="required:true,multiline:true,validType:['length[0,500]']" missingMessage="请输入值"   ></input>
                    </td>
                </tr> 
                <tr>
                    <th style="width:100px;">描述：</th>
                    <td>   
                    	 <input id="info" name="remark" class="easyui-textbox" readonly="readonly" data-options="multiline:true" style="height:80px;width: 90%;"/>
                    </td>
                </tr>
            </table>
        </div>
	</form>
</body>
</html>