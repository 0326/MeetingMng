var mControllers = angular.module("mControllers", [])

//layout是顶级父控制器，应用加载之前需要先初始化user个人数据
.controller("layoutCtrl", function($scope, $window, userService) {
  $scope.company = {};
  
  userService
  .getInfo()
  .then(function(data){
    $scope.company = data;
    $scope.$broadcast('userProfileBroadcast', $scope.company);
    $("#loading").fadeOut("normal",function(){
        $("#layout").fadeIn();
    });
    // $window.MMComet.initialize($scope.company.username);
    console.log("websocket init ok");
  })

  $scope.$on('userProfileChange',function(event, company){
    // console.log('this is in sidemenuCtrl:', company);
    $scope.company = company;
    $scope.$broadcast('userProfileBroadcast', company);
  });
  
})
 
.controller("homeCtrl", function($scope, userService,meetingService) {
  
  $scope.company = userService.profiles;
  $scope.meetinglist = [];
  $scope.statistics = {
    completed:"",
    allToday:"",
    todaySignedRate:"",
    avgSignedRate:""
  };                    //统计数据
  var initFunc = function(pageIndex){  
    meetingService
     .findMeetings(pageIndex,$scope.company.username)
     .then(function(data){
        $scope.meetinglist = data.list;
        $scope.paginationConf.totalItems = data.total;
     });
    meetingService
    .findCompeletedMeetingNumberByHistory($scope.company.username)
    .then(function(data){
      //历史完成会议
      $scope.statistics.completed = data.number;
    });
    meetingService
    .findMeetingNumberByToday($scope.company.username)
    .then(function(data){
      //本日所有会议
      $scope.statistics.allToday = data.number;
    });
    meetingService
    .findMeetingRateByToday($scope.company.username)
    .then(function(data){
      //今日完成会议
      $scope.statistics.todaySignedRate = data.rate;
    });
    meetingService
    .findMeetingRateByCompany($scope.company.username)
    .then(function(data){
      //平均签到率
      $scope.statistics.avgSignedRate = data.rate;
    });

  }
  
  initFunc(1);

  $scope.paginationConf = {
      currentPage: 1,
      // totalItems: 16,
      itemsPerPage: 10,
      pagesLength: 15,
      perPageOptions: [10, 20, 30, 40, 50],
      rememberPerPage: 'perPageItems',
      onChange: function(){
        initFunc(this.currentPage);
      }
  };

  //用户直接刷新网页的话需要重新获得user数据
  $scope.$on("userProfileBroadcast",function(event, company){
    $scope.company = company;
    // console.log("second:",$scope.company.username);
    initFunc(1);
  });
})

.controller("profileCtrl", function($scope, userService) {
  $scope.company = userService.profiles;
  userService
  .getInfo()
  .then(function(data){
    $scope.company = data;
  })

  $scope.userLogout = function(){
    userService.logout({username: $scope.company.username});
  }

  $scope.updatePass = function(){
    userService.updatePass($scope.company.username,$scope.company.password,$scope.company.newpassword);
  }

  $scope.updateInfo = function(){
    $scope.company.avatarUrl = $("#headimg100")[0].src;
    userService.updateInfo($scope.company)
    .then(function(data){
      if(data.code == 0){
        console.log('profileCtrl change data:', $scope.company);
        $scope.$emit('userProfileChange',$scope.company);
      }
    });
  }

})

.controller("departmanageCtrl", function($scope, departmentService, userService) {
  $scope.company = userService.profiles; //公司用户信息
  $scope.departlist = {}; //公司所有部门
  $scope.selectlist = []; // 添加/编辑部门下拉列表数据
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

.controller("meetingcreateCtrl", function($scope) {

})

.controller("meetinglistCtrl", function($scope, userService, meetingService) {

  $scope.company = userService.profiles;
  $scope.meetinglist = [];

  var initFunc = function(pageIndex){  
    meetingService
     .findMeetings(pageIndex,$scope.company.username)
     .then(function(data){
        $scope.meetinglist = data.list;
        $scope.paginationConf.totalItems = data.total;
     });
  }
  
  initFunc(1);

  $scope.paginationConf = {
      currentPage: 1,
      // totalItems: 16,
      itemsPerPage: 10,
      pagesLength: 15,
      perPageOptions: [10, 20, 30, 40, 50],
      rememberPerPage: 'perPageItems',
      onChange: function(){
        initFunc(this.currentPage);
      }
  };

  //用户直接刷新网页的话需要重新获得user数据
  $scope.$on("userProfileBroadcast",function(event, company){
    $scope.company = company;
    // console.log("second:",$scope.company.username);
    initFunc(1);
  });
})

.controller("stufflistCtrl", function($scope, userService, StuffService, departmentService) {
  $scope.company = userService.profiles;
  $scope.departlist = {};
  $scope.selectlist = {};
  $scope.currStuff = {};
  $scope.keywords = "";
  var initFunc = function(pageIndex){  
    StuffService
    .getStuffs(pageIndex)
    .then(function(data){
      $scope.stuffs = data.list;
      $scope.paginationConf.totalItems = data.total;
      // console.log("stuffs:",data);
    });
  }
  initFunc(1);

  departmentService
  .getDepartments()
  .then(function(data){
    $scope.departlist = data;
    $scope.selectlist = departmentService.form2list($scope.departlist);
    console.log($scope.departlist);
  });

  $scope.searchStuff = function(){
    StuffService
    .search($scope.company.username,$scope.keywords,1)
    .then(function(data){
      // $scope.paginationConf.totalItems = data.total;
      $scope.stuffs = data.list;
    });
  }
  
  $scope.onUpdate = function(cellphone){
    StuffService
    .getStuff(cellphone,$scope.company.username)
    .then(function(data){
      $scope.currStuff = data;
    });
  }

  $scope.submitUpdateForm = function(isValid){
    $scope.currStuff.username = userService.profiles.username;

    StuffService.updateStuff($scope.currStuff);
  }

  $scope.paginationConf = {
      currentPage: 1,
      // totalItems: 16,
      itemsPerPage: 10,
      pagesLength: 15,
      perPageOptions: [10, 20, 30, 40, 50],
      rememberPerPage: 'perPageItems',
      onChange: function(){
        initFunc(this.currentPage);
      }
  };

  //用户直接刷新网页的话需要重新获得user数据
  $scope.$on("userProfileBroadcast",function(event, company){
    $scope.company = company;
    // console.log("second:",$scope.company.username);
    initFunc(1);
  });
})

.controller("stuffioCtrl", function($scope, userService, departmentService, StuffService) {
  $scope.company = userService.profiles;

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
    avatarUrl: 'http://7u2j45.com1.z0.glb.clouddn.com/o_19lablf7o1b103vbi2kln615977.png-head100',
    officeLocation: null,
    workNo: null
  };

  $scope.submitAddForm = function(isValid){
    StuffService.add($scope.stuff);
  }

})

.controller("stuffdetailCtrl", function($scope, userService, StuffService, userId) {
  $scope.company = userService.profiles;
  $scope.currStuff = {};
  function initFunc(){
    StuffService
    .getStuff(userId,$scope.company.username)
    .then(function(data){
      $scope.currStuff = data;
    })

  }

  initFunc();

  $scope.updateInfo = function(){
    StuffService
    .updateStuff($scope.currStuff)
    .then(function(data){
      if(data.code == 0){

      }
    });
  }
  $scope.submitDelete = function(){
    StuffService.delete($scope.currStuff);
  }
  //用户直接刷新网页的话需要重新获得user数据
  $scope.$on("userProfileBroadcast",function(event, company){
    $scope.company = company;
    initFunc();
  });


})

.controller("statmeetingCtrl", function($scope) {

})

.controller("statstuffCtrl", function($scope) {

})

