var si = {
	CheckAdd : function() {
		var bdcode = $("select[name='bdCode']").val();
		var bhzcode = $("select[name='bhzCode']").val();
		var signinDate = $("input[name='signInDate']").val();
		var signinUser = $("input[name='signInUser']").val();
		
		var parameter = {
			bdCode : bdcode,
			bhzCode : bhzcode,
			signInDate : signinDate,
			signInUser : signinUser
		};
		
		if (bdcode == "" || bhzcode == "" || signinDate == "" || signinUser == "") {
			alert("所属标段 、所属拌合站、生产日期和签到人  是必填项！");
		} else {
			$.post("signInAction!checkSignIn_Add.action", parameter, function(data) {
				if (data.hint == "") {
					$("#AddSignInForm").submit();
				} else {
					alert(data.hint);
				}
			});
		}
	},
	CheckUpdate : function() {
		var i = $("input[name='id']").val();
		var bdcode = $("select[name='bdCode']").val();
		var bhzcode = $("select[name='bhzCode']").val();
		var signinDate = $("input[name='signInDate']").val();
		var signinUser = $("input[name='signInUser']").val();
		
		var parameter = {
			id: i,
			bdCode : bdcode,
			bhzCode : bhzcode,
			signInDate : signinDate,
			signInUser : signinUser
		};
		
		if (bdcode == "" || bhzcode == "" || signinDate == "" || signinUser == "") {
			alert("所属标段 、所属拌合站、生产日期和签到人  是必填项！");
		} else {
			$.post("signInAction!checkSignIn_Update.action", parameter, function(data) {
				if (data.hint == "") {
					$("#UpdateSignInForm").submit();
				} else {
					alert(data.hint);
				}
			});			
		}		
	},
	CheckFind : function() {
		$("#FindSignInForm").submit();
	}
};