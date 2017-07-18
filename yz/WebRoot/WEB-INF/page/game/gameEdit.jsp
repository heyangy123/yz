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
$(function() {
	if (id != '' && id!='undefined') {
		parent.$.messager.progress({
			text : '数据加载中....'
		});
		$.post(sy.contextPath + '/game/findById.do', {
			id : id
		}, function(result) {
			parent.$.messager.progress('close');
			$('#id').textbox({novalidate:true});
			//$('#jobId').textbox('readonly','readonly');
			if (result) {
				$('form').form('load', result);
				$("#imgView").attr('src',"download?id="+result.img);
				//
				result.gameTypeList.unshift({'name':'--请选择--','id':''});
				$("#gameTypeId").combobox({
					    valueField: 'id',
					    textField: 'name',
					    panelHeight: 'auto',
					    required: true,
					    editable: false,//不可编辑，只能选择
					    data: result.gameTypeList
				});
				$('#gameTypeId').combobox('select', result.gameTypeId || "");
				//
				result.gamePlaceList.unshift({'place':'--请选择--','id':''});
				$("#gamePlaceId").combobox({
					    valueField: 'id',
					    textField: 'place',
					    panelHeight: 'auto',
					    required: true,
					    editable: false,//不可编辑，只能选择
					    data: result.gamePlaceList
				});
				$('#gamePlaceId').combobox('select', result.gamePlaceId || "");
				
				if(result.gameDetailList){
					result.gameDetailList.forEach(function(item, index){
						if(item.type==0){
							$("#amount").val(item.amount);
						}
					});
				}
			}
		}, 'json');
	}else{
	}
});
var submitForm = function($dialog, $grid, $pjq){
	if ($('form').form('validate')) {
		var url=sy.contextPath + '/game/save.do';
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
        <div style="padding:15px;font-size: 12px">
            <table style="table-layout:fixed;" border="0" cellspacing="0" class="formtable">
                <tr>
                	<th style="width:100px;">组织者</th>
                    <td>
                    	<input id="userId" name="userId" class="easyui-textbox" data-options="required:true,validType:['length[0,30]']" 
                    	prompt="请输入组织者" missingMessage="请输入组织者" style="width: 90%;"/>
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">联系电话</th>
                    <td>
                    	<input id="tel" name="tel" class="easyui-textbox" data-options="required:true,validType:['length[0,30]']" 
                    	prompt="请输入联系电话" missingMessage="请输入联系电话" style="width: 90%;"/>
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">比赛主题</th>
                    <td>
                    	<input id="theme" name="theme" class="easyui-textbox" data-options="required:true,validType:['length[0,30]']" 
                    	prompt="请输入比赛主题" missingMessage="请输入比赛主题" style="width: 90%;"/>
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">比赛类型</th>
                    <td>
                    	<input type="text" id="gameTypeId" name="gameTypeId" class="easyui-combobox" style="width: 200px;"/>
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">比赛时间</th>
                    <td>
                    	<input class="easyui-datetimebox" id="playTime" name="playTime" data-options="required:true,showSeconds:true" 
                    		style="width:200px" data-options="formatter:formatDate,parser:parserDate">
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">比赛地点</th>
                    <td>
                    	<input type="text" id="gamePlaceId" name="gamePlaceId" class="easyui-combobox" style="width: 200px;"/>
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">比赛介绍</th>
                    <td>
                    	<input id="remark" name="remark" class="easyui-textbox" data-options="required:false,validType:['length[0,300]']" 
                    	prompt="请输入比赛介绍" missingMessage="请输入比赛介绍" style="width: 90%;"/>
                    </td>
                </tr>
                <tr>
                	<th style="width:100px;">比赛费用</th>
                    <td>
                    	<input id="amount" class="easyui-textbox" data-options="required:true,validType:['length[0,20]']" 
                    	prompt="请填写比赛费用" missingMessage="请填写比赛费用" style="width: 90%;" value="50" />
                    </td>
                </tr>
            </table>
        </div>
	</form>
<script type="text/javascript">
function formatDate(date){
    var y = date.getFullYear();  
    var m = date.getMonth()+1;  
    var d = date.getDate();  
    var h = date.getHours();  
    var min = date.getMinutes();
    //var sec = date.getSeconds();
    var sec = 0;
    //yyyy-mm-dd hh:mm:ss
    return  y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+' '+(h<10?('0'+h):h)+':'+(min<10?('0'+min):min)+':'+(sec<10?('0'+sec):sec);
      
}  
function parserDate(s){
	if (!s) return new Date();  
	var y = s.substring(0,4);  
	var m =s.substring(5,7);  
	var d = s.substring(8,10);  
	var h = s.substring(11,14);  
	var min = s.substring(15,17);  
	var sec = s.substring(18,20);  
	if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h) && !isNaN(min) && !isNaN(sec)){  
	    return new Date(y,m-1,d,h,min,sec);
	} else {
	    return new Date();
	}
}
</script>
</body>
</html>