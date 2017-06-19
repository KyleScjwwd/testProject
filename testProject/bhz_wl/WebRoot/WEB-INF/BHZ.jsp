<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/5
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="/page-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="css/LAYER/ymPrompt.css"/>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/BHZ.js?x"></script>
    <script type="text/javascript" src="js/util.js"></script>
    <script type="text/javascript" src="js/other/sqmqcl.js"></script>
    <script type="text/javascript" src="js/other/ymPrompt.js"></script>
</head>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ5"/>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">系统管理</a></li>
        <li><a href="#">拌合站管理</a></li>
    </ul>
</div>
<div class="rightinfo">
    <form id="FindBHZForm" action="BHZAction!findBHZ.action" method="post">
        <table class="tablelist">
            <tr>
                <td>标段名称：</td>
                <td>
                    <select name="bdCode" class="scinput" style="height: 25px">
                        <option value="">==请选择标段==</option>
                        <c:forEach var="b" items="${BDs}">
                            <option value="${b.bdCode}" ${b.bdCode==bhz.bdCode?'selected':''}>${b.bdName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>拌合站名称：</td>
                <td><input type="text" name="bhzName" value="${bhz.bhzName}" class="scinput" style="height: 25px"/></td>
                <td>上限：</td>
                <td><input type="text" name="upNum" value="${bhz.upNum}" class="scinput" style="height: 25px"/></td>
                <td>下限：</td>
                <td><input type="text" name="downNum" value="${bhz.downNum}" class="scinput" style="height: 25px"/></td>
                <td>
                    <input type="button" class="btn1" onclick="bdz.CheckFind();" value="查询"/>
                    <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                        <input type="button" class="btn1" onclick="javascript:location.href='BHZAction!toAddBHZPage.action'"
                               value="添加"/>
                    </c:if>
                </td>

            </tr>
        </table>
    </form>

    <table class="tablelist">
        <tr>
            <th>所属标段</th>
            <th>拌合站编号</th>
            <th>拌合站名称</th>
            <th>上限</th>
            <th>上限</th>
            <th>拌合站密码</th>
            <th>客户端版本</th>
            <th>最后在线时间</th>
            <th>采集参数</th>
            <th>最后采集时间</th>
            <th>合并</th>
            <th>施工配比校正</th>
            <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                <th>操作</th>
            </c:if>
        </tr>
        <c:choose>
            <c:when test="${fn:length(objPage.objList)>0}">
                <c:forEach var="b" items="${objPage.objList}">
                    <tr>
                        <td style="text-align: center">${map[b.bdCode]}</td>
                        <td style="text-align: center">${b.bhzCode}</td>
                        <td style="text-align: center">${b.bhzName}</td>
                        <td style="text-align: center">${b.upNum}</td>
                        <td style="text-align: center">${b.downNum}</td>
                        <td style="text-align: center">${b.ext1}</td>
                        <td style="text-align: center">${b.clientVersion}</td>
                        <td style="text-align: center">${b.lastOnlineTime}</td>
                        <td style="text-align: center">${b.cjParam}</td>
                        <td style="text-align: center">${b.lastCollTime}</td>
                        <c:choose>
                            <c:when test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                                <td style="text-align: center">
                                    <input type="checkbox" id="mergebox${b.id}" onclick="bdz.updateMerge('${b.id}');" ${b.mergeClos eq '1'?'checked':''}/>
                                </td>
                                <td style="text-align: center">
                                    <input type="checkbox" id="testbox${b.id}" onclick="bdz.updateTest('${b.id}');" ${b.sgpbTest eq '1'?'checked':''}/></td>
                            </c:when>
                            <c:otherwise>
                                <td style="text-align: center">
                                    <input type="checkbox" id="mergebox${b.id}" ${b.mergeClos eq '1'?'checked':''} disabled="disabled"/>
                                </td>
                                <td style="text-align: center">
                                    <input type="checkbox" id="testbox${b.id}" ${b.sgpbTest eq '1'?'checked':''} disabled="disabled"/>
                                </td>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                            <td style="width:300px;">
                                <input type="button" class="btn1" onclick="javascript:location.href='BHZAction!toUpdatePage.action?id=${b.id}'" value="编辑"/>
                                <input type="button" class="btn1" onclick="javascript:if(confirm('确定要删除该拌合站吗?')){location.href='BHZAction!deleteBHZ.action?id=${b.id}&bhzCode=${b.bhzCode}'}" value="删除"/>
                                <c:if test="${sessionScope.username eq 'admin'}">
                                    <input type="button" class="btn1" onclick="showPage('BHZAction!setOrDelDataInfo.action?bdCode=${b.bdCode}&bhzCode=${b.bhzCode}',500,300,'设置上传数据信息');" value="设置"/>
                                    <input type="button" class="btn1" onclick="javascript:location.href='BHZAction!lookLogFile.action?bdCode=${b.bdCode}&bhzCode=${b.bhzCode}'" value="log查看"/>
                                </c:if>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td height="25" colspan="10" style="text-align: center;color: red;">暂无数据记录!</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>
    <p:PagingTag url="BHZAction!toBHZPage.action" page="${objPage}" forPage="true" showCurAndSum="true"/>
</div>
</body>
</html>
