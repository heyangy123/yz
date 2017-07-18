var factory = sy.getModule('fxGoodsModule',['infinite-scroll']);
	// 创建后台数据交互工厂（分页）
	factory.factory('fxGoods', function ($http) {
		var storeId = sessionStorage.storeId || "-1";
	    var Demo = function () {
	        this.items = [];
	        this.busy = false;
	        this.after = '';
	        this.page = 1;
	        this.limit=10;
	        this.isend=false;
	    };
	    
	    Demo.prototype.nextPage = function () {
	        if (this.busy) return;
	        if(this.isend) return;
	        this.busy = true;
	        sy.openLoad();
	        $http.post(sy.path+'/m/store/storeGoods',{
				page:this.page,limit:this.limit,storeId:storeId,sortType:2
			}).success(function (data) {
				if(String(data).indexOf('sessionOut')!=-1){
					location.href=sy.path+"/m/login";
					return;
				}
	            var items = data;
	            for (var i = 0; i < items.length; i++) {
	            	items[i].isCheck=0;
	                this.items.push(items[i]);
	            }
	            if(items.length<this.limit) this.isend=true;
	            this.busy = false;
	            this.page += 1;
	            sy.closeLoad();
	        }.bind(this)).error(function(data, status) {
	        	sy.closeLoad();
			});
	    };
	    return Demo;
	});
	
	// 申明页面控制类
	factory.controller('fxGoodsController',['$http','$scope','fxGoods',
	   	function ($http,$scope, fxGoods) {
			 $scope.weixin = {};
		     $scope.storeId = sessionStorage.storeId;
		     $scope.storeName = sessionStorage.storeName;
		     $scope.logo = sessionStorage.logo;
		     
		     $scope.fxGoods = new fxGoods();
		     $scope.goDetail = function(id){
		   	  	location.href = sy.path + "/m/store/"+id;
		     };
		     var ids = [];
		     $scope.setCheck = function(id) {
		 			$.each($scope.fxGoods.items,function(i){
		 				if(id==this.id){
		 					if(this.isCheck==0){
		 						$scope.fxGoods.items[i].isCheck=1;
		 						ids.push(this.id);
			 				}else{
			 					$scope.fxGoods.items[i].isCheck=0;
			 					var temp = [];
			 					for(var j = 0 ; j < ids.length ; j++){
			 						if(ids[j]!=this.id){
			 							temp.push(this.id);
			 						}
			 					}
			 					ids=temp;
			 				}
		 				}
		 			});
		 	};
		 	
		 	$scope.share = function(){
		 		if(ids.length > 0){
		 			sy.openLoad();
		 			$http.post(sy.path + "/m/share/shareGoods", {
		 				ids:ids.join(",")
		 			}).success(function(data) {
		 				sy.closeLoad();
		 				if(data.code == 0){
		 					$scope.url = data.msg;
		 					if(isWeixin()){
		 						initWxEvent($scope.weixin,data.msg);
		 						sy.alert("生成成功，请点击微信右上角按钮分享");
		 				    }else{
		 				    	$scope.link = data.msg;
		 				    	$("#shareWin").show();
		 				    }
		 				}
		 			}).error(function(data, status) {
		 				sy.closeLoad();
		 			});
		 		}
		 	}
		     
		    shareSuccess = function(){
		    	$http.post(sy.path + "/m/share/success",{
		    	})
				.success(function (data) {
					
		        }).error(function(data, status) {
				});
		    }
			    
		    initWxEvent = function(data,link){
		    	var desc = $scope.storeName;
		    	var imgUrl = sy.path + "/download?id="+$scope.logo;
		    	var title = $scope.storeName;
		    	if(link.indexOf("list") == -1){
		    		var goods;
			    	$.each($scope.fxGoods.items,function(i,o){
			    		if(o.id == ids[0]){
			    			goods = o;
			    		}
			    	});
		    		imgUrl = sy.path + "/download?id="+goods.logo;
			    	title = goods.title;
		    	}
		    	
		    	//监听“分享给朋友”，按钮点击、自定义分享内容及分享结果接口
		    	wx.onMenuShareAppMessage({
		    	      title: title,
		    	      desc: desc,
		    	      link: link,
		    	      imgUrl: imgUrl,
		    	      success: function (res) {
		    	        if(data.u != ''){
		    	        	shareSuccess();
		    	        }
		    	      }
		    	    });
		    	
		    	  // 监听“分享到朋友圈”按钮点击、自定义分享内容及分享结果接口
	    	    wx.onMenuShareTimeline({
	    	      title: title,
	    	      link: link,
	    	      imgUrl:imgUrl,
	    	      success: function (res) {
	    	    	  if(data.u != ''){
		    	        	shareSuccess();
		    	        }
	    	      }
	    	    });
	    	    
	    	    //监听“分享到QQ”按钮点击、自定义分享内容及分享结果接口
	    	    wx.onMenuShareQQ({
	    	        title: title,
	    	        desc: desc,
	    	        link: link,
	    	        imgUrl: imgUrl,
	    	        success: function (res) {
	    	        	if(data.u != ''){
		    	        	shareSuccess();
		    	        }
	    	        }
	    	      });
	    	    
	    	 // 监听“分享到微博”按钮点击、自定义分享内容及分享结果接口
	    	    wx.onMenuShareWeibo({
	    	    	title: title,
	    	        desc: desc,
	    	        link: link,
	    	        imgUrl: imgUrl,
	    	        success: function (res) {
	    	        	if(data.u != ''){
		    	        	shareSuccess();
		    	        }
	    	        }
	    	      });
		    } 
		    
		    initWeixinJsApi = function(){
		    	$http.post(sy.path + "/m/share/initWeixinApi",{
		    		url:location.href
		    	})
				.success(function (data) {
					if(data.errcode == 0){
						 wx.config({
				    	      debug: false,
				    	      appId: data.appId,
				    	      timestamp: data.timestamp,
				    	      nonceStr: data.noncestr,
				    	      signature: data.signature,
				    	      jsApiList: [
				    	        'onMenuShareTimeline',
				    	        'onMenuShareAppMessage',
				    	        'onMenuShareQQ',
				    	        'onMenuShareWeibo'
				    	      ]
				    	  });
						 $("#shareBtn").html("生成分享链接");
						 $scope.weixin = data;
					 }
		        }).error(function(data, status) {
				});
		    }
		    
		    if(isWeixin()){
		    	 initWeixinJsApi();
		     }
		    
		    $scope.shareTo = function(type){
		    	var link = $scope.link;
		    	var summary = $scope.storeName;
		    	var site = sy.path + '/m/';
		    	var imgUrl = sy.path + "/download?id="+$scope.logo;
		    	var title = "我发现这个店铺<"+$scope.storeName+">的很多宝贝都很棒,快去看看吧~";
		    	if(link.indexOf("list") == -1){
		    		var goods;
			    	$.each($scope.fxGoods.items,function(i,o){
			    		if(o.id == ids[0]){
			    			goods = o;
			    		}
			    	});
		    		imgUrl = sy.path + "/download?id="+goods.logo;
			    	title = "我发现了一个宝贝<"+goods.title +">很不错,快去看看吧~";
		    	}
		    	
		    	if(type == 1){
		    		shareTSina(title,link,imgUrl);
		    	}else if(type == 2){
		    		shareTqq(title,link,imgUrl);
		    	}else if(type == 3){
		    		shareQzone(title,link,summary,site,imgUrl);
		    	}else{
		    		copyCode(link);
		    	}
		    }
		    
		    shareTSina = function(title,rLink,pic) {  
		    	location.href = "http://service.weibo.com/share/share.php?pic=" +encodeURIComponent(pic) +"&title=" +   
			    encodeURIComponent(title.replace(/&nbsp;/g, " ").replace(/<br \/>/g, " "))+ "&url=" + encodeURIComponent(rLink);
			}
		    
		    shareTqq = function(title,rLink,pic) {
		    	var _u = "http://v.t.qq.com/share/share.php??c=share&a=index&pic=" +encodeURIComponent(pic) +"&title=" +   
			    encodeURIComponent(title.replace(/&nbsp;/g, " ").replace(/<br \/>/g, " "))+ "&url=" + encodeURIComponent(rLink);
		    	window.open( _u,'转播到腾讯微博', 'toolbar=no, menubar=no, scrollbars=no, location=yes, resizable=no, status=no' );
			}
		    
		    shareQzone = function(title,rLink,summary,site,pic) {  
		          window.open('http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?title='+  
		                           encodeURIComponent(title)+'&url='+encodeURIComponent(rLink)+'&summary='+  
		                           encodeURIComponent(summary)+ '&site='+encodeURIComponent(site)  
		                           ,'分享到QQ空间','scrollbars=no,status=no,resizable=yes');  
		        
		         }
		    
		    copyCode = function(copyText) {
		    	$("#shareWin").hide();
		    	$("#copyValue").html(copyText);
		    	$("#copy").show();
			}
		}
	]);