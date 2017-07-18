<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>首页</title>
    <jsp:include page="include.jsp"></jsp:include>
    <style type="text/css">
    html,html body
    {
        font-family:宋体;
        font-size:13px;
    }
    h2
    {
        font-family: "Trebuchet MS",Arial,sans-serif;
    }
    p
    {
        line-height:22px;
    }
    </style>
    <script type="text/javascript">
    var sys = "${sessionUser.sys}";
    var roleCode = "${sessionUser.roleCode}";
    var areaCode = "${sessionUser.areaCode}";
    function getCurDate(){
	 var d = new Date();
	 var week;
	 switch (d.getDay()){
		 case 1: week="星期一"; break;
		 case 2: week="星期二"; break;
		 case 3: week="星期三"; break;
		 case 4: week="星期四"; break;
		 case 5: week="星期五"; break;
		 case 6: week="星期六"; break;
		 default: week="星期天";
	 }
	 var years = d.getYear()<1000?(d.getYear()+1900):d.getYear();
	 var month = add_zero(d.getMonth()+1);
	 var days = add_zero(d.getDate());
	 var hours = add_zero(d.getHours());
	 var minutes = add_zero(d.getMinutes());
	 var seconds=add_zero(d.getSeconds());
	 var ndate = years+"年"+month+"月"+days+"日 "+hours+":"+minutes+":"+seconds+" "+week;
	 document.getElementById('dd').innerHTML = ndate;
	}
	function add_zero(temp){
	 if(temp<10) return "0"+temp;
	 else return temp;
	}
	setInterval("getCurDate()",1000);
	if(sys=='store'){
	 $(function(){
		 
		 var notifyMenu={
					url : sy.contextPath + '/storeNotifyDraft/list.do',
					toolbar : '#toolbar',
					singleSelect : false,
					frozenColumns : [ [ {
						width : $(this).width() * 0.2,
						title : '标题',
						align : 'center',
						field : 'title',
						formatter:function(v,r,i){
							return UT.addTitle(v);
						}
					}] ],
					columns : [ [ {
						width : $(this).width() * 0.5,
						title : '内容',
						align : 'center',
						field : 'content',
						formatter:function(v,r,i){
							return UT.addTitle(v);
						}
					}, {
						width : $(this).width() * 0.2,
						title : '创建时间',
						field : 'createTime',
						align : 'center'
					}
					]]
				};
		var	notifyGrid = $('#notify').datagrid(notifyMenu);
		 
		 
// 		 var storeNotifyMenu={
// 					url : sy.contextPath + '/storeNotify/list.do',
// 					toolbar : '#toolbar',
// 					singleSelect : false,
// 					frozenColumns : [ [ {
// 						width : $(this).width() * 0.2,
// 						title : '标题',
// 						align : 'center',
// 						field : 'title',
// 						formatter:function(v,r,i){
// 							return UT.addTitle(v);
// 						}
// 					}] ],
// 					columns : [ [ {
// 						width : $(this).width() * 0.5,
// 						title : '内容',
// 						align : 'center',
// 						field : 'content',
// 						formatter:function(v,r,i){
// 							return UT.addTitle(v);
// 						}
// 					}, {
// 						width : $(this).width() * 0.2,
// 						title : '创建时间',
// 						field : 'createTime',
// 						align : 'center'
// 					}
// 					]]
// 				};
// 		var	storeNotifyGrid = $('#storeNotify').datagrid(storeNotifyMenu);
	 });
	}
	var PATH = "${path}";
	$(function(){
// 		$('#tt').tabs({
// 		    border:false,
// 		    height:window.top.$('body').height()*0.7+"px"
// 		});
		if(roleCode=='store'){
			webSockRecive(function(msg){
				if(msg.indexOf('链接成功')==-1){
					try {
						window.top.$.messager.show({
				            title:'您有一条新的消息',
				            msg:msg,
				            timeout:0,
				            width:250,
				            height:150,
				            showType:'slide'
				        });
					} catch (e) {
						
					}
				}
			});
		}
	});
	
	function webSockRecive(onrevice){
			
			var websocket = null;
			if (window['WebSocket']) 
				// ws://host:port/project/websocketpath
				websocket = new WebSocket("ws://" +  sy.contextPath.replace('http://','') + '/websocket');
			else
				websocket = new new SockJS(sy.contextPath + '/websocket/socketjs');
			
			websocket.onopen = function(event) {
				console.log('open', event);
			};
			websocket.onmessage = function(event) {
				console.log('message', event.data);
				onrevice(event.data);
			};
		}
	</script>
  </head>
  <body>
  	<h2><span id="dd"></span></h2>
    <p>欢迎您：${sessionUser.rolename }(${sessionUser.account })</p>
    <br />
    	<!-- 
    	<div id="tt" class="easyui-tabs" style="width:80%;">
		    <div title="公告" class="easyui-layout"  class="easyui-layout" style="cache:true;" id='orderInfo' >
		    	<div data-options="region:'top',fit:true,border:false"></div> 
		    	<div data-options="region:'center',fit:true,border:false" >
					<table id="notify" data-options="border:false" height="260px;" ></table>
				</div>
		    </div>
		    <div  title="消息 " style="cache:true;overflow:auto;" class="easyui-layout" data-options="fit:true,border:false" >
			    <div data-options="region:'top',fit:true,border:false"></div> 
		    	<div data-options="region:'center',fit:true,border:false" >
					<table id="storeNotify" data-options="border:false" height="160px;" ></table>
				</div>
		    </div>
		</div>
    	 -->
    	<!-- 
    	 <div id="cc" class="easyui-layout" style="width:1200px;height:500px;"   >  
	      <div region="north" title="商家公告"  style="height:300px;" data-options="collapsible:false" >
				<div data-options="region:'center',fit:true,border:false">
					<table id="notify" data-options="border:false" height="260px;" ></table>
				</div>
	      </div>  
	     
	      <div region="center" title="商家消息"  style="padding:5px;">
	      		<div data-options="region:'center',fit:true,border:false">
					<table id="storeNotify" data-options="border:false" height="160px;" ></table>
				</div>
	      </div>  
  		 </div> 
    	 -->
    	 <div id="cc" class="easyui-layout" style="width:1200px;height:500px;"   >  
		      <div region="north" title="公告"  style="height:300px;" data-options="collapsible:false" >
					<div data-options="region:'center',fit:true,border:false">
						<table id="notify" data-options="border:false" height="260px;" ></table>
					</div>
		      </div>  
  		 </div>
  </body>
</html>
