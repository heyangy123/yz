jQuery.divselect = function(divselectid,inputselectid,defaultValue,$scope) {
	var inputselect = $(inputselectid);
	$(divselectid+" cite").click(function(){
		var ul = $(divselectid+" ul");
		if(ul.css("display")=="none"){
			ul.slideDown("fast");
		}else{
			ul.slideUp("fast");
		}
	});
	$(divselectid+" ul li a").click(function(){
		var txt = $(this).text();
		$(divselectid+" cite").html(txt);
		var value = $(this).attr("selectid");
		inputselect.val(value);
		
		$scope.$apply(function(){	
			if($scope.list){	// 选择支付方式
				var idx = value.split(',');
				var payType = $scope.list[parseInt(idx[0])].payType[parseInt(idx[1])];
				$scope.list[parseInt(idx[0])].nowPayType = payType;
			}
		});
		$(divselectid+" ul").hide();
	});
	// 初始化
	if(defaultValue){
		$(divselectid+" cite").html(defaultValue);
	}
};

jQuery.sjdivselect = function(sjdivselectid,sjinputselectid,defaultValue, $scope) {
	var inputselect = $(sjinputselectid);
	$(sjdivselectid+" cite").click(function(){
		var ul = $(sjdivselectid+" ul");
		if(ul.css("display")=="none"){
			ul.slideDown("fast");
		}else{
			ul.slideUp("fast");
		}
	});
	$(sjdivselectid+" ul li a").click(function(){
		var txt = $(this).text();
		$(sjdivselectid+" cite").html(txt);
		var value = $(this).attr("selectid");
		inputselect.val(value);
		
		$scope.$apply(function(){
			if($scope.list){	// 选择时间
				var idx = value.split(',');
				if($scope.list[parseInt(idx[0])].isTime==1){
					var day = $scope.list[parseInt(idx[0])].time.day[parseInt(idx[1])];
					$scope.list[parseInt(idx[0])].nowTime.day = day;
				}
			}
		});
		
		$(sjdivselectid+" ul").hide();
	});
	// 初始化
	if(defaultValue){
		$(sjdivselectid+" cite").html(defaultValue);
	}
};

jQuery.sjddivselect = function(sjddivselectid,sjdinputselectid,defaultValue, $scope) {
	var inputselect = $(sjdinputselectid);
	$(sjddivselectid+" cite").click(function(){
		var ul = $(sjddivselectid+" ul");
		if(ul.css("display")=="none"){
			ul.slideDown("fast");
		}else{
			ul.slideUp("fast");
		}
	});
	$(sjddivselectid+" ul li a").click(function(){
		var txt = $(this).text();
		$(sjddivselectid+" cite").html(txt);
		var value = $(this).attr("selectid");
		inputselect.val(value);
		
		$scope.$apply(function(){
			if($scope.list){	// 选择时间段
				var idx = value.split(',');
				if($scope.list[parseInt(idx[0])].isTime==1){
					var time = $scope.list[parseInt(idx[0])].time.time[parseInt(idx[1])];
					$scope.list[parseInt(idx[0])].nowTime.time={};
					$scope.list[parseInt(idx[0])].nowTime.time.beginTime = time.beginTime;
					$scope.list[parseInt(idx[0])].nowTime.time.endTime = time.endTime;
				}
			}
		});
		$(sjddivselectid+" ul").hide();
	});
	// 初始化
	if(defaultValue){
		$(sjddivselectid+" cite").html(defaultValue);
	}
};

jQuery.kddivselect = function(kddivselectid,kdinputselectid, defaultValue, $scope) {
	$(kddivselectid+" cite").click(function(){
		var ul = $(kddivselectid+" ul");
		if(ul.css("display")=="none"){
			ul.slideDown("fast");
		}else{
			ul.slideUp("fast");
		}
	});
	$(kddivselectid+" ul li a").click(function(){
		var txt = $(this).text();
		$(kddivselectid+" cite").html(txt);
		var value = $(this).attr("selectid");
		$scope.$apply(function(){
			if($scope.list){	// 选择快递
				var idx = value.split(',');
				var express = $scope.list[parseInt(idx[0])].express[parseInt(idx[1])];
				$scope.list[parseInt(idx[0])].nowExpress = express;
			}
		});
		$(kddivselectid+" ul").hide();
	});
	// 初始化
	if(defaultValue){
		$(kddivselectid+" cite").html(defaultValue.split(',')[1]);
	}
};