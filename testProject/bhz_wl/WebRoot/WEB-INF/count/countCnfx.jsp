<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产能分析</title>
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
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">统计分析</a></p>
    </div>
    <input type="hidden" id="bdCode" name="bdCode" value="${bdCode}"/>
    <input type="hidden" id="bhzCode" name="bhzCode" value="${bhzCode}"/>
    <form id="form1" action="countAction!countCnfx.action" method="post"> 
    <table class="Tab">
        <tr>
            <td class="FL">标段名称：</td>
            <td>
                <select class="Fwidth" id="bd" name="bd" onchange="showBhz();">
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
    </form>
        <div class="list">
            <a class="onmouse" onclick="openLoad();" href="countAction!countClyx.action?bd=${bdCode}&bhz=${bhzCode}&sdate=${sdate}&edate=${edate}">材料用量</a>
            <a class="mouse" href="javascript:;">产能分析</a>
            <a class="onmouse" onclick="openLoad();" href="countAction!countGzbwSn.action">混凝土产量统计</a>
            <a class="onmouse" onclick="openLoad();" href="countAction!countSnCn.action">材料用量统计</a>
        </div>
        <div class="sj_list">
            <table class="time" >
                <tr>
                    <td class="timebg" colspan="2"><img src="images/bhz/i2icon22.png" width="31" height="30" style="float:left;" />拌合站产能分析</td>
                </tr>
                <tr>
                    <td colspan="2" align="center" valign="top"><img src="${pageContext.request.contextPath}/jfreeChartFile/cnfx_bar.jpg?randomNum=${randomNum}" border="0" /></td>
                </tr>
                <tr>
                    <td align="center" valign="top"><img src="${pageContext.request.contextPath}/jfreeChartFile/cnfx_sj_pie.jpg?randomNum=${randomNum}" border="0" /></td>
                    <td align="center" valign="top"><img src="${pageContext.request.contextPath}/jfreeChartFile/cnfx_sg_pie.jpg?randomNum=${randomNum}" border="0" /></td>
                </tr>
            </table>
            <div class="clear"></div>
         </div>
</div>
</body>
</html>