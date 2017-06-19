function closeDiv(){
	parent.ymPrompt.close();
}
function searchData(){
	$("#FindFilterDataForm").submit();
}
function getBhzInfo(){
	var bdCode = $("select[name='bdCode']").val();
	$.post("userJsonAction!getBHZsByBdcode.action", {bdCode:bdCode}, function(data) {
		$("select[name='bhzCode']").empty();
		$("select[name='bhzCode']").append("<option value=\"\">==请选择拌合站==</option>");
		//$("select[name='m5']").empty();
		//$("select[name='m5']").append("<option value=\"\">==请选择浇筑部位==</option>");
		//$("select[name='m4']").empty();
		//$("select[name='m4']").append("<option value=\"\">==请选择出料时间==</option>");
		for (var i = 0; i < data.BHZs.length; i++) {
			$("select[name='bhzCode']").append("<option value=\"" + data.BHZs[i].bhzCode + "\">" + data.BHZs[i].bhzName + "</option>");
		}
	});
}
function getBaseInfo(){
	var bdCode = $("select[name='bdCode']").val();
	var bhzCode = $("select[name='bhzCode']").val();
	$("select[name='m5']").empty();
	$("select[name='m5']").append("<option value=\"\">==请选择浇筑部位==</option>");
	$("select[name='m4']").empty();
	$("select[name='m4']").append("<option value=\"\">==请选择出料时间==</option>");
	if(bdCode!="" && bhzCode!=""){
		$.post("mainAction!getBaseInfo.action", {bdCode:bdCode,bhzCode:bhzCode}, function(data) {
			if(data!=null && data!=""){
				var obj = eval("("+data+")");
				var m5List = obj.m5;
				var m4List = obj.m4;
				if(m5List!=null){
					for (var i=0;i<m5List.length;i++){
						$("select[name='m5']").append("<option value=\"" + m5List[i] + "\">" + m5List[i] + "</option>");
					}
				}
				if(m4List!=null){
					for (var i=0;i<m4List.length;i++){
						$("select[name='m4']").append("<option value=\"" + m4List[i] + "\">" + m4List[i] + "</option>");
					}
				}
			}
		});
	}
}
function delFilterData(bdCode,bhzCode,m5,m4){
	if(confirm("您确认要删除吗？")){
		$.post("mainAction!deleteFilterData.action", {bdCode:bdCode,bhzCode:bhzCode,m5:m5,m4:m4}, function(data) {
			if(data!=""){
				alert("删除成功!");
				$("#FindFilterDataForm").submit();
			}else{
				alert("删除失败!");
			}
		});
	}
}
function saveFilterData(){
	var bdCode = $("select[name='bdCode']").val();
	var bhzCode = $("select[name='bhzCode']").val();
	var m5 = $("input[name='m5']").val();
	var m4 = $("input[name='m4']").val();
	if(bdCode==""){
		alert("请选择标段!");
		return;
	}
	if(bhzCode==""){
		alert("请选择拌合站!");
		return;
	}
	openLoad();
	$.post("mainAction!saveFilterData.action", {bdCode:bdCode,bhzCode:bhzCode,m5:m5,m4:m4}, function(data) {
		if(data!=""){
			closeLoad();
			alert("本次过滤的数据条数为：【"+data+"】条!");
			subSelect();
		}else{
			alert("保存失败!");
		}
	});
}
function subSelect(){
	$("#FindFilterDataForm", window.parent.document).submit();
	closeDiv();
}