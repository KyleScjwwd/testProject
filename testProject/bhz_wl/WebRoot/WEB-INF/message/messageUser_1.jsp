<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>通讯录设置</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript">
function seleParam(){
	var bdCode = $("#bdCode").val();
	var realName = $("#realName").val();
	var telphone = $("#telphone").val();
	$("#form1").attr("action","messageAction!getMessageUser.action");
	$("#form1").submit();
}
function saveParam(){
	var bdCode = $("#bdCode").val();
	var realName = $("#realName").val();
	var telphone = $("#telphone").val();
	if(bdCode.length==0){
		alert("请选择标段!");
		return;
	}
	if(realName.length==0){
		alert("请输入联系人姓名!");
		return;
	}
	if(telphone.length==0){
		alert("请输入手机号码!");
		return;
	}
	$("#form1").attr("action","messageAction!addMessageUser.action");
	$("#form1").submit();
}
function delParam(id){
	if(confirm('你确定要删除该记录吗?')){
		var bdCode = $("#bdCode").val();
		window.location.href="messageAction!delMessageUser.action?id="+id+"&bdCode="+bdCode;
	}
	return false;
}
</script>
</head>

<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ3"/>
<div class="all">
    <div class="scsj">
            <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">短信报警 </a></p>
    </div>
    <form id="form1" method="post">
    <table class="Tab">
        <tr>
            <td class="FL">标段名称：</td>
            <td>
                <select name="bdCode" id="bdCode" class="Fwidth">
                    <option value="">==请选择标段==</option>
                    <c:forEach var="b" items="${bdList}">
				    <option <c:if test="${bCode eq b.bdCode}">selected="selected"</c:if> value="${b.bdCode}">${b.bdName}</option>
				    </c:forEach>
              	</select>
            </td>
            <td class="FL">联系人姓名：</td>
            <td>
              <input type="text" class="Fwidth" id="realName" name="realName" value="${realName}"/>
            </td>
            <td class="FL">手机号码：</td>
            <td>
              <input type="text" class="Fwidth" id="telphone" name="telphone" value="${telphone}"/>
            </td>
            <td><a href="javascript:;" class="search" onclick="seleParam();">查询</a></td>
			<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
            <td><a href="javascript:;" class="tjia" onclick="saveParam();">添加</a></td>
            </c:if>
        </tr>
    </table>
    </form>
        <div class="list">
            	<a class="mouse">通迅录设置</a>
            	<a class="onmouse" href="messageAction!setMessage.action">短信报警设置</a>
            	<a class="onmouse" href="messageAction!setBaoJingBase.action">报警基本设置</a>
            	<a class="onmouse" href="messageAction!setBaoJingPersonBase.action">报警人员基本设置</a>
	         	<a class="onmouse" href="messageAction!setMessageSendType.action">短信发送方式设置</a>
            	<a class="onmouse" href="messageAction!msgHistory.action">发送记录查询</a>
        </div>
        <div class="sj_list">
            <table class="message" >
                <tr class="onbg">
                    <td>所属标段</td>
                    <td>联系人姓名</td>
                    <td>联系人电话</td>
            		<c:set var="authPage" value="${sessionScope.username}-fun0301"/>
                    <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                    <td>操作</td>
                    </c:if>
                </tr>
                <c:choose>
					<c:when test="${fn:length(objPage.objList)>0}">
					<c:forEach var="m" items="${objPage.objList}">
					<tr>
						<td>${bdMap[m.bdCode]}</td>
						<td>${m.realName}</td>
						<td>${m.telphone}</td>
            			<c:set var="authPage" value="${sessionScope.username}-fun0301"/>
						<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
						<td align="center" style="width:300px;">
                        <div class="tdtwid" >
                            <div class="tr_div">
                                <span class="tdbg"></span>
                                <a href="javascript:;" onclick="delParam(${m.id});" style="color:#000;">删除</a>
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
            <p:PagingTag url="messageAction!getMessageUser.action?bCode=${bCode}&telphone=${telphone}" page="${objPage}" forPage="true" showCurAndSum="true"/>
         </div>
</div>
</body>
</html>