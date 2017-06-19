<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/5
  Time: 13:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="/page-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="css/LAYER/ymPrompt.css"/>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/count.js"></script>
    <script type="text/javascript" src="js/util.js"></script>
    <script type="text/javascript" src="js/other/sqmqcl.js"></script>
    <script type="text/javascript" src="js/other/ymPrompt.js"></script>
    <title></title>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">统计分析</a></li>
        <li><a href="#">混凝土产量统计</a></li>
    </ul>
</div>
<div class="rightinfo">
    <input type="hidden" id="bdCode" name="bdCode" value="${bdCode}"/>
    <input type="hidden" id="bhzCode" name="bhzCode" value="${bhzCode}"/>
    <input type="hidden" id="intensityGrade" name="intensityGrade" value="${m8}"/>

    <form id="form1" action="countAction!countGzbwSn.action" method="post">
        <table class="tablelist">
            <tr>
                <td>标段名称：</td>
                <td>
                    <div class="oneBdDiv">
                        <select class="scinput" style="height: 25px" id="bd" name="bd" onchange="showBhzTo();">
                            <option value="">==请选择标段==</option>
                            <c:forEach var="b" items="${bdList}">
                                <option value="${b.bdCode}" ${bdCode eq b.bdCode?'selected':''}>${b.bdName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="moreBdDiv" style="display:none;">
                        <input type="hidden" id="morebdCode" name="morebdCode" value="${morebdCode}"/>
                        <input type="text" class="scinput" style="height: 25px" id="morebd" name="morebd"
                               value="${morebd}" readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <div class="oneBdDiv">拌合站名称：</div>
                    <div class="moreBdDiv" style="display:none;">
                        <input type="button" class="btn1"  onclick="showPage('countAction!getAllBdInfo.action',600,400,'多标段选择');" value="选择标段"/>
                    </div>
                </td>
                <td>
                    <div class="oneBdDiv">
                        <select class="scinput" style="height: 25px" name="bhz" id="bhz">
                            <option value="">==请选择拌合站==</option>
                        </select>
                    </div>
                </td>
                <td colspan="2">
                    <input type="checkbox" id="isMoreBdBox" name="isMoreBdBox" value="1" onclick="isMoreBd();" ${isMoreBdBox eq '1'?'checked':''}/>&nbsp;是否选择多标段查询
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>强度等级：</td>
                <td>
                    <select class="scinput" style="height: 25px" id="m8" name="m8">
                        <option value="">==请选择强度等级==</option>
                        <c:forEach var="q" items="${qdList}">
                            <option value="${q}" ${q eq m8?'selected':''}>${q}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>使用部位：</td>
                <td>
                    <input type="text" class="scinput" style="height: 25px" id="m5" name="m5" value="${m5}"/>
                </td>
                <td>出料时间：</td>
                <td>
                    <input type="text" class="scinput" style="height: 25px;margin-top: 6px" name="sdate" value="${sdate}" id="sdate"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
                    至
                    <input type="text" class="scinput" style="height: 25px" name="edate" value="${edate}" id="edate"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
                </td>
                <td>
                    <input type="button" class="btn1" onclick="forwordURL();" value="查询"/>
                </td>
            </tr>
        </table>
    </form>
    <table class="tablelist">
        <tr>
            <c:forEach var="c" items="${columnMap}">
                <th>${c.value}</th>
            </c:forEach>
            <th>方量(m<sup>3</sup>)</th>
            <th>混凝土产量(kg)</th>
        </tr>
        <c:set var="flxj" value="0"/>
        <c:set var="zlxj" value="0"/>
        <c:forEach var="h" items="${objPage.objList}" varStatus="j">
            <c:set var="mo" value="${j.index%2}"/>
            <tr ${mo eq 0?'bgcolor="#F0F0F0"':''}>
                <c:forEach var="c" items="${columnMap}" varStatus="i">
                    <td>
                        <c:set var="y" value="${h[i.index]}"/>
                        <c:choose>
                            <c:when test="${c.key eq 'm2'}">
                                ${bdMap[fn:toUpperCase(y)]}
                            </c:when>
                            <c:when test="${c.key eq 'm6'}">
                                ${bhzMap[y].bhzName}
                            </c:when>
                            <c:otherwise>
                                ${y}
                            </c:otherwise>
                        </c:choose>
                    </td>
                </c:forEach>
                <td>
                    <c:set var="x" value="${fn:length(columnMap)}"/>
                    <c:set var="flxj" value="${flxj+h[x]}"/>
                    <fmt:formatNumber value="${h[x]}" pattern="0.00"/>
                </td>
                <td>
                    <c:set var="x" value="${fn:length(columnMap)+1}"/>
                    <c:set var="zlxj" value="${zlxj+h[x]}"/>
                    <fmt:formatNumber value="${h[x]}" pattern="0.00"/>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="${fn:length(columnMap)}">当前页小计：</td>
            <td><fmt:formatNumber value="${flxj}" pattern="0.00"/></td>
            <td><fmt:formatNumber value="${zlxj}" pattern="0.00"/></td>
        </tr>
        <tr>
            <td colspan="${fn:length(columnMap)}">
                <input type="button" class="btn1" value="点击查询总合计" style="width: 100px" onclick="queryTotalNum('${morebdCode}','${bdCode}','${bhzCode}','${m8}','${m5}','${sdate}','${edate}');"/>
            </td>
            <td><font id="fltd" color="red"></font></td>
            <td><font id="zltd" color="red"></font></td>
        </tr>
    </table>
    <p:PagingTag url="countAction!countGzbwSn.action?morebdCode=${morebdCode}&bd=${bdCode}&bhz=${bhzCode}&m8=${m8}&m5=${m5}&sdate=${sdate}&edate=${edate}&isGet=1"
            page="${objPage}" forPage="true" showCurAndSum="true"/>
</div>
</body>
</html>
