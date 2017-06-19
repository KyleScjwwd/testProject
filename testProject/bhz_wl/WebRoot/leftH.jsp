<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title></title> 
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<style type="text/css">
.selecthover{background-color:#D3F2FE}
.lihover{background-color:#D3F2FE}
</style>
<script type="text/javascript">
$().ready(function() {
	$("#sel li").click(function(){
		$(this).addClass("selecthover").siblings().removeClass("selecthover");
	}).hover(function(){
		$(this).addClass("lihover");
   		},function () {
     $(this).removeClass("lihover");
	})
});
function showLoad(){
	window.top.frames["right"].openLoad();
}
</script>
</head>
<body class="leftbodybg" style="background-color:#f2f5fa;">
    <div class="left">
    	<h2><%=new String(request.getParameter("bhzTitle").getBytes("ISO-8859-1"),"UTF-8") %></h2>
        <ul id="sel">
        	<!-- 
        	<li><a class="sc" onclick="showLoad();" href="mainAction!mainIndex.action" target="right"><div style="margin-left:20px;">生产状态</div></a></li>
            <li><a class="jk" onclick="showLoad();" href="mainAction!getGiveDate.action" target="right"><div style="margin-left:20px;">生产数据监控</div></a></li>
            <li><a class="dx" onclick="showLoad();" href="messageAction!getMessageUser.action" target="right"><div style="margin-left:20px;">短信报警</div></a></li>
            <li><a class="tj" onclick="showLoad();" href="countAction!countGzbwSn.action" target="right"><div style="margin-left:20px;">统计分析</div></a></li>
            <li><a class="xt" onclick="showLoad();" href="authorizationAction!sysSystemAuth.action?funCode=fun0101" target="right"><div style="margin-left:20px;">系统管理</div></a></li>
        	 -->
        	<li><a class="sc" onclick="showLoad();" href="authorizationAction!leftAuth.action?funCode=fun05" target="right"><span style="margin-left:20px;">生产状态</span></a></li>
            <li><a class="jk" onclick="showLoad();" href="authorizationAction!leftAuth.action?funCode=fun04" target="right"><span style="margin-left:20px;">生产数据监控</span></a></li>
            <li><a class="dx" onclick="showLoad();" href="authorizationAction!leftAuth.action?funCode=fun03" target="right"><span style="margin-left:20px;">短信报警</span></a></li>
            <li><a class="tj" onclick="showLoad();" href="authorizationAction!leftAuth.action?funCode=fun02" target="right"><span style="margin-left:20px;">统计分析</span></a></li>
            <li><a class="xt" onclick="showLoad();" href="authorizationAction!leftAuth.action?funCode=fun01" target="right"><span style="margin-left:20px;">系统管理</span></a></li>
        </ul>
    </div>
</body>
</html>