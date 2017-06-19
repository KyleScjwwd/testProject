<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改强度等级</title>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/User.js"></script>
<script type="text/javascript" src="js/intensityGrade.js"></script>
<script type="text/javascript" src="js/util.js"></script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">系统管理</a></li>
        <li><a href="#">修改强度等级</a></li>
    </ul>
</div>
<div class="rightinfo">
    <form id="UpdateIntensityGradeForm" action="intensityGradeAction!updateIntensityGrade.action" method="post">
        <input type="hidden" name="id" value="${qddj.id}"/>
        <table class="tablelist">
            <tr>
                <td class="FL" align="right">强度等级：</td>
                <td><input type="text" name="intensityGrade" value="${qddj.intensityGrade}" class="scinput" style="height: 25px"/></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="button" class="btn1" style="margin-left: 350px" onclick="CheckUpdate();" value="修改"/>
                    <input type="button" class="btn1" onclick="javascript:history.back();" value="取消"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>