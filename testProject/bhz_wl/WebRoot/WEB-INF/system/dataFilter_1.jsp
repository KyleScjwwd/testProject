<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据过滤条件</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<link rel="stylesheet" type="text/css" href="css/LAYER/ymPrompt.css"/>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/User.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/filterData.js"></script>
<script type="text/javascript" src="js/other/sqmqcl.js"></script>
<script type="text/javascript" src="js/other/ymPrompt.js"></script>
<script type="text/javascript">
function searchData(){
	$("#FindFilterDataForm").submit();
}
</script>
</head>

<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ5"/>
<div class="all">
    <div class="scsj">
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">系统管理</a></p>
    </div>
    <form id="FindFilterDataForm" action="mainAction!getFilterData.action" method="post">
    <table class="Tab">
        <tr>
        	<td class="FL">所属标段：</td>
            <td>
            	<select name="bdCode" class="Fwidth" style="width:120px;">
                  <option value="">==请选择标段==</option>
                  <c:forEach var="b" items="${bdList}">
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
            <td class="FL">浇筑部位：</td>
            <td><input type="text" name="m5" class="Fwidth" style="width: 120px;" value="${m5}"/></td>
            <td class="FL">出料时间：</td>
            <td><input type="text" name="m4" class="Fwidth" style="width: 120px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" value="${m4}"/></td>
            <td><a href="javascript:;" class="search" onclick="searchData();">查询</a></td>
			<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
            <td><a href="javascript:;" class="tjia" onclick="showPage('mainAction!addFilterData.action',770,200,'添加过滤条件');">添加</a></td>
        	</c:if>
        </tr>
    </table>
    </form>
    <%@ include file="../base/sysLinked.jsp" %>
    <div class="sj_list">
	        <table class="message" >
	            <tr class="onbg">
		            <td>序号</td>
		            <td>标段</td>
		            <td>拌合站</td>
		            <td>浇筑部位</td>
		            <td>出料时间</td>
					<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
		            <td>操作</td>
		            </c:if>
	            </tr>
	            <c:choose>
  				<c:when test="${fn:length(objPage.objList)>0}">
  				<c:forEach var="f" items="${objPage.objList}" varStatus="i">
  				<tr>
	             	<td>${i.count}</td>
	             	<td><c:set var="bd" value="${f[1]}"/>${bdMap[bd]}</td>
	             	<td><c:set var="bhz" value="${f[5]}"/>${bhzMap[bhz]}</td>
	             	<td>${f[4]}</td>
	             	<td><fmt:formatDate value='${f[3]}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
					<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
	             	<td>
	             	<div class="tdwid">
                       <div class="tr_div" style="margin-left:20px;"><span class="tdbg"></span><a href="javascript:;" onclick="delFilterData('${f[1]}','${f[5]}','${f[4]}','${f[3]}');">删除</a></div>
                    </div>
	             	</td>
	             	</c:if>
	            </tr>
	            </c:forEach>
  				</c:when>
  				<c:otherwise>
	            <tr>
	             	<td colspan="6"><font color="red">暂无数据记录!</font></td>
	            </tr>
	            </c:otherwise>
	            </c:choose>
	        </table>
	        <div class="clear"></div>
            <p:PagingTag url="mainAction!getFilterData.action?bdCode=${bdCode}&bhzCode=${bhzCode}&m5=${m5}&m4=${m4}&subType=1" page="${objPage}" forPage="true" showCurAndSum="true"/>
	</div>
</div>
</body>
</html>