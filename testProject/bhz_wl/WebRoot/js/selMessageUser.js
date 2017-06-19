var allMobile = "";
var allMobile_bdCode = "";
var allPerson = "";
var tmpAllMobile="";

function uniQueue(array){
	var arr=[];
	var m;
	while(array.length>0){
		m=array[0];
		arr.push(m);
		array=$.grep(array,function(n,i){
			return n==m;
		},true);
	}
	return arr;
}


function openPhoneDiv(mobileId) {
	allMobile = $('input[name="' + mobileId + '"]').val();
	if (allMobile.length > 0)
		allMobile += ",";
	allMobile_bdCode = $('input[id="' + mobileId + '"]').val();
	if(undefined!=allMobile_bdCode){
		if (allMobile_bdCode.length > 0)
			allMobile_bdCode += ",";
	}
	allPerson = $('a[id="' + mobileId + '"]').html();
	$("#lianxiDiv").html("");
	$("#mobileId").val(mobileId);
	document.getElementById('popDiv').style.display = 'block';
	document.getElementById('popIframe').style.display = 'block';
	document.getElementById('bg').style.display = 'block';
}
function closeDiv() { 
	document.getElementById('popDiv').style.display = 'none'; 
	document.getElementById('bg').style.display = 'none';
	document.getElementById('popIframe').style.display = 'none'; 
}
	
function getPhoneInfo(id) {
	$("a[id^='treeSpan']").removeAttr("style");
	$("#treeSpan"+id).css("color","red");
	
	var mobileArr = new Array();
	var mobile_bdCodeArr = new Array();
	mobileArr = allMobile.split(",");
	if(undefined!=allMobile_bdCode){
		mobile_bdCodeArr = allMobile_bdCode.split(",");
	}
	var strs = "";
	for (var i=0; i<mobileArr.length; i++) {
		strs += mobileArr[i]+ ",";
	}
	
	$.getJSON("messageAction!queryMessageUser.action", {bdCode:id}, function(data){
		var result = "";
		var m = data.msgUserJson;
		var isChecked = "";
		for (var i=0; i<m.length; i++) {
			if (strs.indexOf(m[i].telphone) > -1) {
				isChecked = "checked='checked'";
				if(tmpAllMobile.indexOf(m[i].telphone)>-1){

				}else{
					tmpAllMobile+=m[i].telphone+',';
				}
			} else {
				isChecked = "";
			}
			result += '<div class="telphonediv"><input type="checkbox" name="lianxiren'+m[i].bdCode+'" value="'+m[i].telphone+'" '+isChecked+'/>'+m[i].realName+'('+m[i].telphone+')</div>';
		}
		$("#allSelectBd").val(id);
		$("#lianxiDiv").html(result);
		initCheckboxEvent();
	});
}
	
function queDing() {
	var mobileId = $("#mobileId").val();
	if(mobileId==""){
		alert("发送级别未选中!");
		return false;
	}
	if (allMobile.length > 0){
		allMobile = allMobile.substring(0, allMobile.length - 1);
		var str="";
		var arry=allMobile.split(",");
		var result=uniQueue(arry);
		for(var i=0;i<result.length;i++){
			if(i+1>=result.length){
				str+=result[i];
			}else{
				str+=result[i]+",";
			}
		}
		tmpAllMobile=tmpAllMobile.substring(0, tmpAllMobile.length - 1);
		if(tmpAllMobile.split(",").length==str.split(",").length){
			allMobile=str;
		}else{
			var d=str.replace(tmpAllMobile,"");
			var s=str.replace(d,"");
			allMobile=s;
		}
	}

	if (allMobile_bdCode.length > 0)
		allMobile_bdCode = allMobile_bdCode.substring(0, allMobile_bdCode.length - 1);
	$('input[name="'+mobileId+'"]').val(allMobile);
	$('input[id="'+mobileId+'"]').val(allMobile_bdCode);
	$('a[id="'+mobileId+'"]').html(allPerson);
 	closeDiv();
}
	
function isChecked(c) {
	var b = $("#allSelectBd").val();
	if (c == "1") {
		$("input[name='lianxiren"+b+"']").each(function() {
			$(this).attr("checked", true);
			var mobile = $(this).val() + ",";
			var bdCode = $("#allSelectBd").val() + ",";
			var person = $(this).parent().contents().filter(function(){return this.nodeType == 3;}).text();
			if(allMobile.indexOf(mobile)<-1){
				allMobile += mobile;
			}else{
				allMobile= allMobile.replace(mobile, "");
				allMobile += mobile;
			}
			if(allMobile_bdCode.indexOf(bdCode)<-1){
				allMobile_bdCode += bdCode;
			}else{
				allMobile_bdCode=allMobile_bdCode.replace(bdCode,"");
				allMobile_bdCode+= bdCode;
			}
			if (allPerson.indexOf(person) == -1)
				allPerson += " " + person;			
		});
	} else {
		$("input[name='lianxiren"+b+"']").each(function() {
			$(this).attr("checked", false);
			var mobile = $(this).val() + ",";
			var bdCode = $("#allSelectBd").val() + ",";
			var person = $(this).parent().contents().filter(function(){return this.nodeType == 3;}).text();
			allMobile = allMobile.replace(mobile, "");
			allMobile_bdCode = allMobile_bdCode.replace(bdCode, "");
			allPerson = allPerson.replace(person, "");						
		});
	}
}

function initCheckboxEvent() {
	$("#popDiv input[type='checkbox']").each(function(){
		$(this).unbind("click");
		$(this).bind("click", function(){
			var mobile = $(this).val() + ",";
			var bdCode = $("#allSelectBd").val() + ",";
			var person = $(this).parent().contents().filter(function(){return this.nodeType == 3;}).text();
			
			if ($(this).is(':checked')) {
				if(tmpAllMobile.indexOf(mobile)<-1){
					tmpAllMobile += mobile;
				}else{
					tmpAllMobile= tmpAllMobile.replace(mobile, "");
					tmpAllMobile += mobile;
				}

				if(allMobile.indexOf(mobile)<-1){
					allMobile += mobile;
				}else{
					allMobile= allMobile.replace(mobile, "");
					allMobile += mobile;
				}

				if(undefined!=allMobile_bdCode){
					allMobile_bdCode += bdCode;
				}else{
					allMobile_bdCode="";
					allMobile_bdCode += bdCode
				}
				if (null!=allPerson){
					if(	allPerson.indexOf(person) == -1){
						allPerson += " " + person;
					}
				}else{
					allPerson="";
					allPerson += " " + person;
				}
			} else {
				if(tmpAllMobile.indexOf(mobile)>-1){
					tmpAllMobile = tmpAllMobile.replace(mobile, "");
				}
				if(allMobile.indexOf(mobile)>-1){
					allMobile = allMobile.replace(mobile, "");
				}

				allMobile_bdCode = allMobile_bdCode.replace(bdCode, "");
				allPerson = allPerson.replace(person, "");
			}
		});
	});
}
