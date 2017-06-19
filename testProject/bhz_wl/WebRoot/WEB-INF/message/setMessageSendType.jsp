<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/5
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <title></title>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/messageSendType.js"></script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">短信报警</a></li>
        <li><a href="#">报警方式</a></li>
    </ul>
</div>
<div class="rightinfo">
    <input type="hidden" id="hint" value="${hint}"/>

    <form id="SaveMessageSendTypeForm" action="messageAction!saveMessageSendType.action" method="post">
        <table class="tablelist">
            <tr>
                <td width="30%" align="right">短信发送方式：</td>
                <td width="70%">
                    <input type="radio" name="msgBaoJingType" value="1"
                           <c:if test="${msgBaoJingType eq '1'}">checked="checked"</c:if> />单盘误差报警
                    <input type="radio" name="msgBaoJingType" value="0"
                    <c:if test="${msgBaoJingType eq '0'}">checked="checked"</c:if> />车次误差报警
                </td>
            </tr>
            <tr>
                <td></td>
                <td>提示：短信内容中可以使用的变量：sm1(标段)、sm2(拌合站)、sm3(浇筑部位)、sm4(强度等级)、sm5(出料时间)、sm6(各材料用量及误差百分比)、sm7(方量)</td>
            </tr>
            <tr>
                <td align="right">单盘误差报警送短信内容：</td>
                <td>
                    <textarea rows="4" cols="60" name="msgTemplate1">${msgTemplate1}</textarea>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    提示：短信内容中可以使用的变量：
                    carm1(标段)、carm2(拌合站)、carm3(浇筑部位)、carm4(强度等级)、carm5(车次)、carm6(车牌号)、carm7(开始时间）、carm8(结束时间）、carm9(累积方量）、carmA(各材料用量及误差百分比）
                </td>
            </tr>
            <tr>
                <td align="right">车次误差报警送短信内容：</td>
                <td>
                    <textarea rows="4" cols="60" name="msgTemplate2">${msgTemplate2}</textarea>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="button" class="btn1" style="margin-left: 350px" onclick="messageSendType.checkSave();" value="保存"/>
                    <input type="button" class="btn1" onclick="javascript:history.back();" value="取消"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
