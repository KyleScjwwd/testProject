var BjBase = {
	CheckAdd : function() {
		var clname = $("input[name='clName']").val();
		
		var parameter = {
			clName : clname
		};
		
		if (clname == "") {
			alert("材料名称是必填项！");
		} else {
			var falg = "0";
			var reg = /^\d+(\.\d+)?$/;
			$("#AddBaoJingBaseForm input[name*='Value']").each(function(){
				var val = $(this).val();
				if(val!=""){
					if(reg.test(val)==false){
						falg = "1";
						alert("上下限只能为非负数值!");
						$(this).focus();
					}
				}
			});
			if (falg == "0") {
				$.post("messageAction!checkClName_Add.action", parameter, function(data) {
					if (data.hint == "") {
						$("#AddBaoJingBaseForm").submit();
					} else {
						if (data.hint == "1")
							alert("该材料名称已存在，请修改！");
						else if (data.hint == "2")
							alert("该材料名称在合并列设置里不存在！");
					}
				});
			}
		}
	},
	CheckUpdate : function() {
		var clname = $("input[name='clName']").val();
		var id = $("input[name='id']").val();
		
		var parameter = {
			clName : clname,
			ID : id
		};		
		
		if (clname == "") {
			alert("材料名称是必填项！");
		} else {
			var falg = "0";
			var reg = /^\d+(\.\d+)?$/;
			$("#UpdateBaoJingBaseForm input[name*='Value']").each(function(){
				var val = $(this).val();
				if(val!=""){
					if(reg.test(val)==false){
						falg = "1";
						alert("上下限只能为非负数值!");
						$(this).focus();
					}
				}
			});
			if (falg == "0") {
				$.post("messageAction!checkClName_Update.action", parameter, function(data) {
					if (data.hint == "") {
						$("#UpdateBaoJingBaseForm").submit();
					} else {
						if (data.hint == "1")
							alert("该材料名称已存在，请修改！");
						else if (data.hint == "2")
							alert("该材料名称在合并列设置里不存在！");
					}
				});
			}
		}		
	}
};