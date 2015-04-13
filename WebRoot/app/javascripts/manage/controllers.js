// controllers.js
var mControllers = angular.module("mControllers", [])

.controller("companyCtrl", function($scope, CompanyData) {
  $scope.company = CompanyData;

})
 
.controller("homeCtrl", function($scope, CompanyData) {
   $scope.company = CompanyData;

})

.controller("profileCtrl", function($scope, CompanyData, userService) {
   $scope.company = CompanyData;
   $scope.userLogout = function(){
    userService.logout({
      username: $scope.company.username
    });
   }
})

.controller("departmanageCtrl", function($scope, departmentService, departsData, CompanyData) {
  $scope.company = CompanyData;
  $scope.departs = departsData;
  $scope.depart = {};
  $scope.currdepart ={
    text: $scope.company.companyName,
    id: "departmentID",
    nodes: $scope.departs,
    totalStuff: 102
  };
  
  function refreshTree(){
    $http.post('/MeetingMng/api/v1/manage/department/getAll', {
      companyId: $scope.company.username,
    }, PostCfg)
    .success(function(data){
        departsData = data.departsData;
    });

    $("#departstree").treeview({data: departsData});
  }
  
  $('#departstree').treeview({
    // data: departsData,
    onNodeSelected: function(event, data) {
      $scope.$apply(function(){
        $scope.currdepart.text = data.text;
        $scope.currdepart.nodes = data.nodes;
        $scope.currdepart.totalStuff = 4;
        $scope.currdepart.charge = "李全蛋";
      })
    }
  });

  refreshTree();

  $scope.submitAddForm = function(isValid){
    var department = {
      companyId: $scope.company.username,
      departmentName: $scope.depart.text,
      parentId: 1,
      depth:1
    }
    if(departmentService.add(department)){
      refreshTree();
    }
  }

  $scope.deleteForm = function(){
    var department = {
      companyId: $scope.company.username,
      departmentName: $scope.depart.text,
      parentId: 1,
      depth:1
    }
    if(departmentService.delete(department)){
      refreshTree();
    }
  }


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


