var factory = sy.getModule('storeInfo',[]);
	// 创建后台数据交互工厂（分页）
	factory.factory('store', function ($http) {
	    var Demo = function () {
	        this.items = {};
	        this.items.remark = sessionStorage.getItem('remark');
	        this.items.address = sessionStorage.getItem('address');
	        this.items.lat = sessionStorage.getItem('lat');
	        this.items.lng = sessionStorage.getItem('lng');
	        this.items.logo = sessionStorage.getItem('logo');
	        this.items.isCollect = sessionStorage.getItem('isCollect');
	        this.items.title = sessionStorage.getItem('title');
	        this.items.storeId = sessionStorage.getItem('storeId');
	        this.items.phone = sessionStorage.getItem('phone');
	        if(!this.items.phone||this.items.phone=='undefined') this.items.phone = " ";
	    };
	    return Demo;
	});
	
	
	// 申明页面控制类、
	factory.controller('infoController',['$http','$scope','store',
	   	function ($http,$scope, store) {
		     $scope.store = new store();
		     $scope.openMap = function(){
		    	 
		     }
		     $scope.collect=function(){
		    	 changeCollect($http,$scope.store.items.storeId,$scope,1);
		     };
		     $scope.uncollect=function(){
		    	 changeCollect($http,$scope.store.items.storeId,$scope,0);
		     };
		     $scope.toQr = function(){
					location.href = sy.path + "/m/store/qr/"+$scope.store.items.storeId;
				}
		}
	]);
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
	