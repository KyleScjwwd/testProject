<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>拌合时间</title>
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
    <form id="form1" action="countAction!getBhTime.action" method="post"> 
    <table class="Tab">
        <tr>
            <td class="FL">标段名称：</td>
            <td>
                <select class="Fwidth" name="bd" id="bd" onchange="showBhz();">
                    <option value="">==请选择标段==</option>
                    <c:forEach var="b" items="${bdList}">
					<option <c:if test="${bdCode eq b.bdCode}">selected="selected"</c:if> value="${b.bdCode}">${b.bdName}</option>
					</c:forEach>
                </select>
            </td>
            <td class="FL">拌合站名称：</td>
            <td>
                <select class="Fwidth" name="bhz" id="bhz">
                    <option value="">==请选择拌合站==</option>
                </select>
            </td>   
            <td class="FL">出料时间：</td>
            <td>
                <input type="text" class="Fwidth" style="width:100px;" name="sdate" value="${sdate}" id="sdate" onfocus="WdatePicker();"/>至
                <input type="text" class="Fwidth" style="width:100px;" name="edate" value="${edate}" id="edate" onfocus="WdatePicker();"/>
            </td>   
            <td><a href="javascript:;" class="search" onclick="seleParam();">查询</a></td>
        </tr>
    </table>
        <div class="list">
            <a class="onmouse" href="mainAction!getGiveDate.action?bd=${bdCode}&bhz=${bhzCode}">每盘生产数据</a>
            <a class="onmouse" href="mainAction!countTotalWc.action?bd=${bdCode}&bhz=${bhzCode}">累积误差</a>
            <a class="mouse">拌合时间</a>
        </div>
        <div class="sj_list">
            <table class="time" >
                <tr>
                    <td class="timebg">
                    <img src="images/bhz/i2icon22.png" width="31" height="30" style="float:left;" />拌合时间实时监控
                    </td>
                </tr>
                <tr>
                    <td>
                    <img src="${pageContext.request.contextPath}/jfreeChartFile/bhTime.jpg?randomNum=${randomNum}" />
                    </td>
                </tr>
            </table>
            <div class="clear"></div>
            <p:PagingTag url="countAction!getBhTime.action?bd=${bdCode}&bhz=${bhzCode}&sdate=${sdate}&edate=${edate}" page="${objPage}" forPage="true" showCurAndSum="true"/>
        </div>
</div>
</body>
</html>