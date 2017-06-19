<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
</head>

<body>
<div class="all">
    <div class="scsj">
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">日志查看</a></p>
    </div>
    <table>
        <tr>
        <td align="center" style="text-align:center;margin-top:8px;margin-bottom:8px;">
        <font size="5">${bdCode}标${bhzCode}拌合站&nbsp;日志查看</font>
        </td>
        </tr>
        <tr><td class="FL">
        <c:choose>
        <c:when test="${fileContent eq ''}"><font color="red">无数据!</font></c:when>
        <c:otherwise>${fileContent}</c:otherwise>
        </c:choose>
        </td></tr>
    </table>
</div>
<br/>
</body>
</html>