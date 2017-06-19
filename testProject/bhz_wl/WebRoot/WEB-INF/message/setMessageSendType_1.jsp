<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>短信发送方式设置</title>
	<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
	<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
	<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
	<script type="text/javascript" src="js/messageSendType.js"></script>
</head>

<body>
<div class="all">
    <div class="scsj">
    	<p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">短信发送方式设置 </a></p>
    </div>
        <div class="list">
        	<c:set var="authPage" value="${sessionScope.username}-fun0301"/>
			<c:if test="${sessionScope.userauthor[authPage] eq '1' || sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
            	<a class="onmouse" href="messageAction!getMessageUser.action">通迅录设置</a>
            </c:if>
            <c:set var="authPage" value="${sessionScope.username}-fun0302"/>
			<c:if test="${sessionScope.userauthor[authPage] eq '1' || sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
            	<a class="onmouse" href="messageAction!setMessage.action">短信报警设置</a>
            </c:if>
            <c:set var="authPage" value="${sessionScope.username}-fun0304"/>
			<c:if test="${sessionScope.userauthor[authPage] eq '1' || sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
            	<a class="onmouse" href="messageAction!setBaoJingBase.action">报警基本设置</a>
            </c:if>
            <c:set var="authPage" value="${sessionScope.username}-fun0305"/>
			<c:if test="${sessionScope.userauthor[authPage] eq '1' || sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
            	<a class="onmouse" href="messageAction!setBaoJingPersonBase.action">报警人员基本设置</a>
            </c:if>
            <c:set var="authPage" value="${sessionScope.username}-fun0306"/>
			<c:if test="${sessionScope.userauthor[authPage] eq '1' || sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
            	<a class="mouse">短信发送方式设置</a>
            </c:if>
            <c:set var="authPage" value="${sessionScope.username}-fun0303"/>
			<c:if test="${sessionScope.userauthor[authPage] eq '1' || sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
            	<a class="onmouse" href="messageAction!msgHistory.action">发送记录查询</a>
            </c:if>
        </div>
        <input type="hidden" id="hint" value="${hint}"/>
        <form id="SaveMessageSendTypeForm" action="messageAction!saveMessageSendType.action" method="post">
        <table class="bjdiv">
        	<tr>
        		<td width="30%" align="right">短信发送方式：</td>
	            <td width="70%">
	            	<input type="radio" name="msgBaoJingType" value="0" <c:if test="${msgBaoJingType eq '0'}">checked="checked"</c:if> />报警材料逐条发送
	            	<input type="radio" name="msgBaoJingType" value="1" <c:if test="${msgBaoJingType eq '1'}">checked="checked"</c:if> />报警材料合并发送
	            </td>
        	</tr>
        	<tr>
        		<td></td>
        		<td>提示：短信内容中可以使用的变量：sm1(标段)、sm2(拌合站)、sm3(浇筑部位)、sm4(强度等级)、sm5(出料时间)、sm6(各材料用量及误差百分比)、sm7(方量)</td>
        	</tr>
        	<tr>
        		<td align="right">报警材料合并发送短信内容：</td>
        		<td>
        			<textarea rows="4" cols="60" name="msgTemplate">${msgTemplate}</textarea>
        		</td>
        	</tr>
	        <tr>
	            <td colspan="2" align="center">
	            	<a href="javascript:;" class="bia" style="margin-left: 350px" onclick="messageSendType.checkSave();">保存</a>
	            	<a href="javascript:history.back();" class="bia">取消</a>
	            </td>
	       </tr>
        </table>
        </form>
</div>
</body>
</html>