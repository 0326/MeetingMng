// controllers.js
var mControllers = angular.module("mControllers", [])


.controller("homeCtrl", function($scope, loginService) {
  $scope.user = {
    username: "13026310448",
    password: "123456",
    usertype: "0"
  }
  $scope.submitLoginForm = function(isValid){
    loginService.login($scope.user);
  }
})

.controller("registCtrl", function($scope, registerService, CityData, IndustyData) {
	
  var vm = $scope.vm = {};
  var user = $scope.user = {};
  var vcode = new vCode($(".vcode-box")[0]);
  vm.industies = IndustyData;
  vm.provinces = CityData;

  $scope.$watch('vm.province', function(province) {
    vm.city = null;
  });

  $scope.submitCompanyForm = function(isValid){
    if(!vcode.verify($scope.inputcode)){
        alert("验证码错误");
        return;
    }
    if( user.password != user.repassword){
        alert("两次密码不一致");
        return;
    }
    if(isValid){
        registerService.register({
            'username': user.username,
            'password': user.password,
            'type': vm.industy,
            'location': vm.city,
            'companyName':user.companyName
        });
    }
  };
  //发送短信验证码
  $scope.submitUserForm1 = function(idValid){
    registerService.userStep1({
      'cellphone':user.username
    });
  }
  //提交注册信息
  $scope.submitUserForm2 = function(isValid){
    registerService.userStep2({
      'cellphone': user.cellphone,
      'companyId': user.companyId,
      'name': user.name,
      'password': user.password
    });
  }
})

.controller("callemailCtrl", function($scope) {

})

.controller("activitionCtrl", function($scope, $route, activateService) {
    // console.log($route.current.params);
    $scope.activeSuccess = true;
    activateService.emailActivate({
        uid:$route.current.params.uid,
        aid:$route.current.params.aid
    });
})

.controller("docCtrl", function($scope) {

})
