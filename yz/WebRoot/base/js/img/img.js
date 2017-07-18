var po;
if (!po){
	po = {};
}

var geturl = function(){
	var pathName=window.document.location.pathname; 
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1); 
	return 'http://'+ window.document.location.host + projectName + '/';
};

var url = geturl();
(function($){ 
	$.fn.imgCenter = function(){ 
		var img = this.children('#big_img');
		var con = $(this);
		var btn = con.parent().children('#jcrop_btn');
		img.one('load', function() {
			var height = img.height();
			
			var width = img.width();
			var maxWidth = $(window.parent).width() - 200;
			var maxHeight = $(window.parent).height()-100;
			if(width > maxWidth){
				 var ratio = maxWidth / width; 
		         width = maxWidth;
		         height = height * ratio;
		     }  
		    
		     if(height > maxHeight){  
		         var ratio = maxHeight / height; 
		         width = width * ratio;
		         height = maxHeight;
		     } 
		     img.css("width", width); 
			var top = ($(window.parent).height())/2 - height/2; 
			var left = ($(window.parent).width() - width)/2; 
			var scollTop = ($(window.parent.document).height()-$(window.parent).height())/2;
			var scollWid = ($(window.parent.document).width()-$(window.parent).width())/2;
			con.css( {'top' : top+ scollTop + 'px', 'left' : left + scollWid +'px'} ); 
			if(btn)
				btn.css({'top':top+ scollTop + height + 30 +'px', 'left' : left + scollWid +'px','position':'absolute','z-index':'2147483647'});
		}).each(function() {
		  if(this.complete) $(this).load();
		});
		
	};
	$.fn.imgGlass = function(){
		$(document).on('click','.simple-img',function(){
			var c = $(this).attr('onclick');
			if (c == 'po.clickImg(this)') return;
			po.clickImg(this);
		});
	}
})(jQuery);

//格式化图片
po.showImg = function(option){
	if (arguments.length == 3){
		option = {
			id:arguments[0],
			width:arguments[1],
			height:arguments[2]
		};
	}else if (arguments.length == 2){
		option = {
			id:arguments[0],
			width:arguments[1]
		};
	}else if (arguments.length == 1 && typeof option == 'string'){
		option = {
			id:option
		};
	}

	if (typeof option == 'object'){
		var _option = {id:'',width:'',height:'',parent:false,upload:false,showBtn:false,bestSize:''};
		option = $.extend(_option,option);
		return initImg(option);
	}
	
	function initImg(option){
		if (!option.id) return '';
		var str = '';
		var img = option.id.split(',');
		for (var i = 0; i < img.length; i++){
			if (img[i].search("\.(([j,J][p,P][g,G])|([p,P][n.N][g,G])|([g,G][i,I][f,F]))|([j,J][p,P][e,E][g,G])") != -1){
				str += createImg(option);
			}else{
				option.id = 'download.do?id='+ img[i];
				if (!option.upload)
					str += createImgs(option);
				else
					str += createImgWith(option);
			}
			str += " ";
		}
		return str;
	}
	
	function createImg(option){
		return "<img class='simple-img' onclick='po.clickImg(this)' src='"+option.id+"' width="+option.width+" height="+option.height+" parent='"+option.parent+"' showbtn='"+option.showBtn+"' bestSize='"+option.bestSize+"'  uploadId='"+option.uploadId+"'  />";
	}
	
	function createImgs(option){
		return "<img class='simple-img' onclick='po.clickImg(this)' src='"+option.id+"&w="+option.width+"&h="+option.height+"' width="+option.width+" height="+option.height+" parent='"+option.parent+"' showbtn='"+option.showBtn+"' uploadId='"+option.uploadId+"'   bestSize='"+option.bestSize+"' />";
	}
	
	function createImgWith(option){
		return "<div class='uploadify-img-queue-div'>" + createImgs(option) +
		"<a class='uploadify-img-queue-div-a' href='javascript:void(0)' onclick='$(this).parent().parent().delFileId(\""+option.id+"\""+");$(this).parent().remove();'></a></div>"
	}
};

po.clickImg = function(img){
	var small = $(img);
	var width = small.css('width');
	var height = small.css('height');
	var next = '';
	var parent = small.attr('parent');
	var showBtn = eval(small.attr('showbtn'));
	var bestSize = small.attr('bestSize')||'';
	var v_uploadId=small.attr('uploadId')||'';
	var isrc = small.attr('src');
	if (!isrc){return;}
	//var reg = new RegExp(/(download\.do\?id=).+(&w=.+)*(&h=.+)*/);
	var wreg = new RegExp(/w=\d+/);
	var hreg = new RegExp(/h=\d+/);
	var idreg = new RegExp(/id=\w+/);
	var bigsrc = isrc.match(idreg)!=null?'download.do?'+isrc.match(idreg):isrc;
	var cancleBtn;
	var confirmBtn;
	/*if (isrc.match(wreg) != null){
		width = isrc.match(wreg).toString().substr(2);
	}
	if (isrc.match(hreg)!= null){
		height = isrc.match(hreg).toString().substr(2);
	}*/
	bigsrc = url + bigsrc;
	if(showBtn){
		$(window.top.document.body).append("<div id='temp_contain'></div><div id='big_div_img'><div id='big_div_close'><div></div></div></div><div id='jcrop_btn' style='z-index:2147483647' ></div>");	
	}else{
		$(window.top.document.body).append("<div id='temp_contain'></div><div id='big_div_img'><div id='big_div_close'><div></div></div></div>");
	}
	
	var imgCon = find('#big_div_img');
	var contain = find('#temp_contain');
	contain.css('height',$(window.parent.document).height());
	contain.css('width',$(window.parent.document).width());
	contain.css('z-index',2147483647);
	imgCon.css('z-index',2147483647);
	if(showBtn)
		imgCon.append("<img id='big_img' class='shadow'/><div id='big_size' style='margin-top:5px;' ></div>");
	else
		imgCon.append("<img id='big_img' class='shadow'/>");
	var jcropBtn = find('#jcrop_btn');
	function parse_url(_url){ 
		var pattern = /(\w+)=(\w+)/ig;
		var parames = {}; 
		_url.replace(pattern, function(a, b, c){parames[b] = c;});
		return parames;
	}
	function jcrop_cancle(){
		var jcropBtn = find('#jcrop_btn');
		jcropBtn.remove();
	}
	if(showBtn){
		jcropBtn.append("<a id='confirm-btn' class='cropbtn' style='text-decoration: none;' href='#' >确定裁剪</a>                 <a id='cancle-btn' href='#' class='cropbtn' style='text-decoration: none;' onclick='jcrop_cancle();'>取消裁剪</a>");
		cancleBtn=find('#cancle-btn');
		confirmBtn=find('#confirm-btn');
		cancleBtn.click(function(){
			window.top.$('#big_size').html('');
			clossAll();
		});
		confirmBtn.click(function(){
			if(!img.cutW||!img.cutH){
				alert('请先选择裁剪区域');
				return;
			}
			var src = window.top.$('#big_img').attr("src");
			var para=parse_url(src);
			img.id = parse_url(src)['id']||src;
			 $.ajax({
		        	url:sy.contextPath+"/replaceImg",
		        	type:'post',
		        	data:{imgId:img.id,startX:img.startX,startY:img.startY,width:img.cutW,height:img.cutH,currentW:img.imgW,currentH:img.imgH},
		        	dataType:'json',
		        	success:function(result){
		        		if(result.code==1){
		        			window.top.$('#big_size').html('');
		        			clossAll();
		        			var up=$('#'+v_uploadId);
		        			$('#'+v_uploadId).replaceFileId(img.id,result.msg,true,true,bestSize,v_uploadId);
		        		}else{
		        			alert("裁剪失败,文件格式错误！");
		        		}
		        	}
		        });
			
		});
	}
	var imgClose = find('#big_div_close');
	var big = find('#big_img');
	show();
	big.attr('src',bigsrc);
	smallHide();
	imgCon.imgCenter();
	var jcrop ;
	var img =img||{};
	if(showBtn){
		var maxWidth = $(window.top.parent).width() - 200;
		var maxHeight = $(window.top.parent).height()-100;
		var showCoords = function(o){
			img.imgW=window.top.$('#big_img').width();
			img.imgH=window.top.$('#big_img').height();
			//$('#width').val(o.w);//x起始点x坐标  x2结束点 y起始点  y2结束点 w选中框宽 h高
			//$('#height').val(o.h);
			img.cutW=o.w;
			img.cutH=o.h;
			img.startX=o.x;
			img.startY=o.y;
			if(o.w>0||o.h>0){
				//find('#big_size').css({position:'absolute',left:maxWidth/2+15,top:maxHeight/2-img.imgH/2,color:'red'});
				if(bestSize!='')
					find('#big_size').html(o.w+"*"+o.h+"  "+"最佳尺寸:"+bestSize);
				else
					find('#big_size').html(o.w+"*"+o.h);
			}
		};
		var destoryCoords = function(){
			window.top.$('#big_size').html('');
		};
		window.top.$('#big_img').Jcrop({
	        onChange: showCoords,
	        onSelect: showCoords,
	        onRelease:destoryCoords
		});
		return ;
	}
	big.click(function(){
		close();
		if (parent == 'true')
			next = small.parent().next().first().children('img').first();
		else
			next = small.next('img').first();
		
		if (next.length != 0){
			return po.clickImg(next);
		}else{
			clossAll();

		}
	});
	
	contain.click(function(){
		clossAll();
	});
	find('#big_div_close>div').click(function(){
		 close();
		 containclose();
	});
	function clossAll(){
		jcropBtn.remove();
		if(showBtn){
			cancleBtn.remove();
			confirmBtn.remove();	
		}
		close();
		containclose();
	}
	function find(selector){
		return $(window.top.parent.document).find(selector);
	}
	function close(){
		imgCon.fadeToggle();
		small.attr('src',isrc);
		small.css('width',width);
		small.css('height',height);
		imgCon.remove();
	}
	function containclose(){
		contain.fadeToggle('slow');
		contain.remove();
		
	}
	
	function show(){
		contain.fadeIn('fast');
		imgCon.fadeIn(500);
	}
	function smallHide(){
		small.attr('src','');
		small.removeAttr('width');
		small.removeAttr('height');
		small.css('width',width);
		small.css('height',height);
	}
	
	function jcrop(){
		
	}
};

$(function(){
	$('.simple-img').imgGlass();
});