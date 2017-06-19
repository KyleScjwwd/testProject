var designPHB = {
	CheckFind : function() {
		var bdcode = $("select[name='bdCode']").val();
		var bhzcode = $("select[name='bhzCode']").val();
		
		if (bdcode == "") {
			alert("所属标段 是必填项！");
		} else if (bhzcode == "") {
			alert("所属拌合站 是必填项！");
		} else {
			$("#FindDesignPHBForm").submit();
		}
	},
	saveParam : function() {
		var l = $("#paramsList").val();
		if(l==null || l=="" || l==0) {
			alert("请先查询数据!");
		} else {
			if (confirm("确定批量提交？")) {
				var falg = "0";
				var reg = /^\d+(\.\d+)?$/;
				$("#updateForm input[type='text']").each(function(){
					var val = $(this).val();
					if(val!=""){
						if(reg.test(val)==false){
							falg = "1";
							alert("设计值只能为非负数值!");
							$(this).focus();
						}
					}
				});
				if(falg=="0"){
					$("#spanMsg").html("批量提交数据较慢,请耐心等待");
					$("#updateForm").submit();
				}else{
					return false;
				}
			}
		}
	}	
}
