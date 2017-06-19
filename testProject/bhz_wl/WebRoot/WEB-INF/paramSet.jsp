<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>参数管理</title>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/givedata.js"></script>
    <script type="text/javascript" src="js/util.js"></script>
    <script type="text/javascript">
        function seleParam() {
            var bd = $("#bd").val();
            var bhz = $("#bhz").val();
            if (bd == "") {
                alert("请选择标段!");
                return false;
            }
            if (bhz == "") {
                alert("请选择拌合站!");
                return false;
            }
            $("#bhzName").val($("#bhz").find("option:selected").text());
            $("#paramForm").submit();
        }

        function saveParam() {
            var l = $("#paramsList").val();
            if (l == null || l == "" || l == 0) {
                alert("请先查询数据!");
            } else {
                if (confirm("确定批量提交？")) {
                    $("#spanMsg").html("批量提交数据较慢,请耐心等待");
                    var bd = $("#bd").val();
                    var bhz = $("#bhz").val();
                    var url = "mainAction!updateParam.action?bd=" + bd + "&bhz=" + bhz+"&bhzName="+$("#bhz").find("option:selected").text();
                    $("#updateForm").attr("action", url);
                    $("#updateForm").submit();
                }
            }
        }
    </script>
</head>

<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ5"/>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">系统管理</a></li>
        <li><a href="#">参数管理</a></li>
    </ul>
</div>
<div class="rightinfo">
    <form id="paramForm" action="mainAction!setParam.action" method="post">
        <input type="hidden" id="bhzName" name="bhzName"/>
        <input type="hidden" id="paramsList" name="paramsList" value="${fn:length(tbParams)}"/>
        <input type="hidden" id="bdCode" name="bdCode" value="${bd}"/>
        <input type="hidden" id="bhzCode" name="bhzCode" value="${bhz}"/>
        <table class="tablelist">
            <tr>
                <td>
                    标段名称：
                </td>
                <td>
                    <select id="bd" name="bd" class="scinput" style="height: 25px" onchange="showBhz();">
                        <option value="">==请选择标段==</option>
                        <c:forEach var="b" items="${tbBds}">
                            <option value="${b.bdCode}" ${b.bdCode==bd?'selected':''}>${b.bdName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    拌和站名称：
                </td>
                <td>
                    <select id="bhz" name="bhz" class="scinput" style="height: 25px">
                        <option value="">==请选择拌合站==</option>
                    </select>
                </td>
                <td>
                    <input type="button" class="btn1" onclick="seleParam();" value="查询"/>
                </td>
                <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                    <td>
                        <input type="button" class="btn1" onclick="saveParam();" value="批量修改"/>
                        &nbsp;<span id="spanMsg" style="color: red">${msg}</span>
                    </td>
                </c:if>
            </tr>
        </table>
    </form>

    <form id="updateForm" method="post">
        <table class="tablelist">
            <tr>
                <th>所属标段</th>
                <th>所属拌合站</th>
                <th>参数名</th>
                <th>参数值</th>
                <th>是否启用</th>
            </tr>
            <c:choose>
                <c:when test="${fn:length(tbParams)==0}">
                    <tr>
                        <td colspan="5" style="text-align: center;color: red; height: 25px;">暂无数据记录!</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="p" items="${tbParams}" varStatus="i">
                        <tr>
                            <td style="text-align: center">${bdMap[p.bdCode]}</td>
                            <td style="text-align: center">${bhzName}</td>
                            <td style="text-align: center">${p.pname}</td>
                            <td style="text-align: center">
                                <input type="hidden" name="pId${i.count}" value="${p.id}"/>
                                <input type="text" class="scinput" style="height: 25px" name="pVal${i.count}" value="${p.pvalue}"/>
                            </td>
                            <td style="text-align: center">
                                <input type="checkbox" name="pFlag${i.count}" value="${p.flag}" ${p.flag eq '1'?'checked':''}/>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </table>
    </form>
</div>
</body>
</html>