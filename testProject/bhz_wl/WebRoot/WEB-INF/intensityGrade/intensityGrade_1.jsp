<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>强度等级</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/User.js"></script>
<script type="text/javascript" src="js/intensityGrade.js"></script>
<script type="text/javascript" src="js/util.js"></script>
</head>

<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ5"/>
<div class="all">
    <div class="scsj">
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">系统管理</a></p>
    </div>
    <form action="intensityGradeAction!getIntensityGrade.action" id="FindIntensityGradeForm" method="post">
    <table class="Tab">
        <tr>
       <!-- <td class="FL">所属标段：</td>
            <td>
            	<select name="bdCode" class="Fwidth" style="width:120px;">
                  <option value="">==请选择标段==</option>
                  <c:forEach var="b" items="${BDs}">
				  	<option value="${b.bdCode}" ${b.bdCode==qddj.bdCode?'selected':''}>${b.bdName}</option>
			 	  </c:forEach>
                </select>
            </td>
            <td class="FL">所属拌合站：</td>
            <td>
            	<select name="bhzCode" class="Fwidth" style="width:120px;">
                  	<option value="">==请选择拌合站==</option>
					<c:forEach var="b" items="${BHZs}">
						<option value="${b.bhzCode}" ${b.bhzCode==qddj.bhzCode?'selected':''}>${b.bhzName}</option>
					</c:forEach>
                </select>
            </td> -->
            <td class="FL">强度等级：</td>
            <td width="30%"><input type="text" name="intensityGrade" value="${qddj.intensityGrade}" class="Fwidth" /></td>
            <td width="30%">
            <span><a href="#" class="search" onclick="intensityGrade.CheckFind();">查询</a></span>
			<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
            <span style="margin-left:20px;"><a href="intensityGradeAction!toAddIntensityGradePage.action" class="tjia">添加</a></span>
            </c:if>
            </td>
        </tr>
    </table>
    </form>
    <%@ include file="../base/sysLinked.jsp" %>
    <div class="sj_list">
        <table class="message" >
                <tr class="onbg">
	   	   <!-- <td>所属标段</td>
	            <td>所属拌合站</td> -->
	            <td>强度等级</td>
				<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
	            <td width="150px">操作</td>
	            </c:if>
            </tr>
            <c:choose>
			<c:when test="${fn:length(objPage.objList)>0}">
            <c:forEach var="i" items="${objPage.objList}">
				<tr>
			   <!-- <td>${map1[i.bdCode]}</td>
					<td>${map2[i.bhzCode]}</td> -->
					<td>${i.intensityGrade}</td>
					<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
	            	<td align="center">
	                  <div  class="tdwid">
	                      <div class="tr_odiv">
	                          <span class="tr_bg"></span>
	                          <a href="intensityGradeAction!toUpdatePage.action?id=${i.id}">编缉</a>
	                      </div>
	                      <div class="tr_div">
	                          <span class="tdbg"></span>
	                          <a href="intensityGradeAction!deleteIntensityGrade.action?id=${i.id}" del="del">删除</a>
	                      </div>
	                  </div>
					</td>
					</c:if>	
				</tr>
			</c:forEach>
            </c:when>
            <c:otherwise>
            	<tr><td height="25" colspan="4" style="text-align: center;color: red;">暂无数据记录!</td></tr>
            </c:otherwise>
            </c:choose>                
        </table>
        <div class="clear"></div>
        <p:PagingTag url="intensityGradeAction!getIntensityGrade.action" page="${objPage}" forPage="true" showCurAndSum="true"/>
   <!-- <p:PagingTag url="intensityGradeAction!getIntensityGrade.action?bdCode=${qddj.bdCode}&bhzCode=${qddj.bhzCode}" page="${objPage}" forPage="true" showCurAndSum="true"/> -->
	</div>
</div>
</body>
</html>