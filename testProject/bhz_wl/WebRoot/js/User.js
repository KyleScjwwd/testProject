var user = {
	CheckAdd : function() {
		var bdCode = $("select[name='bdCode']").val();
		var bhzCode = $("select[name='bhzCode']").val();
		var username = $("input[name='username']").val();
		var userpwd = $("input[name='userpwd']").val();
		var reg = /^[0-9a-zA-Z]*$/g;
		if(username == "") {
			alert("用户名 是必填项！");
			return;
		}
		if(reg.test(username)==false){
			alert("用户名只能是字母+数字组合！");
			return;
		}
		if(userpwd == "") {
			alert("登陆密码 是必填项！");
			return;
		}
		$("#AddUserForm").submit();
	},
	CheckFind : function() {
		var bdCode = $("select[name='bdCode']").val();
		var bhzCode = $("select[name='bhzCode']").val();
		var username = $("input[name='username']").val();
		var userpwd = $("input[name='userpwd']").val();
		
		if (bdCode == "" && bhzCode == "" && username == "" && userpwd == "") {
			alert("至少要输入一个查询条件！");
		} else {
			$("#FindUserForm").submit();
		}
	},
	initEvent : function() {
		$("select[name='bdCode']").unbind("change");
		$("select[name='bdCode']").bind("change", function() {
			var bdCode = $("select[name='bdCode']").val();
			if (bdCode != "") {
				user.getBHZ(bdCode);
			} else {
				$("select[name='bhzCode']").empty();
				$("select[name='bhzCode']").append("<option value=\"\">==请选择拌合站==</option>");
			}
		});
	},
	getBHZ : function(bdcode) {
		var parameter = {
			bdCode : bdcode
		};
		$.post("userJsonAction!getBHZsByBdcode.action", parameter, function(data) {
			$("select[name='bhzCode']").empty();
			$("select[name='bhzCode']").append("<option value=\"\">==请选择拌合站==</option>");
			for (var i = 0; i < data.BHZs.length; i++) {
				$("select[name='bhzCode']").append("<option value=\"" + data.BHZs[i].bhzCode + "\">" + data.BHZs[i].bhzName + "</option>");
			}
			var hidBhz = $("#hidBhz").val();
			if(hidBhz!=null && hidBhz!=""){
				$("select[name='bhzCode']").val(hidBhz);
			}
		});
	}
};

$().ready(function() {
	user.initEvent();
	var hint = $("#hint").val();
	if(hint!=null && hint!=""){
		alert(hint);
	}
	var hidBd = $("#hidBd").val();
	if(hidBd!=null && hidBd!=""){
		user.getBHZ(hidBd);
	}
});