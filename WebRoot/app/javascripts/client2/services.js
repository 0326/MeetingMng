// client services.js
var mServices = angular.module("mServices", [])


//个人用户
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


//会议
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

  service.delete = function(meetingId){
    $http.post("/MeetingMng/api/v1/u/meeting/delete",{'meetingId':meetingId}, PostCfg)
    .success(function(data){
      if(data.code == 0){
        alert("删掉了！哈哈哈！");
      }
      else{
        alert("未知错误");
      }
    });
  }

  service.update = function(meeting){
    $http.post("/MeetingMng/api/v1/u/meeting/update",meeting, PostCfg)
    .success(function(data){
      if(data.code == 0){
        alert("更新成功！哈哈哈！");
        $("#detailBox").removeClass("hide");
        $("#updateBox").addClass("hide");
      }
      else{
        alert("未知错误");
      }
    });
  }

  service.getAll = function(cellphone,meetingState,listType){
    // var cellphone = "15071345115";
    var d = $q.defer();
    $http.get("/MeetingMng/api/v1/u/meeting/findMeetingList?cellphone="+cellphone+
      "&meetingState="+meetingState+"&listType="+listType)
    .success(function(data){
      if(data.code == 0){
        d.resolve($.parseJSON(data.meetinglist));  
      }
      else{
        d.resolve("[]");
      }
      
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


//话题
.factory('topicService',['$http', '$q','PostCfg', function($http, $q, PostCfg){
  var service = {};

  service.create = function(topic){
    var d = $q.defer();
    $http.post("/MeetingMng/api/v1/u/topic/create",topic, PostCfg)
    .success(function(data){
      if(data.code == 0){
        alert("创建成功！");
        $('#createModal').foundation('reveal', 'close');
        // window.location.href="/MeetingMng/u";
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
    $http.get("/MeetingMng/api/v1/u/topic/findByUserId?cellphone="+cellphone)
    .success(function(data){
      d.resolve(data);
    });
    return d.promise;
  }

  service.findTopicList = function(meetingId,cellphone){
    // var cellphone = "15071345115";
    var d = $q.defer();
    $http.get("/MeetingMng/api/v1/u/topic/findTopicByMeetingId?meetingId="+meetingId+
      "&cellphone="+cellphone)
    .success(function(data){
      d.resolve($.parseJSON(data.obj));
    });
    return d.promise;
  }
  //获取评论列表
  service.findCommentByTopicId = function(topicId,cellphone){
    var d = $q.defer();
    $http.get("/MeetingMng/api/v1/u/topic/findCommentByTopicId?topicId="+topicId+
      "&cellphone="+cellphone)
    .success(function(data){
      if(data.code == 0){
        d.resolve($.parseJSON(data.comments));
      }
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
