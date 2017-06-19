<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户添加</title>
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
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">系统管理</a>&gt;&gt;<a href="javascript:;">添加用户</a></p>
    </div>
    <%@ include file="base/sysLinked.jsp" %>
    <input type="hidden" id="hint" value="${hint}"/>
    <form name="AddUserForm" id="AddUserForm" action="UserAction!addUser.action" method="post">
    <table class="bjdiv">
    	<tr>
        	<td width="30%" align="right">所属标段：</td>
            <td width="70%">
            <select name="bdCode" class="Fwidth">
					<option value="">==请选择标段==</option>
					<c:forEach var="b" items="${BDs}">
						<option value="${b.bdCode}" ${b.bdCode==user.bdCode?'selected':''}>${b.bdName}</option>
					</c:forEach>
			</select>
            </td>
            </tr>
            <tr>
                <td align="right">所属拌合站：</td>
                <td>
                <select name="bhzCode" class="Fwidth">
					<option value="">==请选择拌合站==</option>
					<c:forEach var="b" items="${BHZs}">
					<option value="${b.bhzCode}" ${b.bhzCode==user.bhzCode?'selected':''}>${b.bhzName}</option>
					</c:forEach>
				</select>
                </td>
      	   </tr>
            <tr>
                <td class="FL" align="right">用户名：</td>
                <td><input type="text" name="username" value="${user.username}" class="Fwidth"/></td>
           </tr>
           <tr>
                <td class="FL" align="right">登陆密码：</td>
                <td><input type="text" name="userpwd" value="${user.userpwd}" class="Fwidth"/></td>
           </tr>
            <tr>
                <td colspan="2" align="center">
                    <a href="javascript:;" class="bia" style="margin-left: 350px" onclick="user.CheckAdd();">添加</a>
                    <a href="javascript:history.back();" class="bia">取消</a>
                </td>
            </tr>
    </table>
    </form>
</div>
</body>
</html>