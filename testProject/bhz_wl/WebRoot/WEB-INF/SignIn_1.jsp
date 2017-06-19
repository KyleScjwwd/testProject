<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>拌合签到</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<link rel="stylesheet" type="text/css" href="css/LAYER/ymPrompt.css"/>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/User.js"></script>
<script type="text/javascript" src="js/SignIn.js"></script>
<script type="text/javascript" src="js/util.js"></script>
</head>

<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ5"/>
<div class="all">
    <div class="scsj">
    	<p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">系统管理</a></p>
    </div>
    <input type="hidden" id="hidBd" value="${si.bdCode}"/>
    <input type="hidden" id="hidBhz" value="${si.bhzCode}"/>
    <form id="FindSignInForm" action="signInAction!FindSignIn.action" method="post" >
    <table class="Tab">
        <tr>
            <td class="FL">标段：</td>
            <td>
	            <select class="Fwidth" name="bdCode" style="width: 120px">            
	            	<option value="">==请选择标段==</option>
					<c:forEach var="b" items="${BDs}">
						<option value="${b.bdCode}" ${b.bdCode==si.bdCode?'selected':''}>${b.bdName}</option>
					</c:forEach>
	            </select>
            </td>
            <td class="FL">拌合站：</td>
            <td>
	            <select class="Fwidth" id="bhz" name="bhzCode" style="width: 120px">            
	            	<option value="">==请选择拌合站==</option>
	            </select>
            </td>   
            <td class="FL">生产日期：</td>
            <td>
               <input type="text" name="sdate" class="Fwidth" style="width: 80px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});" value="${sdate}"/> -
               <input type="text" name="edate" class="Fwidth" style="width: 80px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});" value="${edate}"/>
            </td>   
            <td><a href="javascript:;" class="search" onclick="si.CheckFind()">查询</a></td>
			<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
            	<td><a href="signInAction!toAddSignInPage.action" class="tjia">添加</a></td>
            </c:if>
        </tr>
    </table>
    </form>
        <%@ include file="base/sysLinked.jsp" %>
        <div class="sj_list">
            <table class="message" >
                <tr class="onbg">
                    <td>标段名称</td>
                    <td>拌合站名称</td>
                    <td>生产日期</td>
                    <td>签到人</td>
                    <c:set var="authPage" value="${sessionScope.username}-fun01010"/>
					<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                    	<td>操作</td>
                    </c:if>
                </tr>
                <c:choose>
				<c:when test="${fn:length(objPage.objList)>0}">
	                <c:forEach var="s" items="${objPage.objList}">
	                 <tr>
	                    <td>${bdMap[s.bdCode]}</td>
	                    <td>${bhzMap[s.bhzCode]}</td>
	                    <td><fmt:formatDate value="${s.signInDate}" pattern="yyyy-MM-dd"/></td>
	                    <td>${s.signInUser}</td>
						<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
		                    <td align="center" style="width:300px;">
		                        <!-- <div  class="tdwid"> -->
		                            <div class="tr_odiv" style="margin-left: 41%">
		                                <span class="tr_bg"></span>
		                                <a href="signInAction!toUpdatePage.action?id=${s.id}">编缉</a>
		                            </div>
		                            <!--
		                            <div class="tr_div">
		                                <span class="tdbg"></span>
		                                <a href="signInAction!deleteSignIn.action?id=${s.id}" del="del">删除</a>
		                            </div>
		                             -->
		                        <!-- </div> -->
		                    </td>
	                    </c:if>
	                </tr>
	                </c:forEach>
                </c:when>
                <c:otherwise>
                	<tr><td height="25" colspan="5" style="text-align: center;color: red;">暂无签到记录!</td></tr>
                </c:otherwise>
                </c:choose>
            </table>
            <div class="clear"></div>
            <p:PagingTag url="signInAction!FindSignIn.action?bdCode=${si.bdCode}&bhzCode=${si.bhzCode}&sdate=${sdate}&edate=${edate}" page="${objPage}" forPage="true" showCurAndSum="true"/>
        </div>
</div>
</body>
</html>