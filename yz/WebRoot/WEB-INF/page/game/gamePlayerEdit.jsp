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
			if (result) {
				//$("input[name='winner'][checked]").val();
				var winner = result.winner;
				if(winner!=undefined){
					$("input[name='winner'][value="+winner+"]").attr("checked",true);
					//禁用
					$('input:radio[name="winner"]').prop('disabled', true);
				}
			}
		}, 'json');
	}else{
	}
});
var submitForm = function($dialog, $grid, $pjq){
	endEditing();
	endEditing1();
	var winner = $("input:radio[name='winner']:checked").val();//0:约战方 1:应战方
	if(winner==null){
		$pjq.messager.w('请选择选择胜利的一方！');
		return;
	}
	
	//var dataArr=$('#player1').datagrid('getData');
	var dataArr=$('#player0').datagrid('getChanges');
	var dataArr1=$('#player1').datagrid('getChanges');
	dataArr = dataArr.concat(dataArr1);
	//alert(JSON.stringify(dataArr));
	if(dataArr.length==0){
		$pjq.messager.w('请更新比赛结果！');
		return;
	}
	//dataArr.forEach(function(item,index){});
	//var value=dataArr.rows[0].id;
	
	var data = {id:id, winner:winner, gamePlayerList:dataArr};
	$.ajax({
        type:   "post",
        url :   sy.contextPath + '/game/updateGamePlayers.do',
        contentType :   "application/json;charset=utf-8",
        dataType    :   "json",
        data    :   JSON.stringify(data),
        success :   function(result){
        	if (result.code == 0) {
    			$grid.datagrid('load');
    			$dialog.dialog('destroy');
    		} else {
    			$pjq.messager.e('录入失败,'+result.msg);
    		}
        }
});
	
};



$.extend($.fn.datagrid.methods, {
	editCell: function(jq,param){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field){
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});
//palyer0
var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#player0').datagrid('validateRow', editIndex)){
		$('#player0').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickCell(index, field){
	if (endEditing()){
		//$('#player0').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
		$(this).datagrid('editCell', {index:index,field:field});
		editIndex = index;
		var ed = $(this).datagrid('getEditor', {index:index,field:field});//获取当前编辑器
		ed && ed.target && $(ed.target).focus();//获取焦点
	}
}
//palyer1
var editIndex1 = undefined;
function endEditing1(){
	if (editIndex1 == undefined){return true}
	if ($('#player1').datagrid('validateRow', editIndex1)){
		$('#player1').datagrid('endEdit', editIndex1);
		editIndex1 = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickCell1(index, field){
	//$(this).datagrid('beginEdit',rowIndex);//开启编辑
	//var ed = $(this).datagrid('getEditor', {index:rowIndex,field:field});//获取当前编辑器
	//$(ed.target).focus();//获取焦点
	if (endEditing1()){
		//$('#player1').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
		$(this).datagrid('editCell', {index:index,field:field});
		editIndex1 = index;
		var ed = $(this).datagrid('getEditor', {index:index,field:field});//获取当前编辑器
		ed && ed.target && $(ed.target).focus();//获取焦点
	}
}


$(function(){

/* 	$('#player1').edatagrid({
		url: '${path}/game/findGamePlayers.do?gameId=${id}&type=1',
		saveUrl: './userAction.php?flag=add',
		updateUrl: './userAction.php?flag=modify',
		destroyUrl: './userAction.php?flag=delete',
		onSave: function (index, row) {
			var $datagrid = $('#player1');
			if ($datagrid.data('isSave')) {
				//如果需要刷新，保存完后刷新
				$datagrid.edatagrid('reload');
				$datagrid.removeData('isSave');
			}
		}
	}); */
});

</script>
</head>
<body>
	<div style="margin:20px 0px">
		<th>选择胜利的一方:</th><br>
        <label><input type="radio" name="winner" data-options="required:true"
            class="easyui-radio easyui-validatebox" value="0"/>约战方</label>
            <label><input type="radio" name="winner" data-options="required:true"
            class="easyui-radio easyui-validatebox" value="1"/>应战方</label>
        </td>
	</div>
	<div style="float:left;">
		<table id="player0" class="easyui-datagrid" title="约战方" style="width:380px;height:250px"
				data-options="autoSave:false,fitColumns:true,iconCls:'icon-edit',rownumbers:false,singleSelect:true,collapsible:false,onClickCell:onClickCell,url:'${path}/game/findGamePlayers.do?gameId=${id}&type=0',method:'post',pagination:false">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true">ID</th>
					<th data-options="field:'userId',hidden:true">userId</th>
					<th data-options="field:'userName',width:80,align:'center'">球友</th>
					<th data-options="field:'goal',width:60,align:'center',editor:'text'">得分</th>
					<th data-options="field:'assists',width:60,align:'center',editor:'text'">助攻</th>
					<th data-options="field:'rebounds',width:60,align:'center',editor:'text'">篮板</th>
					<th data-options="field:'steals',width:60,align:'center',editor:'text'">抢断</th>
					<th data-options="field:'block',width:60,align:'center',editor:'text'">封盖</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div style="float:left;">
		<table id="player1" class="easyui-datagrid" title="应战方" style="width:380px;height:250px;float:left;"
				data-options="autoSave:false,fitColumns:true,iconCls:'icon-edit',rownumbers:false,singleSelect:true,collapsible:false,onClickCell:onClickCell1,url:'${path}/game/findGamePlayers.do?gameId=${id}&type=1',method:'post',pagination:false">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true">ID</th>
					<th data-options="field:'userId',hidden:true">userId</th>
					<th data-options="field:'userName',width:80,align:'center'">球友</th>
					<th data-options="field:'goal',width:60,align:'center',editor:'text'">得分</th>
					<th data-options="field:'assists',width:60,align:'center',editor:'text'">助攻</th>
					<th data-options="field:'rebounds',width:60,align:'center',editor:'text'">篮板</th>
					<th data-options="field:'steals',width:60,align:'center',editor:'text'">抢断</th>
					<th data-options="field:'block',width:60,align:'center',editor:'text'">封盖</th>
				</tr>
			</thead>
		</table>
	</div>
	
</body>
</html>