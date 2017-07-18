var myApp = sy.getModule('myApp',[]);
var stepW = 38;
var description = new Array("非常差，回去再练练","真的是差，都不忍心说你了","一般，还过得去吧","很好，是我想要的东西","太完美了，此物只得天上有，人间哪得几回闻!");

myApp.directive('onFinishRenderFilters', function ($timeout) {
    return {
        restrict: 'A',
        link: function(scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function() {
                    scope.$emit('ngRepeatFinished');
                });
            }
        }
    };
});

myApp.controller('MyController', function($scope,$http) {
  $scope.fid = "";
  $scope.order = {};
  $scope.order.id = id;
  
  sy.openLoad();
  $http.post(sy.path + "/m/order/findById", {id:$scope.order.id}).success(function(data) {
	  	sy.closeLoad();
	  	if(data == "sessionOut")location.href = sy.path+"/m/login";
	  	if(data.code == 0){
	  		if(data.order.state != 4){
	  			alert("该订单无法评价");
	  			history.go(-1);
	  		}else{
	  			$scope.order = data.order;
	  		}
	  	}else{
	  		sy.alert(data.msg);
	  	}
	}).error(function(data, status) {
		sy.closeLoad();
		sy.alert('数据请求失败');
	});
  
  $scope.uploadImg = function(goods){
	  $scope.fid = goods.id;
	  $("#img").trigger("click");
  }
  
  $scope.delImg = function(goods,index){
	  if(goods.imgs[index]){
		  goods.imgs.splice(index,1);
	  }
  }
  
  $scope.updateGoodsImgs = function(id){
	  $.each($scope.order.detail,function(i,o){
		  if(o.id == $scope.fid){
			  if(o.imgs.length < 4){
				  o.imgs.push(id);
				  $scope.$apply();
			  }
			  $("#img").val("");
			  sy.closeLoad();
			  return false;
		  }
	  });
  }
  
  $scope.submitComment = function(){
	  var json = {data:[]};
	  var array = [];
	  $.each($scope.order.detail,function(i,o){
		  var obj = {
			id : o.id,
			stars : o.stars,
			content : o.content,
			imgs : o.imgs.join(",")
		  }
		  array.push(obj);
	  });
	  
	  json.data = array;
	  var jsonStr = sy.jsonToString(json);
	  
	  sy.openLoad();
	  $http.post(sy.path + "/m/order/addComment", {orderId:$scope.order.id,commentStr:jsonStr}).success(function(data) {
		  	sy.closeLoad();
		  	if(data == "sessionOut")location.href = sy.path+"/m/login";
		  	if(data.code == 0){
		  		history.go(-1);
		  	}else{
		  		sy.alert(data.msg);
		  	}
		}).error(function(data, status) {
			sy.closeLoad();
			sy.alert('数据请求失败');
		});
  }
  
  $scope.$on('ngRepeatFinished', function (ngRepeatFinishedEvent) {
	  	  var i = 0;	
	      while (i < $scope.order.detail.length) {
	    	  initStar($scope.order.detail[i],i);
	    	  i++;
	      }
	});
  
  initStar = function(goods,index){
	    var stars = $("#star"+index+" > li");
	    var descriptionTemp;
	    $("#showb"+index).css("width",stepW*5/40+"rem");
	    $("#content"+index).val(description[4]);
	    goods.content = description[4];
	    stars.each(function(i){
	        $(stars[i]).click(function(e){
	            var n = i+1;
	            $("#showb"+index).css({"width":stepW*n/40+"rem"});
	            descriptionTemp = description[i];
	            $(this).find('a').blur();
	            goods.stars = n;
	            return stopDefault(e);
	        });
	    });
	    stars.each(function(i){
	        $(stars[i]).hover(
	            function(){
	            	$("#content"+index).val(description[i]);
	            	goods.content = description[i];
	            },
	            function(){
	                if(descriptionTemp != null){
	                	$("#content"+index).val(descriptionTemp);
	                	goods.content = descriptionTemp;
	                }else{
	                	$("#content"+index).val("");
	                	goods.content = "";
	                }
	            }
	        );
	    });
  }
});

function stopDefault(e){
    if(e && e.preventDefault)
           e.preventDefault();
    else
           window.event.returnValue = false;
    return false;
};

//回调
function callback(id){
	var appElement = document.querySelector('[ng-controller=MyController]');
	var $scope = angular.element(appElement).scope(); 
	$scope.updateGoodsImgs(id);
}

$(function(){
	$("#img").bind("change",function(){
		if($(this).val() == '')return;
		sy.openLoad();
		$("#form1").submit();
	});
})