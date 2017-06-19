function replyQuestion(url){//单位建议回复页面
	parent.ymPrompt.win({message:url,width:500,height:320,title:'',handler:null,minBtn:true,maxBtn:true,closeBtn:true,iframe:true});
}
function dealWithQuestion(url,w,h){//意见反馈管理中问题处理页面
	//alert("I am running ...................");
	parent.ymPrompt.win({message:url,width:w,height:h,title:'',handler:null,minBtn:true,maxBtn:true,closeBtn:true,iframe:true});
	//alert("I run end...............");
}
function dealWith(url,w,h,t){//意见反馈管理中问题处理页面
//	alert("I am running ...................");
	ymPrompt.win({message:url,width:w,height:h,title:t,handler:null,minBtn:true,maxBtn:true,closeBtn:true,iframe:true});
//	alert("I run end...............");
}
//处理浏览器传值乱码问题
function dealWithEncode(url,w,h){//意见反馈管理中问题处理页面
	ymPrompt.win({message:encodeURI(encodeURI(url)),width:w,height:h,title:'',handler:null,minBtn:true,maxBtn:true,closeBtn:true,iframe:true});
}
		
//刷新父级页面
function dealWithAfterRefresh(url,w,h){//意见反馈管理中问题处理页面
	ymPrompt.win({message:url,width:w,height:h,title:'',handler:handler,minBtn:true,maxBtn:true,closeBtn:true,iframe:true});
}

function handler(){
	var form =document.getElementById("khRateForm");
	if(form==null){
		form =document.getElementById("myForm");
	}
	form.submit();
	//window.location.reload();
	//alert($("#khRateForm",parent.document.body).attr('action')+"321");
}

function update(url,w,h){//意见反馈管理中问题处理页面
	ymPrompt.win({message:url,width:w,height:h,title:'',handler:null,minBtn:true,maxBtn:true,closeBtn:true,iframe:false});
}
function showInfo(url,w,h){//评议者信息
	parent.ymPrompt.win({message:url,width:w,height:h,title:'',handler:null,minBtn:true,maxBtn:true,closeBtn:true,iframe:true});
}


function showUnit(){
		$.openLayer({
			maxItems : 5,
			pid : "0",
			returnText : "restxts",
			returnValue : "resvals",
			span_width : {d1:120,d2:150,d3:150},
			index : 1
			});
}
function goUrl(URL){
	$("#Main2",parent.document.body).attr('src',URL);
}
function closedWindows(){
	parent.ymPrompt.close();
}
function closeWd(){
	ymPrompt.close();

}
function showTip(type){
	closedWindows();
	parent.operationTip(type);
}