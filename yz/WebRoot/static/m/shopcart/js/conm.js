
$(function(){
	
	//功能展开
	$(".btn_show").click(function(){
		$(".news_func").toggle();
	});
	$(".news_func a").click(function(){
		$(".news_func").toggle();
	});
	//城市定位
	$(".cityNav a").click(function(){
		$(".cityNav a").removeClass('on'); 
		$(this).addClass('on');	
	});
	//关键词展开
	$(".tjTab a").click(function(){
		$(".drap").hide().eq($(".tjTab a").index(this)).show();
		$(".tjTab a").removeClass("on");
		$(this).addClass("on");
	});
	//分享框弹出关闭
	$(".btn_share").click(function(){
		$(".shareInfo").fadeIn(200);
	});	
	$(".shareClose").click(function(){
		$(".shareInfo").fadeOut(200);
	});
	//发送朋友
	$(".btn_send").click(function(){
		$(".sendInfo").fadeIn(200);
	});	
	$(".sendClose").click(function(){
		$(".sendInfo").fadeOut(200);
	});
	
	//收藏与取消
	$(".btn_coll2").click(function(){
		 var var1=$(this).attr("class");
		 var var2=var1.split(' ');
		 if(var2[1]=="on"){
			$(this).removeClass('on'); 
			$(this).addClass('cannel');			
			$(".collInfo").html("收藏已取消");
			$(".collInfo").fadeIn(200);
		 }else{
			$(this).removeClass('cannel'); 
			$(this).addClass('on'); 
			$(".collInfo").html("已收藏");
			$(".collInfo").fadeIn(200);
		 }
		setTimeout(function(){$(".collInfo").fadeOut(500);},2000);
	});
	
	//配送方式
	$(".psfs").click(function(){
		$(".psfsBox").fadeIn(200);
	});
	$(".psfsClose").click(function(){
		$(".psfsBox").fadeOut(200);
	});
	$(".psfsBox li").click(function(){
		$(".psfsBox li").removeClass("on");
		$(this).addClass("on");
		$(".psfsBox").fadeOut(200);
	});
	//配送时间
	$(".pssj").click(function(){
		$(".pssjBox").fadeIn(200);
	});
	$(".pssjClose").click(function(){
		$(".pssjBox").fadeOut(200);
	});
	$(".pssjBox li").click(function(){
		$(".pssjBox li").removeClass("on");
		$(this).addClass("on");
		$(".pssjBox").fadeOut(200);
	});
	
	//规格编辑
	$(".btn-gg").click(function(){
		$(".ggBox").fadeIn(200);
	});
	$(".ggClose").click(function(){
		$(".ggBox").fadeOut(200);
	});
});	
