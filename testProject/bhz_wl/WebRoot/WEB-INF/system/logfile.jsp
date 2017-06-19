<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
</head>

<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">系统管理</a></li>
        <li><a href="#">日志查看</a></li>
    </ul>
</div>
<div class="rightinfo">
    <table class="tablelist">
        <tr>
            <td align="center" style="text-align:center;margin-top:8px;margin-bottom:8px;">
                <font size="5">${bdCode}标${bhzCode}拌合站&nbsp;日志查看</font>
            </td>
        </tr>
        <tr>
            <td>
                <c:choose>
                    <c:when test="${fileContent eq ''}">
                        <font color="red">无数据!</font>
                    </c:when>
                    <c:otherwise>
                        ${fileContent}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>
</div>
<br/>
</body>
</html>