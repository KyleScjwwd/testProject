<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="/page-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>强度等级</title>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/common.js"></script>
    <script type="text/javascript" src="js/User.js"></script>
    <script type="text/javascript" src="js/intensityGrade.js"></script>
    <script type="text/javascript" src="js/util.js"></script>
</head>

<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ5"/>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">系统管理</a></li>
        <li><a href="#">强度等级</a></li>
    </ul>
</div>
<div class="rightinfo">
    <form action="intensityGradeAction!getIntensityGrade.action" id="FindIntensityGradeForm" method="post">
        <table class="tablelist">
            <tr>
                <td width="3%">强度等级：</td>
                <td width="20%">
                    <input type="text" name="intensityGrade" value="${qddj.intensityGrade}" class="scinput" style="height: 25px"/>
                </td>
                <td width="3%">
                    <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                        <input type="button" class="btn1" onclick="javascript:location.href='intensityGradeAction!toAddIntensityGradePage.action'" value="添加"/>
                    </c:if>
                    <input type="button" class="btn1" onclick="intensityGrade.CheckFind();" value="查询"/>
                </td>
            </tr>
        </table>
    </form>
    <table class="tablelist">
        <tr>
            <th>强度等级</th>
            <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                <th width="150px">操作</th>
            </c:if>
        </tr>
        <c:choose>
            <c:when test="${fn:length(objPage.objList)>0}">
                <c:forEach var="i" items="${objPage.objList}">
                    <tr>
                        <td>${i.intensityGrade}</td>
                        <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                            <td align="center">
                                <input type="button" class="btn1" onclick="javascript:location.href='intensityGradeAction!toUpdatePage.action?id=${i.id}'" value="编缉"/>
                                <input type="button" class="btn1" onclick="javascript:location.href='intensityGradeAction!deleteIntensityGrade.action?id=${i.id}'" del="del" value="删除"/>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td height="25" colspan="4" style="text-align: center;color: red;">暂无数据记录!</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>
    <div class="clear"></div>
    <p:PagingTag url="intensityGradeAction!getIntensityGrade.action" page="${objPage}" forPage="true" showCurAndSum="true"/>
</div>
</body>
</html>