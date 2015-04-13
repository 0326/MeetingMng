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

.factory('departmentService',['$http', 'PostCfg', function($http,PostCfg){
  return {
    add: function(department){
      var flag = false;
      $http.post('/MeetingMng/api/v1/manage/department/add', department, PostCfg)
      .success(function(data){
          console.log(data);
          // refreshTree();
          flag = true;
      });
      return flag;
    },
    delete: function(department){
      var flag = false;
      $http.post('/MeetingMng/api/v1/manage/department/delete',$scope.currdepart, PostCfg)
      .success(function(data){
        // refreshTree();
        flag = true;
      });
      return flag;
    }
  };
}])

.constant('PostCfg',{
  headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
  transformRequest: function(data) {
      return $.param(data);
  }
})

.constant('departsData',[
    {
      text: "研发部",
      icon: "glyphicon glyphicon-stop",
      backColor: "#eee",
      href: "#node",
      nodes: [
        {
          text: "移动客户端开发",
          nodes: [
            {
              text: "安卓组"
            },
            {
              text: "IOS组"
            }
          ]
        },
        {
          text: "后台开发"
        }
      ]
    },
    {
      text: "财务部",
      backColor: "#eee",
      href: "#node",
      nodes: [
        {text: "财务组"}
      ]
    },
    {
      text: "运营部",
      backColor: "#eee",
      href: "#node"
    },
  ])