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

KindEditor.ready(function(K) {
    window.editor = K.create('#info', {
    	//cssPath : '../plugins/code/prettify.css',
		uploadJson : projectPath+'/fileUpload.do',
		//fileManagerJson : '../jsp/file_manager_json.jsp',
		allowFileManager : false,
		afterCreate : function() {
			var self = this;
			self.sync();
		}
    });
    //html = editor.html();
    //editor.sync();
});


var id = "${id}";
$(function() {
	if (id != '' && id!='undefined') {
		
		parent.$.messager.progress({
			text : '数据加载中....'
		});
		$.post(sy.contextPath + '/product/findById.do', {
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
				editor.html(result.info);
			}
		}, 'json');
	}else{
	}
});

var submitForm = function($dialog, $grid, $pjq){
	editor.sync();
	if ($('form').form('validate')) {
		var url=sy.contextPath + '/product/save.do';
		var data = sy.serializeObject($('form'));
		
		var info = data.info;
		
		info = info.replace(new RegExp('"download',"gm"), sy.contextPath + '/download');
		info = info.replace(new RegExp('"/download',"gm"), sy.contextPath + '/download');
		info = info.replace(new RegExp('"/yz/download',"gm"), '"' + sy.contextPath + '/download');
		info = info.replace(new RegExp('<img src',"gm"), '<img style="width:100%;" src');
		data.info = info;
		
		$.post(url, data, function(result) {
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
        <div style="padding:15px;font-size: 12px">
            <table style="table-layout:fixed;" border="0" cellspacing="0" class="formtable">
                <tr>
                	<th style="width:100px;">礼品名称</th>
                    <td>
				        <input type="hidden" id="id" name="id" />
                    	<input id="title" name="title" class="easyui-textbox" data-options="required:true,validType:['length[0,50]']" 
                    	prompt="请输入礼品名称" missingMessage="请输入礼品名称" style="width: 90%;"/>
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;height:100px;">图片</th>
                    <td>
                    	<input type="file" id="img" name="img" data-options="required:true" required 
                    		fileType="*.jpg;*.jpeg;*.png;*.gif" fileSize="10240" missingMessage="请上传图片" multi="false"/>
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">积分</th>
                    <td>
                    	<input id="score" name="score" class="easyui-textbox" data-options="required:true,validType:['length[0,30]']" 
                    	prompt="请输入积分" missingMessage="请输入积分" style="width: 90%;"/>
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">库存</th>
                    <td>
                    	<input id="stock" name="stock" class="easyui-textbox" data-options="required:true,validType:['length[0,30]']" 
                    	prompt="请输入库存" missingMessage="请输入库存" style="width: 90%;"/>
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">礼品信息</th>
                    <td>
                    	<!-- <input type="text" id="info" name="info" class="easyui-textbox" data-options="required:true,multiline:true" style="width:90%;height:100px"/> -->
                    	<textarea id="info" name="info" data-options="required:true" style="width:99%;height:300px"></textarea>
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">上架状态</th>
                    <td>
                    	<label><input type="radio" name="state" class="easyui-radio easyui-validatebox" value="0"
                    	 data-options="validType:'requireRadio[\'input[name=state]\', \'上架或下架\']'" />下架</label>
                    	<label><input type="radio" name="state" class="easyui-radio easyui-validatebox" value="1"/>上架</label>
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