<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/base/jsp/include.jsp"%>
<style>
</style>
<script type="text/javascript">
	var id = "${id}";
	var type = "${type}";
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			$('#id').val(id);
			var obj = sy.serializeObject($('form'));
			var url ;
			if(type==1){
				url = sy.contextPath + '/user/update.do';
			}else if(type==2){
				obj.code = id;
				url = sy.contextPath + '/city/save.do';
			}else if(type==3){
				url = sy.contextPath + '/store/save.do';
			}
			
			$.post(url, obj, function(result) {
				if (result.code == 0) {
					$grid.datagrid('load');
					$dialog.dialog('destroy');
				} else {
					$pjq.messager.w('操作失败'+result.msg);
				}
			}, 'json');
		}
	};
	
</script>
</head>
<body>
	<form id="form" method="post">
        <input id="id" name="id" type="hidden" />
        <div style="padding:15px;font-size: 12px">
            <table style="table-layout:fixed;" border="0" cellspacing="0" class="formtable">
                <tr>
                    <th style="width:100px;">密码：</th>
                    <td>   
                    	<input id="password" name="password" type="password" class="easyui-textbox" data-options="required:true" prompt="" missingMessage="请输入密码"/>
                    </td>
                </tr>
                <tr>
                    <th style="width:100px;">确认密码：</th>
                    <td>
                    	<input type="password" class="easyui-textbox" data-options="required:true,validType:'eqPwd[\'#password\']'" prompt="" missingMessage="请输入确认密码"/>
					</td>
                </tr>
            </table>
        </div>
	</form>
</body>
</html>