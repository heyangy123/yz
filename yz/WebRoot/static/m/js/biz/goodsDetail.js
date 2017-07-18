	var normalGoodsApp = sy.getModule('normalGoods',['infinite-scroll','ngSanitize']);
	normalGoodsApp.directive('onFinishRenderFilters', function ($timeout) {
	    return {
	        restrict: 'A',
	        link: function(scope, element, attr) {
	            if (scope.$last === true) {
	                $timeout(function() {
	                    scope.$emit('ngRepeatFinished',attr.onFinishRenderFilters);
	                });
	            }
	        }
	    };
	});
	
	// 商品详情
	normalGoodsApp.factory('ItemInfo', [ '$http', '$q', function($http, $q) {
		return {
			query : function() {
				var deferred = $q.defer(); 
				sy.openLoad();
				$http.post(sy.path+'/m/goods/findById/'+v_id,{
				}).success(function(data, status, headers, config) {
					deferred.resolve(data); 
					sy.closeLoad();
				}).error(function(data, status, headers, config) {
					deferred.reject(data); 
					sy.closeLoad();
				});
				return deferred.promise; 
			}
		};
	}]);
	
	// 收藏商品
	normalGoodsApp.factory('Collect', [ '$http', '$q', function($http, $q) {
		return {
			insert : function() {
				var deferred = $q.defer(); 
				sy.openLoad();
				$http.post(sy.path+'/m/goods/collect/'+v_id,{
					op : 1
				}).success(function(data, status, headers, config) {
					deferred.resolve(data); 
					sy.closeLoad();
				}).error(function(data, status, headers, config) {
					deferred.reject(data); 
					sy.closeLoad();
				});
				return deferred.promise; 
			},
			cancel : function() {
				var deferred = $q.defer(); 
				sy.openLoad();
				$http.post(sy.path+'/m/goods/collect/'+v_id,{
					op : 2
				}).success(function(data, status, headers, config) {
					deferred.resolve(data); 
					sy.closeLoad();
				}).error(function(data, status, headers, config) {
					deferred.reject(data); 
					sy.closeLoad();
				});
				return deferred.promise; 
			}
		};
	}]);
	
	// 评价列表
	normalGoodsApp.factory('CommentList',[ '$http', '$q', function($http, $q) {
		return {
			param : {
				page : 1,limit : 10,busy : false
			},
			query : function(){
				var deferred = $q.defer(); 
				sy.openLoad();
				this.param.busy = true;
				var t = this.param;
				$http.post(sy.path+'/m/goods/commentList/'+v_id,t)
				.success(function(data, status, headers, config) {
					deferred.resolve(data); 
					t.page += 1;
					t.busy = false;
					sy.closeLoad();
				}).error(function(data, status, headers, config) {
					deferred.reject(data); 
					t.busy = false;
					sy.closeLoad();
				});
				return deferred.promise; 
			}
		};
	}]);
	
	// 猜你喜欢
	normalGoodsApp.factory('LikeList',[ '$http', '$q', function($http, $q) {
		return {
			query : function() {
				var deferred = $q.defer(); 
				sy.openLoad();
				$http.post(sy.path+'/m/goods/likeList/'+v_id,{
					areaCode : sy.areaCode
				}).success(function(data, status, headers, config) {
					deferred.resolve(data); 
					sy.closeLoad();
				}).error(function(data, status, headers, config) {
					deferred.reject(data); 
					sy.closeLoad();
				});
				return deferred.promise; 
			}
		};
	}]);
	
	normalGoodsApp.controller('normalGoodsController', 
		function($rootScope, $scope, $http, $timeout, $sanitize, ItemInfo, CommentList, LikeList, Collect){
			// 记录商品规格 $rootScope.sn,$rootScope.pl价格
			$scope.tsn = new Array();
			$scope.selectOkMode = 0;
			$scope.isComplete = false;
			$scope.cartNum = 0;//购物车数量
			$scope.showBottom = true;
			
			$scope.isWeixin = isWeixin;
			
			// 商品信息
			ItemInfo.query().then(function(data) { 
				if(data.code == 0){
					$scope.itemInfo = data.data;
					$scope.snImg = data.data.img;
					if($scope.itemInfo.source == 2){
						checkBuy();
					}
					if(data.data.store.qq != undefined){
						$(".qq").attr("href","mqqwpa://im/chat?chat_type=wpa&uin="+data.data.store.qq+"&version=1&src_type=web&web_src=oicqzone.com");
					}
				}else{
					sy.alert(data.msg);
					window.location = sy.path+'/m/index';
				}
				
			}, function(data) { 	
				 
			});
			
			$scope.$on('ngRepeatFinished', function (event,data) {
				if(data == 'slideBox'){
					TouchSlide({ 
						slideCell:"#slideBox",
						titCell:".hd ul", //开启自动分页 autoPage:true
						mainCell:".bd ul", 
						effect:"leftLoop", 
						autoPage:true,	//自动分页
						autoPlay:false 	//自动播放
					});
				}else if(data == 'snsBox'){
					// 如果规格只有一个，则自动选中
					$.each($scope.itemInfo.sn,function(i,o){
						if(o.sons && o.sons.length==1){
							$scope.choose(i, 0, o.sons[0]);
						}
					});
				}else{
					$("#"+data).lightGallery({});
				}
			});
			
			checkBuy = function(){
				$http.post(sy.path + "/m/vipActGoods/canBuy",{gid:$scope.itemInfo.id})
				.success(function (data) {
					if(data=='sessionOut'){
		    			//location.href = sy.path + "/m/login";
		    		}else if(data.code != 0){
		    			$scope.showBottom = false;
					}
		        }).error(function(data, status) {
		        	 
				});
			};
			
			$scope.getCartNum = function(){
				$http.post(sy.path + "/m/shopcart/shopCartNum",{})
				.success(function (data) {
					if(data=='sessionOut'){
		    			//location.href = sy.path + "/m/login";
		    		}else{
						$scope.cartNum = data.code;
					}
		        }).error(function(data, status) {
		        	 
		        	sy.closeLoad();
				});
			};		
			
			$scope.getCartNum();
			
			// 评论信息
			CommentList.query().then(function(data) { 
				$scope.items = data.items;
			}, function(data) { 	
				$scope.items = [];
			});
			
			// 猜你喜欢
			LikeList.query().then(function(data){
				if(data.code){
					sy.alert('失败，'+data.msg+'!');
				}else{
					$scope.likes = data;
				}
			}, function(data){
				$scope.likes = [];
			});
			
			// 下一页
			$scope.nextPage = function(){
				var prevPage = $scope.items;
				var promise = CommentList.query(); 
				promise.then(function(data) { 
					$(data.items).each(function(){
						prevPage.push(this);
					});
					$scope.items = prevPage;
				}, function(data) { 	
					$scope.items = [];
				});
			};
			
			// 收藏
			$scope.collect = function(op){
				if(op==1){
					Collect.insert().then(function(data){
						if(data=='sessionOut'){
			    			location.href = sy.path + "/m/login";
			    		}else if(data.code==0){
							sy.alert('收藏商品成功!');
							$scope.itemInfo.isCollect = 1;
						}else{
							sy.alert('失败,'+data.msg+'!');
						}
					},function(data, status){
						sy.alert('网络错误，net satatus='+status);
					});
				}else{
					Collect.cancel().then(function(data){
						if(data=='sessionOut'){
			    			location.href = sy.path + "/m/login";
			    		}else if(data.code==0){
							sy.alert('取消收藏商品成功!');
							$scope.itemInfo.isCollect = 0;
						}else{
							sy.alert('失败,'+data.msg+'!');
						}
					},function(data, status){
						sy.alert('网络错误，net satatus='+status);
					});
				}
			};
			
			// 弹出规格选择框
			$scope.showSelectSn = function(){
				$("#d").empty();
				if($rootScope.pl){	// 恢复上次所选
					$("#d").Spinner({value:$rootScope.pl.num, max:$scope.itemInfo.max==0?99:$scope.itemInfo.max});
				}else{
					$("#d").Spinner({value:1, max:$scope.itemInfo.max==0?99:$scope.itemInfo.max});
				}
				$('#On3bq3h2Pe').show();
				$('#fx_spec').slideDown('fast');
				$('#toolBar').slideUp('fast');
				$(document.body).css({height:$(window).height()+'px',overflow:'hidden',position:'fixed'});
			};
			
			// 隐藏规格选择框
			$scope.hideSelectSn = function(flag,from){
				if(from == 0 || (!$scope.isComplete && $scope.itemInfo.hasSn==1))$scope.selectOkMode = 0;
				if($rootScope.pl && flag){
					$rootScope.pl.num = $('#d>input').val();
					$('#toChoose').html($rootScope.pl.snsStr);
					$rootScope.sn = $scope.tsn;
					
					if($scope.selectOkMode == 1){
						quickBuySubmit($scope.itemInfo.id,$rootScope.pl.num,$rootScope.pl.sns,$rootScope.pl.total);
					}else if($scope.selectOkMode == 2){
						addToShopcartSubmit($scope.itemInfo.id,$rootScope.pl.num,$rootScope.pl.sns,$rootScope.pl.total);
					}
				}else{
					var num = $('#d>input').val();
					if($scope.selectOkMode == 1){
						quickBuySubmit($scope.itemInfo.id,num,"",$scope.itemInfo.total);
					}else if($scope.selectOkMode == 2){
						addToShopcartSubmit($scope.itemInfo.id,num,"",$scope.itemInfo.total);
					}
				}
				$('#On3bq3h2Pe').hide();
				$('#fx_spec').slideUp('fast');
				$('#toolBar').slideDown('fast');
				$(document.body).css({overflow:'auto',position:'static'});
			};
			
			// 点击规格按钮
			$scope.choose = function(parentNo, sindex, sn){
				if(sn.img != undefined){
					$scope.snImg = sn.img;
				}else{
					//$scope.snImg = $scope.itemInfo.img;
				}
				
				if($('#li_'+parentNo+'_'+sindex).hasClass('disable')){
					return;
				}
				if($('#li_'+parentNo+'_'+sindex).hasClass('checked')){
					$scope.tsn[parentNo] = undefined;
					$('#li_'+parentNo+'_'+sindex).removeClass('checked');
				} else {
					sn.index = sindex;
					var s = $scope.tsn[parentNo];
					$('li[id^="li_'+parentNo+'_"]').removeClass('checked');
					$('#li_'+parentNo+'_'+sindex).addClass('checked');
					if(s==undefined || s.sonId==sn.sonId){
						$scope.tsn[parentNo] = sn;
					}else if(s.parentId == sn.parentId){
						$scope.tsn[parentNo] = sn;
					}
				}
				
				/////////////////////////////////////////////////////
				for(var i=0; i<$scope.itemInfo.sn.length; i++){
					//if(i == parentNo)continue;
					var nSns = new Array();
					for(var h=0; h<$scope.itemInfo.sn.length; h++){
						if($scope.tsn[h] && h != i)
							nSns.push($scope.tsn[h].sonId);
					}
					
					var lpl = new Array();
					for(var f=0; f<$scope.itemInfo.pl.length; f++){
						p = $scope.itemInfo.pl[f];
						var psns = p.sns.split('-');
						if(nSns.length==0 || $scope.isContained(psns,nSns)){
							lpl.push(p);	
						}
					}
					
					var parent = $scope.itemInfo.sn[i];
					for(var j=0; parent && j<parent.sons.length; j++){
						var tag = false;	// 遍历未选选项是否可选
						for(var k=0; k<lpl.length; k++){
							if(lpl[k].sns.indexOf(parent.sons[j].sonId)!=-1){
								tag = true;break;
							}
						}
						if(!tag){
							//if(!$('#li_'+i+'_'+j).hasClass('checked'))
							$('#li_'+i+'_'+j).addClass('disable');
						}else{
							$('#li_'+i+'_'+j).removeClass('disable');
						}
					}
				}
				
				// 检查是否所有规格都已选完
				var isComplete = true;
				for(var i=0; i<$scope.itemInfo.sn.length; i++){
					if(!$scope.tsn[i])isComplete = false;
				}
				
				$scope.isComplete = isComplete;
				
				// 遍历价格表，取出对应的价格，库存
				if(isComplete){
					var sns = '';
					var snsStr = '已选：';
					for(var i=0; i<$scope.itemInfo.sn.length; i++){
						if(i==0){
							sns += $scope.tsn[i].sonId;
							snsStr += $scope.tsn[i].sonName;
						}else{
							sns = sns + '-' + $scope.tsn[i].sonId;
							snsStr = snsStr + ',' + $scope.tsn[i].sonName;
						}
					}
					var isExist = false;
					for(var j=0; j<$scope.itemInfo.pl.length; j++){
						p = $scope.itemInfo.pl[j];
						var lsns = sns.split('-');
						var psns = p.sns.split('-');
						if($scope.isContained(psns,lsns)){
							$rootScope.pl = p;
							$rootScope.pl.snsStr = snsStr;
							isExist = true;break;
						}
					}
					if(isExist){
						$('#snPrice').html($rootScope.pl.price);
						$('#snTotal').html($rootScope.pl.total);
						$('#snsStr').html($rootScope.pl.snsStr);
					}else{
						// 没有匹配价格
						console.log(sns);
					}
				}else{
					var snsStr = '请选择：',flag = true;
					for(var i=0; i<$scope.itemInfo.sn.length; i++){
						if(!$scope.tsn[i]){
							snsStr = snsStr + (flag?'':',') + $scope.itemInfo.sn[i].title;
							flag = false;
						}
					}
					
					var p = {price:0,total:0,snsStr:snsStr};
					$rootScope.pl = p;
					$('#snsStr').html($rootScope.pl.snsStr);
				}
			};
			
			$scope.isContained = function(a, b){
				if (!(a instanceof Array) || !(b instanceof Array))
					return false;
				if (a.length < b.length)
					return false;
				var aStr = a.toString();
				for (var i = 0, len = b.length; i < len; i++) {
					if (aStr.indexOf(b[i]) == -1)
						return false;
				}
				return true;
			};
			
			$scope.unique = function(arr) {
				var result = [], isRepeated;
			    for (var i = 0, len = arr.length; i < len; i++) {
			        isRepeated = false;
			        for (var j = 0, len = result.length; j < len; j++) {
			              if (arr[i] == result[j]) {   
			                  isRepeated = true;
			                  break;
			              }
			        }
		            if (!isRepeated) {
			             result.push(arr[i]);
			         }
			     }
			     return result;
			};
			
			$scope.toDetail = function(id){
				location.href= sy.path + '/m/goods/'+id;
			}
			
			//立即购买
			$scope.quickBuy = function(){
				if(!$scope.showBottom){
					sy.alert("等级不足");
					return;
				}
				
				if($scope.itemInfo.hasSn == 1){
					if($rootScope.pl && $scope.isComplete){
						quickBuySubmit($scope.itemInfo.id,$rootScope.pl.num,$rootScope.pl.sns,$rootScope.pl.total);
					}else{
						$scope.showSelectSn();
						$scope.selectOkMode = 1;
					}
				}else{
					if($rootScope.pl!= undefined && $rootScope.pl.num != undefined){
						quickBuySubmit($scope.itemInfo.id,$rootScope.pl.num,"",$scope.itemInfo.total);
					}else{
						$scope.showSelectSn();
						$scope.selectOkMode = 1;
					}
				}
			}
			
			//添加到购物车
			$scope.addToShopcart = function(){
				if(!$scope.showBottom){
					sy.alert("等级不足");
					return;
				}
				if($scope.itemInfo.mode == 2){
					return;
				}else{
					if($scope.itemInfo.hasSn == 1){
						if($rootScope.pl && $scope.isComplete){
							addToShopcartSubmit($scope.itemInfo.id,$rootScope.pl.num,$rootScope.pl.sns,$rootScope.pl.total);
						}else{
							$scope.showSelectSn();
							$scope.selectOkMode = 2;
						}
					}else{
						if($rootScope.pl!= undefined && $rootScope.pl.num != undefined){
							addToShopcartSubmit($scope.itemInfo.id,$rootScope.pl.num,"",$scope.itemInfo.total);
						}else{
							$scope.showSelectSn();
							$scope.selectOkMode = 2;
						}
						
					}
				}
			}
			
			quickBuySubmit = function(id,num,fid,total){
				if(!num) num = 1;
				if(total<num){
					sy.alert("库存不足");
					return;
				}
				location.href = sy.path+"/m/order/addOrder?id="+id+"&num="+num+"&fid="+fid;
			}
			
			addToShopcartSubmit = function(id,num,fid,total){
				if(!num) num = 1;
				if(total<num){
					sy.alert("库存不足");
					return;
				}
				sy.openLoad();
				$http.post(sy.path + "/m/shopcart/addShopCart",{id:id,num:num,fid:fid})
				.success(function (data) {
					sy.closeLoad();
					if(data=='sessionOut'){
		    			location.href = sy.path + "/m/login";
		    		}else if(data.code == 0){
						$scope.cartNum = parseInt(data.msg);
					}else{
						sy.alert(data.msg);
					}
		        }).error(function(data, status) {
		        	sy.closeLoad();
		        	 
				});
			}
			
		     //分享
		     $scope.shareGoods = function(){
		    	 if(isWeixin()){
		    		 sy.alert("请点击微信右上角按钮分享");
		    	 }else{
		    		 $("#shareWin").show();
		    	 }
		     }
		     
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
		    	var imgUrl = sy.path + "/download?id="+$scope.itemInfo.img;
		    	var desc = $scope.itemInfo.store.name;
		    	var title = $scope.itemInfo.title;
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
		    	var link = sy.path + '/m/goods/'+v_id+'?u='+$("#userId").val();
		    	var imgUrl = sy.path + "/download?id="+$scope.itemInfo.img;
		    	var summary = $scope.itemInfo.store.remark;
		    	var title = "我发现了一个宝贝<"+$scope.itemInfo.title +">很不错,快去看看吧~";
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
	);