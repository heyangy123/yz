<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/base/jsp/include.jsp"%>
<style>
</style>
<script type="text/javascript">
	var id = "${code}";
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			var url = sy.contextPath + '/city/save.do';
			var obj = sy.serializeObject($('form'));
			$.post(url,obj , function(result) {
				if (result.code == 0) {
					$grid.datagrid('reload');
					$dialog.dialog('destroy');
				} else {
					$pjq.messager.e('操作失败，'+result.msg);
				}
			}, 'json');
		}
	};

	$(function() {
		if (id != '') {
			$(".pwd").remove();
			$("#account").textbox({required:false,validType:'',disabled:true});
			if(id == '0'){
				$(".qq").remove();
			}
			$.post(sy.contextPath + '/city/findById.do', {
				id : id
			}, function(result) {
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
        <input name="code" type="hidden" />
        <div style="padding:15px;font-size: 12px">
            <table style="table-layout:fixed;" border="0" cellspacing="0" class="formtable">
            	<tr>
                    <th style="width:100px;">名称：</th>
                    <td>
                    	 <input id="name" name="name" class="easyui-textbox" data-options="required:true,validType:['length[0,15]']" missingMessage="请输入名称" style="width: 90%;" prompt="请输入名称"/>
                    	 <div>
                    	 	<span style="color: red;" id="labelStar">*</span>
                  		 	<span id="labelText" style="font-size:12px;">例：“常州市”填写为“常州”，否则将影响定位</span>
                    	 </div>
                    </td>
                </tr>
                <tr>
                    <th style="width:100px;">管理账号：</th>
                    <td>
                    	 <input id="account" name="account" class="easyui-textbox" data-options="required:true,validType:['length[0,15]','account','unique[\'/city/checkAccount\']']" missingMessage="请输入用户名" style="width: 90%;"/>
                    </td>
                </tr>
                <tr class="pwd">
                    <th style="width:100px;">密码：</th>
                    <td>
                    	 <input id="password" name="password" class="easyui-textbox" type="password" data-options="required:true,validType:['length[0,15]']" missingMessage="请输入密码" prompt="" style="width: 90%;"/>
                    </td>
                </tr>
                <tr class="pwd">
                    <th style="width:100px;">确认密码：</th>
                    <td>
                    	<input type="password" class="easyui-textbox" data-options="required:true,validType:'eqPwd[\'#password\']'" missingMessage="请输入确认密码" prompt="" style="width: 90%;"/>
					</td>
                </tr>
                <tr class="qq">
                    <th style="width:100px;">是否热门：</th>
                    <td>   
                    	<select id="isHot" class="easyui-combobox" name="isHot" data-options="panelHeight:'auto',editable:false"  style="width: 200px;">   
						    <option value="0">否</option>
						    <option value="1">是</option>
						</select> 
                    </td>
                </tr>
                <tr class="qq">
                    <th style="width:100px;">状态：</th>
                    <td>   
                    	<select id="status" class="easyui-combobox" name="status" data-options="panelHeight:'auto',editable:false"  style="width: 200px;">   
						    <option value="0">禁用</option>
						    <option value="1">启用</option>
						</select> 
                    </td>
                </tr>
            </table>
        </div>
	</form>
</body>
</html>