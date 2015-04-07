// controllers.js
var mControllers = angular.module("mControllers", [])

.controller("companyCtrl", function($scope, CompanyData) {
  $scope.company = CompanyData;

})

.controller("homeCtrl", function($scope, CompanyData) {
   $scope.company = CompanyData;

})

.controller("profileCtrl", function($scope, CompanyData) {
   $scope.company = CompanyData;

})

.controller("departmanageCtrl", function($scope, departsData, CompanyData) {
  $scope.company = CompanyData;
  $scope.departs = departsData;
  $scope.currdepart ={};
  $scope.currdepart.text ="sss";
  $scope.changeDepart = function(data){
    $scope.currdepart = data;
      console.log($scope.currdepart.text);
  }

  $('#departstree').treeview({
    data: departsData,
    onNodeSelected: function(event, data) {
      $scope.changeDepart(data);
      // $scope.currdepart = data;
      // $scope.currdepart.nodes = data.nodes;
      $scope.currdepart.charge = "李全蛋";
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