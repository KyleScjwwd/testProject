<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/5
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/BjBase.js"></script>
    <title></title>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">短信报警</a></li>
        <li><a href="#">修改报警基本信息</a></li>
    </ul>
</div>
<div class="rightinfo">
    <form id="UpdateBaoJingBaseForm" action="messageAction!updateBaoJingBase.action" method="post">
        <input type="hidden" name="id" value="${b.id}"/>
        <table class="tablelist">
            <tr>
                <td width="30%" align="right">材料名称：</td>
                <td width="70%">
                    <input type="text" name="clName" value="${b.clName}" class="scinput" style="height: 25px"/>
                </td>
            </tr>
            <tr>
                <td align="right">是否报警：</td>
                <td>
                    <input type="radio" name="isBaojing" value="1"
                           <c:if test="${b.isBaojing eq '1'}">checked="checked"</c:if> />报警
                    <input type="radio" name="isBaojing" value="0"
                           <c:if test="${b.isBaojing eq '0'}">checked="checked"</c:if> />不报警
                </td>
            </tr>
            <tr>
                <td class="FL" align="right">短信内容水泥值-C不在范围内（-C将替换为实际值）：</td>
                <td><textarea rows="4" cols="60" name="msgContent">${b.msgContent}</textarea></td>
            </tr>
            <tr>
                <td class="FL" align="right">发送级别-初级（项目部）：</td>
                <td>
                    <input type="text" name="downValueCj" value="${b.downValueCj}" class="scinput" style="height: 25px"/>下限
                    <input type="text" name="upValueCj" value="${b.upValueCj}" class="scinput" style="height: 25px"/>上限
                </td>
            </tr>
            <tr>
                <td class="FL" align="right">发送级别-中级（监理）：</td>
                <td>
                    <input type="text" name="downValueZj" value="${b.downValueZj}" class="scinput" style="height: 25px"/>下限
                    <input type="text" name="upValueZj" value="${b.upValueZj}" class="scinput" style="height: 25px"/>上限
                </td>
            </tr>
            <tr>
                <td class="FL" align="right">发送级别-高级（指挥部）：</td>
                <td>
                    <input type="text" name="downValueGj" value="${b.downValueGj}" class="scinput" style="height: 25px"/>下限
                    <input type="text" name="upValueGj" value="${b.upValueGj}" class="scinput" style="height: 25px"/>上限
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="button" class="btn1" style="margin-left: 350px" onclick="BjBase.CheckUpdate();" value="保存"/>
                    <input type="button" class="btn1" onclick="javascript:history.back();" value="取消"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
