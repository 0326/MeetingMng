// filters.js
var mFilters = angular.module("mFilters", [])

//日期过滤器，传入[频率,时间戳]
.filter('meetingDate', function($filter){
	return function(obj){
		var freq = obj[0];
		var time = obj[1]; 
		var result = "";
		if(freq == 1){
			result = $filter('date')(time,'mm月dd日 HH:mm');
		}
		else if(freq == 2){ 
			result = "每天 " + $filter('date')(time,'HH:mm');
		}
		else if(freq == 3){ 
			result="每周" + $filter('date')(time,'EEEE HH:mm')
				.replace('Monday','一')
				.replace('Tuesday','二') 
				.replace('Wednesday','三') 
				.replace('Thursday','四') 
				.replace('Friday','五') 
				.replace('Saturday','六') 
				.replace('Sunday','日'); 
		}
		else if(freq == 4){ 
			result="每月" + $filter('date')(time,'dd日 HH:mm'); 
		}

		return result;
	}
})

//会议组织者，参与者过滤器
.filter('mcontact',function($filter){
	return function(input,arg){
		var result = "";
		if(arg == "isCreator"){
			if(input == true){
				result = "创会者";
			}
			else{
				result = "";
			}
		}
		else if(arg == "state"){
			if(input == 0){
				result = "未发送";
			}
			else if(input == 1){
				result = "已发送";
			}
			else if(input == 2){
				result = "已同意";
			}
			else if(input == 3){
				result = "待定";
			}
			else if(input == 4){
				result = "拒绝";
			}
		}
		return result;
	}
})