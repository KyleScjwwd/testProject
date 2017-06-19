<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/5
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/BD.js"></script>
    <script type="text/javascript" src="js/util.js"></script>
</head>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ5"/>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">系统管理</a></li>
        <li><a href="#">标段管理</a></li>
    </ul>
</div>
<div class="rightinfo">
    <form id="FindBDForm" action="BDAction!findBD.action" method="post">
        <table class="tablelist">
            <tr>
                <td>标段编号：</td>
                <td>
                    <input type="text" class="scinput" style="height: 25px;" name="bdCode" value="${bd.bdCode}"/>
                </td>
                <td>标段名称：</td>
                <td>
                    <input type="text" class="scinput" style="height: 25px;" name="bdName" value="${bd.bdName}"/>
                </td>
                <td>标段序号：</td>
                <td>
                    <input type="text" class="scinput" style="height: 25px;" name="orderNum" value="${bd.orderNum}"/>
                </td>
                <td>
                    <input type="button" class="btn1" onclick="bd.CheckFind()" value="查询"/>
                </td>
                <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                    <td>
                        <input type="button" class="btn1" onclick="javascript:location.href='BDAction!toAddBdPage.action'" value="添加"/>
                    </td>
                </c:if>
            </tr>
        </table>
    </form>

    <table class="tablelist" >
        <tr>
            <th>标段编号</th>
            <th>标段名称</th>
            <th>标段序号</th>
            <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                <th>操作</th>
            </c:if>
        </tr>
        <c:choose>
            <c:when test="${fn:length(objPage.objList)>0}">
                <c:forEach var="b" items="${objPage.objList}">
                    <tr>
                        <td style="text-align: center">${b.bdCode}</td>
                        <td style="text-align: center">${b.bdName}</td>
                        <td style="text-align: center">${b.orderNum}</td>
                        <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                            <td align="center" style="width:300px;">
                                <input type="button" class="btn1" onclick="javascript:location.href='BDAction!toUpdatePage.action?bdCode=${b.bdCode}'" value="编缉"/>
                                <input type="button" class="btn1" onclick="javascript:if(bd.CheckBDtoBHZ('${b.bdCode}')){alert('该标段下存在拌合数据，不能删除此标段'); return false}else{if(confirm('确定要删除该标段吗?')){location.href='BDAction!deleteBD.action?bdCode=${b.bdCode}'}}" del="del" value="删除"/>
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
    <p:PagingTag url="BDAction!toBDPage.action" page="${objPage}" forPage="true" showCurAndSum="true"/>
</div>
</body>
</html>
