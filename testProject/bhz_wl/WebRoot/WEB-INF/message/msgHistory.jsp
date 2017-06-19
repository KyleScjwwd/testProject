<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/5
  Time: 13:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="/page-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#txtStarDate").calendar();
            $("#txtEndDate").calendar();
            var msgInfo = $("#msgInfo").val();
            if (msgInfo != null && msgInfo != "") {
                alert(msgInfo);
            }
        });
        function seleParam() {
            $("#form1").submit();
        }
        function sendMsg() {
            var mobile = $("input[name='mobile']").val();
            var msgContent = $("input[name='msgContent']").val();
            var re = /^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/;
            if (!re.test(mobile)) {
                alert("请输入正确的手机号码!");
                return false;
            }
            if (msgContent == "") {
                alert("请输入发送短信内容!");
                return false;
            }
            if (confirm('你确定要发送该内容吗?')) {
                $("#form1").attr("action", "messageAction!sendMessage.action");
                $("#form1").submit();
                return true;
            }
            return false;
        }
    </script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:;">拌合站监控系统</a></li>
        <li><a href="javascript:;">短信报警</a></li>
        <li><a href="javascript:;">报警记录</a></li>
    </ul>
</div>
<c:if test="${sessionScope.username eq 'admin'}">
<div class="rightinfo">当前所剩短信条数：<font color="red">${balanceNum}</font>&nbsp;条</div>
</c:if>
<div class="rightinfo">
    <input type="hidden" id="msgInfo" value="${msgInfo}"/>
    <form id="form1" action="messageAction!msgHistory.action" method="post">
        <table class="tablelist">
            <tr>
                <td style="text-align: center">标段名称：</td>
                <td>
                    <select name="bdCode" id="bdCode" class="scinput" style="height: 25px">
                        <option value="">==请选择标段==</option>
                        <c:forEach var="b" items="${bdList}">
                            <option <c:if test="${b.bdCode eq sCD}">selected="selected"</c:if> value="${b.bdCode}">${b.bdName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td style="text-align: center">发送内容：</td>
                <td>
                    <input type="text" class="scinput" style="height: 25px" name="msgContent" value="${msgContent}"/>
                </td>
                <td rowspan="2">
                    <input type="button" class="btn1" onclick="seleParam();" value="查询"/>
                    <c:if test="${sessionScope.username eq 'td77011'}">
                        &nbsp;&nbsp;&nbsp;
                        <input type="button" class="btn1" onclick="sendMsg();" value="模拟发送"/>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td style="text-align: center">发送号码：</td>
                <td>
                    <input type="text" class="scinput" style="height: 25px" name="mobile" value="${mobile}"/>
                </td>
                <td style="text-align: center">发送时间：</td>
                <td>
                    <input name="sdate" value="${sdate}" id="txtStartDate" type="text" onfocus="WdatePicker()" class="scinput" style="height: 25px"/>
                    至
                    <input name="edate" value="${edate}" type="text" id="txtEndDate" onfocus="WdatePicker()" class="scinput" style="height: 25px"/>
                </td>

            </tr>
        </table>
    </form>

    <table class="tablelist" >
        <tr>
            <th width="5%">所属标段</th>
            <th width="20%">发送号码</th>
            <th width="50%">发送内容</th>
            <th width="5%">发送时间</th>
            <th width="5%">发送状态</th>
        </tr>
        <c:choose>
            <c:when test="${fn:length(objPage.objList)>0}">
                <c:forEach var="l" items="${objPage.objList}">
                    <tr>
                        <td>${map[l.bdCode]}</td>
                        <td style="word-break:break-all">
                        <c:forEach var="m" items="${fn:split(l.mobile,',')}">
                        ${m}(${realNameMap[m]})
                        </c:forEach>
                        </td>
                        <td style="word-break:break-all">${l.msgContent}</td>
                        <td><fmt:formatDate value="${l.sendDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${l.sendFlag eq '1'}">成功</c:when>
                                <c:otherwise>失败</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr><td height="25" colspan="5" style="text-align: center;color: red;">暂无数据记录!</td></tr>
            </c:otherwise>
        </c:choose>
    </table>
    <div class="clear"></div>
    <p:PagingTag url="messageAction!msgHistory.action?bdCode=${bdCode}&msgContent=${msgContent}&mobile=${mobile}&sdate=${sdate}&edate=${edate}" page="${objPage}" forPage="true" showCurAndSum="true"/>
</div>
</body>
</html>
