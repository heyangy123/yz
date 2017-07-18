var factory = sy.getModule('storeDetail',['infinite-scroll']);
	// 创建后台数据交互工厂（分页）
	factory.factory('store', function ($http) {
	    var Demo = function () {
	        this.items = {};
	    };
	        
        Demo.prototype.query = function (id) {
        	this.items={};
            sy.openLoad();
	        $http.post(sy.path+'/m/store/findById',{
				id:id
			}).success(function (data) {
				sy.closeLoad();
				if(data.code == 0){
					this.items = data.data;
		            if(data.data.qq) $('#info').after("<a href='mqqwpa://im/chat?chat_type=wpa&uin="+data.data.qq+"&version=1&src_type=web&web_src=oicqzone.com'><span>联系商家</span></a>");
		            else $('#info').after("<a href='mqqwpa://im/chat?chat_type=wpa&uin=&version=1&src_type=web&web_src=oicqzone.com'><span>联系商家</span></a>");
				}else{
					alert(data.msg);
					location.href = sy.path + '/m/';
				}
	        }.bind(this)).error(function(data, status) {
	        	sy.closeLoad();
			});
        };
	    return Demo;
	});
	
	factory.factory('coupons', function ($http) {
	    var Demo = function () {
	        this.items = {};
	    };
	        
        Demo.prototype.query = function (id) {
        	this.items={};
            sy.openLoad();
	        $http.post(sy.path+'/m/store/couponList',{
				storeId:id
			}).success(function (data) {
	            this.items = data;
	            sy.closeLoad();
	        }.bind(this)).error(function(data, status) {
	        	sy.closeLoad();
			});
        };
	    return Demo;
	});
	
	factory.factory('goods', function ($http) {
		var Demo = function () {
	        this.items = [];
	        this.busy = false;
	        this.after = '';
	        this.page = 1;
	        this.limit=10;
	        this.isend=false;
	    };
	    
	    Demo.prototype.nextPage = function () {
	    	var id= $('#storeId').val();
	        if (this.busy) return;
	        if(this.isend) return;
	        this.busy = true;
	        sy.openLoad();
	        $http.post(sy.path+'/m/store/storeGoods',{
				page:this.page,limit:this.limit,sortOrder:2,storeId:id
			}).success(function (data) {
	            var items = data;
	            for (var i = 0; i < items.length; i++) {
	                this.items.push(items[i]);
	            }
	            if(items.length<this.limit) this.isend=true;
	            this.busy = false;
	            if(items.length==this.limit) this.page += 1;
	            sy.closeLoad();
	        }.bind(this)).error(function(data, status) {
	        	sy.closeLoad();
			});
	    }; 
	       
	    return Demo;
	});
	
	factory.factory('recommend', function ($http) {
	    var Demo = function () {
	        this.items = {};
	    };
	        
        Demo.prototype.query = function (id) {
        	this.items={};
            sy.openLoad();
	        $http.post(sy.path+'/m/store/storeRecommend',{
				storeId:id
			}).success(function (data) {
	            this.items = data;
	            sy.closeLoad();
	        }.bind(this)).error(function(data, status) {
	        	sy.closeLoad();
			});
        };
	    return Demo;
	});
	
	// 申明页面控制类、
	factory.controller('storeController',['$http','$scope','store','coupons','goods','recommend',
	   	function ($http,$scope, store,coupons,goods,recommend) {
			 var storeId = $('#storeId').val();
			 $scope.isWeixin = isWeixin;
		     $scope.store = new store();
		     $scope.coupons = new coupons();
		     $scope.goods = new goods();
		     $scope.recommend = new recommend();
		     $scope.storeId = storeId;
		     $scope.store.query(storeId);
		     $scope.coupons.query(storeId);
		     $scope.recommend.query(storeId);
		     
		     //分享
		     $scope.shareStore = function(){
		    	 if(isWeixin()){
		    		 sy.alert("请点击微信右上角按钮分享");
		    	 }else{
		    		 $("#shareWin").show();
		    	 }
		     }
		     
		     $scope.uncollect=function(){
		    	 changeCollect($http,storeId,$scope,0);
		     };
		     $scope.collect=function(){
		    	 changeCollect($http,storeId,$scope,1);
		     };
		    $scope.toInfo=function(){
		    	toInfo($scope);
		    };
		    $scope.goDetail = function(id){
		  	  location.href = sy.path + "/m/goods/"+id;
		    };
		    $scope.toAct = function(){
		    	sessionStorage.storeId = storeId;
		    	location.href = sy.path + "/m/store/act";
		    }
		    $scope.getTicket = function(id){
		    	 sy.openLoad();
		    	 $http.post(sy.path+'/m/store/getTicket',{
						id:id
					}).success(function (data) {
						if(String(data).indexOf('session')!=-1){
							location.href = sy.path + "/m/login";
							return;
						}
						sy.closeLoad();
						if(data.code==0){
							sy.alert('领取成功!');
						}else{
							sy.alert(data.msg);
						}
			            
			        }.bind(this)).error(function(data, status) {
			        	sy.closeLoad();
					});
		    };
		    $scope.applySellar = function(){
		    	 sy.openLoad();
		    	 $http.post(sy.path+'/m/store/applyStoreSeller',{
						id:$scope.storeId
					}).success(function (data) {
						if(String(data).indexOf('session')!=-1){
							location.href = sy.path + "/m/login";
							return;
						}
						 sy.closeLoad();
						if(data.code==0){
							$scope.store.items.sellerState=2;
							sy.alert('申请成功!');
	
						}else if(data.code==1){
							$scope.store.items.sellerState=1;
							sy.alert('申请已提交，请耐心等候商家审核!');
	
						}else if(data.code==2){
							$scope.store.items.sellerState=0;
							var type = data.msg.split(',')[0];
							var val = data.msg.split(',')[1];
							var str="";
							if(type==1) str="必须在本店消费满"+val+"元才能申请成为推广专员！";
							if(type==2) str="必须在本店成功支付"+val+"笔订单才能申请成为推广专员！";
							sy.alert(str);
						}else{
							sy.alert(data.msg);
						}
			           
			        }.bind(this)).error(function(data, status) {
			        	sy.closeLoad();
					});
		    };
		    
		    shareSuccess = function(){
		    	$http.post(sy.path + "/m/share/success",{
		    	})
				.success(function (data) {
					
		        }).error(function(data, status) {
				});
		    }
		    
		    var wxdata;
		    initWeixinJsApi = function(){
		    	$http.post(sy.path + "/m/share/initWeixinApi",{
		    		url:location.href
		    	})
				.success(function (data) {
					if(data.errcode == 0){
						wxdata = data;
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
					 }
		        }).error(function(data, status) {
				});
		    }
		    
		    wx.error(function(res){
		    	console.log(res);
		    });
		    
		    wx.ready(function(){
		    	var link = wxdata.url + '?u=' + wxdata.u;
		    	var imgUrl = sy.path + "/download?id="+$scope.store.items.logo;
		    	var desc = $scope.store.items.remark;
		    	var title = $scope.store.items.title;
		    	//监听“分享给朋友”，按钮点击、自定义分享内容及分享结果接口
		    	wx.onMenuShareAppMessage({
		    	      title: title,
		    	      desc: desc,
		    	      link: link,
		    	      imgUrl: imgUrl,
		    	      success: function (res) {
		    	        if(wxdata.u != ''){
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
	    	    	  if(wxdata.u != ''){
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
	    	        	if(wxdata.u != ''){
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
	    	        	if(wxdata.u != ''){
		    	        	shareSuccess();
		    	        }
	    	        }
	    	      });
		    });
		    
		    if(isWeixin()){
		    	 initWeixinJsApi();
		     }
		    
		    $scope.shareTo = function(type){
		    	var link = sy.path + '/m/store/'+$scope.storeId+'?u='+$("#userId").val();
		    	var imgUrl = sy.path + "/download?id="+$scope.store.items.logo;
		    	var summary = $scope.store.items.remark;
		    	var title = "我发现了一家名叫<"+$scope.store.items.title +">的店铺很不错,快去看看吧~";
		    	var site = sy.path + '/m/';
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
	
	function toInfo($scope){
		sessionStorage.clear();
		sessionStorage.setItem('remark',$scope.store.items.remark);
		sessionStorage.setItem('address',$scope.store.items.address);
		sessionStorage.setItem('lat',$scope.store.items.lat);
		sessionStorage.setItem('lng',$scope.store.items.lng);
		sessionStorage.setItem('logo',$scope.store.items.logo);
		sessionStorage.setItem('isCollect',$scope.store.items.isCollect);
		sessionStorage.setItem('title',$scope.store.items.title);
		sessionStorage.setItem('storeId',$scope.store.items.id);
		sessionStorage.setItem('phone',$scope.store.items.phone);
		window.location = sy.path+"/m/store/sjxq";
	}
	function changeCollect($http,storeId,$scope,type){
		 sy.openLoad();
		$http.post(sy.path+'/m/store/changeCollect',{
			storeId:storeId,isCollect:type
		}).success(function (data) {
			if(String(data).indexOf('sessionOut')!=-1){
				window.location = sy.path+"/m/login";
			}
			$scope.store.items.isCollect=type;
            sy.closeLoad();
        }.bind(this)).error(function(data, status) {
        	sy.closeLoad();
		});
	}
