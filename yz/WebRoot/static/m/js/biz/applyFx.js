var factory = sy.getModule('applyFx',['infinite-scroll']);
	// 创建后台数据交互工厂（分页）
	factory.factory('Recommends', function ($http) {
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
	        $http.post(sy.path+'/m/storerecommend/list',{
				page:this.page,areaCode:sy.areaCode,limit:this.limit,type:2
			}).success(function (data) {
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
	factory.controller('applyFxController',['$http','$scope','Recommends',
	   	function ($http,$scope, Recommends) {
		     $scope.Recommends = new Recommends();
		     $scope.goDetail = function(id){
		   	  	location.href = sy.path + "/m/store/"+id;
		     };
		     sy.openLoad();
		     $http.post(sy.path+'/m/getCode/1003',{
					
				}).success(function (data) {
		            $scope.info = data.msg;
		            sy.closeLoad();
		        }.bind(this)).error(function(data, status) {
		        	sy.closeLoad();
				});
		}
	]);