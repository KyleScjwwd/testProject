<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加报警基本信息</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/BjBase.js"></script>
</head>
<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ3"/>
<div class="all">
    <div class="scsj">
    	<p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">短信报警</a>&gt;&gt;<a href="javascript:;">添加报警基本信息</a></p>
    </div>
    <div class="list">
        	<a class="onmouse" href="messageAction!getMessageUser.action">通迅录设置</a>
        	<a class="onmouse" href="messageAction!setMessage.action">短信报警设置</a>
        	<a class="onmouse" href="messageAction!setBaoJingBase.action">报警基本设置</a>
        	<a class="onmouse" href="messageAction!setBaoJingPersonBase.action">报警人员基本设置</a>
         	<a class="onmouse" href="messageAction!setMessageSendType.action">短信发送方式设置</a>
        	<a class="onmouse" href="messageAction!msgHistory.action">发送记录查询</a>
    </div>
    <form id="AddBaoJingBaseForm" action="messageAction!addBaoJingBase.action" method="post">
    <table class="bjdiv">
    	<tr>
        	<td width="30%" align="right">材料名称：</td>
            <td width="70%">
            	<input type="text" name="clName" value="${b.clName}" class="Fwidth"/>
            </td>
        </tr>
        <tr>
        	<td align="right">是否报警：</td>
            <td>
            	<input type="radio" name="isBaojing" value="1" checked="checked" />报警
            	<input type="radio" name="isBaojing" value="0" />不报警
            </td>
      	</tr>
        <tr>
            <td class="FL" align="right">短信内容水泥值-C不在范围内（-C将替换为实际值）：</td>
            <td><textarea rows="4" cols="60" name="msgContent">${b.msgContent}</textarea></td>
        </tr>
        <tr>
            <td class="FL" align="right">发送级别-初级（项目部）：</td>
            <td>
            	<input type="text" name="downValueCj" value="${b.downValueCj}" class="Fwidth" style="width: 90px;"/>下限
            	<input type="text" name="upValueCj" value="${b.upValueCj}" class="Fwidth" style="width: 90px;"/>上限
            </td>
        </tr>
        <tr>
            <td class="FL" align="right">发送级别-中级（监理）：</td>
            <td>
            	<input type="text" name="downValueZj" value="${b.downValueZj}" class="Fwidth" style="width: 90px;"/>下限
            	<input type="text" name="upValueZj" value="${b.upValueZj}" class="Fwidth" style="width: 90px;"/>上限
            </td>
        </tr>
        <tr>
            <td class="FL" align="right">发送级别-高级（指挥部）：</td>
            <td>
            	<input type="text" name="downValueGj" value="${b.downValueGj}" class="Fwidth" style="width: 90px;"/>下限
            	<input type="text" name="upValueGj" value="${b.upValueGj}" class="Fwidth" style="width: 90px;"/>上限
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
            	<a href="javascript:;" class="bia" style="margin-left: 350px" onclick="BjBase.CheckAdd();">添加</a>
            	<a href="javascript:history.back();" class="bia">取消</a>
            </td>
       </tr>
    </table>
    </form>
</div>
</body>
</html>