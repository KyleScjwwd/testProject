<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>权限设置</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
</head>

<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ5"/>
<div class="all">
    <div class="scsj">
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">权限设置</a></p>
    </div>
        <%@ include file="base/sysLinked.jsp" %>
        <div class="sj_list">
            <table class="message" >
                <tr class="onbg">
                    <td>序号</td>
	                <td>用户名</td>
	                <td>标段</td>
					<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
	                <td>权限设置</td>
	                </c:if>
                </tr>
                <c:choose>
				<c:when test="${fn:length(users)>0}">
                <c:forEach var="u" varStatus="v" items="${users}">
					<tr>
					<td>${v.count}</td>
					<td>${u.username}</td>
					<td>${bdMap[u.bdCode]}</td>
					<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
					<td>
					<c:if test="${u.username ne 'admin'}">
					<a style="color:blue;" href="authorizationAction!setUserAuthorization.action?userID=${u.id}&username=${u.username}" target="_blank">设置</a>
					</c:if>
					</td>
					</c:if>
					</tr>
				</c:forEach>
                </c:when>
                <c:otherwise>
                	<tr><td height="25" colspan="7" style="text-align: center;color: red;">暂无用户数据记录!</td></tr>
                </c:otherwise>
                </c:choose>                
            </table>
            <div class="clear"></div>
    </div>
</div>
</body>
</html>