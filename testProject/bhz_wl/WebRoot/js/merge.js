function showMergeData(){
	var bd = $("#bd").val();
	var bhz = $("#bhz").val();
	window.location.href="mainAction!setMerge.action?bdCode="+bd+"&bhzCode="+bhz;
}
function addMerge(){
	var bd = $("#bd").val();
	var bhz = $("#bhz").val();
	if(bd==""){
		alert("请选择标段!");
		return;
	}
	if(bhz==""){
		alert("请选择拌合站!");
		return;
	}
	showPage('mainAction!addMerge.action?bd='+bd+'&bhz='+bhz,650,500,'新增合并列详细信息');
}
function addTextData(){
	var colsName = "";
	var len = $("input[name='pname']:checked").length;
	$("input[name='pname']:checked").each(function(i){
		colsName+=$(this).val();
		if(i<len-1){
			colsName+="+";
		}
	});
	$("#colsName").val(colsName);
}
function delMerge(id,bdCode,bhzCode){
	if(confirm("确定要删除该组数据？")){
		window.location.href = "mainAction!setMerge.action?&id="+id+"&bdCode="+bdCode+"&bhzCode="+bhzCode+"&isDel=1";
	}
}
function saveMerge(){
	var req = /^[0-9]*[1-9][0-9]*$/;
	var sortIndex = $("#sortIndex").val();
	var colsName = $("#colsName").val();
	var colsValue = $("#colsValue").val();
	if(sortIndex==""){
		alert("请输入显示顺序!");
		return;
	}
	if(colsName==""){
		alert("请选择合并列!");
		return;
	}
	if(colsValue==""){
		alert("请输入合并后列名!");
		return;
	}
	if(req.test(sortIndex)==false){
		alert("显示顺序只能为正整数!");
		return;
	}
	if(confirm("确定要保存该组数据？")){
		var bdCode = $("#bdCode").val();
		var bhzCode = $("#bhzCode").val();
		$.ajax({
			type:"POST", 
			url:"mainAction!saveMerge.action", 
			data:{bdCode:bdCode,bhzCode:bhzCode,colsName:colsName,colsValue:colsValue,sortIndex:sortIndex}, 
			timeout:10000, 
			success:function (data) {
				if(data=="1"){
					alert("有合并列在该标段和拌合站已经存在,不能累加相同列!");
				}else if(data=="2"){
					alert("保存成功!");
					parent.location.reload();
				}else{
					alert("保存失败!")
				}					
			}
		});
	}
	return;
}
function closeDiv(){
	parent.ymPrompt.close();
}