var factory = sy.getModule('flzqModule',['infinite-scroll']);
	// 创建后台数据交互工厂（分页）
	factory.factory('flGoods', function ($http) {
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
	        $http.post(sy.path+'/m/goods/fllist',{
				page:this.page,areaCode:sy.areaCode,limit:this.limit
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
	        
	        Demo.prototype.query = function (type) {
	        	this.items=[];
	        	sy.openLoad();
		        $http.post(sy.path+'/m/goods/fllist',{
					page:1,areaCode:sy.areaCode,limit:this.limit,sortType:type
				}).success(function (data) {
		            var items = data;
		            for (var i = 0; i < items.length; i++) {
		                this.items.push(items[i]);
		            }
		            if(items.length<this.limit) this.isend=true;
		            this.after = "t3_" + this.items[this.items.length - 1].id;
		            this.busy = true;
		            this.page=1;
		            sy.closeLoad();
		        }.bind(this)).error(function(data, status) {
		        	sy.closeLoad();
				});
	        };
	    };
	    return Demo;
	});
	
	// 申明页面控制类、
	factory.controller('flzqController',['$scope','flGoods',
	   	function ($scope, flGoods) {
		     $scope.flGoods = new flGoods();
		     $scope.sortType = 1;
		     $scope.sort=function(type){
		    	 if($scope.sortType == type && type == 3){
		    		 type = 4;
		    	 }
		    	 $scope.sortType = type;
		    	 $scope.flGoods.sortType=type;
		 		$scope.flGoods.query(type);
		 	}
		     
		     $scope.goDetail = function(id){
		    	 location.href = sy.path+'/m/goods/'+id;
		     }
		}
	]);
	/*$scope.sort=function(type){
		$scope.flGoods.query(type);
	}*/