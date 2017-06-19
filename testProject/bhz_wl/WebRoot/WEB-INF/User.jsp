<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/User.js"></script>
<script type="text/javascript" src="js/util.js"></script>
</head>

<body>
<div class="all">
    <div class="scsj">
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">系统管理</a></p>
    </div>
    <input type="hidden" id="hidBd" value="${user.bdCode}"/>
    <input type="hidden" id="hidBhz" value="${user.bhzCode}"/>
    <form action="UserAction!findUser.action" id="FindUserForm" method="post">
    <table class="Tab">
        <tr>
        	<td class="FL">所属标段：</td>
            <td>
            	<select name="bdCode" class="Fwidth" style="width:120px;">
                  <option value="">==请选择标段==</option>
                  <c:forEach var="b" items="${BDs}">
				    <option value="${b.bdCode}" ${b.bdCode==user.bdCode?'selected':''}>${b.bdName}</option>
			 	  </c:forEach>
                </select>
            </td>
            <td class="FL">所属拌合站：</td>
            <td>
            	<select name="bhzCode" class="Fwidth" style="width:120px;">
                  	<option value="">==请选择拌合站==</option>
                </select>
            </td>
            <td class="FL">用户名：</td>
            <td><input type="text" name="username" class="Fwidth" style="width:120px;"/></td>   
            <td class="FL">登陆密码：</td>
            <td><input type="text" name="userpwd" class="Fwidth" style="width:120px;"/></td>   
            <td><a href="javascript:;" class="search" onclick="user.CheckFind();">查询</a></td>
            <c:set var="authPage" value="${sessionScope.username}-fun0103"/>
			<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
            <td><a href="UserAction!toAddUserPage.action" class="tjia">添加</a></td>
            </c:if>
        </tr>
    </table>
    </form>
        <%@ include file="base/sysLinked.jsp" %>
        <div class="sj_list">
            <table class="message" >
                <tr class="onbg">
                    <td>所属标段</td>
	                <td>所属拌合站</td>
	                <td>用户名</td>
	                <td>登录密码</td>
	                <c:set var="authPage" value="${sessionScope.username}-fun0103"/>
					<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
	                <td>操作</td>
	                </c:if>
                </tr>
                <c:choose>
				<c:when test="${fn:length(objPage.objList)>0}">
                <c:forEach var="u" items="${objPage.objList}">
					<tr>
					<td>${map[u.bdCode]}</td>
					<td>${map2[u.bhzCode]}</td>
					<td>${u.username}</td>
					<td>${u.userpwd}</td>
					<c:set var="authPage" value="${sessionScope.username}-fun0103"/>
					<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
					<td align="center" style="width:300px;">
						<c:if test="${u.username ne 'admin'}">
                        <div  class="tdwid">
                            <div class="tr_odiv">
                                <span class="tr_bg"></span>
                                <a href="UserAction!toUpdatePage.action?id=${u.id}&bdCode=${u.bdCode}">编辑</a>
                            </div>
                            <div class="tr_div">
                                <span class="tdbg"></span>
                                <a href="UserAction!deleteUser.action?id=${u.id}" del="del">删除</a>
                            </div>
                        </div>
                        </c:if>
                    	</td>
                    	</c:if>
					</tr>
				</c:forEach>
                </c:when>
                <c:otherwise>
                <tr><td height="25" colspan="7" style="text-align: center;color: red;">暂无数据记录!</td></tr>
                </c:otherwise>
                </c:choose>                
            </table>
            <div class="clear"></div>
            <p:PagingTag url="UserAction!toUserPage.action" page="${objPage}" forPage="true" showCurAndSum="true"/>
    </div>
</div>
</body>
</html>