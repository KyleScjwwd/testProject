<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="/page-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>合并列数据监控</title>
    <link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
    <link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/givedata.js"></script>
    <script type="text/javascript" src="js/util.js"></script>
</head>

<body>
<div class="all">
    <div class="scsj">
        <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">生产数据监控 </a></p>
    </div>
    <input type="hidden" id="bdCode" name="bdCode" value="${bdCode}"/>
    <input type="hidden" id="bhzCode" name="bhzCode" value="${bhzCode}"/>

    <form id="form1" action="mainAction!countTotalWc.action" method="post">
        <table class="Tab" border="0">
            <tr>
                <td class="FL" width="20%">标段名称：</td>
                <td>
                    <select class="Fwidth" id="bd" name="bd" onchange="showBhz();">
                        <option value="">==请选择标段==</option>
                        <c:forEach var="b" items="${bdList}">
                            <option value="${b.bdCode}" ${bdCode eq b.bdCode?'selected':''}>${b.bdName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td class="FL">拌合站名称：</td>
                <td colspan="2">
                    <select class="Fwidth" id="bhz" name="bhz">
                        <option value="">==请选择拌合站==</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="FL">浇筑部位：</td>
                <td><input type="text" name="m5" value="${m5}" class="Fwidth"/></td>
                <td class="FL">强度等级：</td>
                <td colspan="2">
                    <select class="Fwidth" id="m8" name="m8">
                        <option value="">==请选择强度等级==</option>
                        <c:forEach var="q" items="${qdList}">
                            <option value="${q}" ${q eq m8?'selected':''}>${q}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="FL">出料时间：</td>
                <td colspan="4">
                    <input type="text" class="Fwidth" style="width:100px;" name="sdate" value="${sdate}" id="sdate"
                           onfocus="WdatePicker();"/>至
                    <input type="text" class="Fwidth" style="width:100px;" name="edate" value="${edate}" id="edate"
                           onfocus="WdatePicker();"/>
                    <font color="red">注：标段和拌合站必选</font>
                </td>
                <td><a href="javascript:;" class="search" onclick="forwordURL();">查询</a></td>
            </tr>
        </table>
    </form>
    <div class="list">
        <a class="onmouse" href="mainAction!getGiveDate.action?bd=${bdCode}&bhz=${bhzCode}">每盘生产数据</a>
        <a class="mouse">累积误差</a>
        <a class="onmouse"
           href="countAction!getBhTime.action?bd=${bdCode}&bhz=${bhzCode}&isCurrent=${isCurrent}">拌合时间</a>
    </div>
    <c:choose>
        <c:when test="${fn:length(mergeList) eq 0}">
            <table class="list_tab">
                <tr class="onbg">
                    <td height="30" style="text-align: center;color: red;">
                        材料数据未合并，无法准确统计，请先校正合并!
                    </td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
            <table class="list_tab">
                <tr class="onbg">
                    <td>标段</td>
                    <td>拌合站名称</td>
                    <td style="width:90px;">浇筑部位</td>
                    <td>强度等级</td>
                    <td colspan="2" style="width:90px;">起止时间</td>
                    <td>累计方量</td>
                    <td>配合比类型</td>
                    <c:forEach var="m" varStatus="s" items="${mergeList}">
                        <td>${m.mergeColsName}</td>
                    </c:forEach>
                </tr>
                <c:choose>
                    <c:when test="${fn:length(objList.baseObj) eq 0}">
                        <tr>
                            <td colspan="25" style="color: red;text-align: center;">暂无累计误差数据记录!</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="m" items="${objList.baseObj}" varStatus="i">
                            <tr ${i.index%2==0?'bgcolor="#F0F0F0"':''}>
                                <td rowspan="4">${bdMap[m.value[2][2]]}</td>
                                <td rowspan="4">${tbBhz.bhzName}</td>
                                <td rowspan="4">${m.value[2][4]}</td>
                                <td rowspan="4">${m.value[2][5]}</td>
                                <td rowspan="4"><fmt:formatDate value="${m.value[2][6]}"
                                                                pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td rowspan="4"><fmt:formatDate value="${m.value[2][7]}"
                                                                pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td rowspan="4"><fmt:formatNumber value="${m.value[2][8]}" pattern="0.0"/></td>
                                <td>累计施工</td>
                                <c:forEach var="r" items="${mergeList}" varStatus="j">
                                    <td>
                                        <c:set var="n" value="${j.index+9}"/>
                                        <fmt:formatNumber value="${m.value[2][n]}" pattern="0.00"/>
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr ${i.index%2==0?'bgcolor="#F0F0F0"':''}>
                                <td>累计实际</td>
                                <c:forEach var="r" items="${mergeList}" varStatus="j">
                                    <td>
                                        <c:set var="n" value="${j.index+9}"/>
                                        <fmt:formatNumber value="${m.value[1][n]}" pattern="0.00"/>
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr ${i.index%2==0?'bgcolor="#F0F0F0"':''}>
                                <td>累计误差</td>
                                <c:forEach var="r" items="${mergeList}" varStatus="j">
                                    <td>
                                        <c:set var="n" value="${j.index+9}"/>
                                        <c:set var="wc" value="${m.value[2][n]-m.value[1][n]}"/>
                                        <fmt:formatNumber value="${wc}" pattern="0.00"/>
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr ${i.index%2==0?'bgcolor="#F0F0F0"':''}>
                                <td>误差百分率(%)</td>
                                <c:forEach var="r" items="${mergeList}" varStatus="j">
                                    <td>
                                        <c:set var="n" value="${j.index+9}"/>
                                        <c:set var="wc" value="${m.value[2][n]-m.value[1][n]}"/>
                                        <c:choose>
                                            <c:when test="${m.value[2][n] ne null && m.value[2][n]>0}">
                                                <c:set var="wcbfl" value="${wc/m.value[2][n]*100}"/>
                                                <fmt:formatNumber value="${wcbfl}" pattern="0.00"/>
                                            </c:when>
                                            <c:otherwise>
                                                &nbsp;
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </table>
            <div class="clear"></div>
            <p:PagingTag url="mainAction!countTotalWc.action?bd=${bdCode}&bhz=${bhzCode}&m5=${m5}&m8=${m8}&sdate=${sdate}&edate=${edate}"
                    page="${objList}" forPage="true" showCurAndSum="true"/>
        </c:otherwise>
    </c:choose>
</div>
<br/>
</body>
</html>