var indexTo = 0;
$(document).ready(function () {
	var bdCode = $("#bdCode").val();
	if (bdCode != null && bdCode != "") {
		indexTo = 1;
		showBhzTo();
	}
	isMoreBd();
});
function showBhzTo() {
	var bdVal = $("#bd").val();
	$.ajax({type:"POST", url:"mainAction!getBhzInfo.action", data:"bdCode=" + bdVal, timeout:10000, success:function (data) {
		setValueTo(data);
	}});
}
function setValueTo(data) {
	$("#bhz").empty();
	$("#bhz").append("<option value=\"\">==请选择拌合站==</option>");
	var bhzCode = $("#bhzCode").val();
	if (data!=null && data!="") {
		var arr = data.split(",");
		for (var i = 0; i < arr.length - 1; i++) {
			var val = arr[i].split("|");
			if (val[1] == undefined) {
				break;
			}
			$("#bhz").append("<option value=\"" + val[0] + "\">" + val[1] + "</option>");
		}
	}
	if (bhzCode == null || bhzCode == "" || indexTo == 0) {
		$("#bhz").val("");
	} else {
		$("#bhz").val(bhzCode);
	}
}
function showQangDu(){
	var bdVal = $("#bd").val();
	var bhzVal = $("#bhz").val();
	$.ajax({type:"POST", 
		url:"countAction!getQiangDuInfo.action", 
		data:{bdCode:bdVal,bhzCode:bhzVal}, 
		timeout:10000, success:function (data) {
		setQdValue(data);
	}});
}
function setQdValue(data){
	$("#m8").empty();
	$("#m8").append("<option value=\"\">==请选择强度等级==</option>");
	var m8 = $("#intensityGrade").val();;
	if (data!=null && data != "") {
		var arr = data.split(",");
		for (var i = 0; i < arr.length-1; i++) {
			var val = arr[i];
			$("#m8").append("<option value=\"" + val + "\">" + val + "</option>");
		}
	}
	if (m8 == null || m8 == "" || indexTo == 0) {
		$("#m8").val("");
	} else {
		indexTo = 0;
		$("#m8").val(m8);
	}
}
function isMoreBd(){
	if($("#isMoreBdBox").is(":checked")){
		$("#bd").val("");
		$("#bhz").empty();
		$("#bhz").append("<option value=\"\">==请选择拌合站==</option>");
		$(".oneBdDiv").hide();
		$(".moreBdDiv").show();
	}else{
		$("#morebd").val("");
		$("#morebdCode").val("");
		$(".oneBdDiv").show();
		$(".moreBdDiv").hide();
	}
}
function zkOperator(id){
	var tr = $("#zkTr"+id);
	if(tr.is(":hidden")==true){
		tr.show(200);
		$("#zkSpan"+id).html('<img src="images/bhz/jian.png" border="0"/>');
	}else{
		tr.hide(200);
		$("#zkSpan"+id).html('<img src="images/bhz/jia.png" border="0"/>');
	}
}
function queryTotalNum(morebdCode,bd,bhz,m8,m5,sdate,edate){
	openLoad();
	$.ajax({
		type:"POST", 
		url:"countAction!countTotalGzbwSn.action", 
		data:{morebdCode:morebdCode,bd:bd,bhz:bhz,m8:m8,m5:m5,sdate:sdate,edate:edate}, 
		timeout:10000, 
		success:function (data) {
			if(data!=null && data!=""){
				var arr = data.split("|");
				$("#fltd").html(arr[0]);
				$("#zltd").html(arr[1]);
			}else{
				alert("统计错误!");
			}
			closeLoad();
		}
	})
}