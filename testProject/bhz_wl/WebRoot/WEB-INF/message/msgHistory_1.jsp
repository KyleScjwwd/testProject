<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发送记录</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	$("#txtStarDate").calendar();
	$("#txtEndDate").calendar();
	var msgInfo = $("#msgInfo").val();
	if (msgInfo != null && msgInfo != "") {
		alert(msgInfo);
	}
});
function seleParam(){
	$("#form1").submit();
}
function sendMsg(){
	var mobile = $("input[name='mobile']").val();
	var msgContent = $("input[name='msgContent']").val();
	var  re = /^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/;
	 if(!re.test(mobile)){
	 	alert("请输入正确的手机号码!");
	 	return false;
	 }
	if(msgContent==""){
		alert("请输入发送短信内容!");
		return false;
	}
	if(confirm('你确定要发送该内容吗?')){
		$("#form1").attr("action","messageAction!sendMessage.action");
		$("#form1").submit();
		return true;
	}
	return false;
}
</script>
</head>

<body>
<div class="all">
    <div class="scsj">
            <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">短信报警 </a></p>
    </div>
    <input type="hidden" id="msgInfo" value="${msgInfo}"/>
    <form id="form1" action="messageAction!msgHistory.action" method="post">
    <table class="Tab">
        <tr>
            <td class="FL">标段名称：</td>
            <td>
                <select name="bdCode" id="bdCode" class="Fwidth">
                    <option value="">==请选择标段==</option>
                    <c:forEach var="b" items="${bdList}">
				    <option <c:if test="${b.bdCode eq sCD}">selected="selected"</c:if> value="${b.bdCode}">${b.bdName}</option>
				    </c:forEach>
              	</select>
            </td>
            <td class="FL">发送内容：</td>
            <td>
                <input type="text" class="Fwidth" name="msgContent" value="${msgContent}"/>
            </td>   
        </tr>
        <tr>
            <td class="FL">发送号码：</td>
            <td>
                <input type="text" class="Fwidth" name="mobile" value="${mobile}"/>
            </td>
            <td class="FL">发送时间：</td>
            <td>
            	<input name="sdate" value="${sdate}" id="txtStartDate" type="text" onfocus="WdatePicker()" class="Fwidth" style="width:100px;"/>
                   至
                <input name="edate" value="${edate}" type="text" id="txtEndDate" onfocus="WdatePicker()" class="Fwidth" style="width:100px;"/>
            </td>   
            <td>
            <a href="javascript:;" class="search" onclick="seleParam();">查询</a>
            <c:if test="${sessionScope.username eq 'td77011'}">
            &nbsp;&nbsp;&nbsp;
            <a href="javascript:;" class="search" onclick="sendMsg();">模拟发送</a>
            </c:if>
            </td>
        </tr>
    </table>
    </form>
        <div class="list">
            	<a class="onmouse" href="messageAction!getMessageUser.action">通迅录设置</a>
            	<a class="onmouse" href="messageAction!setMessage.action">短信报警设置</a>
            	<a class="onmouse" href="messageAction!setBaoJingBase.action">报警基本设置</a>
            	<a class="onmouse" href="messageAction!setBaoJingPersonBase.action">报警人员基本设置</a>
	         	<a class="onmouse" href="messageAction!setMessageSendType.action">短信发送方式设置</a>
            	<a class="mouse">发送记录查询</a>
        </div>
        <div class="sj_list">
            <table class="message" >
                <tr class="onbg">
                    <td width="5%">所属标段</td>
                    <td width="20%">发送号码</td>
                    <td width="50%">发送内容</td>
                    <td width="5%">发送时间</td>
                    <td width="5%">发送状态</td>
                </tr>
                <c:choose>
					<c:when test="${fn:length(objPage.objList)>0}">
					<c:forEach var="l" items="${objPage.objList}">
					<tr>
						<td>${map[l.bdCode]}</td>
						<td style="word-break:break-all">${l.mobile}</td>
						<td style="word-break:break-all">${l.msgContent}</td>
						<td><fmt:formatDate value="${l.sendDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
						<c:choose>
						<c:when test="${l.sendFlag eq '1'}">成功</c:when>
						<c:otherwise>失败</c:otherwise>
						</c:choose>
						</td>
					</tr>
					</c:forEach>
					</c:when>
					<c:otherwise>
					<tr><td height="25" colspan="5" style="text-align: center;color: red;">暂无数据记录!</td></tr>
					</c:otherwise>
				</c:choose>
            </table>
            <div class="clear"></div>
            <p:PagingTag url="messageAction!msgHistory.action" page="${objPage}" forPage="true" showCurAndSum="true"/>
        </div>
</div>
</body>
</html>