<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>拌合站添加</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/BHZ.js"></script>
<script type="text/javascript" src="js/util.js"></script>
</head>
<body>
<div class="all">
    <div class="scsj">
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">系统管理</a>&gt;&gt;<a href="javascript:;">添加拌合站</a></p>
    </div>
    <%@ include file="base/sysLinked.jsp" %>
    <input type="hidden" id="bhzCodeIn" value="${bhzCodeIn}"/>
    <form id="AddBHZForm" action="BHZAction!addBHZ.action" method="post" >
    <table class="bjdiv">
    	<tr>
        	<td width="30%" align="right">所属标段：</td>
            <td width="70%">
            <select name="bdCode" class="Fwidth">
					<option value="null">==请选择标段==</option>
					<c:forEach var="b" items="${BDs}">
						<option value="${b.bdCode}">${b.bdName}</option>
					</c:forEach>
			</select>
            </td>
            </tr>
            <tr>
                <td align="right">拌合站编号：</td>
                <td><input type="text" name="bhzCode" class="Fwidth"/></td>
      	   </tr>
            <tr>
                <td align="right">拌合站名称：</td>
                <td><input type="text" name="bhzName" class="Fwidth"/></td>
      	   </tr>
            <tr>
                <td class="FL" align="right">上限：</td>
                <td><input type="text" name="upNum" class="Fwidth"/></td>
           </tr>
           <tr>
                <td class="FL" align="right">下限：</td>
                <td><input type="text" name="downNum" class="Fwidth"/></td>
           </tr>
           <tr>
                <td class="FL" align="right">拌合站密码：</td>
                <td><input type="text" name="ext1" class="Fwidth"/></td>
           </tr>
            <tr>
                <td colspan="2" align="center">
                    <a href="javascript:;" class="bia" style="margin-left: 350px" onclick="bdz.CheckAdd();">添加</a>
                    <a href="javascript:history.back();" class="bia">取消</a>
                </td>
            </tr>
    </table>
    </form>
</div>
</body>
</html>