var bd = {
	CheckAdd : function() {
		var bdCode = $("input[name='bdCode']").val();
		var bdName = $("input[name='bdName']").val();
		if (bdCode == "" || bdName == "") {
			alert("标段编号 和 标段名称 是必填项！");
		} else {
			var reg = /^[0-9a-zA-Z]+$/;
			if(!reg.test(bdCode)){
				alert("标段编号只能输入字母和数字!");
				return;
			}
			$("#AddBDForm").submit();
		}
	},
	CheckBDtoBHZ:function(bdCode){
		var b=false;
		$.ajax({
			type:'post',
			url:'BDAction!checkBDtoBHZ.action?bdCode='+bdCode,
			async:false,
			success:function(msg){
				b=msg;
			}
		});
		return b;
	},
	CheckFind : function() {
		$("#FindBDForm").submit();
	}
};
var common = {
	myconfirm : function() {
		return confirm("该操作将一并删除对应拌合站及上传数据,您确认要删除吗？");
	},
	initEvent : function() {
		$("a[del]").each(function(){
			$(this).unbind("click");
			$(this).bind("click", function(){
				return common.myconfirm();
			});
		});
	}
};



$().ready(function() {
	common.initEvent();
});