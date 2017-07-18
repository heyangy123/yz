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
                	<th style="width:100px;">球场名称</th>
                    <td>
                    	<input id="place" name="place" class="easyui-textbox" data-options="required:true,validType:['length[0,50]']" 
                    	prompt="请输入球场名称" missingMessage="请输入球场名称" style="width: 90%;"/>
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">省份</th>
                    <td>
                    	<input id="provinceCode" class="easyui-combobox" data-options="required:true,panelHeight:'200px',editable:false"  style="width: 200px;" />
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">城市</th>
                    <td>
                    	<input id="cityCode" name="cityCode" class="easyui-combobox" data-options="required:true,panelHeight:'200px',editable:false"  style="width: 200px;"/>   
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">详细地址</th>
                    <td>
                    	<input id="address" name="address" class="easyui-textbox" data-options="required:true,validType:['length[0,50]']" 
                    	prompt="请输入详细地址" missingMessage="请输入详细地址" style="width: 90%;"/>
                    </td>
                </tr>
                
                <tr>
                	<th style="width:100px;">场馆图</th>
                    <td>
	                    <input type="file" id="img" name="img" data-options="required:true" required 
                    		fileType="*.jpg;*.jpeg;*.png;*.gif" fileSize="10240" missingMessage="请上传图片" multi="false"/>
                    </td>
                </tr>
            </table>
        </div>
	</form>
<script type="text/javascript">
var id = "${id}";
$(function() {
	var provinceCode = "";
	var cityCode = "";
	
	if (id != '' && id!='undefined') {
		parent.$.messager.progress({
			text : '数据加载中....'
		});
		$.post(sy.contextPath + '/place/findById.do', {
			id : id
		}, function(result) {
			parent.$.messager.progress('close');
			$('#id').textbox({novalidate:true});
			//$('#jobId').textbox('readonly','readonly');
			if (result) {
				provinceCode = result.provinceCode;
				cityCode = result.cityCode;
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
	
	var loadCities = function(pvCode){
		var url=sy.contextPath + '/area/findCities.do?provinceCode='+pvCode;
		$.post(url, function(result) {
			if (Array.isArray(result)) {
				$('#cityCode').combobox({
		            data:result,
		            valueField:'cityCode',
		            textField:'city',
		            multiple:false, //允许多选
		            editable:false,//禁止编辑
		            //value:cityCode, //设置隐含值
		            onLoadSuccess:function(){
		            	if(pvCode == provinceCode){
			                $('#cityCode').combobox('select',cityCode);
		            	}
		            }
		        });
			}
		}, 'json');
	};
	var loadProvinces = function(){
		var url=sy.contextPath + '/area/findProvinces.do';
		$.post(url, function(result) {
			if (Array.isArray(result)) {
				$('#provinceCode').combobox({         
		            data:result,
		            valueField:'provinceCode',
		            textField:'province',
		            multiple:false, //允许多选
		            editable:false,//禁止编辑
		            value:provinceCode, //设置隐含值
		            onLoadSuccess:function(){
						$('#provinceCode').combobox('select',provinceCode);
						if(provinceCode!=""){
							loadCities(provinceCode);
		             	}
		            }
		        });
			}
		}, 'json');
	};
	loadProvinces();
	
	$("#provinceCode").combobox({
		onChange: function(newVal,oldVal) {
			if(newVal!="" && newVal!=oldVal){
				loadCities(newVal);
			}
		}
	});
});
var submitForm = function($dialog, $grid, $pjq){
	if ($('form').form('validate')) {
		var url=sy.contextPath + '/place/save.do';
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