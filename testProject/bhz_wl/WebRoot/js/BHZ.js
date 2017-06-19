var bdz = {
	CheckAdd : function() {
		var bdCode = $("select[name='bdCode']").val();
		var bhzName = $("input[name='bhzName']").val();
		var upNum = $("input[name='upNum']").val();
		var downNum = $("input[name='downNum']").val();
		
		if (bdCode == "null" || bhzName == "") {
			alert("所属标段 和 拌合站名称 是必填项！");
		} else if(upNum != "" && isNaN(upNum)) {
			alert("上限  必须是个数字！");
		} else if(downNum != "" && isNaN(downNum)) {
			alert("下限  必须是个数字！");
		} else {
			$("#AddBHZForm").submit();
		}
	},
	CheckFind : function() {
		var bdCode = $("select[name='bdCode']").val();
		var bhzName = $("input[name='bhzName']").val();
		var upNum = $("input[name='upNum']").val();
		var downNum = $("input[name='downNum']").val();
		
		if(bdCode == "null" && bhzName == "" && upNum == "" && downNum == "") {
			alert("至少要输入一个查询条件！");
		} else {
			$("#FindBHZForm").submit();
		}
	},
	updateMerge : function(id) {
		var flag = false,cName="";
		var mergebox = $("#mergebox"+id);
		if(mergebox.is(':checked')){
			flag = false;
			cName = "【合并列】";
		}else{
			flag = true;
			cName = "【常规列】";
		}
		if(confirm("您确定该拌合站各材料用量按"+cName+"显示吗？")){
			$.ajax({
			type:"post", 
			url:"BHZAction!setMergeShow.action", 
			data:{id:id,flag:flag}, 
			timeout:10000, 
			success:function (data) {
				if(data=="1"){
					alert("操作失败,请先进行该拌合站合并列设置!");
					mergebox.attr("checked",flag);
				}else if(data=="2"){
					alert("操作成功!");
				}else{
					alert("操作失败,请检查各参数及记录值或权限!");
					mergebox.attr("checked",flag);
				}
			}
		});
		}else{
			mergebox.attr("checked",flag);
		}
	},
	updateTest : function(id) {
		var flag = false,cName = "";
		var testbox = $("#testbox"+id);
		if(testbox.is(':checked')){
			flag = false;
			cName = "按";
		}else{
			flag = true;
			cName = "取消";
		}
		if(confirm("您确定该拌合站各材料用量【"+cName+"】施工配比校正吗？")){
			$.ajax({
			type:"post", 
			url:"BHZAction!setSgphbTest.action", 
			data:{id:id,flag:flag}, 
			timeout:10000, 
			success:function (data) {
				if(data=="1"){
					alert("操作成功!");
				}else{
					alert("操作失败!");
					testbox.attr("checked",flag);
				}
			}
		});
		}else{
			testbox.attr("checked",flag);
		}
	}
};
var common = {
	myconfirm : function(t) {
		return confirm(t);
	},
	initEvent : function() {
		$("a[del]").each(function(){
			$(this).unbind("click");
			$(this).bind("click", function(){
				var t = "【"+$(this).attr("title")+"】";
				var r = "该操作将一并删除"+t+"上传数据,您确认要删除吗？"
				return common.myconfirm(r);
			});
		});
	}
};
$().ready(function() {
	var bhzCodeIn = $("#bhzCodeIn").val();
	common.initEvent();
	if(bhzCodeIn=="1"){
		alert("拌合站编号重复,请重新输入!");
	}
});