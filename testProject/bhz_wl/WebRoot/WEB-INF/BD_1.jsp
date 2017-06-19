<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>标段管理</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/BD.js"></script>
<script type="text/javascript" src="js/util.js"></script>
</head>

<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ5"/>
<div class="all">
    <div class="scsj">
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">系统管理</a></p>
    </div>
    <form id="FindBDForm" action="BDAction!findBD.action" method="post" >
    <table class="Tab">
        <tr>
            <td class="FL">标段编号：</td>
            <td>
                <input type="text" class="Fwidth" name="bdCode" value="${bd.bdCode}"/>
            </td>
            <td class="FL">标段名称：</td>
            <td>
               <input type="text" class="Fwidth" name="bdName" value="${bd.bdName}"/>
            </td>   
            <td class="FL">标段序号：</td>
            <td>
               <input type="text" class="Fwidth" name="orderNum" value="${bd.orderNum}"/>
            </td>   
            <td><a href="javascript:;" class="search" onclick="bd.CheckFind()">查询</a></td>
			<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
            <td><a href="BDAction!toAddBdPage.action" class="tjia">添加</a></td>
            </c:if>
        </tr>
    </table>
    </form>
        <%@ include file="base/sysLinked.jsp" %>
        <div class="sj_list">
            <table class="message" >
                <tr class="onbg">
                    <td>标段编号</td>
                    <td>标段名称</td>
                    <td>标段序号</td>
					<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                    <td>操作</td>
                    </c:if>
                </tr>
                <c:choose>
				<c:when test="${fn:length(objPage.objList)>0}">
                <c:forEach var="b" items="${objPage.objList}">
                 <tr>
                    <td>${b.bdCode}</td>
                    <td>${b.bdName}</td>
                    <td>${b.orderNum}</td>
					<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                    <td align="center" style="width:300px;">
                        <div  class="tdwid">
                            <div class="tr_odiv">
                                <span class="tr_bg"></span>
                                <a href="BDAction!toUpdatePage.action?bdCode=${b.bdCode}">编缉</a>
                            </div>
                            <div class="tr_div">
                                <span class="tdbg"></span>
                                <a href="BDAction!deleteBD.action?bdCode=${b.bdCode}" del="del">删除</a>
                            </div>
                        </div>
                     </td>
                     </c:if>
                </tr>
                </c:forEach>
                </c:when>
                <c:otherwise>
                <tr><td height="25" colspan="4" style="text-align: center;color: red;">暂无数据记录!</td></tr>
                </c:otherwise>
                </c:choose>
            </table>
            <div class="clear"></div>
            <p:PagingTag url="BDAction!toBDPage.action" page="${objPage}" forPage="true" showCurAndSum="true"/>
        </div>
</div>
</body>
</html>