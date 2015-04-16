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
      getInfo: function(username){
        var d = $q.defer();
        $http.get("/MeetingMng/api/v1/companyManagerGetInfo?username="+username)
        .success(function(data, status){
          d.resolve(data.user);
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
          if(data.code != '0'){
            alert("修改密码失败");
          }
          else{
            alert("修改成功！");
            $("#passModal").modal('hide');
          }
        });
      },
      updateInfo: function(company){
        var d = $q.defer();
        $http.post("/MeetingMng/api/v1/companyManagerUpdateInfo",company, PostCfg)
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

.factory('departmentService',['$http', '$q', 'PostCfg', 'CompanyData', 
function($http, $q, PostCfg, CompanyData){
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
    $http.get("/MeetingMng/api/v1/manage/department/findByCompanyId?companyId="+CompanyData.getUsername())
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

.constant('PostCfg',{
  headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
  transformRequest: function(data) {
      return $.param(data);
  }
})
