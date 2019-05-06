/**
 * Created by Administrator on 2016/7/4.
 */
(function (angular) {
  'use strict';
    var app=angular.module('app',['ngRoute']);
    app.config(['$routeProvider',function ($routeProvider) {
        $routeProvider
            .when('/doAdminTimetable/:courseNo',{
                controller:"JiaoWuController",
                templateUrl:function(params){
                	 return 'doAdminTimetable/' + params.courseNo;
                }
            })
            .when('/doOneTimetable/:courseNo',{
                controller:"ShiYanController",
                templateUrl:function(params){
               	 return 'doOneTimetable/' + params.courseNo;
               }
            })
            .when('/doSelfTimetable/:courseNo',{
                controller:"zizhuController",
                templateUrl:function(params){
                	return 'doSelfTimetable/' + params.courseNo;
                }
            })
            .when('/doGroupTimetable/:courseNo/:groupId/:isAdmin',{
            	controller:"zizhuController",
            	templateUrl:function(params){
            		return 'doGroupTimetable/' + params.courseNo + "/" + params.groupId + "/" + params.isAdmin;
            	}
            })
            .when('/listSelfSchoolCourseInfo/:currpage',{
            	controller:"zizhuController",
            	templateUrl:function(params){
            		return 'listSelfSchoolCourseInfo/' + params.currpage;
            	}
            })
            .when('/newTimetableBatch/:courseCode/:batchId/:isSelf',{
            	controller:"zizhuController",
            	templateUrl:function(params){
            		return 'newTimetableBatch/' + params.courseCode+"/"+params.batchId+"/"+params.isSelf;
            	}
            })
              .when('/editTimetableBatchGroup/:courseCode/:batchId',{
            	controller:"zizhuController",
            	templateUrl:function(params){
            		return 'editTimetableBatchGroup/' + params.courseCode+"/"+params.batchId;
            	}
            })
            .otherwise({
                redirectTo: ''
            })
    }]).run(['$rootScope', '$location', function($rootScope, $location) {
        /* 监听路由的状态变化 */
        $rootScope.$on('$routeChangeStart', function(evt, next, current){
         console.log('route begin change');
         $rootScope.pageClass = 'page-contact';
         $(".main_container .fa-spinner").css("display","");
        }); 
        $rootScope.$on('$routeChangeSuccess', function(evt, current, previous) {
         console.log('route have already changed ：'+$location.path());
         $rootScope.pageClass = 'page-success';
         $(".main_container .fa-spinner").css("display","none");
        }); 
      }]);

    //教务处推送排课
    app.controller('JiaoWuController',['$scope', function($scope) {
        $scope.title = '这是A控制器';
    }]);

    //实验室推送排课
    app.controller('ShiYanController',['$scope', function($scope) {
        $scope.title = '这是b控制器';
    }]);

   //
    app.controller('zizhuController',['$scope', function($scope) {
        $scope.title = '这是c控制器';
        $scope.Week=[{
          id:1,
          name: '星期一'
        },{
            id:2,
            name: '星期二'
        },{
            id:3,
            name: '星期三'
        }];
    }]);
 
    
})(angular);
