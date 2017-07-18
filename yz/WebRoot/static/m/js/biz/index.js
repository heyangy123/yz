if(isPc()){
	//location.href = sy.path + "/web/";
}
$(function(){
	if(sy.areaCode != undefined){
		$("#cityName").html(sy.areaName);
		init();
		hasNews();
	}
});

//热门商品
var indexGoods = sy.getModule('indexGoods',['infinite-scroll']);
indexGoods.controller('IndexGoodsController', function($scope,$http) {
  $scope.list = [];
  $scope.busy = false;
  $scope.page = 1;
  $scope.limit = 10;
  $scope.hasMore = true;
  
  $scope.nextPage = function() {
	  if(!sy.areaCode) return;
	  if ($scope.busy || !$scope.hasMore || sy.areaCode == undefined) return;
	  $scope.busy = true;
      sy.openLoad();
      $http.post(sy.path + "/m/goods/goodsList",{
    	  		areaCode:sy.areaCode,type:1,page:$scope.page,limit:$scope.limit
      		})
			.success(function (data) {
				if(data.length < $scope.limit)$scope.hasMore = false;
				$.each(data,function(i,o){
					$scope.list.push(o);
				});
				$scope.busy = false;
				$scope.page += 1;
	            sy.closeLoad();
	        }).error(function(data, status) {
	        	 
	        	sy.closeLoad();
			});
  };
  
  $scope.toDetail = function(g){
	  location.href = sy.path + "/m/goods/"+g.id;
  }
});

//加载模块
function init(){
	if(!sy.areaCode) return;
	sy.openLoad();
	var url = sy.path + "/m/i/moduleList";
	$.post(url, {areaCode:sy.areaCode}, function(result) {
		sy.closeLoad();
		if(result){
			$.each(result,function(i,o){
				switch (o.type) {
				case 101://焦点图
					module101(sy.stringToJSON(o.data));
					break;
				case 102://分类图标
					module102(sy.stringToJSON(o.data));
					break;
				case 103://图片组合
					module103(o.style,sy.stringToJSON(o.data));
					break;
				case 104://标题
					$("#module").append("<div class=\"index_line\" style=\"padding:0;\"><span>"+o.data+"</span></div>");
					break;
				case 105://分割线
					$("#module").append("<div class=\"h20\"></div>");
					break;
				case 106://留白横条
					$("#module").append("<div class=\"h15\" style=\"background: #fff;\"></div>");
					break;
				case 107://限时抢购(唯一)
					module107(sy.stringToJSON(o.data));
					break;
				case 108://公告滚动栏(唯一)
					module108(sy.stringToJSON(o.data));
					break;
				default:
					break;
				}
			});
		}else{
			 
		}
	}, 'json');
}

//焦点图 101
function module101(obj){
	var num = Math.ceil(Math.random()*100000);
	var html = "";
	html += "<!-- 焦点图 101 -->";
	html += "<div class=\"jdt\">";
	html += "  <div id=\""+num+"\" class=\"slideBox\">";
	html += "    <div class=\"bd\">";
	html += "      <div class=\"tempWrap\" style=\"overflow:hidden; position:relative; width:100%;\">";
	html += "        <ul style=\"width:100%; position: relative; overflow: hidden; padding: 0px; margin: 0px; transition: 0ms; -webkit-transition: 0ms; -webkit-transform: translate(-640px, 0px) translateZ(0px);\">";
    $.each(obj,function(i,o){
    	html += "          <li style=\"float: left; width:100%;\"> <a class=\"pic\" href=\"javascript:;\" onclick=\"focusRedirect("+o.type+",'"+o.value+"')\"><img src=\""+sy.path+"/download?w=720&h=240&id="+o.img+"\"/></a> </li>";
    });
	html += "        </ul>";
	html += "      </div>";
	html += "    </div>";
	html += "    <div class=\"hd\">";
	html += "      <ul>";
	$.each(obj,function(i,o){
		html += "        <li class=\""+(i == 0?"on":"")+"\">"+(i+1)+"</li>";
	});
	html += "      </ul>";
	html += "    </div>";
	html += "  </div>";
	html += "</div>";
	$("#module").append(html);
	
	TouchSlide({ 
		slideCell:"#"+num,
		titCell:".hd ul",
		mainCell:".bd ul", 
		effect:"leftLoop", 
		autoPage:true,//自动分页
		autoPlay:true //自动播放
	});
}

//焦点图跳转
function focusRedirect(type,value){
	switch (type) {
	case 1:
		location.href = sy.path + "/www?url="+escape(value);
		break;
	case 2:
		location.href = sy.path +value;
		break;
	case 3:
		location.href = sy.path + '/m/goods/'+value;
		break;
	case 4:
		location.href = sy.path + '/m/store/'+value;
		break;
	case 5:
		location.href = sy.path + '/m/cateAct/'+value;
		break;
	case 6:
		//location.href = sy.path + '/m/vipActGoods/'+value;
		location.href = sy.path + '/m/indexAct/'+value;
		break;
	default:
		break;
	}
}

//分类图标 102
function module102(obj){
	var html = "";
	html += "<!-- 分类图标 102 -->";
	html += "<div class=\"index_row1\">";
	html += "<dl>";
	html += "	<dt><a href=\"javascript:;\" onclick=\"iconRedirect("+obj.type1+",'"+obj.value1+"')\"><img src=\""+sy.path+"/download?w=78&h=78&id="+obj.icon1+"\" /></a></dt>";
	html += "    <dd><a href=\"javascript:;\" onclick=\"iconRedirect("+obj.type1+",'"+obj.value1+"')\">"+obj.title1+"</a></dd>";
	html += "</dl>";
	html += "<dl>";
	html += "	<dt><a href=\"javascript:;\" onclick=\"iconRedirect("+obj.type2+",'"+obj.value2+"')\"><img src=\""+sy.path+"/download?w=78&h=78&id="+obj.icon2+"\" /></a></dt>";
	html += "    <dd><a href=\"javascript:;\" onclick=\"iconRedirect("+obj.type2+",'"+obj.value2+"')\">"+obj.title2+"</a></dd>";
	html += "</dl>";
	html += " <dl>";
	html += "	<dt><a href=\"javascript:;\" onclick=\"iconRedirect("+obj.type3+",'"+obj.value3+"')\"><img src=\""+sy.path+"/download?w=78&h=78&id="+obj.icon3+"\" /></a></dt>";
	html += "    <dd><a href=\"javascript:;\" onclick=\"iconRedirect("+obj.type3+",'"+obj.value3+"')\">"+obj.title3+"</a></dd>";
	html += "</dl>";
	html += "<dl>";
	html += "	<dt><a href=\"javascript:;\" onclick=\"iconRedirect("+obj.type4+",'"+obj.value4+"')\"><img src=\""+sy.path+"/download?w=78&h=78&id="+obj.icon4+"\" /></a></dt>";
	html += "    <dd><a href=\"javascript:;\" onclick=\"iconRedirect("+obj.type4+",'"+obj.value4+"')\">"+obj.title4+"</a></dd>";
	html += "</dl>";
	html += "<h6 class=\"clear\"></h6>";
	html += "</div>";
	$("#module").append(html);
}

//分类图标跳转
function iconRedirect(type,value){
	switch (type) {
	case 1:
		location.href = sy.path + "/www?url="+escape(value);
		break;
	case 2:
		location.href = sy.path + '/m/goods?cateCode='+value;
		break;
	case 3:
		location.href = sy.path + '/m/credit';
		break;
	case 4:
		location.href = sy.path + '/m/lottery';
		break;
	case 5:
		location.href = sy.path + '/m/vipActivity';
		break;
	case 6:
		location.href = sy.path + '/m/coupon';
		break;
	case 7:
		location.href = sy.path + '/m/goods/flzq';
		break;
	case 8:
		location.href = sy.path + '/m/storerecommend';
		break;
	default:
		break;
	}
}

//组合图片 103
function module103(style,obj){
	var html = "";
	switch (style) {
	case 10300://一张大图
		html += "<div class=\"index_row3\" style=\"border-bottom: 1px solid #d9d9d9;border-top: 1px solid #d9d9d9;\">";
		html += "	<a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type1+",'"+obj.value1+"','"+obj.title1+"')\"><img src=\""+sy.path+"/download?w=640&h=160&id="+obj.icon1+"\" /></a>";
		html += "</div>";
		break;
	case 10301://左一右一
		html += "<div class=\"index_row15\">";
		html += "	<dl style=\"border-bottom: 1px solid #d9d9d9;\">";
		html += "    	<dt><a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type1+",'"+obj.value1+"','"+obj.title1+"')\"><img src=\""+sy.path+"/download?w=320&h=320&id="+obj.icon1+"\" /></a></dt>";
		html += "        <dd><a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type2+",'"+obj.value2+"','"+obj.title2+"')\"><img src=\""+sy.path+"/download?w=320&h=320&id="+obj.icon2+"\" /></a></dd>";
		html += "        <h6 class=\"clear\"></h6>";
		html += "    </dl>";
		html += "</div>";
		break;
	case 10302://左二右一
		html += "<div class=\"index_row2\">";
		html += "    <div class=\"index_row2_l\">";
		html += "		<dl>";
		html += "        	<dt style=\"border-bottom: 1px solid #d9d9d9;\">";
		html += "            	<a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type1+",'"+obj.value1+"','"+obj.title1+"')\"><img src=\""+sy.path+"/download?w=320&h=160&id="+obj.icon1+"\" /></a>";
		html += "            </dt>";
		html += "            <dd><a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type2+",'"+obj.value2+"','"+obj.title2+"')\"><img src=\""+sy.path+"/download?w=320&h=160&id="+obj.icon2+"\" /></a></dd>";
		html += "        </dl>";
		html += "    </div>";
		html += "    <div class=\"index_row2_r\">";
		html += "    	<a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type3+",'"+obj.value3+"','"+obj.title3+"')\"><img src=\""+sy.path+"/download?w=320&h=320&id="+obj.icon3+"\" /></a>";
		html += "    </div>";
		html += "    <div class=\"clear\"></div>";
		html += "</div>";
		break;
	case 10303://左一右二
		html += "<div class=\"index_gg\">";
		html += "   	 <dl>";
		html += "      	<dt><a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type1+",'"+obj.value1+"','"+obj.title1+"')\"><img src=\""+sy.path+"/download?w=320&h=320&id="+obj.icon1+"\" /></a></dt>";
		html += "          <dd>";
		html += "          	<a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type2+",'"+obj.value2+"','"+obj.title2+"')\"><img src=\""+sy.path+"/download?w=320&h=160&id="+obj.icon2+"\" /></a>";
		html += "          	<a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type3+",'"+obj.value3+"','"+obj.title3+"')\"><img src=\""+sy.path+"/download?w=320&h=160&id="+obj.icon3+"\" /></a>";
		html += "          </dd>";
		html += "         <h6 class=\"clear\"></h6>";
		html += "     </dl>";
		html += "</div>";
		break;
	case 10304://上一下二
		html += "<div class=\"index_row14\">";
		html += "    <dl>";
		html += "    	<dt><a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type1+",'"+obj.value1+"','"+obj.title1+"')\"><img src=\""+sy.path+"/download?w=640&h=160&id="+obj.icon1+"\" /></a></dt>";
		html += "        <dd><a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type2+",'"+obj.value2+"','"+obj.title2+"')\"><img src=\""+sy.path+"/download?w=320&h=160&id="+obj.icon2+"\" /></a></dd>";
		html += "        <dd><a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type3+",'"+obj.value3+"','"+obj.title3+"')\"><img src=\""+sy.path+"/download?w=320&h=160&id="+obj.icon3+"\" /></a></dd>";
		html += "    </dl>";
		html += "    <h6 class=\"clear\"></h6>";
		html += "</div>";
		break;
	case 10305://左一右上一右下二
		html += "<div class=\"index_row11\">";
		html += "	<dl>";
		html += "    	<a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type1+",'"+obj.value1+"','"+obj.title1+"')\"><img src=\""+sy.path+"/download?w=320&h=320&id="+obj.icon1+"\" /></a>";
		html += "    </dl>";
		html += "    <dl>";
		html += "   	<dt><a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type2+",'"+obj.value2+"','"+obj.title2+"')\"><img src=\""+sy.path+"/download?w=320&h=160&id="+obj.icon2+"\" /></a></dt>";
		html += "       <dd><a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type3+",'"+obj.value3+"','"+obj.title3+"')\"><img src=\""+sy.path+"/download?w=160&h=160&id="+obj.icon3+"\" /></a></dd>";
		html += "       <dd><a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type4+",'"+obj.value4+"','"+obj.title4+"')\"><img src=\""+sy.path+"/download?w=160&h=160&id="+obj.icon4+"\" /></a></dd>";
		html += "   </dl>";
		html += "    <h6 class=\"clear\"></h6>";
		html += "</div>";
		break;
	case 10306://下四
		html += "<div class=\"index_row12\">";
		html += "	<dl>";
		html += "    	<dd><a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type1+",'"+obj.value1+"','"+obj.title1+"')\"><img src=\""+sy.path+"/download?w=160&h=160&id="+obj.icon1+"\" /></dd>";
		html += "        <dd><a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type2+",'"+obj.value2+"','"+obj.title2+"')\"><img src=\""+sy.path+"/download?w=160&h=160&id="+obj.icon2+"\" /></dd>";
		html += "        <dd><a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type3+",'"+obj.value3+"','"+obj.title3+"')\"><img src=\""+sy.path+"/download?w=160&h=160&id="+obj.icon3+"\" /></a></dd>";
		html += "        <dd><a href=\"javascript:;\" onclick=\"imgRedirect("+obj.type4+",'"+obj.value4+"','"+obj.title4+"')\"><img src=\""+sy.path+"/download?w=160&h=160&id="+obj.icon4+"\" /></a></dd>";
		html += "        <h6 class=\"clear\"></h6>";
		html += "    </dl>";
		html += "</div>";
		break;
	default:
		break;
	}
	$("#module").append(html);
}

//组合图片跳转
function imgRedirect(type,value,id){
	switch (type) {
	case 1:
		location.href = sy.path + "/www?url="+escape(value);
		break;
	case 2:
		location.href = sy.path + '/m/goods?cateCode='+value;
		break;
	case 3:
		location.href = sy.path + '/m/credit';
		break;
	case 4:
		location.href = sy.path + '/m/lottery';
		break;
	case 5:
		location.href = sy.path + '/m/vipActivity';
		break;
	case 6:
		location.href = sy.path + '/m/coupon';
		break;
	case 7:
		location.href = sy.path + '/m/flzq';
		break;
	case 8:
		location.href = sy.path + '/m/storerecommend';
		break;
	case 9:
		location.href = sy.path + '/m/vipActGoods/'+id;
		break;
	case 10:
		location.href = sy.path + '/m/cateAct/'+id;
		break;
	case 11:
		location.href = sy.path + '/m/cateAct';
		break;
	default:
		break;
	}
}


//限时抢购(唯一) 107
function module107(obj){
	var url = sy.path + "/m/i/quickShopping";
	$("#module").append("<!-- 限时抢购(唯一) 107--><div class=\"index_row13\" id=\"quick\"></div>");
	sy.openLoad();
	$.post(url, {areaCode:sy.areaCode}, function(result) {
		sy.closeLoad();
		if(result){
			if(result.seconds){
				seconds = result.seconds;
				minutes = result.minutes;
				
				$("#quick").on("click",function(){
					location.href = sy.path + "/m/qianggou";
				});
				
				var html = "";
				html += "  <div class=\"index_row13_title\">";
				html += "    <h2>限时抢购</h2>";
				html += "    <div class=\"seckill-timer\">";
				html += "    	<span class=\"seckill-time\">0</span> ";
				html += "    	<span class=\"seckill-time\">0</span> ";
				html += "    	<span class=\"seckill-time-separator\">:</span> ";
				html += "    	<span class=\"seckill-time\">0</span> ";
				html += "    	<span class=\"seckill-time\">0</span> ";
				html += "    	<span class=\"seckill-time-separator\">:</span> ";
				html += "    	<span class=\"seckill-time\">0</span> ";
				html += "    	<span class=\"seckill-time\">0</span> ";
				html += "   </div>";
				html += "    <h6 class=\"clear\"></h6>";
			    html += "  </div>";
				html += "  <div class=\"index_row3_pro\">";
				
				$.each(result.imgs,function(i,o){
					html += "  	<dl>";
					html += "  		<dt><img src=\""+sy.path+"/download?id="+o+"\" /></dt>";
					html += "    </dl>";
				});

				html += " 	<h6 class=\"clear\"></h6>";
				html += " </div>";
				$(".index_row13").html(html);
				
				setInterval("timer()", 1000);
			}else{
				var html = "";
				html += "  <div class=\"index_row13_title\">";
				html += "    <h2>限时抢购</h2>";
				html += "    <div class=\"seckill-timer\">";
				html += "		<span class=\"seckill-title\">暂无抢购</span>";
				html += "    	<span class=\"seckill-time\">0</span> ";
				html += "    	<span class=\"seckill-time\">0</span> ";
				html += "    	<span class=\"seckill-time-separator\">:</span> ";
				html += "    	<span class=\"seckill-time\">0</span> ";
				html += "    	<span class=\"seckill-time\">0</span> ";
				html += "    	<span class=\"seckill-time-separator\">:</span> ";
				html += "    	<span class=\"seckill-time\">0</span> ";
				html += "    	<span class=\"seckill-time\">0</span> ";
				html += "   </div>";
				html += "    <h6 class=\"clear\"></h6>";
			    html += "  </div>";
				$(".index_row13").html(html);
			}
		}else{
			 
		}
	}, 'json');
}

var seconds = 0;
var minutes = 0;
function timer(){
	var time = 0;
	--seconds;
	if(seconds > 0){//未开始
		time = formatTime(seconds);
	}else{
		var s = minutes*60+seconds;
		time = formatTime(s);
	}
	
	var html = "";
	html += "		<span class=\"seckill-title\">"+(seconds>0?"距本场开始":"距本场结束")+"</span>";
	html += "    	<span class=\"seckill-time\">"+ time.substring(0,1) +"</span> ";
	html += "    	<span class=\"seckill-time\">"+ time.substring(1,2) +"</span> ";
	html += "    	<span class=\"seckill-time-separator\">:</span> ";
	html += "    	<span class=\"seckill-time\">"+ time.substring(3,4) +"</span> ";
	html += "    	<span class=\"seckill-time\">"+ time.substring(4,5) +"</span> ";
	html += "    	<span class=\"seckill-time-separator\">:</span> ";
	html += "    	<span class=\"seckill-time\">"+ time.substring(6,7) +"</span> ";
	html += "    	<span class=\"seckill-time\">"+ time.substring(7,8) +"</span> ";
	$(".seckill-timer").html(html);
}

function formatTime(s){
    // 计算
    var h=0,i=0;
    if(s>60){
        i=parseInt(s/60);
        s=parseInt(s%60);
        if(i > 60) {
            h=parseInt(i/60);
            i = parseInt(i%60);
        }
    }
    // 补零
    var zero=function(v){
        return (v>>0)<10?"0"+v:v;
    };
    return [zero(h),zero(i),zero(s)].join(":");
};

//公告滚动栏(唯一) 108
function module108(obj){
	var html = "";
	html += "<!-- 公告滚动栏(唯一) 108-->";
	html += "<div class=\"index_row1_gg\" style=\"border-bottom: 1px solid #f1f1f1;border-top: 1px solid #f1f1f1;\" onclick=\"location.href='"+sy.path+"/m/notify#2'\"><h2></h2><ul id=\"twitter\">";
	$.each(obj,function(i,o){
		html += "<li>"+o.title+"</li>";
	});
	html += "</ul></div>";
	$("#module").append(html);
	
	$("#twitter li:not(:first)").css("display", "none");
	var B = $("#twitter li:last");
	var C = $("#twitter li:first");
	setInterval(function() {
		if (B.is(":visible")) {
			C.fadeIn(500).addClass("in");
			B.hide();
		} else {
			$("#twitter li:visible").addClass("in");
			$("#twitter li.in").next().fadeIn(500);
			$("li.in").hide().removeClass("in");
		}
	}, 3000); // 每3秒钟切换一条，你可以根据需要更改
}

function hasNews(){
	$.post(sy.path + "/m/i/hasNews", {}, function(result) {
    	if (result.code == 0) {
		}else{
			$("#hasNews").show();
		}
	}, 'json');
}