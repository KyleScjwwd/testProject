<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加合并列数据</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/merge.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	var paramIsNull = $("#paramIsNull").val();
	if(paramIsNull=="1"){
		alert("该标段、拌合站没有参数，请先至【参数管理】中进行设置!");
		closeDiv();
	}
});
</script>
</head>

<body style="overflow: hidden;">
<c:set var="authPage" value="${sessionScope.username}-baseBHZ5"/>
<div class="all">
    <div class="scsj">
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">系统管理 </a>&gt;&gt;<font color="red"> ${bdMap[bdCode].bdName}标${bhzMap[bhzCode].bhzName}拌合站合并列设置</font></p>
    </div>
    <input type="hidden" id="paramIsNull" value="${paramIsNull}"/>
    <table class="message" style="margin: 10px 0 10px 0;">
        <tr>
            <td style="height:250px;width:50%" valign="top">
            <input type="hidden" id="bdCode" name="bdCode" value="${bdCode}"/>
            <input type="hidden" id="bhzCode" name="bhzCode" value="${bhzCode}"/>
            <div style="width:100%;height:230px;overflow:auto;">
            	<c:forEach var="p" items="${paramList}">
            	<div style="text-align:left;margin: 0 0 0 10px;"><input type="checkbox" name="pname" value="${p.pname}" onclick="addTextData();"/>${p.pname}&nbsp;${p.pvalue}</div>
            	</c:forEach>
            </div>
            </td>
            <td valign="top">
            <textarea id="colsName" name="colsName" rows="15" cols="40" readonly="readonly"></textarea>
            </td>
        </tr>
        <tr>
        <td>显示顺序:<input type="text" id="sortIndex" name="sortIndex"/></td>
        <td>合并列名:<input type="text" id="colsValue" name="colsValue"/></td>
        </tr>
    </table>
    <div style="text-align:center;margin-top:20px;">
	<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
    <input style="width:45px;height:25px;" type="button" value="保存" onclick="saveMerge();"/>
    </c:if>
    <input style="width:45px;height:25px;" type="button" value="取消" onclick="closeDiv();"/>
    </div>
</div>
</body>
</html>