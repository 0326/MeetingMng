/*
author: Ling
表单验证库，基于jquery
格式：
<input type="text" class="i-validater validater-{{validate type}}">
<span class="validater-helper"></span>
*/
;(function($){
	//验证处理
	var validater = {
		httpValidater: function (api_url, key, value, callback){
			var url = encodeURI(api_url+'?'+key+'='+value);
			$.get(url, function(data, status){
				callback(data,status);
				// if(!!data.code && data.code != 0){
				// 	showError(classname, error_info);
				// }
				// else{
				// 	hideError(classname);
				// }
			});
		},
		password: function(val, classname){
			if(val.length == 0 ){
				showError(classname,"密码不能为空");
			}
			else if(val.length < 6){
				showError(classname,"密码长度太短");
			}
			else{
				hideError(classname);
			}
		},
		repassword: function(val1, val2, classname){
			if(val1 != val2){
				showError(classname,"两次密码不一致");
			}
			else{
				hideError(classname);
			}
		},
		phone: function(phone, classname){
			// console.log(/^1[3|4|5|7|8][0-9]\d{8}$/.test($.trim(phone)));
			if( /^1[3|4|5|7|8][0-9]\d{8}$/.test($.trim(phone)) == false ){
				showError(classname, "手机格式不正确");
				return false;
			}
			else{
				hideError(classname);
				return true;
			}
		},
		notEmpty: function(val, classname){
			if($.trim(val) == ""){
				showError(classname, "此项不能为空");
			}
			else{
				hideError(classname);
			}
		},
		other: function(){

		}

	};
	//绑定事件
	$(".i-validater").blur(function() {
		if($(this).hasClass("validater-company-username")){
			validater.httpValidater(
				'/MeetingMng/api/v1/usernameRepeat', 
				'username', $(this).val(),
				function(data, status){
					if(!!data.code && data.code != 0){
						showError('validater-company-username', '该邮箱已注册');
					}
					else{
						hideError('validater-company-username');
					}
				}
			);	
		}

		else if($(this).hasClass("validater-company-name")){
			validater.httpValidater('/MeetingMng/api/v1/companyNameRepeat', 
				'companyName', $(this).val(),
				function(data, status){
					if(!!data.code && data.code != 0){
						showError('validater-company-name', '该公司已被注册');
					}
					else{
						hideError('validater-company-name');
					}
				}
			);
		}
		
		else if($(this).hasClass("validater-password")){
			validater.password($(this).val(),"validater-password");
		}
		
		else if($(this).hasClass("validater-repassword")){
			validater.repassword($(this).val(),$("#password").val(), "validater-repassword");
		}
		
		else if($(this).hasClass("validater-phone")){
			validater.phone($(this).val(),"validater-phone");
		}

		else if($(this).hasClass("validater-notempty")){
			validater.notEmpty($(this).val(),"validater-notempty");
		}
		//用于普通用户注册
		else if($(this).hasClass("validater-user-company")){

		}
		else if($(this).hasClass("validater-user-cellphone")){
			
			if(false == validater.phone($(this).val(),"validater-user-cellphone")) return;

			validater.httpValidater('/MeetingMng/api/v1/findUserByCellphone', 
				'cellphone', $(this).val(),
				function(data, status){
					if(data.code == 0){
						console.log(data.userInfo);
						$("label[for='companyName']").text("所在公司："+data.userInfo.companyName);
						$("#companyName").hide();
						$("label[for='ordinaryUsername']").text("用户姓名："+data.userInfo.name)
						$("#ordinaryUsername").hide();
						$("#companyId").val(data.userInfo.companyId);
						// hideError('validater-user-cellphone');
						showError('validater-user-cellphone', '您的信息已被贵公司管理员录入系统，请直接注册');
					}
					else{
						// showError('validater-user-cellphone', '该手机号未被注册');
					}
				}
			);
		}
		else{
			
		}

	});

	function showError(classname, err){
		$("."+ classname + "+ .validater-helper").text(err).slideDown();
	}
	
	function hideError(classname){
		$("."+ classname + "+ .validater-helper").slideUp();
	}

})(jQuery);