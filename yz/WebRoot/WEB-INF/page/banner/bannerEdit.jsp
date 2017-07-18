<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<%@ include file="/base/jsp/include.jsp"%>
<style>
/* 自适应宽高 */
#imgView img { max-width:200px; width:expression(document.body.clientWidth>200?"200px":"auto"); overflow:hidden; }
</style>
<script type="text/javascript">
</script>
</head>
<body>
	<form id="form" method="post" accept-charset="UTF-8">
        <input type="hidden" id="id" name="id" />
        <div style="padding:15px;font-size: 12px">
            <table style="table-layout:fixed;" border="0" cellspacing="0" class="formtable">
                <tr>
                	<th style="width:100px;">标题</th>
                    <td>
                    	<input id="title" name="title" class="easyui-textbox" data-options="required:true,validType:['length[0,30]']" 
                    	prompt="请输入标题" missingMessage="请输入标题" style="width: 90%;"/>
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">板块</th>
                    <td>
                    	<select id="type" name="type" class="easyui-combobox" data-options="required:true,panelHeight:'auto',editable:false"  style="width: 200px;">   
						    <option value="">--请选择--</option>
						    <option value="1">约战</option>
						    <option value="2">发现</option>
						    <option value="3">篮球圈</option>
						    <option value="4">商城</option>
						</select>
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">图片</th>
                    <td>
	                    <input type="file" id="img" name="img" data-options="required:true" required 
                    		fileType="*.jpg;*.jpeg;*.png;*.gif" fileSize="10240" missingMessage="请上传图片" multi="false"/>
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">链接</th>
                    <td>
                    	<input id="jumpUrl" name="jumpUrl" class="easyui-textbox" data-options="required:false,validType:['length[0,20]']" 
                    	prompt="请填写链接" missingMessage="请填写链接" style="width: 90%;"/>
                    </td>
                </tr>
            </table>
        </div>
	</form>
<script type="text/javascript">
var id = "${id}";
$(function() {
	if (id != '' && id!='undefined') {
		parent.$.messager.progress({
			text : '数据加载中....'
		});
		$.post(sy.contextPath + '/banner/findById.do', {
			id : id
		}, function(result) {
			parent.$.messager.progress('close');
			$('#id').textbox({novalidate:true});
			//$('#jobId').textbox('readonly','readonly');
			if (result) {
				var imgId = result.img;
				if(imgId!='' && imgId!=undefined){
					var imgHtm = '<div class="uploadify-img-queue-div">'
						+'<img class="simple-img" onclick="po.clickImg(this)" src="download.do?id='+imgId+'&amp;w=80&amp;h=80"'
						+' width="80" height="80" parent="true" showbtn="false" uploadid="img" bestsize="">'
						+'<a class="uploadify-img-queue-div-a" href="javascript:void(0)"'
						+' onclick="$(&quot;#img&quot;).delFileId(&quot;'+imgId+'&quot;,false);$(this).parent().remove();a_resize();"></a></div>'
					$("#img_img_queue").html(imgHtm);
					result.img = "";
				}
				$('form').form('load', result);
			}
		}, 'json');
	}else{
		
	}
});
var submitForm = function($dialog, $grid, $pjq){
	if ($('form').form('validate')) {
		var url=sy.contextPath + '/banner/save.do';
		$.post(url, sy.serializeObject($('form')), function(result) {
			if (result.code == 0) {
				$grid.datagrid('load');
				$dialog.dialog('destroy');
			} else {
				$pjq.messager.e('添加失败,'+result.msg);
			}
		}, 'json');
	}
};

</script>
</body>
</html>