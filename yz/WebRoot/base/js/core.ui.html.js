/**
 * 解决easyui tabs 兼容问题
 */

var v_idQueue=[];
var a_resize = function(){
	//$('#'+v_itemId).find('object').css('position','fixed');
	$.each(v_idQueue,function(i,o){
		var pos = $('#'+o).find('object').css('position');
		if(pos=='fixed')  $('#'+o).find('object').css('position','absolute');
		if(pos=='absolute')  $('#'+o).find('object').css('position','fixed');
	});
};
$(function(){

	
	$.fn.clearFileId = function(){
		var v_itemId = $(this).attr("id");
		$("#"+ v_itemId+"_hidden").val("");
		$("#"+ v_itemId + "_img_queue").html("");
	}
	
	$.fn.setFileId = function(fid,multi,showImage,showBtn,bestSize) {
		var v_itemId = $(this).attr("id");
		if(fid == null) fid = '';
		var ids = $("#"+ v_itemId+"_hidden").val();
		if(ids == undefined || ids == '' || !multi){
			$("#"+ v_itemId+"_hidden").val(fid);
		}else{
			if(fid.indexOf(",")>-1){
				$("#"+ v_itemId+"_hidden").val(fid);
			}else{
				$("#"+ v_itemId+"_hidden").val(ids+","+fid);
			}
		}
		var showBtn = showBtn||false;
		var bestSize = bestSize||'';
		if(showImage && fid.length>0){
			if(multi){
				var array = fid.split(",");
				for ( var i = 0; i < array.length; i++) {
					$("#"+ v_itemId + "_img_queue").append("<div class='uploadify-img-queue-div'>" + 
						po.showImg({id:array[i],width:80,height:80,parent:true,showBtn:showBtn,bestSize:bestSize,uploadId:v_itemId}) +
						"<a class='uploadify-img-queue-div-a' href='javascript:void(0)' onclick='$(\"#"+v_itemId+"\").delFileId(\""+array[i]+"\","+multi+");$(this).parent().remove();a_resize();'></a></div>");
				}
			}else{
				$("#"+ v_itemId + "_img_queue").html("<div class='uploadify-img-queue-div'>" + 
						po.showImg({id:fid,width:80,height:80,parent:true,showBtn:showBtn,bestSize:bestSize,uploadId:v_itemId}) +
						"<a class='uploadify-img-queue-div-a' href='javascript:void(0)' onclick='$(\"#"+v_itemId+"\").delFileId(\""+fid+"\","+multi+");$(this).parent().remove();a_resize();'></a></div>");
			}
		}
		a_resize();
	};
	
	$.fn.delFileId = function(fid,multi) {
		var v_itemId = $(this).attr("id");
		var ids = $("#"+ v_itemId+"_hidden").val();
		if(ids == undefined || ids == '' || !multi){
			$("#"+ v_itemId+"_hidden").val("");
		}else{
			var array = ids.split(",");
			for ( var i = 0; i < array.length; i++) {
				if(array[i] == fid){
					array.splice(i,1);
					break;
				}
			}
			ids=array.join(",");
			$("#"+ v_itemId+"_hidden").val(ids);
		}
	};
	
	$.fn.getFileId = function() {
		var v_itemId = $(this).attr("id");
		var ids = $("#"+ v_itemId+"_hidden").val();
		if(ids == undefined || ids == ''){
			return '';
		}else{
			return ids;
		}
	};
	
	$.fn.replaceFileId = function(old,newId,multi,showImage,bestSize,v_itemid) {
		var v_itemId = $(this).attr("id");
		var ids = $("#"+ v_itemId+"_hidden").val();
		var bestSize = bestSize||'';
		if(ids == undefined || ids == ''){
			$("#"+ v_itemId+"_hidden").val("");
		}else{
			var array = ids.split(",");
			for ( var i = 0; i < array.length; i++) {
				if(array[i] == old){
					array[i]=newId;
					break;
				}
			}
			ids=array.join(",");
			$("#"+ v_itemId+"_hidden").val(ids);
		}
		$("#"+ v_itemId + "_img_queue").html("");//清空当前队列
		if(showImage && ids.length>0){
			var array = ids.split(",");
			for ( var i = 0; i < array.length; i++) {
				$("#"+ v_itemId + "_img_queue").append("<div class='uploadify-img-queue-div'>" + 
					po.showImg({id:array[i],width:80,height:80,parent:true,showBtn:true,bestSize:bestSize,uploadId:v_itemid}) +
					"<a class='uploadify-img-queue-div-a' href='javascript:void(0)' onclick='$(\"#"+v_itemId+"\").delFileId(\""+array[i]+"\","+multi+");$(this).parent().remove();'></a></div>");
		
			}
		}
	};
	
	sy.initFileUpload();
});

sy.initFileUpload = function(target){
	
	  //文件上传控件
	  $.each($("input[type=file]"),function(index,obj){
		  	if(target!=undefined) {
		  		if($(obj).attr("id") != target){
		  			return true;
		  		}
		  	}
		  
		  	 var v_init = ("false" != $(obj).attr('init'));
		  	 if(!v_init)return;
	  		 var method = $(obj).attr('onUploadSuccess');
	  		 var showImage = ("false" != $(obj).attr("showImage"));
	  		 var o = obj;
	  		 var v_itemId = $(obj).attr("id");
	  		 v_idQueue.push(v_itemId);
	  		 var v_multi = ("true" == $(obj).attr('multi'));
	  		 var showBtn=$(obj).attr("showBtn")||'false';
	  		 var bestSize=$(obj).attr("bestSize")||'';
	  		 if (showImage) {
	  		 	$("#" + v_itemId).before('<div id="' + v_itemId + '_img_queue" style="width:100%;height:100%;"></div><div style="clear:both;"></div>');
	  		 }
	  		 
	  		var v_required = ("required" == $(obj).attr('required'));
	  		var v_missingMessage = $(obj).attr("missingMessage")||'请上传文件';
	  		$("#" + v_itemId).after("<input id='" + v_itemId + "_hidden' type=\"hidden\" name=\""+v_itemId+"\" "+(v_required?"required":"")+" missingMessage=\""+v_missingMessage+"\"></input>");
	  		$("#" + v_itemId).after("<div id='" + v_itemId + "_queue'></div>");
	  		
	  		var v_fileType = $(obj).attr('fileType') || "";
	  		var v_fileSize = $(obj).attr('fileSize') || "";
	  		var v_fileCountLimit = $(obj).attr("fileCountLimit") || "";
	  		var v_formData = $(obj).attr("formData") || "{}";
	  		v_formData = $.parseJSON(v_formData);
	  		
	  		setTimeout(function(){
		  	 $(obj).uploadify({
		    	//开启调试
		        debug : false,
		      	//是否自动上传
		        auto:$(obj).attr('auto')||true,
		      	//超时时间
		        successTimeout:99999,
		      	//附带值
		        formData:v_formData,
		      	//flash
		        swf: projectPath+'/base/js/uploadify/uploadify.swf',
		        uploader: projectPath+'/uploadHtml.do',
		        buttonText:$(obj).attr('buttonText')|| "文件上传",
				itemTemplate : '<div id="${fileID}" class="uploadify-queue-item">\
                  <div class="cancel"><a id="${fileID}_cancelBtn" href="javascript:$(\'#${instanceID}\').uploadify(\'cancel\', \'${fileID}\')">X</a></div>\
                  <span class="fileName">${fileName} (${fileSize})</span><span class="data"></span></div>',
		        'multi': v_multi||false, // 是否支持多文件一起上传
		      	//文件选择后的容器ID
		        'queueID':v_itemId + '_queue',
		        'removeCompleted' : false,
		        //'fileObjName': 'filedata',
		      	//浏览按钮的宽度
		        //'width':'120',
		        //浏览按钮的高度
		        //'height':'30',
		      	//允许上传的文件后缀
		        'fileTypeExts':v_fileType||'*',
		        //上传文件的大小限制
		        'fileSizeLimit':v_fileSize||'1MB',
		        'onUploadStart':function(file){
		        	var ids = $("#"+ v_itemId+"_hidden").val();
		        	var file_cnt = 0;
		    		if(ids == undefined || ids == ''){
		    			file_cnt = 0;
		    		}else{
		    			var array = ids.split(",");
		    			file_cnt = array.length;
		    		}
		        	if (v_fileCountLimit != "" && parseInt(v_fileCountLimit) <= file_cnt) {
						alert("文件上传数量超出限制(" + v_fileCountLimit + "个)");
						this.cancelUpload(file.id);
						$("#" + v_itemId).uploadify("cancel",file.id);
						return false;
					}
		        },
		        //返回一个错误，选择文件的时候触发
		        /*'onSelectError':function(file, errorCode, errorMsg){
		        	switch(errorCode) {
			        	case -100:
		                    alert("每次最多上传"+this.settings.queueSizeLimit+"个文件！");
		                    break;
		                case -110:
		                    alert("文件 ["+file.name+"] 大小超出系统限制的"+this.settings.fileSizeLimit+"大小！");
		                    break;
		                case -120:
		                    alert("文件 ["+file.name+"] 大小为0！");
		                    break;
		                case -130:
		                    alert("文件 ["+file.name+"] 格式不正确！");
		                    break;
		                default:
		                	alert("错误代码：" + errorCode + "\n" + errorMsg);
		            }
		        },*/
		      	//检测FLASH失败调用
		        'onFallback':function(){
		            alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
		        },
		        //出错
		        'onUploadError':function(file, errorCode, errorMsg, errorString){
		        	switch (errorCode) {
			            case -200:
			            	alert("HTTP 错误\n" + errorMsg);
			                break;
			            case -210:
			            	alert("上传文件丢失，请重新上传");
			                break;
			            case -220:
			            	alert("IO错误");
			                break;
			            case -230:
			            	alert("安全性错误\n" + errorMsg);
			                break;
			            case -240:
			            	alert("每次最多上传 " + this.settings.uploadLimit + "个");
			                break;
			            case -250:
			            	alert(errorMsg);
			                break;
			            case -260:
			            	alert("找不到指定文件，请重新操作");
			                break;
			            case -270:
			            	alert("参数错误");
			                break;
			        }
		        },
		        //上传到服务器，服务器返回相应信息到data里
		        'onUploadSuccess':function(file, data, response){
		        	var ret = $.parseJSON(data);
		        	if (ret.error == 0) {
		        		$("#"+v_itemId).setFileId(ret.url,v_multi,showImage,showBtn,bestSize);
		        		// 去除队列信息
		        		if(showImage){
		        			$("#" + v_itemId).uploadify('cancel', file.id);
							$("#" + file.id).remove();
		        		}
						
		        		if(method!='' && window[method]){
			        		window[method].call(window, ret);
			        	}
		        	}else{	
		        		alert(ret.message);
		        	}
		        }
		    });
	  		},10);
	  });
}


sy.amap = function(callback,lat,lng) {
	var dialog = parent.sy.modalDialog({
		title : '地图',
		width:800,    
	    height:600,
		url : sy.contextPath + "/go.do?path=common/amap&lat="+lat+"&lng="+lng,
		buttons : [ {
			text : '确定',
			handler : function() {
				dialog.find('iframe').get(0).contentWindow.submitLocation(dialog, callback, parent.$);
			}
		} ]
	});
};

sy.bmap = function(callback,lat,lng) {
	var dialog = parent.sy.modalDialog({
		title : '地图',
		width:800,    
	    height:600,
		url : sy.contextPath + "/go.do?path=common/bmap&lat="+lat+"&lng="+lng,
		buttons : [ {
			text : '确定',
			handler : function() {
				dialog.find('iframe').get(0).contentWindow.submitLocation(dialog, callback, parent.$);
			}
		} ]
	});
};

function HtmlEditor(v_id, v_options) {
	// 对外参数
	this.options = {
		items : [
			        'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
			        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
			        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
			        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
			        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
			        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image','multiimage',
			         'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
			        'anchor', 'link', 'unlink', '|', 'about'
			]
	};

	$.extend(this.options, v_options);

	if (!KindEditor) {
		mini.alert("请先加载htmlEditor的JS文件!");
		return;
	}

	var v_loader = this;

	// 初始化
	this.options.editor = KindEditor.create('#' + v_id, {
				uploadJson : 'fileUpload.do',
				newlineTag : "br",
				filePostName:"Filedata",
				afterUpload : function(url) {
		                //alert(url);
		        },
				items : this.options.items,
				formatUploadUrl:true,
				fillDescAfterUploadImage:true
			});

	return this;
}

function easyHtmlEditor(v_id, v_options) {
	// 对外参数
	this.options = {
		items : [
			        'source', '|', 'undo', 'redo', '|', 'preview', 'print', 
			        '|', 'justifyleft', 'justifycenter', 'justifyright',
			        'justifyfull',  'selectall', '|', 'fullscreen', '/',
			        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
			        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'link', 'unlink', '|', 'about'
			]
	};

	$.extend(this.options, v_options);

	if (!KindEditor) {
		mini.alert("请先加载htmlEditor的JS文件!");
		return;
	}

	var v_loader = this;

	// 初始化
	this.options.editor = KindEditor.create('#' + v_id, {
				uploadJson : 'fileUpload.do',
				newlineTag : "br",
				filePostName:"Filedata",
				afterUpload : function(url) {
		                //alert(url);
		        },
				items : this.options.items,
				formatUploadUrl:true,
				fillDescAfterUploadImage:true,
				pasteType:1 //0:禁止粘贴, 1:纯文本粘贴, 2:HTML粘贴（默认）
			});

	return this;
}
easyHtmlEditor.prototype = {
		getHtml : function() {
			return this.options.editor.html();
		},
		setHtml : function(v_html) {
			if (v_html != null) {
				return this.options.editor.html(v_html);
			}
		}
	};

HtmlEditor.prototype = {
	getHtml : function() {
		return this.options.editor.html();
	},
	setHtml : function(v_html) {
		if (v_html != null) {
			return this.options.editor.html(v_html);
		}
	}
};