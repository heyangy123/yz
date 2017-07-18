<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/base/jsp/include.jsp"%>
<style>
</style>
<script type="text/javascript">
	var code = "${code}";

	var save = function() {
		var obj = {code:code};
		var fid=$('#file_upload').getFileId();
		if(fid==null||fid==''){
			$.messager.e('您还未上传图片');
			return ;
		}
		obj.value = fid;
		var url = sy.contextPath + '/code/save.do';
		$.post(url, obj, function(result) {
			if (result.code==0) {
				$.messager.i('保存成功');
			} else {
				$.messager.e('操作失败');
			}
		}, 'json');
	};
	
	$(function() {
		parent.$.messager.progress({
			text : '数据加载中....'
		});
		$.post(sy.contextPath + '/code/findById.do', {
			code : code
		}, function(result) {
			parent.$.messager.progress('close');
			if (result) {
				$("#remark").html(result.remark);
				$('#file_upload').setFileId(result.value,false,true,false);
			}
		}, 'json');
	});
</script>
</head>
<body>
	<form id="form" method="post">
        <div style="padding:15px;font-size: 12px">
        	<a class="easyui-linkbutton" onclick="save()" data-options="iconCls:'ext-icon-database_save'">保存</a>
			<h3 id="remark"></h3>
        	<input id="file_upload" multi="false" type="file" showImage="true" showBtn='false' fileType="*.jpg;*.png" fileSize="500KB" buttonText="上传欢迎页"/>
        	<span><span style="color: red;" >*</span>1080*1920</span>
        </div>
	</form>
</body>
</html>