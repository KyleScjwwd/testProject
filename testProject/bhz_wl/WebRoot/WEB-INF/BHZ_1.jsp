<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>拌合站管理</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<link rel="stylesheet" type="text/css" href="css/LAYER/ymPrompt.css"/>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/BHZ.js?x"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/other/sqmqcl.js"></script>
<script type="text/javascript" src="js/other/ymPrompt.js"></script>
</head>

<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ5"/>
<div class="all">
    <div class="scsj">
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">系统管理</a></p>
    </div>
    <form id="FindBHZForm" action="BHZAction!findBHZ.action" method="post" >
    <table class="Tab">
        <tr>
        	<td class="FL">标段名称：</td>
            <td>
            	<select name="bdCode" class="Fwidth" style="width:120px;">
                  <option value="">==请选择标段==</option>
                  <c:forEach var="b" items="${BDs}">
				    <option value="${b.bdCode}" ${b.bdCode==bhz.bdCode?'selected':''}>${b.bdName}</option>
			 	  </c:forEach>
                </select>
            </td>
            <td class="FL">拌合站名称：</td>
            <td><input type="text" name="bhzName" value="${bhz.bhzName}" class="Fwidth" style="width:120px;"/></td>
            <td class="FL">上限：</td>
            <td><input type="text" name="upNum" value="${bhz.upNum}" class="Fwidth" style="width:120px;"/></td>   
            <td class="FL">下限：</td>
            <td><input type="text" name="downNum" value="${bhz.downNum}" class="Fwidth" style="width:120px;"/></td>   
            <td><a href="javascript:;" class="search" onclick="bdz.CheckFind();">查询</a></td>
			<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
            <td><a href="BHZAction!toAddBHZPage.action" class="tjia">添加</a></td>
            </c:if>
        </tr>
    </table>
    </form>
        <%@ include file="base/sysLinked.jsp" %>
        <div class="sj_list">
            <table class="message" >
                <tr class="onbg">
                    <td>所属标段</td>
                    <td>拌合站编号</td>
                    <td>拌合站名称</td>
                    <td>上限</td>
                    <td>上限</td>
                    <td>拌合站密码</td>
                    <td>客户端版本</td>
                    <td>合并</td>
                    <td>施工配比校正</td>
					<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                    <td>操作</td>
                    </c:if>
                </tr>
                <c:choose>
				<c:when test="${fn:length(objPage.objList)>0}">
                <c:forEach var="b" items="${objPage.objList}">
					<tr>
					<td>${map[b.bdCode]}</td>
					<td>${b.bhzCode}</td>
					<td>${b.bhzName}</td>
					<td>${b.upNum}</td>
					<td>${b.downNum}</td>
					<td>${b.ext1}</td>
					<td>${b.clientVersion}</td>
					<c:choose>
					<c:when test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
					<td><input type="checkbox" id="mergebox${b.id}" onclick="bdz.updateMerge('${b.id}');" ${b.mergeClos eq '1'?'checked':''}/></td>
					<td><input type="checkbox" id="testbox${b.id}" onclick="bdz.updateTest('${b.id}');" ${b.sgpbTest eq '1'?'checked':''}/></td>
					</c:when>
					<c:otherwise>
					<td><input type="checkbox" id="mergebox${b.id}" ${b.mergeClos eq '1'?'checked':''} disabled="disabled"/></td>
					<td><input type="checkbox" id="testbox${b.id}" ${b.sgpbTest eq '1'?'checked':''} disabled="disabled"/></td>
					</c:otherwise>
					</c:choose>
					<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
					<td style="width:300px;">
                        <div class="tdwid" style="width:250px;">
                            <div class="tr_odiv" style="width: 25%">
                                <span class="tr_bg"></span>
                                <a href="BHZAction!toUpdatePage.action?id=${b.id}">编辑</a>
                            </div>
                            <div class="tr_div" style="width: 25%">
                                <span class="tdbg"></span>
                                <a href="BHZAction!deleteBHZ.action?id=${b.id}&bhzCode=${b.bhzCode}" del="del" title="${map[b.bdCode]}标${b.bhzName}拌合站">删除</a>
                            </div>
                            <c:if test="${sessionScope.username eq 'admin'}">
                            <div class="tr_odiv" style="border-left: 1px solid;width: 24%">
                                <span class="tr_bg"></span>
                                <a href="javascript:;" onclick="showPage('BHZAction!setOrDelDataInfo.action?bdCode=${b.bdCode}&bhzCode=${b.bhzCode}',500,300,'设置上传数据信息');">设置</a>
                            </div>
                            <div class="tr_odiv" style="width: 25%">
                                <span class="tr_bg"></span>
                                <a target="_blank" href="BHZAction!lookLogFile.action?bdCode=${b.bdCode}&bhzCode=${b.bhzCode}">log查看</a>
                            </div>
                            </c:if>
                        </div>
                    </td>
                    </c:if>
					</tr>
				</c:forEach>
                </c:when>
                <c:otherwise>
                <tr><td height="25" colspan="9" style="text-align: center;color: red;">暂无数据记录!</td></tr>
                </c:otherwise>
                </c:choose>                
            </table>
            <div class="clear"></div>
            <p:PagingTag url="BHZAction!toBHZPage.action" page="${objPage}" forPage="true" showCurAndSum="true"/>
    </div>
</div>
</body>
</html>