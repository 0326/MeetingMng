// controllers.js
var mControllers = angular.module("mControllers", [])

.controller("companyCtrl", function($scope, CompanyData) {
  $scope.company = CompanyData;

})
 
.controller("homeCtrl", function($scope, CompanyData) {
   $scope.company = CompanyData;

})

.controller("profileCtrl", function($scope, CompanyData, PostCfg) {
   $scope.company = CompanyData;
   $scope.userLogout = function(){
    $http.post("/MeetingMng/api/v1/companyManagerLogout",{
      username: document.cookie
    },PostCfg).success(function(data){

    });
   }
})

.controller("departmanageCtrl", function($scope, $http, departsData, CompanyData, PostCfg) {
  $scope.company = CompanyData;
  $scope.departs = departsData;
  $scope.depart = {};
  $scope.currdepart ={
    text: $scope.company.companyName,
    nodes: $scope.departs,
    totalStuff: 102
  };
  
  $scope.submitAddForm = function(isValid){
    var department = {
      companyId: "testcompany",
      departmentName: $scope.depart.text,
      parentId: 1,
      depth:1
    }

    $http.post('/MeetingMng/api/v1/manage/department/add', department, PostCfg)
    .success(function(data){
        console.log(data);
    });
    
  }

  $('#departstree').treeview({
    data: departsData,
    onNodeSelected: function(event, data) {
      $scope.$apply(function(){
        $scope.currdepart.text = data.text;
        $scope.currdepart.nodes = data.nodes;
        $scope.currdepart.totalStuff = 4;
        $scope.currdepart.charge = "李全蛋";
      })
    }
  });

})

.controller("meetingcreateCtrl", function($scope, CompanyData) {

})

.controller("meetinglistCtrl", function($scope, CompanyData) {

})

.controller("stufflistCtrl", function($scope, CompanyData) {
  $scope.company = CompanyData;

  $scope.stuffs = [
    {name: "Bob",depart:"技术部"},
    {name: "Lili",depart:"市场部"},
    {name: "Perl",depart:"技术部"},
    {name: "Python",depart:"技术部"}];

  $scope.getFormData = function(){
    console.log($scope.user);
  }
})

.controller("stuffioCtrl", function($scope, CompanyData) {
  $scope.company = CompanyData;

})

.controller("statmeetingCtrl", function($scope, CompanyData) {

})

.controller("statstuffCtrl", function($scope, CompanyData) {

})

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