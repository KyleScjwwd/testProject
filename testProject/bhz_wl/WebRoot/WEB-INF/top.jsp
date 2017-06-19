<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>top页面</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv=Content-Type content=text/html;charset=gb2312 />
		<link type="text/css" rel="stylesheet" href="css/bhz/style.css" />
		<link type="text/css" rel="stylesheet" href="css/bhz/index1.css" />
		<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
	</head>
	<body>
		<div class="title">
			<div class="top">
				<div class="topone">
					<div class="logo">
						<img id="img_title_id" src="css/bhz/images/logo2_03.png" />
					</div>
					<div class="out">
						<div class="outtwo">
							<p style="color: white;">
								您好！${username}欢迎登录
							</p>
							<a href="loginAction!exitLogin.action" class="outbg" style="float: right; margin-right: 20px; width: 100px;">退出登录</a>
						</div>
					</div>
				</div>
				<div class="topT">
					<div class="toptwo">
						<span class="menuL"></span>
						<ul class="xianshi">
							<li><a name="dhName" href="${hqmsSite}/nodeAction!listProjectInfo.action" target="_top">首页</a></li>
						</ul>
						<span class="menuR"></span>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>