<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="/page-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>拌合签到</title>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="css/LAYER/ymPrompt.css"/>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="js/User.js"></script>
    <script type="text/javascript" src="js/SignIn.js"></script>
    <script type="text/javascript" src="js/util.js"></script>
</head>

<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ5"/>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">系统管理</a></li>
        <li><a href="#">拌合签到</a></li>
    </ul>
</div>
<div class="rightinfo">
    <input type="hidden" id="hidBd" value="${si.bdCode}"/>
    <input type="hidden" id="hidBhz" value="${si.bhzCode}"/>
    <form id="FindSignInForm" action="signInAction!FindSignIn.action" method="post">
        <table class="tablelist">
            <tr>
                <td>标段：</td>
                <td>
                    <select class="scinput" style="height: 25px" name="bdCode" >
                        <option value="">==请选择标段==</option>
                        <c:forEach var="b" items="${BDs}">
                            <option value="${b.bdCode}" ${b.bdCode==si.bdCode?'selected':''}>${b.bdName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>拌合站：</td>
                <td>
                    <select class="scinput" style="height: 25px" id="bhz" name="bhzCode">
                        <option value="">==请选择拌合站==</option>
                    </select>
                </td>
                <td>生产日期：</td>
                <td>
                    <input type="text" name="sdate" class="scinput" style="height: 25px;margin-top: 6px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});" value="${sdate}"/> -
                    <input type="text" name="edate" class="scinput" style="height: 25px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});" value="${edate}"/>
                </td>
                <td>
                    <input type="button" class="btn1" onclick="si.CheckFind()" value="查询"/>
                </td>
                <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                    <td>
                        <input type="button" class="btn1" onclick="javascript:location.href='signInAction!toAddSignInPage.action'" value="添加"/>
                    </td>
                </c:if>
            </tr>
        </table>
    </form>

    <table class="tablelist">
        <tr>
            <th>标段名称</th>
            <th>拌合站名称</th>
            <th>生产日期</th>
            <th>签到人</th>
            <c:set var="authPage" value="${sessionScope.username}-fun01010"/>
            <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                <th>操作</th>
            </c:if>
        </tr>
        <c:choose>
            <c:when test="${fn:length(objPage.objList)>0}">
                <c:forEach var="s" items="${objPage.objList}">
                    <tr>
                        <td style="text-align: center">${bdMap[s.bdCode]}</td>
                        <td style="text-align: center">${bhzMap[s.bhzCode]}</td>
                        <td style="text-align: center"><fmt:formatDate value="${s.signInDate}" pattern="yyyy-MM-dd"/></td>
                        <td style="text-align: center">${s.signInUser}</td>
                        <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                            <td align="center" style="width:300px;">
                                <input type="button" class="btn1" onclick="javascript:location.href='signInAction!toUpdatePage.action?id=${s.id}'" value="编缉"/>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td height="25" colspan="5" style="text-align: center;color: red;">暂无签到记录!</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>
    <div class="clear"></div>
    <p:PagingTag url="signInAction!FindSignIn.action?bdCode=${si.bdCode}&bhzCode=${si.bhzCode}&sdate=${sdate}&edate=${edate}" page="${objPage}" forPage="true" showCurAndSum="true"/>
</div>
</body>
</html>