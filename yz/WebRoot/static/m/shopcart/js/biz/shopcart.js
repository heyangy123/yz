var myApp = sy.getModule('myApp',[]);

// 商品详情
myApp.factory('ItemInfo', [ '$http', '$q', function($http, $q) {
	return {
		query : function(id) {
			var deferred = $q.defer(); 
			sy.openLoad();
			$http.post(sy.path+'/m/goods/findById/'+id,{
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

myApp.controller('MyController', function($rootScope,$scope,$http,ItemInfo) {
	$scope.list = [];
	
	sy.openLoad();
	$http.post(sy.path + "/m/shopcart/myShopCart", {}).success(function(data) {
		sy.closeLoad();
		if (data == "sessionOut"){
			location.href = sy.path + "/m/login";
		}else if (data.code == 0) {
			$scope.list = data.data;
		}else{
			sy.alert(data.msg);
		}
	}).error(function(data, status) {
		sy.closeLoad();
		sy.alert('数据请求失败');
	});
  
	/**********************************级联选择开始**************************************/
	
	$scope.totalPrice = 0;	//总价
	$scope.selectCnt = 0;	//计数器
	$scope.isSelectAll = false;//是否已经全选
	
	//是否已经全选
	isSelectAll = function(){
		var flag = true;
		$.each($scope.list,function(i,o){
			if(o.select == 0){
				flag = false;
				return false;
			}
		});
		return flag;
	}
	//全选
	$scope.selectAll = function(type){
		var type = 0;
		if(!$scope.isSelectAll){
			type = 1;
		}
		$scope.isSelectAll = type == 1;
		$.each($scope.list,function(i,o){
			$scope.selectStore(o,type);
		});
	}
	//全选商家
	$scope.selectStore = function(sc,type){
		if(type!=undefined){
			sc.select = type;
		}else{
			sc.select = (sc.select == 1?0:1);
			type = sc.select;
		}
		$.each(sc.goods,function(i,o){
			$scope.selectGoods(o,sc,type);
		});
		
		if(isSelectAll()){
			$scope.isSelectAll = true;
		}
	}
	//是否已经全选商家
	isStoreSelectAll = function(sc){
		var flag = true;
		$.each(sc.goods,function(i,o){
			if(o.select == 0){
				flag = false;
				return false;
			}
		});
		return flag;
	}
	//选择商品
	$scope.selectGoods = function(goods,sc,type){
		var flag = true;
		if(type!=undefined){
			if(goods.select == type)flag = false;
			goods.select = type;
		}else{
			goods.select = (goods.select == 1?0:1);
		}
		
		if(flag){
			if(goods.select == 1){
				$scope.selectCnt++;
				$scope.totalPrice += parseFloat(goods.nowPrice*goods.num);
			}else{
				$scope.selectCnt--;
				$scope.totalPrice -= parseFloat(goods.nowPrice*goods.num);
			}
		}
		
		
		if(goods.select == 1 && sc.select == 0 && isStoreSelectAll(sc)){
			sc.select = 1;
			if(isSelectAll()){
				$scope.isSelectAll = true;
			}
		}else if(goods.select == 0){
			sc.select = 0;
			$scope.isSelectAll = false;
		}
	}
	
	/**********************************级联选择结束**************************************/
	
	
	/**********************************编辑开始**************************************/
	
	$scope.isEditingAll = false;//是否全部编辑
	
	//有正在编辑的
	$scope.hasEditing = function(){
		var flag = false;
		$.each($scope.list,function(i,o){
			if(o.editing)flag = true;
			return false;
		});
		return flag;
	}
	
	//全部编辑
	$scope.editAll = function(){
		$.each($scope.list,function(i,o){
			$scope.updateStore(o,!$scope.isEditingAll);
		});
		$scope.isEditingAll = !$scope.isEditingAll;
	}
	
	//编辑商家
	$scope.updateStore = function(sc,type){
		if(type == undefined || type != sc.editing){
			sc.editing = !sc.editing;
			
			if(!sc.editing){
				$.each(sc.goods,function(i,o){
					if(o.hasUpdated){
						submitEdit(o,o.id,o.num);
					}
				});
			}
		}
	}
	
	//提交更新
	submitEdit = function(goods,id,num){
		$http.post(sy.path + "/m/shopcart/editShopCart", {id:id,num:num}).success(function(data) {
			if (data == "sessionOut"){
				location.href = sy.path + "/m/login";
			}else if (data.code == 0) {
				goods.hasUpdated = false;
			}else{
				sy.alert(data.msg);
			}
		}).error(function(data, status) {
			sy.alert('数据请求失败');
		});
	}
	
	//数量编辑
	$scope.numUpdate = function(goods,num){
		if(num)num = 1; else num = -1;
		
		if(num < 0){
			if(goods.num > 1){
				goods.num += num; 
				goods.hasUpdated = true;
				if(goods.select == 1){
					$scope.totalPrice -= parseFloat(goods.nowPrice);
				}
			}
		}else{
			if(goods.max == 0){
				goods.num += num; 
				goods.hasUpdated = true;
				if(goods.select == 1){
					$scope.totalPrice += parseFloat(goods.nowPrice);
				}
			}else{
				if(goods.max > goods.num && goods.total > goods.num){
					goods.num += num; 
					goods.hasUpdated = true;
					if(goods.select == 1){
						$scope.totalPrice += parseFloat(goods.nowPrice);
					}
				}else{
					if(goods.max <= goods.num){
						sy.alert("超出限购数量");
					}else{
						sy.alert("库存不足");
					}
				}
			}
			
		}
	}
	
	//删除
	$scope.delCart = function(id){
		var ids = id;
		if(!id){
			//批量
			var array = [];
			if($scope.selectCnt>0){
				$.each($scope.list,function(i,o){
					$.each(o.goods,function(j,x){
						if(x.select == 1){
							array.push(x.id);
						}
					});
				});
			}
			ids = array.join(",");
		}
		
		if(ids){
			sy.confirm("确定要删除吗？",function(){
				sy.openLoad();
				$http.post(sy.path + "/m/shopcart/delShopCart", {ids:ids}).success(function(data) {
					sy.closeLoad();
					if (data == "sessionOut"){
						location.href = sy.path + "/m/login";
					}else if (data.code == 0) {
						location.reload();
					}else{
						sy.alert(data.msg);
					}
				}).error(function(data, status) {
					sy.closeLoad();
					sy.alert('数据请求失败');
				});
			});
		}
	}
	
	//编辑规格
	$scope.itemInfo = {};
	$scope.tsn = new Array();
	$scope.isComplete = false;
	$scope.editSn = function(goods){
		$scope.isSelectAll = false;
		$.each($scope.list,function(i,o){
			$scope.selectStore(o,0);
		});
		
		if(goods.itemInfo){
			$scope.itemInfo = goods.itemInfo;
		}else{
			ItemInfo.query(goods.goodsId).then(function(data) { 
				data.data.cartId = goods.id;
				goods.itemInfo = data.data;
				goods.itemInfo.snImg = goods.img;
				$scope.itemInfo = goods.itemInfo;
			}, function(data) {
				sy.alert('请求商品信息失败！');
			});
		}
		$scope.showSelectSn();
	}
	// 弹出规格选择框
	$scope.showSelectSn = function(){
		$("#d").empty();
		$('#On3bq3h2Pe').show();
		$('#fx_spec').slideDown('fast');
		$('#toolBar').slideUp('fast');
		$(document.body).css({height:$(window).height()+'px',overflow:'hidden',position:'fixed'});
	};
	// 隐藏规格选择框
	$scope.hideSelectSn = function(flag,from){
		if(from == 1 && $scope.isComplete){
			submitSnEdit($scope.itemInfo.cartId,$rootScope.pl.sns);
		}
		$('#On3bq3h2Pe').hide();
		$('#fx_spec').slideUp('fast');
		$('#toolBar').slideDown('fast');
		$(document.body).css({overflow:'auto',position:'static'});
	};
	//提交更新
	submitSnEdit = function(id,fid){
		$http.post(sy.path + "/m/shopcart/editShopCartSn", {id:id,fid:fid}).success(function(data) {
			if (data == "sessionOut"){
				location.href = sy.path + "/m/login";
			}else if (data.code == 0) {
				$.each($scope.list,function(i,o){
					$.each(o.goods,function(j,p){
						if(p.id == data.data.id){
							p.info = data.data.info;
							p.nowPrice = data.data.nowPrice;
							
							p.max = data.data.max;
							p.total = data.data.total; 
							p.num = 1;
							
							$scope.$apply();
							return false;
						}
					});
				});
			}else{
				sy.alert(data.msg);
			}
		}).error(function(data, status) {
			sy.alert('数据请求失败');
		});
	}
	
	// 点击规格按钮
	$scope.choose = function(parentNo, sindex, sn){
		if(sn.img != undefined){
			$scope.itemInfo.snImg = sn.img;
		}else{
//			$scope.snImg = $scope.itemInfo.img;
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
				//console.log(sns);
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
	
	/**********************************编辑结束**************************************/
	
	
	
	//结算
	$scope.addOrder = function(){
		if($scope.selectCnt>0){
			//批量
			var array = [];
			if($scope.selectCnt>0){
				$.each($scope.list,function(i,o){
					$.each(o.goods,function(j,x){
						if(x.select == 1){
							array.push(x.id);
						}
					});
				});
			}
			var ids = array.join(",");
			location.href = sy.path+"/m/order/addOrders?ids="+ids;
		}
	}
	
	
	
	
	
	
	
	//跳转商家详情
	$scope.storeDetail = function(id){
		if(id!='0'){
			location.href = sy.path + "/m/store/"+id;
		}
	}
	//跳转商品详情
	$scope.goodsDetail = function(id){
		location.href = sy.path + "/m/goods/"+id;
	}
});
