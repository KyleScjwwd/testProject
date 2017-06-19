<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>合并列设置</title>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="css/LAYER/ymPrompt.css"/>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/givedata.js"></script>
    <script type="text/javascript" src="js/util.js"></script>
    <script type="text/javascript" src="js/other/sqmqcl.js"></script>
    <script type="text/javascript" src="js/other/ymPrompt.js"></script>
    <script type="text/javascript" src="js/merge.js"></script>
</head>

<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ5"/>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">系统管理</a></li>
        <li><a href="#">合并列设置</a></li>
    </ul>
</div>
<div class="rightinfo">
    <input type="hidden" id="bdCode" value="${bdCode}"/>
    <input type="hidden" id="bhzCode" value="${bhzCode}"/>
    <table class="tablelist">
        <tr>
            <td>
                标段名称：
            </td>
            <td>
                <select id="bd" name="bd" class="scinput" style="height: 25px" onchange="showBhz();">
                    <option value="">==请选择标段==</option>
                    <c:forEach var="b" items="${tbBds}">
                        <option value="${b.bdCode}" ${b.bdCode==bdCode?'selected':''}>${b.bdName}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                拌和站名称：
            </td>
            <td>
                <select id="bhz" name="bhz" class="scinput" style="height: 25px" onchange="showMergeData();">
                    <option value="">==请选择拌合站==</option>
                </select>
            </td>
            <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                <td>
                    <input type="button" class="btn1" onclick="addMerge();" value="新增"/>
                </td>
            </c:if>
        </tr>
    </table>

    <table class="tablelist">
        <tr>
            <th>所属标段</th>
            <th>所属拌合站</th>
            <th>显示顺序</th>
            <th>合并列参数</th>
            <th>合并列名</th>
            <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                <th>操作</th>
            </c:if>
        </tr>
        <c:choose>
            <c:when test="${fn:length(mcoList)>0}">
                <c:forEach var="m" items="${mcoList}">
                    <tr>
                        <td>${bdMap[m.bdCode].bdName}</td>
                        <td>${bhzMap[m.bhzCode].bhzName}</td>
                        <td>${m.orderNum}</td>
                        <td>${m.mergeCols}</td>
                        <td>${m.mergeColsName}</td>
                        <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                            <td style="text-align: center">
                                <input type="button" class="btn1" onclick="delMerge('${m.id}','${m.bdCode}','${m.bhzCode}')" value="删除"/>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="6"><font color="red">暂无数据,请选择标段及拌合站查询!</font></td>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>
</div>
</body>
</html>