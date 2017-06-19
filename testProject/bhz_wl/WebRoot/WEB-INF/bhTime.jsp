<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/4
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="p" uri="/page-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="newWeb/js/jquery.js"></script>
    <script type="text/javascript" src="js/givedata.js"></script>
    <script type="text/javascript" src="js/util.js"></script>
    <title></title>

    <script type="text/javascript">
        $(document).ready(function () {
            $(".click").click(function () {
                $(".tip").fadeIn(200);
            });

            $(".tiptop a").click(function () {
                $(".tip").fadeOut(200);
            });

            $(".sure").click(function () {
                $(".tip").fadeOut(100);
            });

            $(".cancel").click(function () {
                $(".tip").fadeOut(100);
            });

        });
    </script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">生产数据监控</a></li>
        <li><a href="#">拌合时间</a></li>
    </ul>
</div>
<input type="hidden" id="bdCode" name="bdCode" value="${bdCode}"/>
<input type="hidden" id="bhzCode" name="bhzCode" value="${bhzCode}"/>
<form id="form1" action="countAction!getBhTime.action" method="post">
    <table class="tablelist">
        <tr>
            <td>标段名称：</td>
            <td>
                <select class="scinput" name="bd" id="bd" onchange="showBhz();" style="height: 25px">
                    <option value="">==请选择标段==</option>
                    <c:forEach var="b" items="${bdList}">
                        <option <c:if test="${bdCode eq b.bdCode}">selected="selected"</c:if> value="${b.bdCode}">${b.bdName}</option>
                    </c:forEach>
                </select>
            </td>
            <td>拌合站名称：</td>
            <td>
                <select class="scinput" name="bhz" id="bhz" style="height: 25px">
                    <option value="">==请选择拌合站==</option>
                </select>
            </td>
            <td>出料时间：</td>
            <td>
                <input type="text" class="scinput" style="height: 25px" name="sdate" value="${sdate}" id="sdate" onfocus="WdatePicker();"/>至
                <input type="text" class="scinput" style="height: 25px" name="edate" value="${edate}" id="edate" onfocus="WdatePicker();"/>
            </td>
            <td>
                <input name="" type="button" class="btn1" onclick="seleParam();" value="查询"/>
            </td>
        </tr>
    </table>

    <table class="tablelist">
        <tr>
            <td>
                <img src="${pageContext.request.contextPath}/jfreeChartFile/bhTime.jpg?randomNum=${randomNum}" />
            </td>
        </tr>
    </table>
    <p:PagingTag url="countAction!getBhTime.action?bd=${bdCode}&bhz=${bhzCode}&sdate=${sdate}&edate=${edate}" page="${objPage}" forPage="true" showCurAndSum="true"/>
</form>
</body>
<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');
</script>
</html>
