<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(document).ready(function(){
	var linkFunCode = $("#linkFunCode").val();
	$("#linkMainDiv a").each(function(){
		var id = $(this).attr("id");
		if(id=="link_"+linkFunCode){
			$(this).addClass("mouse");
		}else{
			$(this).addClass("onmouse");
		}
	});
});
</script>
<div id="linkMainDiv" class="list">
	<input type="hidden" id="linkFunCode" value="${sessionScope.linkFunCode}"/>
	<a id="link_fun0101" href="authorizationAction!sysSystemAuth.action?funCode=fun0101">标段管理</a>
    <a id="link_fun0102" href="authorizationAction!sysSystemAuth.action?funCode=fun0102">拌合站管理</a>
    <!-- 
    <a id="link_fun0103" href="authorizationAction!sysSystemAuth.action?funCode=fun0103">人员管理</a>
     -->
    <a id="link_fun0104" href="authorizationAction!sysSystemAuth.action?funCode=fun0104">参数管理</a>
    <a id="link_fun0109" href="authorizationAction!sysSystemAuth.action?funCode=fun0109">合并列设置</a>
    <a id="link_fun0105" href="authorizationAction!sysSystemAuth.action?funCode=fun0105">强度等级</a>
    <a id="link_fun0106" href="authorizationAction!sysSystemAuth.action?funCode=fun0106">设计配合比</a>
    <a id="link_fun0107" href="authorizationAction!sysSystemAuth.action?funCode=fun0107">过滤条件</a>
    <a id="link_fun01010" href="authorizationAction!sysSystemAuth.action?funCode=fun01010">拌合站生产登记</a>
    <!-- 
    <a id="link_fun0108" href="authorizationAction!sysSystemAuth.action?funCode=fun0108">权限设置</a>
     -->
</div>