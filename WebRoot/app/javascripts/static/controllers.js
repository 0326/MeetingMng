// controllers.js
var mControllers = angular.module("mControllers", [])


.controller("homeCtrl", function($scope, $http, PostCfg) {
  $scope.user = {
    username: "1833559609@qq.com",
    password: "123456",
    usertype: "1",
    autoLogin: false
  }
  $scope.submitLoginForm = function(isValid){
    console.log($scope.user);
    if($scope.user.usertype === "1"){
        $http.post('/MeetingMng/api/v1/companyManagerLogin',$scope.user)
        .success(function(data){
            if(data.code == 0){
                window.location.href="/MeetingMng/manage";
                document.cookie = data.username;
            }
            else{
                alert("用户名或密码错误");
            }
        });
    }
    else{
        $http.post('/MeetingMng/api/v1/oridinaryManagerRegister',$scope.user)
        .success(function(data){
            window.location.href="/MeetingMng/manage";
        });
    }
  }
})

.controller("registCtrl", function($scope,$http, PostCfg, CityData, IndustyData) {
	
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
        // alert("ok");
        // alert([user.username,user.password,user.companyName,vm.province,
        //     vm.city,vm.industy].join(";"));
        // console.log(vm);
        $http.post('/MeetingMng/api/v1/companyManagerRegister',{
            'username': user.username,
            'password': user.password,
            'type': vm.industy,
            'location': vm.city,
            'companyName':user.companyName
        }, PostCfg).success(function(data){
            switch(data.code){
                case "10401":
                    alert("该用户名已存在");
                    break;
                case "10402":
                    alert("该公司名已存在");
                    break;
                case "10403":
                    alert("请选择正确的行业类型");
                    break;
                case "10404":
                    alert("请选择正确的公司地理位置");
                    break;
                case "10408":
                    alert("邮件发送失败。。。");
                    break;
                case "0":
                    alert("恭喜您注册成功");
                    $(".company-regist-form").fadeOut();
                    $(".email-box").fadeIn();
                    break;
                default:
                    alert("您注册的姿势不对，请按太阳穴，轮刮眼眶。")
            }
        });
    }
  };
})

.constant('PostCfg',{
    headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
    transformRequest: function(data) {
        return $.param(data);
    }
  })

.constant('IndustyData',[
  {"category":"IT行业","name":"计算机软件","code":010001},
  {"category":"IT行业","name":"计算机硬件","code":010002},
  {"category":"IT行业","name":"IT服务","code":010003},
  {"category":"IT行业","name":"互联网","code":010004},
  {"category":"IT行业","name":"电子商务","code":010005},
  {"category":"IT行业","name":"游戏","code":010006},
  {"category":"IT行业","name":"通信","code":010007},
  {"category":"IT行业","name":"电子/半导体","code":010008},
  {"category":"金融行业","name":"银行","code":020001},
  {"category":"金融行业","name":"保险","code":020002},
  {"category":"金融行业","name":"证券/基金/期货","code":020003},
  {"category":"金融行业","name":"投资","code":020004},
  {"category":"专业服务","name":"会计/审计","code":030001},
  {"category":"专业服务","name":"人力资源","code":030002},
  {"category":"专业服务","name":"管理咨询","code":030003},
  {"category":"专业服务","name":"法律","code":030004},
  {"category":"专业服务","name":"检测/认证","code":030005},
  {"category":"专业服务","name":"翻译","code":030006},
  {"category":"教育培训行业","name":"高等教育","code":040001},
  {"category":"教育培训行业","name":"初中等教育","code":040002},
  {"category":"教育培训行业","name":"培训","code":040003},
  {"category":"教育培训行业","name":"高等教育","code":050001},
  {"category":"教育培训行业","name":"初中等教育","code":050002},
  {"category":"教育培训行业","name":"培训","code":050003},
  {"category":"消费品行业","name":"日用品/化妆品","code":060001},
  {"category":"消费品行业","name":"食品/饮料","code":060002},
  {"category":"消费品行业","name":"服装/纺织","code":060003},
  {"category":"消费品行业","name":"家电/数码产品","code":060004},
  {"category":"消费品行业","name":"奢侈品/珠宝","code":060005},
  {"category":"消费品行业","name":"酒品","code":060006},
  {"category":"消费品行业","name":"烟草业","code":060007},
  {"category":"消费品行业","name":"办公用品","code":060008},
  {"category":"文化传媒行业","name":"广告/公关/会展","code":070001},
  {"category":"文化传媒行业","name":"报纸/杂志","code":070002},
  {"category":"文化传媒行业","name":"广播","code":070003},
  {"category":"文化传媒行业","name":"影视","code":070004},
  {"category":"文化传媒行业","name":"出版","code":070005},
  {"category":"文化传媒行业","name":"艺术/工艺","code":070006},
  {"category":"文化传媒行业","name":"体育","code":070007},
  {"category":"文化传媒行业","name":"动漫","code":070008},
  {"category":"建筑/房地产行业","name":"建筑设计/规划","code":080001},
  {"category":"建筑/房地产行业","name":"土木工程","code":080002},
  {"category":"建筑/房地产行业","name":"房地产","code":080003},
  {"category":"建筑/房地产行业","name":"物业管理","code":080004},
  {"category":"建筑/房地产行业","name":"建材","code":080005},
  {"category":"建筑/房地产行业","name":"装修装潢","code":080006},
  {"category":"贸易物流行业","name":"进出口","code":090001},
  {"category":"贸易物流行业","name":"批发/零售","code":090002},
  {"category":"贸易物流行业","name":"商店/超市","code":090003},
  {"category":"贸易物流行业","name":"物流/仓储","code":090004},
  {"category":"贸易物流行业","name":"运输/铁路/航空","code":090005},
  {"category":"服务业","name":"酒店","code":100001},
  {"category":"服务业","name":"餐饮","code":100002},
  {"category":"服务业","name":"旅游","code":100003},
  {"category":"服务业","name":"休闲/娱乐/健身","code":100004},
  {"category":"服务业","name":"私人/家政服务","code":100005},
  {"category":"其他","name":"环境","code":110001},
  {"category":"其他","name":"农/林/牧/渔","code":110002},
  {"category":"其他","name":"研究所/研究院","code":110003},
  {"category":"其他","name":"公共事业","code":110004},
  {"category":"其他","name":"非营利组织","code":110005},
  {"category":"其他","name":"政府部门","code":110006},
  {"category":"其他","name":"其他","code":110007}
])

.constant('CityData',[
        {
            "province": "北京",
            "cities": [
                {
                    "name": "北京",
                    "code": "101010100"
                },
                {
                    "name": "朝阳",
                    "code": "101010300"
                },
                {
                    "name": "顺义",
                    "code": "101010400"
                },
                {
                    "name": "怀柔",
                    "code": "101010500"
                },
                {
                    "name": "通州",
                    "code": "101010600"
                },
                {
                    "name": "昌平",
                    "code": "101010700"
                },
                {
                    "name": "延庆",
                    "code": "101010800"
                },
                {
                    "name": "丰台",
                    "code": "101010900"
                },
                {
                    "name": "石景山",
                    "code": "101011000"
                },
                {
                    "name": "大兴",
                    "code": "101011100"
                },
                {
                    "name": "房山",
                    "code": "101011200"
                },
                {
                    "name": "密云",
                    "code": "101011300"
                },
                {
                    "name": "门头沟",
                    "code": "101011400"
                },
                {
                    "name": "平谷",
                    "code": "101011500"
                },
                {
                    "name": "八达岭",
                    "code": "101011600"
                },
                {
                    "name": "佛爷顶",
                    "code": "101011700"
                },
                {
                    "name": "汤河口",
                    "code": "101011800"
                },
                {
                    "name": "密云上甸子",
                    "code": "101011900"
                },
                {
                    "name": "斋堂",
                    "code": "101012000"
                },
                {
                    "name": "霞云岭",
                    "code": "101012100"
                },
                {
                    "name": "北京城区",
                    "code": "101012200"
                },
                {
                    "name": "海淀",
                    "code": "101010200"
                }
            ]
        },
        {
            "province": "天津",
            "cities": [
                {
                    "name": "天津",
                    "code": "101030100"
                },
                {
                    "name": "宝坻",
                    "code": "101030300"
                },
                {
                    "name": "东丽",
                    "code": "101030400"
                },
                {
                    "name": "西青",
                    "code": "101030500"
                },
                {
                    "name": "北辰",
                    "code": "101030600"
                },
                {
                    "name": "蓟县",
                    "code": "101031400"
                },
                {
                    "name": "汉沽",
                    "code": "101030800"
                },
                {
                    "name": "静海",
                    "code": "101030900"
                },
                {
                    "name": "津南",
                    "code": "101031000"
                },
                {
                    "name": "塘沽",
                    "code": "101031100"
                },
                {
                    "name": "大港",
                    "code": "101031200"
                },
                {
                    "name": "武清",
                    "code": "101030200"
                },
                {
                    "name": "宁河",
                    "code": "101030700"
                }
            ]
        },
        {
            "province": "上海",
            "cities": [
                {
                    "name": "上海",
                    "code": "101020100"
                },
                {
                    "name": "宝山",
                    "code": "101020300"
                },
                {
                    "name": "嘉定",
                    "code": "101020500"
                },
                {
                    "name": "南汇",
                    "code": "101020600"
                },
                {
                    "name": "浦东",
                    "code": "101021300"
                },
                {
                    "name": "青浦",
                    "code": "101020800"
                },
                {
                    "name": "松江",
                    "code": "101020900"
                },
                {
                    "name": "奉贤",
                    "code": "101021000"
                },
                {
                    "name": "崇明",
                    "code": "101021100"
                },
                {
                    "name": "徐家汇",
                    "code": "101021200"
                },
                {
                    "name": "闵行",
                    "code": "101020200"
                },
                {
                    "name": "金山",
                    "code": "101020700"
                }
            ]
        },
        {
            "province": "河北",
            "cities": [
                {
                    "name": "石家庄",
                    "code": "101090101"
                },
                {
                    "name": "张家口",
                    "code": "101090301"
                },
                {
                    "name": "承德",
                    "code": "101090402"
                },
                {
                    "name": "唐山",
                    "code": "101090501"
                },
                {
                    "name": "秦皇岛",
                    "code": "101091101"
                },
                {
                    "name": "沧州",
                    "code": "101090701"
                },
                {
                    "name": "衡水",
                    "code": "101090801"
                },
                {
                    "name": "邢台",
                    "code": "101090901"
                },
                {
                    "name": "邯郸",
                    "code": "101091001"
                },
                {
                    "name": "保定",
                    "code": "101090201"
                },
                {
                    "name": "廊坊",
                    "code": "101090601"
                }
            ]
        },
        {
            "province": "河南",
            "cities": [
                {
                    "name": "郑州",
                    "code": "101180101"
                },
                {
                    "name": "新乡",
                    "code": "101180301"
                },
                {
                    "name": "许昌",
                    "code": "101180401"
                },
                {
                    "name": "平顶山",
                    "code": "101180501"
                },
                {
                    "name": "信阳",
                    "code": "101180601"
                },
                {
                    "name": "南阳",
                    "code": "101180701"
                },
                {
                    "name": "开封",
                    "code": "101180801"
                },
                {
                    "name": "洛阳",
                    "code": "101180901"
                },
                {
                    "name": "商丘",
                    "code": "101181001"
                },
                {
                    "name": "焦作",
                    "code": "101181101"
                },
                {
                    "name": "鹤壁",
                    "code": "101181201"
                },
                {
                    "name": "濮阳",
                    "code": "101181301"
                },
                {
                    "name": "周口",
                    "code": "101181401"
                },
                {
                    "name": "漯河",
                    "code": "101181501"
                },
                {
                    "name": "驻马店",
                    "code": "101181601"
                },
                {
                    "name": "三门峡",
                    "code": "101181701"
                },
                {
                    "name": "济源",
                    "code": "101181801"
                },
                {
                    "name": "安阳",
                    "code": "101180201"
                }
            ]
        },
        {
            "province": "安徽",
            "cities": [
                {
                    "name": "合肥",
                    "code": "101220101"
                },
                {
                    "name": "芜湖",
                    "code": "101220301"
                },
                {
                    "name": "淮南",
                    "code": "101220401"
                },
                {
                    "name": "马鞍山",
                    "code": "101220501"
                },
                {
                    "name": "安庆",
                    "code": "101220601"
                },
                {
                    "name": "宿州",
                    "code": "101220701"
                },
                {
                    "name": "阜阳",
                    "code": "101220801"
                },
                {
                    "name": "亳州",
                    "code": "101220901"
                },
                {
                    "name": "黄山",
                    "code": "101221001"
                },
                {
                    "name": "滁州",
                    "code": "101221101"
                },
                {
                    "name": "淮北",
                    "code": "101221201"
                },
                {
                    "name": "铜陵",
                    "code": "101221301"
                },
                {
                    "name": "宣城",
                    "code": "101221401"
                },
                {
                    "name": "六安",
                    "code": "101221501"
                },
                {
                    "name": "巢湖",
                    "code": "101221601"
                },
                {
                    "name": "池州",
                    "code": "101221701"
                },
                {
                    "name": "蚌埠",
                    "code": "101220201"
                }
            ]
        },
        {
            "province": "浙江",
            "cities": [
                {
                    "name": "杭州",
                    "code": "101210101"
                },
                {
                    "name": "舟山",
                    "code": "101211101"
                },
                {
                    "name": "湖州",
                    "code": "101210201"
                },
                {
                    "name": "嘉兴",
                    "code": "101210301"
                },
                {
                    "name": "金华",
                    "code": "101210901"
                },
                {
                    "name": "绍兴",
                    "code": "101210501"
                },
                {
                    "name": "台州",
                    "code": "101210601"
                },
                {
                    "name": "温州",
                    "code": "101210701"
                },
                {
                    "name": "丽水",
                    "code": "101210801"
                },
                {
                    "name": "衢州",
                    "code": "101211001"
                },
                {
                    "name": "宁波",
                    "code": "101210401"
                }
            ]
        },
        {
            "province": "重庆",
            "cities": [
                {
                    "name": "重庆",
                    "code": "101040100"
                },
                {
                    "name": "合川",
                    "code": "101040300"
                },
                {
                    "name": "南川",
                    "code": "101040400"
                },
                {
                    "name": "江津",
                    "code": "101040500"
                },
                {
                    "name": "万盛",
                    "code": "101040600"
                },
                {
                    "name": "渝北",
                    "code": "101040700"
                },
                {
                    "name": "北碚",
                    "code": "101040800"
                },
                {
                    "name": "巴南",
                    "code": "101040900"
                },
                {
                    "name": "长寿",
                    "code": "101041000"
                },
                {
                    "name": "黔江",
                    "code": "101041100"
                },
                {
                    "name": "万州天城",
                    "code": "101041200"
                },
                {
                    "name": "万州龙宝",
                    "code": "101041300"
                },
                {
                    "name": "涪陵",
                    "code": "101041400"
                },
                {
                    "name": "开县",
                    "code": "101041500"
                },
                {
                    "name": "城口",
                    "code": "101041600"
                },
                {
                    "name": "云阳",
                    "code": "101041700"
                },
                {
                    "name": "巫溪",
                    "code": "101041800"
                },
                {
                    "name": "奉节",
                    "code": "101041900"
                },
                {
                    "name": "巫山",
                    "code": "101042000"
                },
                {
                    "name": "潼南",
                    "code": "101042100"
                },
                {
                    "name": "垫江",
                    "code": "101042200"
                },
                {
                    "name": "梁平",
                    "code": "101042300"
                },
                {
                    "name": "忠县",
                    "code": "101042400"
                },
                {
                    "name": "石柱",
                    "code": "101042500"
                },
                {
                    "name": "大足",
                    "code": "101042600"
                },
                {
                    "name": "荣昌",
                    "code": "101042700"
                },
                {
                    "name": "铜梁",
                    "code": "101042800"
                },
                {
                    "name": "璧山",
                    "code": "101042900"
                },
                {
                    "name": "丰都",
                    "code": "101043000"
                },
                {
                    "name": "武隆",
                    "code": "101043100"
                },
                {
                    "name": "彭水",
                    "code": "101043200"
                },
                {
                    "name": "綦江",
                    "code": "101043300"
                },
                {
                    "name": "酉阳",
                    "code": "101043400"
                },
                {
                    "name": "秀山",
                    "code": "101043600"
                },
                {
                    "name": "沙坪坝",
                    "code": "101043700"
                },
                {
                    "name": "永川",
                    "code": "101040200"
                }
            ]
        },
        {
            "province": "福建",
            "cities": [
                {
                    "name": "福州",
                    "code": "101230101"
                },
                {
                    "name": "泉州",
                    "code": "101230501"
                },
                {
                    "name": "漳州",
                    "code": "101230601"
                },
                {
                    "name": "龙岩",
                    "code": "101230701"
                },
                {
                    "name": "晋江",
                    "code": "101230509"
                },
                {
                    "name": "南平",
                    "code": "101230901"
                },
                {
                    "name": "厦门",
                    "code": "101230201"
                },
                {
                    "name": "宁德",
                    "code": "101230301"
                },
                {
                    "name": "莆田",
                    "code": "101230401"
                },
                {
                    "name": "三明",
                    "code": "101230801"
                }
            ]
        },
        {
            "province": "甘肃",
            "cities": [
                {
                    "name": "兰州",
                    "code": "101160101"
                },
                {
                    "name": "平凉",
                    "code": "101160301"
                },
                {
                    "name": "庆阳",
                    "code": "101160401"
                },
                {
                    "name": "武威",
                    "code": "101160501"
                },
                {
                    "name": "金昌",
                    "code": "101160601"
                },
                {
                    "name": "嘉峪关",
                    "code": "101161401"
                },
                {
                    "name": "酒泉",
                    "code": "101160801"
                },
                {
                    "name": "天水",
                    "code": "101160901"
                },
                {
                    "name": "武都",
                    "code": "101161001"
                },
                {
                    "name": "临夏",
                    "code": "101161101"
                },
                {
                    "name": "合作",
                    "code": "101161201"
                },
                {
                    "name": "白银",
                    "code": "101161301"
                },
                {
                    "name": "定西",
                    "code": "101160201"
                },
                {
                    "name": "张掖",
                    "code": "101160701"
                }
            ]
        },
        {
            "province": "广东",
            "cities": [
                {
                    "name": "广州",
                    "code": "101280101"
                },
                {
                    "name": "惠州",
                    "code": "101280301"
                },
                {
                    "name": "梅州",
                    "code": "101280401"
                },
                {
                    "name": "汕头",
                    "code": "101280501"
                },
                {
                    "name": "深圳",
                    "code": "101280601"
                },
                {
                    "name": "珠海",
                    "code": "101280701"
                },
                {
                    "name": "佛山",
                    "code": "101280800"
                },
                {
                    "name": "肇庆",
                    "code": "101280901"
                },
                {
                    "name": "湛江",
                    "code": "101281001"
                },
                {
                    "name": "江门",
                    "code": "101281101"
                },
                {
                    "name": "河源",
                    "code": "101281201"
                },
                {
                    "name": "清远",
                    "code": "101281301"
                },
                {
                    "name": "云浮",
                    "code": "101281401"
                },
                {
                    "name": "潮州",
                    "code": "101281501"
                },
                {
                    "name": "东莞",
                    "code": "101281601"
                },
                {
                    "name": "中山",
                    "code": "101281701"
                },
                {
                    "name": "阳江",
                    "code": "101281801"
                },
                {
                    "name": "揭阳",
                    "code": "101281901"
                },
                {
                    "name": "茂名",
                    "code": "101282001"
                },
                {
                    "name": "汕尾",
                    "code": "101282101"
                },
                {
                    "name": "韶关",
                    "code": "101280201"
                }
            ]
        },
        {
            "province": "广西",
            "cities": [
                {
                    "name": "南宁",
                    "code": "101300101"
                },
                {
                    "name": "柳州",
                    "code": "101300301"
                },
                {
                    "name": "来宾",
                    "code": "101300401"
                },
                {
                    "name": "桂林",
                    "code": "101300501"
                },
                {
                    "name": "梧州",
                    "code": "101300601"
                },
                {
                    "name": "防城港",
                    "code": "101301401"
                },
                {
                    "name": "贵港",
                    "code": "101300801"
                },
                {
                    "name": "玉林",
                    "code": "101300901"
                },
                {
                    "name": "百色",
                    "code": "101301001"
                },
                {
                    "name": "钦州",
                    "code": "101301101"
                },
                {
                    "name": "河池",
                    "code": "101301201"
                },
                {
                    "name": "北海",
                    "code": "101301301"
                },
                {
                    "name": "崇左",
                    "code": "101300201"
                },
                {
                    "name": "贺州",
                    "code": "101300701"
                }
            ]
        },
        {
            "province": "贵州",
            "cities": [
                {
                    "name": "贵阳",
                    "code": "101260101"
                },
                {
                    "name": "安顺",
                    "code": "101260301"
                },
                {
                    "name": "都匀",
                    "code": "101260401"
                },
                {
                    "name": "兴义",
                    "code": "101260906"
                },
                {
                    "name": "铜仁",
                    "code": "101260601"
                },
                {
                    "name": "毕节",
                    "code": "101260701"
                },
                {
                    "name": "六盘水",
                    "code": "101260801"
                },
                {
                    "name": "遵义",
                    "code": "101260201"
                },
                {
                    "name": "凯里",
                    "code": "101260501"
                }
            ]
        },
        {
            "province": "云南",
            "cities": [
                {
                    "name": "昆明",
                    "code": "101290101"
                },
                {
                    "name": "红河",
                    "code": "101290301"
                },
                {
                    "name": "文山",
                    "code": "101290601"
                },
                {
                    "name": "玉溪",
                    "code": "101290701"
                },
                {
                    "name": "楚雄",
                    "code": "101290801"
                },
                {
                    "name": "普洱",
                    "code": "101290901"
                },
                {
                    "name": "昭通",
                    "code": "101291001"
                },
                {
                    "name": "临沧",
                    "code": "101291101"
                },
                {
                    "name": "怒江",
                    "code": "101291201"
                },
                {
                    "name": "香格里拉",
                    "code": "101291301"
                },
                {
                    "name": "丽江",
                    "code": "101291401"
                },
                {
                    "name": "德宏",
                    "code": "101291501"
                },
                {
                    "name": "景洪",
                    "code": "101291601"
                },
                {
                    "name": "大理",
                    "code": "101290201"
                },
                {
                    "name": "曲靖",
                    "code": "101290401"
                },
                {
                    "name": "保山",
                    "code": "101290501"
                }
            ]
        },
        {
            "province": "内蒙古",
            "cities": [
                {
                    "name": "呼和浩特",
                    "code": "101080101"
                },
                {
                    "name": "乌海",
                    "code": "101080301"
                },
                {
                    "name": "集宁",
                    "code": "101080401"
                },
                {
                    "name": "通辽",
                    "code": "101080501"
                },
                {
                    "name": "阿拉善左旗",
                    "code": "101081201"
                },
                {
                    "name": "鄂尔多斯",
                    "code": "101080701"
                },
                {
                    "name": "临河",
                    "code": "101080801"
                },
                {
                    "name": "锡林浩特",
                    "code": "101080901"
                },
                {
                    "name": "呼伦贝尔",
                    "code": "101081000"
                },
                {
                    "name": "乌兰浩特",
                    "code": "101081101"
                },
                {
                    "name": "包头",
                    "code": "101080201"
                },
                {
                    "name": "赤峰",
                    "code": "101080601"
                }
            ]
        },
        {
            "province": "江西",
            "cities": [
                {
                    "name": "南昌",
                    "code": "101240101"
                },
                {
                    "name": "上饶",
                    "code": "101240301"
                },
                {
                    "name": "抚州",
                    "code": "101240401"
                },
                {
                    "name": "宜春",
                    "code": "101240501"
                },
                {
                    "name": "鹰潭",
                    "code": "101241101"
                },
                {
                    "name": "赣州",
                    "code": "101240701"
                },
                {
                    "name": "景德镇",
                    "code": "101240801"
                },
                {
                    "name": "萍乡",
                    "code": "101240901"
                },
                {
                    "name": "新余",
                    "code": "101241001"
                },
                {
                    "name": "九江",
                    "code": "101240201"
                },
                {
                    "name": "吉安",
                    "code": "101240601"
                }
            ]
        },
        {
            "province": "湖北",
            "cities": [
                {
                    "name": "武汉",
                    "code": "101200101"
                },
                {
                    "name": "黄冈",
                    "code": "101200501"
                },
                {
                    "name": "荆州",
                    "code": "101200801"
                },
                {
                    "name": "宜昌",
                    "code": "101200901"
                },
                {
                    "name": "恩施",
                    "code": "101201001"
                },
                {
                    "name": "十堰",
                    "code": "101201101"
                },
                {
                    "name": "神农架",
                    "code": "101201201"
                },
                {
                    "name": "随州",
                    "code": "101201301"
                },
                {
                    "name": "荆门",
                    "code": "101201401"
                },
                {
                    "name": "天门",
                    "code": "101201501"
                },
                {
                    "name": "仙桃",
                    "code": "101201601"
                },
                {
                    "name": "潜江",
                    "code": "101201701"
                },
                {
                    "name": "襄樊",
                    "code": "101200201"
                },
                {
                    "name": "鄂州",
                    "code": "101200301"
                },
                {
                    "name": "孝感",
                    "code": "101200401"
                },
                {
                    "name": "黄石",
                    "code": "101200601"
                },
                {
                    "name": "咸宁",
                    "code": "101200701"
                }
            ]
        },
        {
            "province": "四川",
            "cities": [
                {
                    "name": "成都",
                    "code": "101270101"
                },
                {
                    "name": "自贡",
                    "code": "101270301"
                },
                {
                    "name": "绵阳",
                    "code": "101270401"
                },
                {
                    "name": "南充",
                    "code": "101270501"
                },
                {
                    "name": "达州",
                    "code": "101270601"
                },
                {
                    "name": "遂宁",
                    "code": "101270701"
                },
                {
                    "name": "广安",
                    "code": "101270801"
                },
                {
                    "name": "巴中",
                    "code": "101270901"
                },
                {
                    "name": "泸州",
                    "code": "101271001"
                },
                {
                    "name": "宜宾",
                    "code": "101271101"
                },
                {
                    "name": "内江",
                    "code": "101271201"
                },
                {
                    "name": "资阳",
                    "code": "101271301"
                },
                {
                    "name": "乐山",
                    "code": "101271401"
                },
                {
                    "name": "眉山",
                    "code": "101271501"
                },
                {
                    "name": "凉山",
                    "code": "101271601"
                },
                {
                    "name": "雅安",
                    "code": "101271701"
                },
                {
                    "name": "甘孜",
                    "code": "101271801"
                },
                {
                    "name": "阿坝",
                    "code": "101271901"
                },
                {
                    "name": "德阳",
                    "code": "101272001"
                },
                {
                    "name": "广元",
                    "code": "101272101"
                },
                {
                    "name": "攀枝花",
                    "code": "101270201"
                }
            ]
        },
        {
            "province": "宁夏",
            "cities": [
                {
                    "name": "银川",
                    "code": "101170101"
                },
                {
                    "name": "中卫",
                    "code": "101170501"
                },
                {
                    "name": "固原",
                    "code": "101170401"
                },
                {
                    "name": "石嘴山",
                    "code": "101170201"
                },
                {
                    "name": "吴忠",
                    "code": "101170301"
                }
            ]
        },
        {
            "province": "青海",
            "cities": [
                {
                    "name": "西宁",
                    "code": "101150101"
                },
                {
                    "name": "黄南",
                    "code": "101150301"
                },
                {
                    "name": "海北",
                    "code": "101150801"
                },
                {
                    "name": "果洛",
                    "code": "101150501"
                },
                {
                    "name": "玉树",
                    "code": "101150601"
                },
                {
                    "name": "海西",
                    "code": "101150701"
                },
                {
                    "name": "海东",
                    "code": "101150201"
                },
                {
                    "name": "海南",
                    "code": "101150401"
                }
            ]
        },
        {
            "province": "山东",
            "cities": [
                {
                    "name": "济南",
                    "code": "101120101"
                },
                {
                    "name": "潍坊",
                    "code": "101120601"
                },
                {
                    "name": "临沂",
                    "code": "101120901"
                },
                {
                    "name": "菏泽",
                    "code": "101121001"
                },
                {
                    "name": "滨州",
                    "code": "101121101"
                },
                {
                    "name": "东营",
                    "code": "101121201"
                },
                {
                    "name": "威海",
                    "code": "101121301"
                },
                {
                    "name": "枣庄",
                    "code": "101121401"
                },
                {
                    "name": "日照",
                    "code": "101121501"
                },
                {
                    "name": "莱芜",
                    "code": "101121601"
                },
                {
                    "name": "聊城",
                    "code": "101121701"
                },
                {
                    "name": "青岛",
                    "code": "101120201"
                },
                {
                    "name": "淄博",
                    "code": "101120301"
                },
                {
                    "name": "德州",
                    "code": "101120401"
                },
                {
                    "name": "烟台",
                    "code": "101120501"
                },
                {
                    "name": "济宁",
                    "code": "101120701"
                },
                {
                    "name": "泰安",
                    "code": "101120801"
                }
            ]
        },
        {
            "province": "陕西",
            "cities": [
                {
                    "name": "西安",
                    "code": "101110101"
                },
                {
                    "name": "延安",
                    "code": "101110300"
                },
                {
                    "name": "榆林",
                    "code": "101110401"
                },
                {
                    "name": "铜川",
                    "code": "101111001"
                },
                {
                    "name": "商洛",
                    "code": "101110601"
                },
                {
                    "name": "安康",
                    "code": "101110701"
                },
                {
                    "name": "汉中",
                    "code": "101110801"
                },
                {
                    "name": "宝鸡",
                    "code": "101110901"
                },
                {
                    "name": "咸阳",
                    "code": "101110200"
                },
                {
                    "name": "渭南",
                    "code": "101110501"
                }
            ]
        },
        {
            "province": "山西",
            "cities": [
                {
                    "name": "太原",
                    "code": "101100101"
                },
                {
                    "name": "临汾",
                    "code": "101100701"
                },
                {
                    "name": "运城",
                    "code": "101100801"
                },
                {
                    "name": "朔州",
                    "code": "101100901"
                },
                {
                    "name": "忻州",
                    "code": "101101001"
                },
                {
                    "name": "长治",
                    "code": "101100501"
                },
                {
                    "name": "大同",
                    "code": "101100201"
                },
                {
                    "name": "阳泉",
                    "code": "101100301"
                },
                {
                    "name": "晋中",
                    "code": "101100401"
                },
                {
                    "name": "晋城",
                    "code": "101100601"
                },
                {
                    "name": "吕梁",
                    "code": "101101100"
                }
            ]
        },
        {
            "province": "新疆",
            "cities": [
                {
                    "name": "乌鲁木齐",
                    "code": "101130101"
                },
                {
                    "name": "石河子",
                    "code": "101130301"
                },
                {
                    "name": "昌吉",
                    "code": "101130401"
                },
                {
                    "name": "吐鲁番",
                    "code": "101130501"
                },
                {
                    "name": "库尔勒",
                    "code": "101130601"
                },
                {
                    "name": "阿拉尔",
                    "code": "101130701"
                },
                {
                    "name": "阿克苏",
                    "code": "101130801"
                },
                {
                    "name": "喀什",
                    "code": "101130901"
                },
                {
                    "name": "伊宁",
                    "code": "101131001"
                },
                {
                    "name": "塔城",
                    "code": "101131101"
                },
                {
                    "name": "哈密",
                    "code": "101131201"
                },
                {
                    "name": "和田",
                    "code": "101131301"
                },
                {
                    "name": "阿勒泰",
                    "code": "101131401"
                },
                {
                    "name": "阿图什",
                    "code": "101131501"
                },
                {
                    "name": "博乐",
                    "code": "101131601"
                },
                {
                    "name": "克拉玛依",
                    "code": "101130201"
                }
            ]
        },
        {
            "province": "西藏",
            "cities": [
                {
                    "name": "拉萨",
                    "code": "101140101"
                },
                {
                    "name": "山南",
                    "code": "101140301"
                },
                {
                    "name": "阿里",
                    "code": "101140701"
                },
                {
                    "name": "昌都",
                    "code": "101140501"
                },
                {
                    "name": "那曲",
                    "code": "101140601"
                },
                {
                    "name": "日喀则",
                    "code": "101140201"
                },
                {
                    "name": "林芝",
                    "code": "101140401"
                }
            ]
        },
        {
            "province": "台湾",
            "cities": [
                {
                    "name": "台北县",
                    "code": "101340101"
                },
                {
                    "name": "高雄",
                    "code": "101340201"
                },
                {
                    "name": "台中",
                    "code": "101340401"
                }
            ]
        },
        {
            "province": "海南",
            "cities": [
                {
                    "name": "海口",
                    "code": "101310101"
                },
                {
                    "name": "三亚",
                    "code": "101310201"
                },
                {
                    "name": "东方",
                    "code": "101310202"
                },
                {
                    "name": "临高",
                    "code": "101310203"
                },
                {
                    "name": "澄迈",
                    "code": "101310204"
                },
                {
                    "name": "儋州",
                    "code": "101310205"
                },
                {
                    "name": "昌江",
                    "code": "101310206"
                },
                {
                    "name": "白沙",
                    "code": "101310207"
                },
                {
                    "name": "琼中",
                    "code": "101310208"
                },
                {
                    "name": "定安",
                    "code": "101310209"
                },
                {
                    "name": "屯昌",
                    "code": "101310210"
                },
                {
                    "name": "琼海",
                    "code": "101310211"
                },
                {
                    "name": "文昌",
                    "code": "101310212"
                },
                {
                    "name": "保亭",
                    "code": "101310214"
                },
                {
                    "name": "万宁",
                    "code": "101310215"
                },
                {
                    "name": "陵水",
                    "code": "101310216"
                },
                {
                    "name": "西沙",
                    "code": "101310217"
                },
                {
                    "name": "南沙岛",
                    "code": "101310220"
                },
                {
                    "name": "乐东",
                    "code": "101310221"
                },
                {
                    "name": "五指山",
                    "code": "101310222"
                },
                {
                    "name": "琼山",
                    "code": "101310102"
                }
            ]
        },
        {
            "province": "湖南",
            "cities": [
                {
                    "name": "长沙",
                    "code": "101250101"
                },
                {
                    "name": "株洲",
                    "code": "101250301"
                },
                {
                    "name": "衡阳",
                    "code": "101250401"
                },
                {
                    "name": "郴州",
                    "code": "101250501"
                },
                {
                    "name": "常德",
                    "code": "101250601"
                },
                {
                    "name": "益阳",
                    "code": "101250700"
                },
                {
                    "name": "娄底",
                    "code": "101250801"
                },
                {
                    "name": "邵阳",
                    "code": "101250901"
                },
                {
                    "name": "岳阳",
                    "code": "101251001"
                },
                {
                    "name": "张家界",
                    "code": "101251101"
                },
                {
                    "name": "怀化",
                    "code": "101251201"
                },
                {
                    "name": "黔阳",
                    "code": "101251301"
                },
                {
                    "name": "永州",
                    "code": "101251401"
                },
                {
                    "name": "吉首",
                    "code": "101251501"
                },
                {
                    "name": "湘潭",
                    "code": "101250201"
                }
            ]
        },
        {
            "province": "江苏",
            "cities": [
                {
                    "name": "南京",
                    "code": "101190101"
                },
                {
                    "name": "镇江",
                    "code": "101190301"
                },
                {
                    "name": "苏州",
                    "code": "101190401"
                },
                {
                    "name": "南通",
                    "code": "101190501"
                },
                {
                    "name": "扬州",
                    "code": "101190601"
                },
                {
                    "name": "宿迁",
                    "code": "101191301"
                },
                {
                    "name": "徐州",
                    "code": "101190801"
                },
                {
                    "name": "淮安",
                    "code": "101190901"
                },
                {
                    "name": "连云港",
                    "code": "101191001"
                },
                {
                    "name": "常州",
                    "code": "101191101"
                },
                {
                    "name": "泰州",
                    "code": "101191201"
                },
                {
                    "name": "无锡",
                    "code": "101190201"
                },
                {
                    "name": "盐城",
                    "code": "101190701"
                }
            ]
        },
        {
            "province": "黑龙江",
            "cities": [
                {
                    "name": "哈尔滨",
                    "code": "101050101"
                },
                {
                    "name": "牡丹江",
                    "code": "101050301"
                },
                {
                    "name": "佳木斯",
                    "code": "101050401"
                },
                {
                    "name": "绥化",
                    "code": "101050501"
                },
                {
                    "name": "黑河",
                    "code": "101050601"
                },
                {
                    "name": "双鸭山",
                    "code": "101051301"
                },
                {
                    "name": "伊春",
                    "code": "101050801"
                },
                {
                    "name": "大庆",
                    "code": "101050901"
                },
                {
                    "name": "七台河",
                    "code": "101051002"
                },
                {
                    "name": "鸡西",
                    "code": "101051101"
                },
                {
                    "name": "鹤岗",
                    "code": "101051201"
                },
                {
                    "name": "齐齐哈尔",
                    "code": "101050201"
                },
                {
                    "name": "大兴安岭",
                    "code": "101050701"
                }
            ]
        },
        {
            "province": "吉林",
            "cities": [
                {
                    "name": "长春",
                    "code": "101060101"
                },
                {
                    "name": "延吉",
                    "code": "101060301"
                },
                {
                    "name": "四平",
                    "code": "101060401"
                },
                {
                    "name": "白山",
                    "code": "101060901"
                },
                {
                    "name": "白城",
                    "code": "101060601"
                },
                {
                    "name": "辽源",
                    "code": "101060701"
                },
                {
                    "name": "松原",
                    "code": "101060801"
                },
                {
                    "name": "吉林",
                    "code": "101060201"
                },
                {
                    "name": "通化",
                    "code": "101060501"
                }
            ]
        },
        {
            "province": "辽宁",
            "cities": [
                {
                    "name": "沈阳",
                    "code": "101070101"
                },
                {
                    "name": "鞍山",
                    "code": "101070301"
                },
                {
                    "name": "抚顺",
                    "code": "101070401"
                },
                {
                    "name": "本溪",
                    "code": "101070501"
                },
                {
                    "name": "丹东",
                    "code": "101070601"
                },
                {
                    "name": "葫芦岛",
                    "code": "101071401"
                },
                {
                    "name": "营口",
                    "code": "101070801"
                },
                {
                    "name": "阜新",
                    "code": "101070901"
                },
                {
                    "name": "辽阳",
                    "code": "101071001"
                },
                {
                    "name": "铁岭",
                    "code": "101071101"
                },
                {
                    "name": "朝阳",
                    "code": "101071201"
                },
                {
                    "name": "盘锦",
                    "code": "101071301"
                },
                {
                    "name": "大连",
                    "code": "101070201"
                },
                {
                    "name": "锦州",
                    "code": "101070701"
                }
            ]
        }
    ])