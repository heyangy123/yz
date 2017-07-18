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
	
	var submitForm = function($dialog, $grid, $pjq, $mainMenu) {
		if ($('form').form('validate')) {
			var url = sy.contextPath + '/addright.do';
			var obj = sy.serializeObject($('form'));
			if(parent!=""){
				obj.level = level;
				obj.parentId = parent;
			}
			obj.appId = '0';
			$.post(url,obj , function(result) {
				if (result.success) {
					$grid.treegrid('reload',parent);
					$dialog.dialog('destroy');
					$mainMenu.tree('reload');
				} else {
					$pjq.messager.alert('提示', '操作失败', 'error');
				}
			}, 'json');
		}
	};

	$(function() {
		if(level == 2){
			$("#url").textbox({required:true});
		}
		
		if (id != '') {
			$.post(sy.contextPath + '/findright.do', {
				id : id
			}, function(result) {
				if (result) {
					if(result.parentId == '0'){
						$("#hot").show();
					}
					$('form').form('load', result);
					
				}
			}, 'json');
			$('#code').textbox({novalidate:true});
			$('#code').textbox({'disabled':true});
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
            	<tr>
                    <th style="width:100px;">名称：</th>
                    <td>
                    	 <input id="name" name="name" class="easyui-textbox" data-options="required:true,validType:['length[0,15]']" missingMessage="请输入名称" style="width: 90%;"/>
                    </td>
                </tr>
                <tr>
                    <th style="width:100px;">标识：</th>
                    <td>   
                    	 <input id="code" name="code" class="easyui-textbox" data-options="required:true" missingMessage="请输入标识" style="width: 90%;"/>
                    </td>
                </tr>
                <tr>
                    <th style="width:100px;">跳转：</th>
                    <td>   
                    	 <input id="url" name="url" class="easyui-textbox" data-options="required:false" missingMessage="请输入跳转地址" style="width: 90%;"/>
                    </td>
                </tr>
                <tr>
                    <th style="width:100px;">排序(倒序)：</th>
                    <td>   
                    	 <input id="info" name="sortOrder" class="easyui-textbox" data-options="required:true,validType:'number'" missingMessage="请输入序号(倒序)" style="width: 90%;"/>
                    </td>
                </tr>
                <tr>
                    <th style="width:100px;">描述：</th>
                    <td>   
                    	 <input id="info" name="info" class="easyui-textbox" data-options="" missingMessage="请输入编号" style="width: 90%;"/>
                    </td>
                </tr>
                 <tr>
                    <th style="width:100px;">图标：</th>
                    <td>   
                    	<%@ include file="icons.jsp"%>
                    </td>
                </tr>
            </table>
        </div>
	</form>
</body>
</html>