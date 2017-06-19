var index = 0;
$(document).ready(function () {
	var bdCode = $("#bdCode").val();
	if (bdCode != null && bdCode != "") {
		index = 1;
		showBhz();
	}
});
function showBhz() {
	var bdVal = $("#bd").val();
	$.ajax({type:"POST", url:"mainAction!getBhzInfo.action", data:"bdCode=" + bdVal, timeout:10000, success:function (data) {
		setValue(data);
	}});
}
function setValue(data) {
	$("#bhz").empty();
	$("#bhz").append("<option value=\"\">==\u8bf7\u9009\u62e9\u62cc\u5408\u7ad9==</option>");
	var bhzCode = $("#bhzCode").val();
	if (data != "") {
		var arr = data.split(",");
		for (var i = 0; i < arr.length - 1; i++) {
			var val = arr[i].split("|");
			if (val[1] == undefined) {
				break;
			}
			$("#bhz").append("<option value=\"" + val[0] + "\">" + val[1] + "</option>");
		}
	}
	if (bhzCode == null || bhzCode == "" || index == 0) {
		$("#bhz").val("");
	} else {
		index = 0;
		$("#bhz").val(bhzCode);
	}
}
function openwin(m2,m6,groupId) {
	window.showModalDialog("mainAction!getGiveDetail.action?bd="+m2+"&bhz="+m6+"&groupId="+groupId, window,'dialogwidth:1500px;dialogheight:1200px;toolbars:0;location:0;statusbars:0;menubars:500') ;			
}