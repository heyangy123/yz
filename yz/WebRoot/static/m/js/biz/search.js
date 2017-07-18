var myApp = sy.getModule('myApp',[]);

var tipList;
myApp.controller('SearchController', function($scope,$http) {
	  $scope.hotList = [];
	  $scope.tipList = [];
	  $scope.type = 1;
	  
	  //历史记录
	  $scope.getTips = function(){
		  if($scope.type == 1){
			  tipList = localStorage.goodsTipList;
		  }else{
			  tipList = localStorage.storeTipList;
		  }
		  if(tipList != undefined && tipList != ''){
			  $scope.tipList = sy.stringToJSON(tipList).reverse();
		  }else{
			  $scope.tipList = [];
		  }
	  }
	  
	  $scope.getTips();
	  
	  
	  //清除历史记录
	  $scope.clearTips = function(){
		  if($scope.type == 1)
			  localStorage.removeItem("goodsTipList");
		  else
			  localStorage.removeItem("storeTipList");
		  $scope.tipList = [];
	  }
	  
	  $http.post(sy.path + "/m/getCode/1006",{})
		.success(function (data) {
			if(data.code == 0){
				$scope.hotList = data.msg.split(",");
		  }
        }).error(function(data, status) {
		});
	  
	  $scope.selectType = function(e){
		  var target = $(e.target);
		  if(target.hasClass("goods")){
       		 $(".s-input-tab-txt").html("商品");
       		 $scope.type = 1;
       		 $scope.getTips();
       	  }else if(target.hasClass("store")){
       		 $(".s-input-tab-txt").html("店铺");
       		 $scope.type = 2;
       		 $scope.getTips();
       	  }
	      $(".s-input-tab-nav").hide();
	  }
	  
	  $scope.search = function(tip,from){
		  var keyword = "";
		  if(tip == undefined){
			  var keyword = $("#keyword").val();
			  if(keyword == ''){
				  return;
			  }
			  var tips = [];
			  if($scope.tipList.length != 0){
				  tips = sy.stringToJSON(tipList);
			  }
			  $.each(tips,function(i,o){
				  if(o.name == keyword){
					  tips.splice(i,1);
					  return false;
				  }
			  });
			  tips.push({name:keyword});
			  if($scope.type == 1){
				  localStorage.goodsTipList = sy.jsonToString(tips);
			  }else{
				  localStorage.storeTipList = sy.jsonToString(tips);
			  }
		  }else{
			  keyword = tip;
			  if(from == 2)$scope.type = 1;
		  }
		  
		  if($scope.type == 1){//跳转商品列表
			  location.href = sy.path + "/m/goods?keyword="+encodeURI(keyword);
		  }else{//跳转商家列表
			  location.href = sy.path + "/m/store?keyword="+encodeURI(keyword);
		  }
	  }
	});