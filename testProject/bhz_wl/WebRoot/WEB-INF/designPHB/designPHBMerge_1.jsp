<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设计配合比</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/User.js"></script>
<script type="text/javascript" src="js/designPHB.js"></script>
<script type="text/javascript" src="js/util.js"></script>
</head>

<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ5"/>
<div class="all">
    <div class="scsj">
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">系统管理</a></p>
    </div>
    <form action="designPHBAction!getDesignPHB.action" id="FindDesignPHBForm" method="post">
    <input type="hidden" id="paramsList" value="${fn:length(designPhbMerges)}"/>
    <table class="Tab">
        <tr>
        	<td class="FL">所属标段：</td>
            <td>
            	<select name="bdCode" class="Fwidth" style="width:120px;">
                  <option value="">==请选择标段==</option>
                  <c:forEach var="b" items="${BDs}">
				  	<option value="${b.bdCode}" ${b.bdCode==bdCode?'selected':''}>${b.bdName}</option>
			 	  </c:forEach>
                </select>
            </td>
            <td class="FL">所属拌合站：</td>
            <td>
            	<select name="bhzCode" class="Fwidth" style="width:120px;">
                  	<option value="">==请选择拌合站==</option>
					<c:forEach var="b" items="${BHZs}">
						<option value="${b.bhzCode}" ${b.bhzCode==bhzCode?'selected':''}>${b.bhzName}</option>
					</c:forEach>
                </select>
            </td>
            <td><a href="javascript:;" class="search" onclick="designPHB.CheckFind();">查询</a></td>
			<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
            <td><a href="javascript:;" class="tjia" onclick="designPHB.saveParam();">批量修改</a>&nbsp;<span id="spanMsg" style="color: red">${msg}</span></td>
        	</c:if>
        </tr>
    </table>
    </form>
    <%@ include file="../base/sysLinked.jsp" %>
    <div class="sj_list">
    	<form id="updateForm" action="designPHBAction!updateDesignPHBMerge.action?bdCode=${bdCode}&bhzCode=${bhzCode}" method="post">
    		<input type="hidden" name="ids" value="${ids}"/>
	        <table class="message2" >
	            <tr class="onbg">
		            <td rowspan="2">所属标段</td>
		            <td rowspan="2">所属拌合站</td>
		            <td rowspan="2">强度等级</td>
		            <td colspan="${fn:length(mergeColsList)}">各参数的设计配合比</td>
	            </tr>
	            <tr class="onbg">
	            	<c:choose>
					<c:when test="${fn:length(mergeColsList)>0}">
	             	<c:forEach var="m" items="${mergeColsList}">
	 				<td>${m.mergeColsName}</td>
					</c:forEach>
					</c:when>
					<c:otherwise><td>&nbsp;</td></c:otherwise>
					</c:choose>
	            </tr>
	            <c:choose>
		            <c:when test="${fn:length(mergeColsList)>0}">
			            <c:choose>
						<c:when test="${fn:length(designPhbMerges)>0}">
				            <c:forEach var="d" items="${designPhbMerges}" varStatus="i">
				            	<c:set var="s" value="${designPhbMerges[d.key]}"/>
				            	<tr>
				            		<td>${map1[bdCode]}</td>
				            		<td>${map2[bhzCode]}</td>
				            		<td>${d.key}</td>
					             	<c:forEach var="m" items="${mergeColsList}">
					 					<td>
					 						<input type="text" size="3" name="ext_${s[m.mergeCols].id}" value="${s[m.mergeCols].ext1}"/>
					 					</td>
									</c:forEach>	            		
				            	</tr>
							</c:forEach>
			            </c:when>
			            <c:otherwise>
			            	<tr><td height="25" colspan="${fn:length(mergeColsList) + 4}" style="text-align: center;color: red;">“${map1[bdCode]}”的“${map2[bhzCode]}”下还没有设置强度等级，请先去强度等级模块设置!</td></tr>
			            </c:otherwise>
			            </c:choose>
		            </c:when>
		            <c:otherwise>
		            	<tr><td height="25" colspan="${fn:length(mergeColsList) + 4}" style="text-align: center;color: red;">“${map1[bdCode]}”的“${map2[bhzCode]}”下还没有设置参数，请先去合并列设置!</td></tr>
		            </c:otherwise>
	            </c:choose>   
	        </table>
        </form>
	</div>
</div>
</body>
</html>