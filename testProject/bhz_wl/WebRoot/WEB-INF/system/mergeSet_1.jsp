<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>合并列设置</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<link rel="stylesheet" type="text/css" href="css/LAYER/ymPrompt.css"/>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/givedata.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/other/sqmqcl.js"></script>
<script type="text/javascript" src="js/other/ymPrompt.js"></script>
<script type="text/javascript" src="js/merge.js"></script>
</head>

<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ5"/>
<div class="all">
    <div class="scsj">
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">系统管理</a></p>
    </div>
    <input type="hidden" id="bdCode" value="${bdCode}"/>
    <input type="hidden" id="bhzCode" value="${bhzCode}"/>
    <table class="Tab">
        <tr>
        	<td>
            	标段名称：
            </td>
            <td>
            	<select id="bd" name="bd" class="Fwidth" onchange="showBhz();" >
                  <option value="">==请选择标段==</option>
                  <c:forEach var="b" items="${tbBds}">
				    <option value="${b.bdCode}" ${b.bdCode==bdCode?'selected':''}>${b.bdName}</option>
			 	  </c:forEach>
                </select>
            </td><td>
            	拌和站名称：
            </td>
            <td>
            	<select id="bhz" name="bhz" class="Fwidth" onchange="showMergeData();">
                  <option value="">==请选择拌合站==</option>
                </select>
            </td>
			<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
            <td><a href="javascript:;" class="tjia" onclick="addMerge();">新增</a></td>
        	</c:if>
        </tr>
    </table>
        <%@ include file="../base/sysLinked.jsp" %>
        <div class="sj_list">
            <table class="message" >
                <tr class="onbg">
                    <td>所属标段</td>
                    <td>所属拌合站</td>
                    <td>显示顺序</td>
                    <td>合并列参数</td>
                    <td>合并列名</td>
					<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
		            <td>操作</td>
		            </c:if>
                </tr>
                <c:choose>
				<c:when test="${fn:length(mcoList)>0}">
				<c:forEach var="m" items="${mcoList}">
				<tr>
				<td>${bdMap[m.bdCode].bdName}</td>
				<td>${bhzMap[m.bhzCode].bhzName}</td>
				<td>${m.orderNum}</td>
				<td>${m.mergeCols}</td>
				<td>${m.mergeColsName}</td>
				<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
				<td><a href="javascript:;" onclick="delMerge('${m.id}','${m.bdCode}','${m.bhzCode}')">删除</a></td>
				</c:if>
				</tr>
				</c:forEach>
				</c:when>
				<c:otherwise>
				<tr>
				<td colspan="6"><font color="red">暂无数据,请选择标段及拌合站查询!</font></td>
				</tr>
				</c:otherwise>
				</c:choose>
            </table>
            <div class="clear"></div>
        </div>
</div>
</body>
</html>