<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/5
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/common.js"></script>
    <script type="text/javascript" src="js/BHZ.js"></script>
    <script type="text/javascript" src="js/util.js"></script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">系统管理</a></li>
        <li><a href="#">拌合站修改</a></li>
    </ul>
</div>
<div class="rightinfo">
    <form id="AddBHZForm" action="BHZAction!updateBHZ.action" method="post">
        <input type="hidden" name="id" value="${bhz.id}"/>
        <table class="tablelist">
            <tr>
                <td width="30%" align="right">所属标段：</td>
                <td width="70%">
                    <select name="bdCode" class="scinput" style="height: 25px" disabled="disabled">
                        <option value="null">==请选择标段==</option>
                        <c:forEach var="b" items="${BDs}">
                            <option value="${b.bdCode}" ${b.bdCode==bhz.bdCode?'selected':''}>${b.bdName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right">拌合站编号：</td>
                <td><input type="text" name="bhzCode" value="${bhz.bhzCode}" class="scinput" style="height: 25px" readonly="readonly"/></td>
            </tr>
            <tr>
                <td align="right">拌合站名称：</td>
                <td><input type="text" name="bhzName" value="${bhz.bhzName}" class="scinput" style="height: 25px"/></td>
            </tr>
            <tr>
                <td class="FL" align="right">上限：</td>
                <td><input type="text" name="upNum" value="${bhz.upNum}" class="scinput" style="height: 25px"/></td>
            </tr>
            <tr>
                <td class="FL" align="right">下限：</td>
                <td><input type="text" name="downNum" value="${bhz.downNum}" class="scinput" style="height: 25px"/></td>
            </tr>
            <tr>
                <td class="FL" align="right">拌合站密码：</td>
                <td><input type="text" name="ext1" value="${bhz.ext1}" class="scinput" style="height: 25px"/></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="button" class="btn1" style="margin-left: 350px" onclick="bdz.CheckAdd();" value="修改"/>
                    <input type="button" class="btn1" onclick="javascript:history.back();" value="取消"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
