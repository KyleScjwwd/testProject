<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>各拌和站产能统计</title>
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
    <form id="form1" action="countAction!countBhzCn.action" method="post"> 
    <table class="Tab">
        <tr>
            <td class="FL">标段名称：</td>
            <td>
                <select class="Fwidth" id="bd" name="bd">
                  <option value="">==请选择标段==</option>
				  <c:forEach var="b" items="${bdList}">
				  	<option <c:if test="${bdCode eq b.bdCode}">selected="selected"</c:if> value="${b.bdCode}">${b.bdName}</option>
				  </c:forEach>
              	</select>
            </td>  
            <td class="FL">出料时间：</td>
            <td>
                <input type="text" class="Fwidth" style="width:100px;" name="sdate" value="${sdate}" id="sdate" onfocus="WdatePicker();"/>至
                <input type="text" class="Fwidth" style="width:100px;" name="edate" value="${edate}" id="edate" onfocus="WdatePicker();"/>
            </td>   
            <td><a href="#" class="search" onclick="seleParam();">查询</a></td>
        </tr>
    </table>
    </form>
        <div class="list">
            <a class="onmouse" href="countAction!countClyx.action?bd=${bdCode}&bhz=${bhzCode}&sdate=${sdate}&edate=${edate}">材料用量</a>
            <a class="onmouse" href="countAction!countCnfx.action?bd=${bdCode}&bhz=${bhzCode}&sdate=${sdate}&edate=${edate}">产能分析</a>
            <a class="onmouse" href="countAction!countGzbwSn.action">构造部位水泥统计</a>
            <a class="onmouse" href="countAction!countSnCn.action">水泥产能统计</a>
            <a class="mouse" href="javascript:;">各拌合站产能统计</a>
			<a class="onmouse" href="countAction!countjlyl.action">各集料用量统计</a>
        </div>
        <c:choose>
			<c:when test="${fn:length(objPage.objList) eq 0}">
				<table class="list_tab">
					<tr class="onbg">
						<td height="25" style="text-align:center;"><font color="red">暂无数据!</font></td>
					</tr>
				</table>
			</c:when>
			<c:otherwise>
				<table class="message" >
					<tr class="onbg">
						<td>标段名称</td>
						<td>拌和站名称</td>
						<td>产能(m<sup>3</sup>)</td>
					</tr>
					<c:forEach var="c" items="${objPage.objList}">
						<tr>
							<td>${map[c[0]]}</td>
							<td>${map2[c[1]]}</td>
							<td>${c[2]}</td>
						</tr>
					</c:forEach>
				</table>	
			</c:otherwise>
		</c:choose>
        <div class="clear"></div>
        <c:if test="${fn:length(objPage.objList)>0}">
        <p:PagingTag url="countAction!countBhzCn.action?bd=${bdCode}&sdate=${sdate}&edate=${edate}" page="${objPage}" forPage="true" showCurAndSum="true"/>
        </c:if>
</div>
</body>
</html>