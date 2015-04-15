// services.js
var mServices = angular.module("mServices", [])

.factory('userService',['$http', '$q', '$cookieStore', 'PostCfg', 
  function($http, $q, $cookieStore, PostCfg){
  	return {
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
        $http.post("/MeetingMng/api/v1/companyManagerUpdateInfo",company, PostCfg)
        .success(function(data){
          if(data.code != '0'){
            alert("修改失败");
          }
          else{
            alert("修改成功！");
          
          }
        });
      }
  	};
  }
])

.factory('departmentService',['$http', '$q', 'PostCfg', 'CompanyData', 
function($http, $q, PostCfg, CompanyData){
  var service = {};
  var _departments = {};

  service.add = function(currdepart){
    $http.post("/MeetingMng/api/v1/manage/department/add",{
      companyId: currdepart.username,
      departmentName: currdepart.departmentName,
      depth: currdepart.depth,
      parentId: currdepart.parentId
    }, PostCfg)
    .success(function(data){
      if(data.code == '0'){
        service.refreshTree(currdepart.username);
        alert("添加成功！");
        $("#addModal").modal('hide');

      }
      else{
        alert("添加失败");
      }
    });
  };
  
  service.refreshTree = function(username){
    service.getDepartments().then(function(data){
      _departments = data.departments;
      $("#departstree").treeview({data: _departments});
    });
  }

  service.getDepartments = function(){
    var d = $q.defer();
    $http.post("/MeetingMng/api/v1/manage/department/findByCompanyId",{
      companyId: CompanyData.getUsername(),
    }, PostCfg)
    .success(function(data){
      d.resolve(data.departments);
      _departments = data.departments;
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

// .constant('departsData',[
//   {
//     text: "研发部",
//     icon: "glyphicon glyphicon-stop",
//     backColor: "#eee",
//     href: "#node",
//     nodes: [
//       {
//         text: "移动客户端开发",
//         nodes: [
//           {
//             text: "安卓组"
//           },
//           {
//             text: "IOS组"
//           }
//         ]
//       },
//       {
//         text: "后台开发"
//       }
//     ]
//   },
//   {
//     text: "财务部",
//     backColor: "#eee",
//     href: "#node",
//     nodes: [
//       {text: "财务组"}
//     ]
//   },
//   {
//     text: "运营部",
//     backColor: "#eee",
//     href: "#node"
//   },
// ])