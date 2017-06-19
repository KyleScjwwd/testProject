<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="/page-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>设计配合比</title>
    <link rel="stylesheet" type="text/css" href="newWeb/css/style.css"/>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/common.js"></script>
    <script type="text/javascript" src="js/User.js"></script>
    <script type="text/javascript" src="js/designPHB.js"></script>
    <script type="text/javascript" src="js/util.js"></script>
</head>

<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ5"/>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">系统管理</a></li>
        <li><a href="#">设计配合比</a></li>
    </ul>
</div>
<div class="rightinfo">
    <form action="designPHBAction!getDesignPHB.action" id="FindDesignPHBForm" method="post">
        <input type="hidden" id="paramsList" value="${fn:length(designPhbMerges)}"/>
        <table class="tablelist">
            <tr>
                <td>所属标段：</td>
                <td>
                    <select name="bdCode" class="scinput" style="height:25px;">
                        <option value="">==请选择标段==</option>
                        <c:forEach var="b" items="${BDs}">
                            <option value="${b.bdCode}" ${b.bdCode==bdCode?'selected':''}>${b.bdName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td class="FL">所属拌合站：</td>
                <td>
                    <select name="bhzCode" class="scinput" style="height:25px;">
                        <option value="">==请选择拌合站==</option>
                        <c:forEach var="b" items="${BHZs}">
                            <option value="${b.bhzCode}" ${b.bhzCode==bhzCode?'selected':''}>${b.bhzName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <input type="button" class="btn1" onclick="designPHB.CheckFind();" value="查询"/>
                <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                    <td>
                        <input type="button" class="btn1" onclick="designPHB.saveParam();" value="批量修改"/>
                        &nbsp;<span id="spanMsg" style="color: red">${msg}</span>
                    </td>
                </c:if>
            </tr>
        </table>
    </form>
    <form id="updateForm" action="designPHBAction!updateDesignPHBMerge.action?bdCode=${bdCode}&bhzCode=${bhzCode}"
          method="post">
        <input type="hidden" name="ids" value="${ids}"/>
        <table class="tablelist">
            <tr>
                <td rowspan="2" style="width: 60px">所属标段</td>
                <td rowspan="2" style="width: 80px">所属拌合站</td>
                <td rowspan="2" style="width: 60px">强度等级</td>
                <td colspan="${fn:length(mergeColsList)}" style="text-align: center">各参数的设计配合比</td>
            </tr>
            <tr>
                <c:choose>
                    <c:when test="${fn:length(mergeColsList)>0}">
                        <c:forEach var="m" items="${mergeColsList}">
                            <td>${m.mergeColsName}</td>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <td>&nbsp;</td>
                    </c:otherwise>
                </c:choose>
            </tr>
            <c:choose>
                <c:when test="${fn:length(mergeColsList)>0}">
                    <c:choose>
                        <c:when test="${fn:length(designPhbMerges)>0}">
                            <c:forEach var="d" items="${designPhbMerges}" varStatus="i">
                                <c:set var="s" value="${designPhbMerges[d.key]}"/>
                                <tr>
                                    <td >${map1[bdCode]}</td>
                                    <td>${map2[bhzCode]}</td>
                                    <td>${d.key}</td>
                                    <c:forEach var="m" items="${mergeColsList}">
                                        <td>
                                            <input class="scinput" style="height: 25px;width: 130px" type="text" size="3" name="ext_${s[m.mergeCols].id}"
                                                   value="${s[m.mergeCols].ext1}"/>
                                        </td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td height="25" colspan="${fn:length(mergeColsList) + 4}"
                                    style="text-align: center;color: red;">“${map1[bdCode]}”的“${map2[bhzCode]}”下还没有设置强度等级，请先去强度等级模块设置!
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td height="25" colspan="${fn:length(mergeColsList) + 4}"
                            style="text-align: center;color: red;">“${map1[bdCode]}”的“${map2[bhzCode]}”下还没有设置参数，请先去合并列设置!
                        </td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </table>
    </form>
</div>
</body>
</html>