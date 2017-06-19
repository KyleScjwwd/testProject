<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/5
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="/page-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/givedata.js"></script>
    <script type="text/javascript" src="js/common.js"></script>
    <script type="text/javascript" src="js/selMessageUser.js"></script>
    <title></title>
</head>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ3"/>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">短信报警</a></li>
        <li><a href="#">报警群组</a></li>
    </ul>
</div>
<input type="hidden" id="bdCode" name="bdCode" value="${bdCode}"/>
<input type="hidden" id="bhzCode" name="bhzCode" value="${bhzCode}"/>

<div class="rightinfo">
    <form id="FindBaoJingPersonBaseForm" action="messageAction!setBaoJingPersonBase.action" method="post">
        <table class="tablelist">
            <tr>
                <td>标段：</td>
                <td>
                    <select class="scinput" id="bd" name="bd" onchange="showBhz();" style="height: 25px">
                        <option value="">==请选择标段==</option>
                        <c:forEach var="b" items="${BDs}">
                            <option value="${b.bdCode}">${b.bdName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>拌合站：</td>
                <td>
                    <select class="scinput" name="bhz" id="bhz" style="height: 25px">
                        <option value="">==请选择拌合站==</option>
                    </select>
                </td>
                <td>
                    <input type="button" class="btn1" onclick="javascript:$(FindBaoJingPersonBaseForm).submit()" value="查询"/>
                    <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                        <input type="button" class="btn1" onclick="javascript:location.href='messageAction!toAddBaoJingPersonBasePage.action'" value="添加"/>
                    </c:if>

                </td>

            </tr>
        </table>
    </form>
    <table class="tablelist">
        <tr>
            <th>标段</th>
            <th>拌合站</th>
            <th width="20%">发送级别（初/中/高）级</th>
            <th width="50%">报警发送人员</th>
            <th>操作</th>
        </tr>
        <c:choose>
            <c:when test="${fn:length(objPage.objList)==0}">
                <td colspan="5" align="center" height="50" style="color:red">您还没有设置报警基本人员信息!</td>
            </c:when>
            <c:otherwise>
                <c:forEach var="b" items="${objPage.objList}" varStatus="i">
                    <tr <c:if test="${i.index%2==0}">bgcolor="#F0F0F0"</c:if>>
                        <td rowspan="3">${bdMap[b.bdCode]}</td>
                        <td rowspan="3">${bhzMap[b.bhzCode]}</td>
                        <td>初级(项目部)</td>
                        <td>
                            <c:set var="msgUsers" value=""/>
                            <c:forEach var="u" items="${messageUserList}">
                                <c:if test="${fn:contains(b.userMobileCj, u.telphone)==true}">
                                    <c:set var="msgUsers" value="${msgUsers} ${u.realName}(${u.telphone})"/>
                                </c:if>
                            </c:forEach>
                                ${msgUsers}
                        </td>
                        <td rowspan="3">
                            <input type="button" class="btn1" onclick="javascript:location.href='messageAction!toUpdateBaoJingPersonPage.action?id=${b.id}'" value="编缉"/>
                            <input type="button" class="btn1" onclick="javascript:location.href='messageAction!deleteBaoJingPersonBase.action?id=${b.id}'" del="del" value="删除"/>
                        </td>
                    </tr>
                    <tr <c:if test="${i.index%2==0}">bgcolor="#F0F0F0"</c:if>>
                        <td>中级(监理)</td>
                        <td>
                            <c:set var="msgUsers" value=""/>
                            <c:forEach var="u" items="${messageUserList}">
                                <c:if test="${fn:contains(b.userMobileZj, u.telphone)==true}">
                                    <c:set var="msgUsers" value="${msgUsers} ${u.realName}(${u.telphone})"/>
                                </c:if>
                            </c:forEach>
                                ${msgUsers}
                        </td>
                    </tr>
                    <tr <c:if test="${i.index%2==0}">bgcolor="#F0F0F0"</c:if>>
                        <td>高级(指挥部)</td>
                        <td>
                            <c:set var="msgUsers" value=""/>
                            <c:forEach var="u" items="${messageUserList}">
                                <c:if test="${fn:contains(b.userMobileGj, u.telphone)==true}">
                                    <c:set var="msgUsers" value="${msgUsers} ${u.realName}(${u.telphone})"/>
                                </c:if>
                            </c:forEach>
                                ${msgUsers}
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </table>
    <p:PagingTag url="messageAction!setBaoJingPersonBase.action" page="${objPage}" forPage="true" showCurAndSum="true"/>
</div>
</body>
</html>
