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
        $http({
          method: 'GET',
          url: "/MeetingMng/api/v1/findUserInfo?cellphone="+$cookieStore.get("cellphone"),
          timeout: 10000
        })
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
        })
        .error(function(data, status){
          d.resolve("timeout");
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

  service.delete = function(meetingId,cellphone){
    $http.post("/MeetingMng/api/v1/u/meeting/delete",{
      'meetingId':meetingId,
      'cellphone':cellphone
    }, PostCfg)
    .success(function(data){
      if(data.code == 0){
        alert("删掉了！哈哈哈！");
        window.location.href="/MeetingMng/u#/";
      }
      else if(data.code == -1){
        alert("数据库错误");
      }
      else if(data.code == -2){
        alert("权限不足");
      }
      else{
        alert("当前不能完成此会议");
      }
    });
  }

  service.finish = function(meetingId,cellphone){
    $http.post("/MeetingMng/api/v1/u/meeting/finish",{
      'meetingId':meetingId,
      'cellphone':cellphone
    }, PostCfg)
    .success(function(data){
      if(data.code == 0){
        alert("您成功结束此次会议！该会议将会被移入历史会议中~");
        window.location.href="/MeetingMng/u#/";
      }
      else if(data.code == -1){
        alert("数据库错误");
      }
      else if(data.code == -2){
        alert("权限不足");
      }
      else{
        alert("当前不能完成此会议");
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

///////////////////////////////////办会人

.factory('organizerService',['$http', '$q','PostCfg', function($http, $q, PostCfg){
  var service = {};

  //添加办会者
  service.addOrganizer = function(cellphone,meetingId,users){
    var d = $q.defer();
    $http.post("/MeetingMng/api/v1/u/meeting/addOrganizer",{
      'cellphone': cellphone,
      'meetingId': meetingId,
      'users': JSON.stringify(users)
    }, PostCfg)
    .success(function(data){
      d.resolve(data);
    });
    return d.promise;
  }
  //查找办会者列表
  service.findOrganizer = function(cellphone,meetingId){
    var d = $q.defer();
    $http.get("/MeetingMng/api/v1/u/meeting/findOrganizer?cellphone="+cellphone+"&meetingId="+meetingId)
    .success(function(data){
      if(data.code == 0){
        d.resolve($.parseJSON(data.organizers));  
      }
      else{
        d.resolve(null);
      }
      
    });
    return d.promise;
  }

  //删除办会者
  service.deleteOrganizer = function(cellphone,meetingId,users){
    var d = $q.defer();
    $http.post("/MeetingMng/api/v1/u/meeting/deleteOrganizer",{
      'cellphone': cellphone,
      'meetingId': meetingId,
      'users': JSON.stringify(users)
    }, PostCfg)
    .success(function(data){
      d.resolve(data);
    });
    return d.promise;
  }
  //更新办会者状态
  service.updateOrganizer = function(cellphone,meetingId,operatedCellphone,state){
    var d = $q.defer();
    $http.post("/MeetingMng/api/v1/u/meeting/updateOrganizer",{
      'cellphone': cellphone,
      'meetingId': meetingId,
      'operatedCellphone': operatedCellphone,
      'state': state
    }, PostCfg)
    .success(function(data){
      // if(data.code == 0){
      //   alert("更新成功！");
      //   window.location.reload();
      // }
      // else{
      //   alert("创建失败");
      // }
      d.resolve(data);
    });
    return d.promise;
  }
  //向办会者发送邀请
  service.inviteOrganizer = function(cellphone,meetingId,operatedCellphone){
    var d = $q.defer();
    $http.post("/MeetingMng/api/v1/u/meeting/inviteOrganizer",{
      'cellphone': cellphone,
      'meetingId': meetingId,
      'operatedCellphone': operatedCellphone
    }, PostCfg)
    .success(function(data){
      // if(data.code == 0){

      // }
      // else{
      //   // alert("创建失败");
      // }
      d.resolve(data);
    });
    return d.promise;
  }
  return service;
}])

///////////////////////////////////参会人

.factory('participatorService',['$http', '$q','PostCfg', function($http, $q, PostCfg){
  var service = {};

  //添加办会者
  service.addParticipator = function(cellphone,meetingId,users){
    var d = $q.defer();
    $http.post("/MeetingMng/api/v1/u/meeting/addParticipator",{
      'cellphone': cellphone,
      'meetingId': meetingId,
      'users': JSON.stringify(users)
    }, PostCfg)
    .success(function(data){
      d.resolve(data);
    });
    return d.promise;
  }
  //查找办会者列表
  service.findParticipator = function(cellphone,meetingId){
    var d = $q.defer();
    $http.get("/MeetingMng/api/v1/u/meeting/findParticipator?cellphone="+cellphone+"&meetingId="+meetingId)
    .success(function(data){
      if(data.code == 0){
        d.resolve($.parseJSON(data.participators));  
      }
      else{
        d.resolve(null);
      }
      
    });
    return d.promise;
  }

  //删除办会者
  service.deleteParticipator = function(cellphone,meetingId,users){
    var d = $q.defer();
    $http.post("/MeetingMng/api/v1/u/meeting/deleteParticipator",{
      'cellphone': cellphone,
      'meetingId': meetingId,
      'users': JSON.stringify(users)
    }, PostCfg)
    .success(function(data){
      d.resolve(data);
    });
    return d.promise;
  }
  //更新办会者状态
  service.updateParticipator = function(cellphone,meetingId,operatedCellphone,state){
    var d = $q.defer();
    $http.post("/MeetingMng/api/v1/u/meeting/updateParticipator",{
      'cellphone': cellphone,
      'meetingId': meetingId,
      'operatedCellphone': operatedCellphone,
      'state': state
    }, PostCfg)
    .success(function(data){
      // if(data.code == 0){
      //   alert("更新成功！");
      //   window.location.reload();
      // }
      // else{
      //   alert("创建失败");
      // }
      d.resolve(data);
    });
    return d.promise;
  }
  //向办会者发送邀请
  service.inviteParticipator = function(cellphone,meetingId,operatedCellphone){
    var d = $q.defer();
    $http.post("/MeetingMng/api/v1/u/meeting/inviteParticipator",{
      'cellphone': cellphone,
      'meetingId': meetingId,
      'operatedCellphone': operatedCellphone
    }, PostCfg)
    .success(function(data){
      // if(data.code == 0){

      // }
      // else{
      //   // alert("创建失败");
      // }
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
        // alert("创建成功！");
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
  service.comment = function(comment){
    var d = $q.defer();
    $http.post("/MeetingMng/api/v1/u/topic/comment",comment, PostCfg)
    .success(function(data){
      d.resolve(data);
    });
    return d.promise;
  }
  service.delete = function(topicId, creatorId){
    var d = $q.defer();
    $http.post("/MeetingMng/api/v1/u/topic/delete",{
      'topicId': topicId,
      'creatorId': creatorId
    }, PostCfg)
    .success(function(data){
      d.resolve(data);
    });
    return d.promise;
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

  service.findTopicById = function(topicId){
    var d = $q.defer();
    $http.get("/MeetingMng/api/v1/u/topic/findTopicById?topicId="+topicId)
    .success(function(data){
      if(data.obj){
        d.resolve($.parseJSON(data.obj));
      }
      else{
        d.resolve({});
      }
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
  service.findCommentByTopicId = function(topicId,meetingId,cellphone){
    var d = $q.defer();
    $http.get("/MeetingMng/api/v1/u/topic/findCommentByTopicId?topicId="+topicId+
      "&cellphone="+cellphone+'&meetingId='+meetingId)
    .success(function(data){
      if(data.code == 0){
        d.resolve($.parseJSON(data.comments));
      }
    });
    return d.promise;
  }

  return service;
}])




///////////////////////联系人
.factory('contactService',['$http', '$q','PostCfg', function($http, $q, PostCfg){
  var service = {};
  var _companyUsers = null;
  var _myContactors = null;
  //获取公司联系人列表  
  service.findCompanyContact = function(cellphone){
    var d = $q.defer();
    $http.get("/MeetingMng/api/v1/u/contact/findCompanyContact?cellphone="+cellphone)
    .success(function(data){
      if(data.code == 0){
        _companyUsers = $.parseJSON(data.companyContact);
        d.resolve(_companyUsers);
      }
    });
    return d.promise;
  }
  //添加办会人员时列表
  service.findCompanyContactForOrganizer = function(cellphone, meetingId){
    var d = $q.defer();
    $http.get("/MeetingMng/api/v1/u/contact/findCompanyContactForOrganizer?cellphone="+cellphone+
      "&meetingId="+meetingId)
    .success(function(data){
      if(data.code == 0){
        d.resolve($.parseJSON(data.companyContact));
      }
    });
    return d.promise;
  }
  //添加参会人员时列表
  service.findCompanyContactForParticipator = function(cellphone, meetingId){
    var d = $q.defer();
    $http.get("/MeetingMng/api/v1/u/contact/findCompanyContactForParticipator?cellphone="+cellphone+
      "&meetingId="+meetingId)
    .success(function(data){
      if(data.code == 0){
        d.resolve($.parseJSON(data.companyContact));
      }
    });
    return d.promise;
  }

  
  service.getCompanyUsers = function(){ return _companyUsers;}
  service.getMyContactors = function(){ return _myContactors;} 

  return service;
}])



///////////////////////消息处理
.factory('messageService',['$http', '$q','PostCfg', function($http, $q, PostCfg){
  var service = {};
 
  //获取公司联系人列表  
  service.findMsgList = function(cellphone){
    var d = $q.defer();
    $http.get("/MeetingMng/api/v1/u/message/findMsgList?username="+cellphone)
    .success(function(data){
      d.resolve($.parseJSON(data.list));
    });
    return d.promise;
  }

  //修改参会人员状态
  service.updateSate = function(cellphone,meetingId,state){
    var d = $q.defer();
    $http.post("/MeetingMng/api/v1/u/meeting/updateState",{
      "cellphone":cellphone,
      "meetingId":meetingId,
      "state":state
    }, PostCfg)
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