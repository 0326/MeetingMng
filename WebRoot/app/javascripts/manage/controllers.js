// controllers.js
var mControllers = angular.module("mControllers", [])

.controller("sidemenuCtrl", function($scope, $cookieStore, userService, CompanyData) {
  $scope.company = CompanyData.getAll();
  //CompanyData监听事件，随时广播
  $scope.$on('CompanyDataChange',function(event, company){
    console.log('this is in sidemenuCtrl:', company);
    $scope.company = company;
    $scope.$broadcast('CompanyDataBroadcast', company);
  });
})
 
.controller("homeCtrl", function($scope, $cookieStore, CompanyData, userService) {
  userService
  .getInfo($cookieStore.get('username'))
  .then(function(data){
    $("#loading").text("数据加载完成！");
    CompanyData.setAll(data);
    $scope.company = CompanyData.getAll();
  });
  window.MMComet.initialize($cookieStore.get('username'));
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
  $scope.selectlist = [{text:'hello',id:'this id '}]; // 添加/编辑部门下拉列表数据
  $scope.newdepart = {username: $scope.company.username};//添加的新部门信息
  $scope.currdepart ={
    username: $scope.company.username,
    departmentName: $scope.company.companyName,
    departmentId: -1,
    parentId: -1,
    depth: 0
  };//当前操作部门,初始化为整个公司信息

  var _resetDepartTree = function(){
    departmentService
    .getDepartments()
    .then(function(data){
      $scope.departlist = data;
      $('#departstree').treeview({
        data: $scope.departlist,
        onNodeSelected: function(event, data) {
          _setCurrdepart(data);
        }
      });
      $scope.selectlist = departmentService.form2list($scope.departlist);
      console.log($scope.departlist);
    });
  };

  var _setCurrdepart = function(data){
    $scope.$apply(function(){
      $scope.currdepart.departmentName = data.text;
      $scope.currdepart.departmentId = data.departmentId;
      $scope.currdepart.parentId = data.pid;
      $scope.currdepart.depth = data.depth;

      $scope.currdepart.nodes = data.nodes;
      $scope.currdepart.totalStuff = 40;
      $scope.currdepart.charge = "李全蛋";
    })
  };
  _resetDepartTree();

  $scope.submitAddForm = function(isValid){
    var pa = departmentService.findDepartById(
      $scope.newdepart.parentId == undefined ? -1 : $scope.newdepart.parentId,
      $scope.departlist
    );
    $scope.newdepart.depth =pa.depth +1;    
    // $scope.newdepart.parentId = pa.departmentId;
    console.log('add:',$scope.newdepart);
    departmentService
    .add($scope.newdepart)
    .then(function(code){
      if( code == '0'){
        alert("添加成功！");
        $("#addModal").modal('hide');
        _resetDepartTree();
        // service.refreshTree(currdepart.username);
      }
      else{
        alert("添加失败");
      }
    });
  };

  $scope.submitModifyForm = function(isValid){

    departmentService
    .modify($scope.currdepart)
    .then(function(code){
      if( code == '0'){
        alert("修改成功！");
        $("#modifyModal").modal('hide');
        _resetDepartTree();
        // service.refreshTree(currdepart.username);
      }
      else{
        alert("修改失败");
      }
    });
  };

  $scope.submitDelete = function(isValid){
    departmentService
    .delete($scope.currdepart.departmentId)
    .then(function(code){
      if( code == '0'){
        alert("删除成功！");
        _resetDepartTree();
      }
      else{
        alert("该部门下还有员工，无法删除！请先转移下属员工到其他部门再删除。");
      }
    });
  };


})

.controller("meetingcreateCtrl", function($scope, CompanyData) {

})

.controller("meetinglistCtrl", function($scope, CompanyData) {

})

.controller("stufflistCtrl", function($scope, CompanyData, StuffService) {
  $scope.company = CompanyData.getAll();

  // $scope.stuffs = [
  //   {name: "Bob",depart:"技术部"},
  //   {name: "Lili",depart:"市场部"},
  //   {name: "Perl",depart:"技术部"},
  //   {name: "Python",depart:"技术部"}];
  StuffService
  .getStuffs()
  .then(function(data){
    $scope.stuffs = data;
    console.log("stuffs:",$scope.stuffs);
  });
})

.controller("stuffioCtrl", function($scope, CompanyData, departmentService, StuffService) {
  $scope.company = CompanyData.getAll();
  departmentService
    .getDepartments()
    .then(function(data){
      $scope.departlist = departmentService.form2list(data);
      console.log($scope.departlist);
    });

  $scope.stuff = {
    username: $scope.company.username,
    departmentId:null,
    cellphone:null,
    name: null,
    email:null,
    sex: 0,
    officePhone:null,
    job: null,
    avatarUrl: 'app/images/headimg.jpg',
    officeLocation: null,
    workNo: null
  };

  $scope.submitAddForm = function(isValid){
    StuffService.add($scope.stuff);
  }


})

.controller("statmeetingCtrl", function($scope, CompanyData) {

})

.controller("statstuffCtrl", function($scope, CompanyData) {

})

