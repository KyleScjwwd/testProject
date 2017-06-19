<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>参数管理</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/givedata.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript">
		function seleParam(){
			var bd = $("#bd").val();
			var bhz = $("#bhz").val();
			if(bd==""){
				alert("请选择标段!");
				return false;
			}
			if(bhz==""){
				alert("请选择拌合站!");
				return false;
			}
			$("#bhzName").val($("#bhz").find("option:selected").text());
			$("#paramForm").submit();
		}
		
		function saveParam(){
			var l = $("#paramsList").val();
			if(l==null || l=="" || l==0){
				alert("请先查询数据!");
			}else{
				if (confirm("确定批量提交？")){
					$("#spanMsg").html("批量提交数据较慢,请耐心等待");
					var bd = $("#bd").val();
					var bhz = $("#bhz").val();
					var url = "mainAction!updateParam.action?bd="+bd+"&bhz="+bhz; 
					$("#updateForm").attr("action",url);
					$("#updateForm").submit();
				}
			}
		}
</script>
</head>

<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ5"/>
<div class="all">
    <div class="scsj">
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">系统管理</a></p>
    </div>
    <form id="paramForm" action="mainAction!setParam.action" method="post">
	<input type="hidden" id="bhzName" name="bhzName"/>
	<input type="hidden" id="paramsList" name="paramsList" value="${fn:length(tbParams)}"/>
	<input type="hidden" id="bdCode" name="bdCode" value="${bd}"/>
	<input type="hidden" id="bhzCode" name="bhzCode" value="${bhz}"/>
    <table class="Tab">
        <tr>
        	<td>
            	标段名称：
            </td>
            <td>
            	<select id="bd" name="bd" class="Fwidth" onchange="showBhz();" >
                  <option value="">==请选择标段==</option>
                  <c:forEach var="b" items="${tbBds}">
				    <option value="${b.bdCode}" ${b.bdCode==bd?'selected':''}>${b.bdName}</option>
			 	  </c:forEach>
                </select>
            </td><td>
            	拌和站名称：
            </td>
            <td>
            	<select id="bhz" name="bhz" class="Fwidth">
                  <option value="">==请选择拌合站==</option>
                </select>
            </td>
            <td><a href="javascript:;" class="search" onclick="seleParam();">查询</a></td>
			<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
            <td><a href="javascript:;" class="tjia" onclick="saveParam();">批量修改</a>&nbsp;<span id="spanMsg" style="color: red">${msg}</span></td>
        	</c:if>
        </tr>
    </table>
    </form>
        <%@ include file="base/sysLinked.jsp" %>
        <div class="sj_list">
        	<form id="updateForm" method="post">
            <table class="message" >
                <tr class="onbg">
                    <td>所属标段</td>
                    <td>所属拌合站</td>
                    <td>参数名</td>
                    <td>参数值</td>
                    <td>是否启用</td>
                </tr>
                <c:choose>
						<c:when test="${fn:length(tbParams)==0}">
						<tr><td colspan="5" style="text-align: center;color: red; height: 25px;">暂无数据记录!</td></tr>
						</c:when>
						<c:otherwise>
						<c:forEach var="p" items="${tbParams}" varStatus="i">
						<tr>
							<td>${bdMap[p.bdCode]}</td>
							<td>${bhzName}</td>
							<td>${p.pname}</td>
							<td>
							<input type="hidden" name="pId${i.count}" value="${p.id}"/>
							<input type="text" name="pVal${i.count}" value="${p.pvalue}"/>
							</td>
							<td><input type="checkbox" name="pFlag${i.count}" value="${p.flag}" ${p.flag eq '1'?'checked':''}/></td>
						</tr>
						</c:forEach>
						</c:otherwise>
					</c:choose>
            </table>
            </form>
            <div class="clear"></div>
        </div>
</div>
</body>
</html>