<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/5
  Time: 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
    <link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/common.js"></script>
    <title></title>
</head>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ3"/>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">短信报警</a></li>
        <li><a href="#">材料报警规则</a></li>
    </ul>
</div>

<div class="rightinfo">
    <table style="">
        <tr>
            <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                <td>
                    <input type="button" class="btn1" value="添加" onclick="javascript:location.href='messageAction!toAddBaoJingBasePage.action'" />
                </td>
            </c:if>
        </tr>
    </table>
    <br/>
    <table class="tablelist" >
        <tr>
            <th>材料名称</th>
            <th>是否短信报警</th>
            <th>短信内容水泥值-C不在范围内（-C将替换为实际值）</th>
            <th>发送级别（初/中/高）级</th>
            <th>下限</th>
            <th>上限</th>
            <th>操作</th>
        </tr>
        <c:choose>
            <c:when test="${fn:length(objPage.objList)==0}">
                <td colspan="7" align="center" height="50" style="color:red">您还没有设置报警基本信息!</td>
            </c:when>
            <c:otherwise>
                <c:forEach var="b" items="${objPage.objList}" varStatus="i">
                    <tr>
                        <td rowspan="3">${b.clName}</td>
                        <td rowspan="3">${b.isBaojing=='1' ? '报警' : '不报警'}</td>
                        <td rowspan="3"><textarea rows="4" cols="60" type="_moz">${b.msgContent}</textarea></td>
                        <td>初级(项目部)</td>
                        <td>${b.downValueCj}</td>
                        <td>${b.upValueCj}</td>
                        <td rowspan="3">
                            <input type="button" class="btn1" onclick="javascript:location.href='messageAction!toUpdatePage.action?id=${b.id}'" value="编缉"/>
                            <input type="button" class="btn1" onclick="javascript:location.href='messageAction!deleteBaoJingBase.action?id=${b.id}'" value="删除"/>
                        </td>
                    </tr>
                    <tr>
                        <td>中级(监理)</td>
                        <td>${b.downValueZj}</td>
                        <td>${b.upValueZj}</td>
                    </tr>
                    <tr>
                        <td>高级(指挥部)</td>
                        <td>${b.downValueGj}</td>
                        <td>${b.upValueGj}</td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </table>
</div>
</body>
</html>
