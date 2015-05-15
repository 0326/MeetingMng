// services.js
var mServices = angular.module("mServices", [])

.factory('userService',['$http', '$q', '$cookieStore', 'PostCfg',
  function($http, $q, $cookieStore, PostCfg){
    var service = {
  		logout: function(user){
        $http.post("/MeetingMng/api/v1/companyManagerLogout", user, PostCfg)
        .success(function(data){
          $cookieStore.remove("username");
          window.location.href = "/MeetingMng";
        });
      },
      getInfo: function(){
        var d = $q.defer();
        $http
        .get("/MeetingMng/api/v1/companyManagerGetInfo?username="+$cookieStore.get("username"))
        .success(function(data, status){
          service.profiles = $.parseJSON(data.user);
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
        $http.post("/MeetingMng/api/v1/companyManagerUpdatePass", {
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
      updateInfo: function(companyData){
        var d = $q.defer();
        var company = $.extend({},companyData);
        if(company.sex == 0){
          company.sex = false;
        }
        else{
          company.sex = true;
        }
        $http.post("/MeetingMng/api/v1/companyManagerUpdateInfo",company, PostCfg)
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

.factory('departmentService',['$http', '$q', 'PostCfg', 'userService', 
function($http, $q, PostCfg, userService){
  var service = {};
  var _departments = {};

  service.add = function(currdepart){
    var d = $q.defer();
    $http.post("/MeetingMng/api/v1/manage/department/add",{
      companyId: currdepart.username,
      departmentName: currdepart.departmentName,
      depth: currdepart.depth,
      parentId: currdepart.parentId
    }, PostCfg)
    .success(function(data){
      d.resolve(data.code);
    });
    return d.promise;
  };

  service.modify = function(currdepart){
    var d = $q.defer();
    $http.post("/MeetingMng/api/v1/manage/department/update",{
      departmentId: currdepart.departmentId,
      departmentName: currdepart.departmentName,
      depth: currdepart.depth,
      parentId: currdepart.parentId
    }, PostCfg)
    .success(function(data){
      d.resolve(data.code);
    });
    return d.promise;
  };

  service.delete = function(id){
    var d = $q.defer();
    $http.post("/MeetingMng/api/v1/manage/department/delete",{
      departmentId: id
    }, PostCfg)
    .success(function(data){
      d.resolve(data.code);
    });
    return d.promise;
  };

  service.getDepartments = function(){
    var d = $q.defer();
    $http.get("/MeetingMng/api/v1/manage/department/findByCompanyId?companyId="+userService.profiles.username)
    .success(function(data){
      _departments = data.departments;
      d.resolve(_departments);
    });
    return d.promise;
  }
  //将层级树结构转化成一级列表
  service.form2list = function(data){
    var list = [];//[{name:'部门名称',id:'部门id'},...]
    var p;        //父部门-子部门-全名

    (function recur(parent, data){
      for(var i = 0;i<data.length;i++){
        if(parent == null){
          p = data[i].text;
        }
        else{
          p = parent + '-' + data[i].text;
        }

        list.push({text:p, departmentId:data[i].departmentId});

        if(data[i].nodes && data[i].nodes.length > 0){
          recur(p, data[i].nodes);
        }
      }
    })(null, $.parseJSON(data));

    return list;
  }
  //根据部门id在所有部门中查询指定部门
  service.findDepartById = function(id, data){
    //添加的是一级部门
    if(id == -1){
      return {depth: 0};
    }
    var flag = false;
    var obj = null;
    (function recur(id,data){
      if(flag) return;
      for(var i=0;i<data.length;i++){
        if(data[i].departmentId == id){
          obj = data[i];
          flag = true;
          return;
        }
        if(data[i].nodes && data[i].nodes.length > 0){
          recur(id, data[i].nodes);
        }
      }
    })(id,$.parseJSON(data));

    return obj;
  }

  return service;
}])

.factory('StuffService',['$http', '$q', 'userService', 'PostCfg',
  function($http, $q, userService, PostCfg){
    var _stuffs = null;

    var service = {
      add: function(stuff){
        $http.post("/MeetingMng/api/v1/companyManagerAddOrdinaryUser", stuff, PostCfg)
        .success(function(data){
          if(data.code == 0){
            alert("添加成功");
            $("#addModal").modal('hide');
          }
          else{
            alert("添加失败");
          }
        });
      },
      searchByName: function(username,keywords,pageIndex){
        var d = $q.defer();
        $http.post("/MeetingMng/api/v1/companyManagerSearchName", {
          'username':username,
          'keywords':keywords,
          'pageIndex':pageIndex
        }, PostCfg)
        .success(function(data){
          var user =$.parseJSON(data.user);
            d.resolve(user);
          // if(data.code == 0){
            
          // }
          // else{
          //   // alert("删除失败");
          // }
        });
        return d.promise;
      },
      delete: function(stuff){
        $http.post("/MeetingMng/api/v1/companyManagerDeleteOrdinaryUser", stuff, PostCfg)
        .success(function(data){
          if(data.code == 0){
            // alert("添加成功");
            $("#passModal").modal('hide');
            window.location.href="/MeetingMng/manage#/stufflist";
          }
          else{
            alert("删除失败");
          }
        });
      },
      getStuff: function(cellphone,username){
        // if(_stuffs) return _stuffs;
        var d = $q.defer();
        $http.get("/MeetingMng/api/v1/companyManagerGetOrdinaryUserInfo?cellphone="+cellphone+"&username="+username)
        .success(function(data){
          if(data.code == 0){
            var user =$.parseJSON(data.user);
            if(user.sex == true){
              user.sex =0;
            }
            else{
              user.sex =1;
            }
            d.resolve(user);
          }
        });
        return d.promise;
      },
      getStuffs: function(pageIndex){
        // if(_stuffs) return _stuffs;
        var d = $q.defer();
        $http.get("/MeetingMng/api/v1/companyManagerGetStuffs?pageIndex="+pageIndex+"&username="+userService.profiles.username)
        .success(function(data){
          if(data.code == 0){
            _stuffs = $.parseJSON(data.stuffs);
            d.resolve(_stuffs);
          }
        });
        return d.promise;
      },
      updateStuff: function(stuff){
        var d = $q.defer();
        $http.post("/MeetingMng/api/v1/companyManagerUpdateOrdinaryUser",stuff, PostCfg)
        .success(function(data){
          if(data.code != '0'){
            alert("修改失败");
          }
          else{
            alert("修改成功！");
            d.resolve(data);
          }
        });
        return d.promise;
      }
    };//service end

    return service;
  }
])



//会议
.factory('meetingService',['$http', '$q','PostCfg', function($http, $q, PostCfg, userService){
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
        alert("未知错误");
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

  service.findMeetings = function(pageIndex, username){
    var d = $q.defer();
    $http.get("/MeetingMng/api/v1/c/meeting/findMeetings?pageIndex="+pageIndex+"&username="+username)
    .success(function(data){
      if(data.code == 0){
        d.resolve($.parseJSON(data.meetings));
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

.constant('PostCfg',{
  headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
  transformRequest: function(data) {
      return $.param(data);
  }
})
