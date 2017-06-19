<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <title>标段选择</title>
    <script type="text/javascript">
        function subProject() {
            var inNums = "", inNames = "", i = 0;
            var len = $('input[name="sectionName"]:checked').size();
            $('input[name="sectionName"]:checked').each(function () {
                i++;
                inNums += $(this).val();
                inNames += $(this).attr("title");
                if (len > i) {
                    inNums += ",";
                    inNames += ",";
                }
            });
            $("#morebd", window.parent.document).val(inNames);
            $("#morebdCode", window.parent.document).val(inNums);
            closeDiv();
        }
        function allChecked(obj) {
            $("input[name='sectionName']").attr("checked", obj.checked);
        }
        function closeDiv() {
            parent.ymPrompt.close();
        }
        $(document).ready(function () {
            var morebdCode = $("#morebdCode", window.parent.document).val();
            var arr = morebdCode.split(",");
            for (var i = 0; i < arr.length; i++) {
                $("input[name='sectionName']").each(function () {
                    if (arr[i] == $(this).val()) {
                        $(this).attr("checked", true);
                    }
                });
            }
        });
    </script>
</head>

<body style="height:500px;">
<table class="tablelist" style="width: 570px;height: 350px">
    <tr>
        <td colspan="2">
            标段选择
            <div style="float: right">
                全选<input id="allcheck" type="checkbox"
                         onclick="allChecked(this);"/>
            </div>
        </td>
    </tr>
    <c:set var="totalNum" value="${fn:length(requestScope.tbbds)}"/>
    <c:forEach var="s" items="${requestScope.tbbds}" varStatus="inx">
        <c:set var="i" value="${inx.index}"/>
        <c:choose>
            <c:when test="${i%2==0 && i==0}">
                <tr><td><input type="checkbox" id="${s.bdCode}" name="sectionName" value="${s.bdCode}" title="${s.bdName}">${s.bdName}</td>
            </c:when>
            <c:when test="${i%2==0 && i!=(totalNum-1)}">
                <!-- 省略/tr -->
                <tr><td><input type="checkbox" id="${s.bdCode}" name="sectionName" value="${s.bdCode}" title="${s.bdName}">${s.bdName}</td>
            </c:when>
            <c:when test="${i%2==0 && i==(totalNum-1) && (totalNum%2!=0)}">
                <!-- 省略/tr -->
                <tr>
                    <td>
                        <input type="checkbox" id="${s.bdCode}" name="sectionName" value="${s.bdCode}"
                               title="${s.bdName}">${s.bdName}
                    </td>
                    <td></td>
                </tr>
            </c:when>
            <c:when test="${i%2!=0}">
                <td>
                    <input type="checkbox" id="${s.bdCode}" name="sectionName" value="${s.bdCode}"
                           title="${s.bdName}">${s.bdName}
                </td>
            </c:when>
        </c:choose>
    </c:forEach>
    <tr>
        <td colspan="2" align="right">
            <input type="button" class="btn1" value="确定" onclick="subProject();"/>
            <input type="button" class="btn1" value="退出" onclick="closeDiv();"/></td>
    </tr>
</table>
</body>
</html>