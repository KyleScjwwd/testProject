<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="/page-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>添加合并列数据</title>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/util.js"></script>
    <script type="text/javascript" src="js/merge.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            var paramIsNull = $("#paramIsNull").val();
            if (paramIsNull == "1") {
                alert("该标段、拌合站没有参数，请先至【参数管理】中进行设置!");
                closeDiv();
            }
        });
    </script>
</head>

<body style="overflow: hidden;">
<c:set var="authPage" value="${sessionScope.username}-baseBHZ5"/>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">系统管理</a></li>
        <li>
            <a href="#">
                <font color="red"> ${bdMap[bdCode].bdName}标${bhzMap[bhzCode].bhzName}拌合站合并列设置</font>
            </a>
        </li>
    </ul>
</div>
<div class="rightinfo">
    <input type="hidden" id="paramIsNull" value="${paramIsNull}"/>
    <table class="tablelist" style="width: 65%">
        <tr>
            <td style="height:250px;width:35%" valign="top">
                <input type="hidden" id="bdCode" name="bdCode" value="${bdCode}"/>
                <input type="hidden" id="bhzCode" name="bhzCode" value="${bhzCode}"/>

                <div style="width:100%;height:230px;overflow:auto;">
                    <c:forEach var="p" items="${paramList}">
                        <div style="text-align:left;margin: 0 0 0 10px;">
                            <input  type="checkbox" name="pname" value="${p.pname}" onclick="addTextData();"/>${p.pname}&nbsp;${p.pvalue}
                        </div>
                    </c:forEach>
                </div>
            </td>
            <td valign="center">
                <textarea id="colsName" name="colsName" rows="17" cols="60" readonly="readonly"></textarea>
            </td>
        </tr>
        <tr>
            <td>显示顺序:<input type="text" class="scinput" style="height: 25px;margin-top: 6px" id="sortIndex" name="sortIndex"/></td>
            <td>合并列名:<input type="text" class="scinput" style="height: 25px;margin-top: 6px" id="colsValue" name="colsValue"/></td>
        </tr>

    </table>
    <div style="margin-top:10px;">
        <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
            <input class="btn1" style="width:45px;" type="button" value="保存" onclick="saveMerge();"/>
        </c:if>
        <input  class="btn1" style="width:45px;" type="button" value="取消" onclick="closeDiv();"/>
    </div>
</div>
</body>
</html>