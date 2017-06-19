<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>混凝土产量统计</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<link rel="stylesheet" type="text/css" href="css/LAYER/ymPrompt.css"/>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/count.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/other/sqmqcl.js"></script>
<script type="text/javascript" src="js/other/ymPrompt.js"></script>
</head>

<body>
<div class="all">
    <div class="scsj">
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">统计分析</a></p>
    </div>
    <input type="hidden" id="bdCode" name="bdCode" value="${bdCode}"/>
    <input type="hidden" id="bhzCode" name="bhzCode" value="${bhzCode}"/>
    <input type="hidden" id="intensityGrade" name="intensityGrade" value="${m8}"/>
    <form id="form1" action="countAction!countGzbwSn.action" method="post"> 
    <table class="Tab" style="width:1080px;">
        <tr>
            <td class="FL" style="width:150px;">标段名称：</td>
            <td>
            	<div class="oneBdDiv">
                <select class="Fwidth" style="width:150px;" id="bd" name="bd" onchange="showBhzTo();">
                  <option value="">==请选择标段==</option>
				  <c:forEach var="b" items="${bdList}">
				  <option value="${b.bdCode}" ${bdCode eq b.bdCode?'selected':''}>${b.bdName}</option>
				  </c:forEach>
              	</select>
              	</div>
              	<div class="moreBdDiv" style="display:none;">
              	<input type="hidden" id="morebdCode" name="morebdCode" value="${morebdCode}"/>
              	<input type="text" class="Fwidth" style="width:150px;" id="morebd" name="morebd" value="${morebd}" readonly="readonly"/>
              	</div>
            </td>
            <td class="FL">
            <div class="oneBdDiv">拌合站名称：</div>
            <div class="moreBdDiv" style="display:none;"><a href="javascript:;" class="search" onclick="showPage('countAction!getAllBdInfo.action',600,400,'多标段选择');">选择标段</a></div>
            </td>
            <td>
            	<div class="oneBdDiv">
                <select class="Fwidth" style="width:150px;" name="bhz" id="bhz">
               	<option value="">==请选择拌合站==</option>
                </select>
                </div>
            </td>
            <td colspan="2"><input type="checkbox" id="isMoreBdBox" name="isMoreBdBox" value="1" onclick="isMoreBd();" ${isMoreBdBox eq '1'?'checked':''}/>&nbsp;是否选择多标段查询</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
        	<td class="FL">强度等级：</td>
            <td>
            	<select class="Fwidth" style="width:150px;" id="m8" name="m8">
               	<option value="">==请选择强度等级==</option>
               	<c:forEach var="q" items="${qdList}">
               	<option value="${q}" ${q eq m8?'selected':''}>${q}</option>
               	</c:forEach>
                </select>
            </td>
            <td class="FL">使用部位：</td>
            <td><input type="text" class="Fwidth" style="width:150px;" id="m5" name="m5" value="${m5}"/></td>
            <td class="FL">出料时间：</td>
            <td>
                <input type="text" class="Fwidth" style="width:150px;" name="sdate" value="${sdate}" id="sdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
                   至
                <input type="text" class="Fwidth" style="width:150px;" name="edate" value="${edate}" id="edate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
            </td>
            <td><a href="javascript:;" class="search" onclick="forwordURL();">查询</a></td>
        </tr>
    </table>
    </form>
        <div class="list">
        	<!-- 
            <a class="onmouse" onclick="openLoad();" href="countAction!countClyx.action?bd=${bdCode}&bhz=${bhzCode}&sdate=${sdate}&edate=${edate}">材料用量</a>
            <a class="onmouse" onclick="openLoad();" href="countAction!countCnfx.action?bd=${bdCode}&bhz=${bhzCode}&sdate=${sdate}&edate=${edate}">产能分析</a>
             -->
            <a class="mouse" href="javascript:;">混凝土产量统计</a>
            <a class="onmouse" onclick="openLoad();" href="countAction!countSnCn.action">材料用量统计</a>
        </div>
        <table class="message">
            <tr class="onbg">
            	<c:forEach var="c" items="${columnMap}">
            	<td>${c.value}</td>
            	</c:forEach>
		        <td>方量(m<sup>3</sup>)</td>
		        <td>混凝土产量(kg)</td>
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
            <td colspan="${fn:length(columnMap)}"><input type="button" value="点击查询总合计" onclick="queryTotalNum('${morebdCode}','${bdCode}','${bhzCode}','${m8}','${m5}','${sdate}','${edate}');"/></td>
            <td><font id="fltd" color="red"></font></td>
            <td><font id="zltd" color="red"></font></td>
            </tr>
        </table>
        <div class="clear"></div>
        <c:if test="${fn:length(objPage.objList)>0}">
        <p:PagingTag url="countAction!countGzbwSn.action?morebdCode=${morebdCode}&bd=${bdCode}&bhz=${bhzCode}&m8=${m8}&m5=${m5}&sdate=${sdate}&edate=${edate}&isGet=1" page="${objPage}" forPage="true" showCurAndSum="true"/>
        </c:if>
</div>
</body>
</html>