<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<%@ include file="/base/jsp/include.jsp"%>
<style>
</style>
<script type="text/javascript">
var id = "${id}";
var gameId = "${gameId}";
$(function() {
	if (id != '' && id!='undefined') {
		parent.$.messager.progress({
			text : '数据加载中....'
		});
		$.post(sy.contextPath + '/video/findById.do', {
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
		//$("#gameId").val(gameId);
	}
});
var submitForm = function($dialog, $grid, $pjq){
	if ($('form').form('validate')) {
		var url=sy.contextPath + '/video/save.do';
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
</head>
<body>
	<form id="form" method="post" accept-charset="UTF-8">
        <input type="hidden" id="id" name="id" />
        <input type="hidden" id="gameId" name="gameId" />
        <div style="padding:15px;font-size: 12px">
            <table style="table-layout:fixed;" border="0" cellspacing="0" class="formtable">
                <tr>
                	<th style="width:100px;">视频标题</th>
                    <td>
                    	<input id="title" name="title" class="easyui-textbox" data-options="required:true,validType:['length[0,30]']" 
                    	prompt="请输入视频标题" missingMessage="请输入视频标题" style="width: 90%;"/>
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;height:100px;">视频封面</th>
                    <td>
                    	<input type="file" id="img" name="img" data-options="required:true" required 
                    		fileType="*.jpg;*.jpeg;*.png;*.gif" fileSize="10240" missingMessage="请上传图片" multi="false"/>
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">视频链接</th>
                    <td>
                    	<input id="url" name="url" class="easyui-textbox" data-options="required:true,validType:['url']" 
                    	prompt="请输入视频链接" missingMessage="请输入有效的http链接" style="width: 90%;"  />
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">视频时长</th>
                    <td>
                    	<input id="time" name="time" class="easyui-textbox" data-options="required:false,validType:['length[0,30]']" 
                    	prompt="请输入视频时长" missingMessage="请输入有效的视频时长" style="width: 90%;"  />
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">排序</th>
                    <td>
                    	<input id="rank" name="rank" class="easyui-textbox" data-options="required:false,validType:['length[0,10]']" 
                    	prompt="请输入排序号" missingMessage="请输入有效的排序编号" style="width: 90%;"  />
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">备注</th>
                    <td>
                    	<input id="remark" name="remark" class="easyui-textbox" data-options="required:false,validType:['length[0,300]']" 
                    	prompt="请输入备注" missingMessage="请输入备注" style="width: 90%;"/>
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">启用状态</th>
                    <td>
                    	<label><input type="radio" name="state" class="easyui-radio easyui-validatebox" value="0"
                    	 data-options="validType:'requireRadio[\'input[name=state]\', \'启用状态\']'" />停用</label>
                    	<label><input type="radio" name="state" class="easyui-radio easyui-validatebox" value="1"/>启用</label>
                    </td>
                </tr>
            </table>
        </div>
	</form>
<script type="text/javascript">
$.extend($.fn.validatebox.defaults.rules, {
    requireRadio: {  
        validator: function(value, param){  
            var input = $(param[0]);
            input.off('.requireRadio').on('click.requireRadio',function(){
                $(this).focus();
            });
            return $(param[0] + ':checked').val() != undefined;
        },  
        message: '请选择： {1}.'  
    }  
});
</script>
</body>
</html>