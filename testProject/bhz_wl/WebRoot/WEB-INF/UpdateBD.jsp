<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/5
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/common.js"></script>
    <script type="text/javascript" src="js/BD.js"></script>
    <script type="text/javascript" src="js/util.js"></script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">系统管理</a></li>
        <li><a href="#">标段修改</a></li>
    </ul>
</div>
<div class="rightinfo">
    <form id="AddBDForm" action="BDAction!UpdateBD.action" method="post">
        <input type="hidden" name="flagBdCode" value="${bd.bdCode}"/>
        <table class="tablelist">
            <tr>
                <td width="30%" align="right">标段编号：</td>
                <td width="70%">
                    <input type="text" name="bdCode" value="${bd.bdCode}" class="scinput" style="height: 25px" readonly="readonly"/>
                    <font color="red"><b>${existName}</b>${hint}</font>
                </td>
            </tr>
            <tr>
                <td align="right">标段名称：</td>
                <td><input type="text" name="bdName" value="${bd.bdName}" class="scinput" style="height: 25px"/></td>
            </tr>
            <tr>
                <td class="FL" align="right">标段序号：</td>
                <td><input type="text" name="orderNum" value="${bd.orderNum}" class="scinput" style="height: 25px"/></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="button" class="btn1" style="margin-left: 350px" onclick="bd.CheckAdd();" value="修改"/>
                    <input type="button" class="btn1" onclick="javascript:history.back();" value="取消"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
