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
      getStuff: function(cellphone){
        // if(_stuffs) return _stuffs;
        var d = $q.defer();
        $http.get("/MeetingMng/api/v1/companyManagerGetOrdinaryUserInfo?cellphone="+cellphone+"&username="+userService.profiles.username)
        .success(function(data){
          if(data.code == 0){
            d.resolve($.parseJSON(data.user));
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

.constant('PostCfg',{
  headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
  transformRequest: function(data) {
      return $.param(data);
  }
})
