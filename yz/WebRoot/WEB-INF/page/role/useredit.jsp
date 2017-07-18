<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/base/jsp/include.jsp"%>
<style>
</style>
<script type="text/javascript">
	var id = "${id}";
	var parent = "${parent}";
	var level = "${level}";
	var groupId= '${groupId}';
	
	var getValues = function(){
		var obj = [];
		//var tt = $('#ss').combotree('tree');
		var tt = $('#ss');
        var nodes = tt.tree('getChecked');
		if (!nodes) return obj;
		for (var i=0;i<nodes.length;i++){
			obj.push(nodes[i].id);
			if (tt.tree('isLeaf',nodes[i].target)){
				var node = tt.tree('getParent',nodes[i].target);
				if (!isHave(obj,node.id)){obj.push(node.id);}
			}
		}
		return obj;
	};
	
	var setValues = function(rid){
		var tt = $('#ss');
		if (!rid) return;
		var array = rid.split(',');
		for (var i=0;i<array.length;i++){
			var node = tt.tree('find',array[i]);
			tt.tree('check',node.target);
		}
	};
	
	var isHave = function(obj,node){
		for (var i=0;i<obj.length;i++){
			if (node== obj[i])
				return true;
		}
		return false;
	};
	
	
	var submitForm = function($dialog, $grid, $pjq, $mainMenu) {
		if ($('form').form('validate')) {
			var url = sy.contextPath + '/adduser.do';
			var obj = sy.serializeObject($('form'));
			if (groupId)
				obj.groupId = groupId;
			$.post(url,obj , function(result) {
				if (result.success) {
					$grid.datagrid('reload');
					$dialog.dialog('destroy');
				} else {
					$pjq.messager.alert('提示', '操作失败', 'error');
				}
			}, 'json');
		}
	};

	$(function() {
		if (id == ''){
			$('#roleId').combobox({
				valueField:'code',
				textField:'name',
				disabled:true,
				panelHeight:'auto'
			});
			$('#groupId').combobox({
				url:sy.contextPath + '/grouprolelist.do',
				valueField:'id',
				textField:'name',
				panelHeight:'auto',
				onSelect:function(data){
					$('#roleId').combobox({
						disabled:false,
						url:sy.contextPath + '/grouprolelist.do?id='+data.id,
					});
				}
			});
		}
		if (id != '') {
			$('#group').hide();
			$('#role').hide();
			$('#roleId').combobox({required:false});
			$('#groupId').combobox({required:false});
			$.post(sy.contextPath + '/finduser.do', {
				id : id
			}, function(result) {
				if (result) {
					result.password='';
					$('form').form('load', result);	
					//$('#password').val('');
				}
			}, 'json');
			$('#account').textbox({novalidate:true});
			$('#account').textbox({'disabled':true});
		}else{
			if(parent == '0'){
				$("#hot").show();
			}
		}
	});
</script>
</head>
<body>
	<form id="form" method="post">
        <input name="id" type="hidden" />
        <div style="padding:15px;font-size: 12px">
            <table style="table-layout:fixed;" border="0" cellspacing="0" class="formtable">
            	 <tr id='group'>
                    <th style="width:100px;">用户组：</th>
                    <td>   
                    	 <input id="groupId" name="groupId" class="easyui-combobox" data-options="required:true" missingMessage="请选择用户组"  style="width: 90%;"/>
                    </td>
                </tr>
                <tr id='role'>
                    <th style="width:100px;">角色：</th>
                    <td>   
                    	 <input id="roleId" name="roleCode" class="easyui-combobox" data-options="required:true" missingMessage="请选择角色" style="width: 90%;"/>
                    </td>
                </tr>
            	<tr>
                    <th style="width:100px;">用户名：</th>
                    <td>
                    	 <input id="account" name="account" class="easyui-textbox" data-options="required:true,validType:['length[0,15]','account','unique[\'/checkAccount.do\']']" missingMessage="请输入用户名" style="width: 90%;"/>
                    </td>
                </tr>
                <tr>
                    <th style="width:100px;">密码：</th>
                    <td>
                    	 <input id="password" name="password" class="easyui-textbox" type="password" data-options="required:true,validType:['length[0,15]']" missingMessage="请输入密码" style="width: 90%;"/>
                    </td>
                </tr>
                <tr>
                    <th style="width:100px;">确认密码：</th>
                    <td>
                    	<input type="password" class="easyui-textbox" data-options="required:true,validType:'eqPwd[\'#password\']'" missingMessage="请输入确认密码" style="width: 90%;"/>
					</td>
                </tr>
            </table>
        </div>
	</form>
</body>
</html>