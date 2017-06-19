<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/4
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="p" uri="/page-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <title></title>
    <script type="text/javascript">
        function seleParam() {
            var bdCode = $("#bdCode").val();
            var realName = $("#realName").val();
            var telphone = $("#telphone").val();
            $("#form1").attr("action", "messageAction!getMessageUser.action");
            $("#form1").submit();
        }
        function saveParam() {
            var bdCode = $("#bdCode").val();
            var realName = $("#realName").val();
            var telphone = $("#telphone").val();
            if (bdCode.length == 0) {
                alert("请选择标段!");
                return;
            }
            if (realName.length == 0) {
                alert("请输入联系人姓名!");
                return;
            }
            if (telphone.length == 0) {
                alert("请输入手机号码!");
                return;
            }
            $("#form1").attr("action", "messageAction!addMessageUser.action");
            $("#form1").submit();
        }
        function delParam(id) {
            if (confirm('你确定要删除该记录吗?')) {
                var bdCode = $("#bdCode").val();
                window.location.href = "messageAction!delMessageUser.action?id=" + id + "&bdCode=" + bdCode;
            }
            return false;
        }

        function loadPage(){


        }
    </script>
</head>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ3"/>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">短信报警</a></li>
        <li><a href="#">通讯录设置</a></li>
    </ul>
</div>
<div class="rightinfo">
    <form id="form1" method="post">
        <table class="tablelist">
            <tr>
                <td>标段名称：</td>
                <td>
                    <select name="bdCode" id="bdCode" class="scinput" style="height: 25px">
                        <option value="">==请选择标段==</option>
                        <c:forEach var="b" items="${bdList}">
                            <option <c:if test="${bCode eq b.bdCode}">selected="selected"</c:if> value="${b.bdCode}">${b.bdName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>联系人姓名：</td>
                <td>
                    <input type="text" class="scinput" id="realName" name="realName" value="${realName}" style="height: 25px"/>
                </td>
                <td>手机号码：</td>
                <td>
                    <input type="text" class="scinput" id="telphone" name="telphone" value="${telphone}" style="height: 25px"/>
                </td>
                <td>
                    <input name="" type="button" class="btn1" onclick="seleParam();" value="查询"/>
                </td>
                <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                    <td>
                        <input name="" type="button" class="btn1" onclick="saveParam();" value="添加"/>
                    </td>
                </c:if>
            </tr>
        </table>
    </form>

    <table class="tablelist" >
        <tr>
            <th>所属标段</th>
            <th>联系人姓名</th>
            <th>联系人电话</th>
            <c:set var="authPage" value="${sessionScope.username}-fun0301"/>
            <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
                <th>操作</th>
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
                                <input type="button" class="btn1" onclick="delParam(${m.id});" style="color:#000;" value="删除"/>
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
    <p:PagingTag url="messageAction!getMessageUser.action?bdCode=${bCode}&telphone=${telphone}" page="${objPage}" forPage="true" showCurAndSum="true"/>
</div>

</body>
</html>
