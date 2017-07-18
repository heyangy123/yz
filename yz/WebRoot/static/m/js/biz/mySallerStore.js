var factory = sy.getModule('sallerStore',['infinite-scroll']);
	// 创建后台数据交互工厂（分页）
	factory.factory('saller', function ($http) {
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
	        $http.post(sy.path+'/m/saleCenter/mySallerStore',{
				page:this.page,limit:this.limit
			}).success(function (data) {
				if(String(data).indexOf('sessionOut')!=-1){
					location.href=sy.path+"/m/login";
					return;
				}
	            var items = data;
	            for (var i = 0; i < items.length; i++) {
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
	
	// 申明页面控制类、
	factory.controller('sallerStoreController',['$http','$scope','saller',
	   	function ($http,$scope, saller) {
		     $scope.saller = new saller();
		     $scope.goDetail = function(id){
		    	sessionStorage.clear();
		    	$.each($scope.saller.items,function(){
		    		if(this.id==id){
		    			sessionStorage.storeName = this.title;
		    			sessionStorage.logo = this.logo;
		    		}
		    	});
		    	sessionStorage.storeId = id;
		   	  	location.href = sy.path + "/m/saleCenter/salerEmployee";
		     };
		     
		     $scope.shareGoods = function(id){
		    	 sessionStorage.storeId = id;
		    	 $.each($scope.saller.items,function(i,o){
			    		if(o.id==id){
			    			sessionStorage.storeName = o.title;
			    			sessionStorage.logo = o.logo;
			    		}
			    	});
		    	 location.href = sy.path+'/m/saleCenter/fxGoods';
		     }
		     $scope.shareStore = function(id){
		    	 location.href = sy.path+'/m/store/'+id;
		     }
		}
	]);