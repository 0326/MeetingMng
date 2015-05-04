// client services.js
var mServices = angular.module("mServices", [])

.factory('userService',['$http', '$q', '$cookieStore', 'PostCfg',
  function($http, $q, $cookieStore, PostCfg){
    var service = {
      logout: function(user){
        $http.post("/MeetingMng/api/v1/ordinaryUserLogout", user, PostCfg)
        .success(function(data){
          $cookieStore.remove("cellphone");
          window.location.href = "/MeetingMng";
        });
      },
      getInfo: function(){
        var d = $q.defer();
        $http
        .get("/MeetingMng/api/v1/findUserInfo?cellphone="+$cookieStore.get("cellphone"))
        .success(function(data, status){
          service.profiles = $.parseJSON(data.userInfo);
          if(service.profiles.avatarUrl == null){
            service.profiles.avatarUrl = "app/images/logo.png";
          }
          if(service.profiles.sex == false){
            service.profiles.sex = 0;
          }
          else{
            service.profiles.sex = 1;
          }
          d.resolve(service.profiles);
        });
        return d.promise;
      },
      updatePass: function(username,oldpass, newpass){
        $http.post("/MeetingMng/api/v1/OrdinaryUserUpdatePass", {
          password:oldpass,
          newpassword:newpass,
          username:username
        }, PostCfg)
        .success(function(data){
          if(data.code == 0){
            alert("修改成功！");
            $("#passModal").modal('hide');
          }
          else{
            alert("修改密码失败");
          }
        });
      },
      updateInfo: function(user){
        var d = $q.defer();
        $http.post("/MeetingMng/api/v1/OrdinaryUserUpdateInfo",user, PostCfg)
        .success(function(data){
          if(data.code == 0){
            alert("修改成功！");
          }
          else{
            alert("修改失败");
          }
          d.resolve(data);
        });
        return d.promise;
      }
    };//service end
    service.profiles = {};

    return service;
  }
])

.factory('meetingService',['$http', '$q','PostCfg', function($http, $q, PostCfg){
  var service = {};

  service.add = function(meeting){
    var d = $q.defer();
    $http.post("/MeetingMng/api/v1/u/meeting/create",meeting, PostCfg)
    .success(function(data){
      if(data.code == 0){
        alert("创建成功！");
        window.location.href="/MeetingMng/u";
      }
      else{
        alert("创建失败");
      }
      d.resolve(data);
    });
    return d.promise;
  }

  service.delete = function(){

  }

  service.update = function(){

  }

  service.getAll = function(cellphone){
    // var cellphone = "15071345115";
    var d = $q.defer();
    $http.get("/MeetingMng/api/v1/u/meeting/findByUserId?cellphone="+cellphone)
    .success(function(data){
      // var obj;
      // if(data.code == 0){
      //   obj = $.parseJSON(data.meetinglist);
      //   // for(var i=0;i<obj.length;i++){
      //   //   obj[i].meetingStartTime = new Date(obj[i].meetingStartTime);
      //   // }
      // }
      d.resolve(data);
    });
    return d.promise;
  }

  service.findByMeetingId = function(meetingId){
    // var cellphone = "15071345115";
    var d = $q.defer();
    $http.get("/MeetingMng/api/v1/u/meeting/findByMeetingId?meetingId="+meetingId)
    .success(function(data){
      d.resolve(data);
    });
    return d.promise;
  }

  return service;
}])

.factory('meetingService',['$http', '$q','PostCfg', function($http, $q, PostCfg){
  var service = {};

  service.add = function(meeting){
    var d = $q.defer();
    $http.post("/MeetingMng/api/v1/u/meeting/create",meeting, PostCfg)
    .success(function(data){
      if(data.code == 0){
        alert("创建成功！");
        window.location.href="/MeetingMng/u";
      }
      else{
        alert("创建失败");
      }
      d.resolve(data);
    });
    return d.promise;
  }

  service.delete = function(){

  }

  service.update = function(){

  }

  service.getAll = function(cellphone){
    // var cellphone = "15071345115";
    var d = $q.defer();
    $http.get("/MeetingMng/api/v1/u/meeting/findByUserId?cellphone="+cellphone)
    .success(function(data){
      // var obj;
      // if(data.code == 0){
      //   obj = $.parseJSON(data.meetinglist);
      //   // for(var i=0;i<obj.length;i++){
      //   //   obj[i].meetingStartTime = new Date(obj[i].meetingStartTime);
      //   // }
      // }
      d.resolve(data);
    });
    return d.promise;
  }

  service.findByMeetingId = function(meetingId){
    // var cellphone = "15071345115";
    var d = $q.defer();
    $http.get("/MeetingMng/api/v1/u/meeting/findByMeetingId?meetingId="+meetingId)
    .success(function(data){
      d.resolve(data);
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
