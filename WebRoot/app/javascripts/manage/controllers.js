// controllers.js
var mControllers = angular.module("mControllers", [])

.controller("sidemenuCtrl", function($scope, $cookieStore, userService, CompanyData) {
  $scope.company = CompanyData.getAll();
  //监听事件，随时广播
  $scope.$on('CompanyDataChange',function(event, company){
    console.log('this is in sidemenuCtrl:', company);
    $scope.company = company;
    $scope.$broadcast('CompanyDataBroadcast', company);
  });
})
 
.controller("homeCtrl", function($scope, CompanyData) {
   $scope.company = CompanyData.getAll();

})

.controller("profileCtrl", function($scope, CompanyData, userService) {
   $scope.company = CompanyData.getAll();
   // console.log($scope.company);
   $scope.userLogout = function(){
    userService.logout({
      username: $scope.company.username
    });
   }

   $scope.updatePass = function(){
    userService.updatePass($scope.company.username,$scope.company.password,$scope.company.newpassword);
   }

   $scope.updateInfo = function(){
    $scope.company.avatarUrl = $("#headimg100")[0].src;
    // console.log($scope.company);
    userService.updateInfo($scope.company)
    .then(function(data){
      CompanyData.setAvatarUrl($scope.company.avatarUrl);
      CompanyData.setCellphone($scope.company.cellphone);
      CompanyData.setEmail($scope.company.email);
      CompanyData.setName($scope.company.name);
      CompanyData.setOfficeLocation($scope.company.officeLocation);
      CompanyData.setOfficePhone($scope.company.officePhone);
      CompanyData.setSex($scope.company.sex);  

      console.log('profileCtrl change data:', $scope.company);
      $scope.$emit('CompanyDataChange',$scope.company);
      // console.log(CompanyData);
    });
   }

})

.controller("departmanageCtrl", function($scope, departmentService, CompanyData) {
  $scope.company = CompanyData.getAll(); //公司用户信息
  $scope.departlist = {}; //公司所有部门
  $scope.newdepart = {
    username: $scope.company.username
  };//添加的新部门信息
  $scope.currdepart ={
    username: $scope.company.username,
    departmentName: $scope.company.companyName,
    departmentId: -1,
    parentId: -1,
    depth: 0
  };//当前操作部门,初始化为整个公司信息

  departmentService
  .getDepartments()
  .then(function(data){
    //初始化departlist
    $scope.departlist = data;
    //初始化部门树形图
    $('#departstree').treeview({
      data: $scope.departlist,
      onNodeSelected: function(event, data) {
        $scope.$apply(function(){
          $scope.currdepart.departmentName = data.text;
          $scope.currdepart.departmentId = data.departmentId;
          $scope.currdepart.parentId = data.parentId;
          $scope.currdepart.depth = data.depth;

          $scope.currdepart.nodes = data.nodes;
          $scope.currdepart.totalStuff = 40;
          $scope.currdepart.charge = "李全蛋";

          $scope.newdepart.depth = $scope.currdepart.depth + 1;
          $scope.newdepart.parentId = $scope.currdepart.departmentId;

          console.log($scope.currdepart);
        })
      }
    });
    console.log($scope.departlist);
  });

  // departmentService.refreshTree($scope.company.username);
  $scope.submitAddForm = function(isValid){
    departmentService.add($scope.newdepart);
    console.log($scope.newdepart);
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