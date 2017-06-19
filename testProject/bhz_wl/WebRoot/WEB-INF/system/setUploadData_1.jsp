<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设置上传数据信息</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript">
function setbaseData(){
	var bdCode = $("input[name='bdCode']").val();
	var bhzCode = $("input[name='bhzCode']").val();
	var maxCode = $("input[name='maxCode']").val();
	var custCode = $("input[name='custCode']").val();
	var req = /^[0-9]*[1-9][0-9]*$/;
	if(req.test(custCode)==false){
		alert("自定义编号只能是整数!");
		return;
	}
	if(custCode>=maxCode){
		alert("自定义编号只能小于最大编号!");
	}
	if(confirm("您确认要设置吗？")){
		$.ajax({
			type:"post",
			url:"BHZAction!saveSetDataInfo.action",
			data:{bdCode:bdCode,bhzCode:bhzCode,maxCode:maxCode,custCode:custCode},
			timeout:10000,
			success:function (data) {
				if(data=="success"){
					alert("设置成功!");
					closeDiv();
				}else{
					alert("设置失败!");
					closeDiv();
				}
			}
		});
	}
	return;
}
function getcldate(){
	var bdCode = $("input[name='bdCode']").val();
	var bhzCode = $("input[name='bhzCode']").val();
	var selCode = $("input[name='selCode']").val();
	if(selCode==""){
		alert("请输入需要查询的编号!");
		return;
	}
	$.ajax({
		type:"post",
		url:"BHZAction!getclDate.action",
		data:{bdCode:bdCode,bhzCode:bhzCode,selCode:selCode},
		timeout:10000,
		success:function (data) {
			$("#cldatemsg").html(data);
		}
	});
}
function closeDiv(){
	parent.ymPrompt.close();
}
</script>
</head>

<body style="overflow: hidden;">
<div class="all">
    <div class="scsj">
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">系统管理</a>&gt;&gt;<font color="red">${bdObj.bdName}</font>标<font color="red">${bhzObj.bhzName}</font>拌合站</p>
    </div>
    <table class="Tab">
        <tr>
            <td>
                <input type="hidden" name="bdCode" value="${bdCode}"/>
                <input type="hidden" name="bhzCode" value="${bhzCode}"/>
            	最大编号:
            	<input type="text" name="maxCode" value="${maxGroup}" class="Fwidth" style="width:120px;" readonly="readonly"/>
                  自定义编号:
                <input type="text" name="custCode" value="" class="Fwidth" style="width:120px;"/>
            </td>
        </tr>
    </table>
    <div style="text-align:center;margin-top:20px;">
    <input style="width:45px;height:25px;" type="button" value="确定" onclick="setbaseData();"/>
    <input style="width:45px;height:25px;" type="button" value="取消" onclick="closeDiv();"/>
    </div>
    <div style="margin-top:50px;">
    <font color="gray">出料时间查询</font>&nbsp;&nbsp;&nbsp;&nbsp;<font id="cldatemsg" color="red"></font>
    <div style="margin-top:10px;">
    输入编号：<input type="text" name="selCode" class="Fwidth" style="width:120px;"/>
    <input style="width:45px;height:25px;" type="button" value="查询" onclick="getcldate();"/>
    </div>
    </div>
</div>
</body>
</html>