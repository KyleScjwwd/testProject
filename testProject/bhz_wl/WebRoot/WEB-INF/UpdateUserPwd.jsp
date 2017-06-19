<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改个人密码</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript">
function updatePwdInfo(){
	var oldPwd = $("#oldPwd").val();
	var newPwd = $("#newPwd").val();
	var subPwd = $("#subPwd").val();
	if(oldPwd==""){
		alert("请输入老密码!");
		$("#oldPwd").focus();
		return;
	}
	if(newPwd==""){
		alert("请输入新密码!");
		$("#newPwd").focus();
		return;
	}
	if(subPwd==""){
		alert("请输入确认信密码!");
		$("#subPwd").focus();
		return;
	}
	if(newPwd!=subPwd){
		alert("两次密码输入不一致!");
		$("#subPwd").focus();
		return;
	}
	$("#updatePwdForm").submit();
}
</script>
</head>
<body>
<div class="all">
  <div class="scsj"><p>您当前的位置：<a href="javascript:;">修改个人密码</a></p></div>
  <br/>
  <form id="updatePwdForm" action="UserAction!updateUserPwd.action" method="post">
    <table class="bjdiv">
    	   <tr><td colspan="2" style="text-align: center"><font size="4">修改个人密码</font></td></tr>
           <tr>
                <td class="FL" align="right" width="40%">用户名：</td>
                <td><font color="red">${sessionScope.username}</font>&nbsp;&nbsp;&nbsp;${msgInfo}</td>
           </tr>
           <tr>
                <td class="FL" align="right">老密码：</td>
                <td><input type="password" id="oldPwd" name="oldPwd" class="Fwidth"/></td>
           </tr>
           <tr>
                <td class="FL" align="right">新密码：</td>
                <td><input type="password" id="newPwd" name="newPwd" class="Fwidth"/></td>
           </tr>
           <tr>
                <td class="FL" align="right">确认新密码：</td>
                <td><input type="password" id="subPwd" name="subPwd" class="Fwidth"/></td>
           </tr>
            <tr>
                <td colspan="2" align="center">
                    <a href="javascript:;" class="bia" style="margin-left:440px" onclick="updatePwdInfo();">修改</a>
                    <a href="javascript:history.back();" class="bia">取消</a>
                </td>
            </tr>
    </table>
    </form>
</div>
</body>
</html>