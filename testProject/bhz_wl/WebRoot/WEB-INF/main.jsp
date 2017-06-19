<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${bhzTitle}混凝土拌合站动态监控系统</title>
</head>

<frameset rows="116,*,48" cols="*" frameborder="no" border="0" framespacing="0">
    <frame src="mainAction!mainTop.action" name="top" scrolling="No" noresize="noresize" id="top" title="topFrame"/>
    <frameset id="main" name="main" rows="*" cols="231,9,*" framespacing="0" frameborder="no" border="0">
        <frame src="leftH.jsp?bhzTitle=${bhzTitle}" name="left" scrolling="No" noresize="noresize" id="left" title="leftFrame"/>
        <frame name="left" src="center.html">
        <frame src="authorizationAction!leftAuth.action?funCode=fun05" name="right" id="right" title="rightFrame"/>
    </frameset>
    <frame src="footH.jsp" name="foot" scrolling="No" noresize="noresize" id="foot" title="footFrame"/>
</frameset>
<body>
</body>
</html>