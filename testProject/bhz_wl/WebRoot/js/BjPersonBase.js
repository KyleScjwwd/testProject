var BjPersonBase = {
	CheckAddOrUpdate : function(param) {
		var bdcode = $("select[name='bdCode']").val();
		var bhzcode = $("select[name='bhzCode']").val();
		var userMobilecj = $("input[name='userMobileCj']").val();
		var userMobilezj = $("input[name='userMobileZj']").val();
		var userMobilegj = $("input[name='userMobileGj']").val();
		var id = $("input[name='id']").val();
		
		if (param == "add") {
			var parameter = {
				bdCode : bdcode,
				bhzCode : bhzcode
			};			
		} else if (param == "update") {
			var parameter = {
				bdCode : bdcode,
				bhzCode : bhzcode,
				ID : id
			};			
		}

		
		if (bdcode == "" || bhzcode == "") {
			alert("标段 和拌合站是必填项！");
		} else {	
			if (userMobilecj == "" && userMobilezj == "" && userMobilegj == "") {
				alert("请至少设置一个级别的报警联系人！");
			} else {
				if (param == "add") {
					$.post("messageAction!checkBaoJingPersonBase_Add.action", parameter, function(data) {
						if (data.hint == "") {
							$("#AddBaoJingPersonBaseForm").submit();
						} else {
							alert(data.hint);
						}
					});						
				} else if (param == "update") {
					$.post("messageAction!checkBaoJingPersonBase_Update.action", parameter, function(data) {
						if (data.hint == "") {
							$("#UpdateBaoJingPersonBaseForm").submit();
						} else {
							alert(data.hint);
						}
					});						
				}
			}
		}		
	}
};