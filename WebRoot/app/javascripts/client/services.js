// services.js
var mServices = angular.module("mServices", [])

.factory('userService',['$http', 'PostCfg', function($http,PostCfg){
	return {
		logout: function(user){
      $http.post("/MeetingMng/api/v1/companyManagerLogout", user, PostCfg)
      .success(function(data){
        $cookieStore.remove("username");
        window.location.href = "/MeetingMng";
      });
    }
	};
}])

.factory('meetingService',['$http', '$q','PostCfg', function($http, $q, PostCfg){
  var service = {};

  service.add = function(){

  }

  service.delete = function(){

  }

  service.update = function(){

  }

  service.getAll = function(cellphone){
    var cellphone = "15071345115";
    var d = $q.defer();
    $http.get("/MeetingMng/api/v1/u/meeting/findByUserId?meetingCreatorId="+cellphone)
    .success(function(data){
      var obj;
      if(data.code == 0){
        obj = $.parseJSON(data.meetings);
        // for(var i=0;i<obj.length;i++){
        //   obj[i].meetingStartTime = new Date(obj[i].meetingStartTime);
        // }
      }
      d.resolve(obj);
    });
    return d.promise;
  }


  return service;
}])

.constant('PostCfg',{
  headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
  transformRequest: function(data) {
      return $.param(data);
  }
})
