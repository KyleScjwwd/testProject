<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>拌合站修改</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/BHZ.js"></script>
<script type="text/javascript" src="js/util.js"></script>
</head>
<body>
<div class="all">
    <div class="scsj">
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">系统管理</a>&gt;&gt;<a href="javascript:;">编辑拌合站</a></p>
    </div>
    <%@ include file="base/sysLinked.jsp" %>
    <form id="AddBHZForm" action="BHZAction!updateBHZ.action" method="post" >
    <input type="hidden" name="id" value="${bhz.id}"/>
    <table class="bjdiv">
    	<tr>
        	<td width="30%" align="right">所属标段：</td>
            <td width="70%">
            <select name="bdCode" class="Fwidth" disabled="disabled">
					<option value="null">==请选择标段==</option>
					<c:forEach var="b" items="${BDs}">
						<option value="${b.bdCode}" ${b.bdCode==bhz.bdCode?'selected':''}>${b.bdName}</option>
					</c:forEach>
			</select>
            </td>
            </tr>
            <tr>
                <td align="right">拌合站编号：</td>
                <td><input type="text" name="bhzCode" value="${bhz.bhzCode}" class="Fwidth" readonly="readonly"/></td>
      	   </tr>
            <tr>
                <td align="right">拌合站名称：</td>
                <td><input type="text" name="bhzName" value="${bhz.bhzName}" class="Fwidth"/></td>
      	   </tr>
            <tr>
                <td class="FL" align="right">上限：</td>
                <td><input type="text" name="upNum" value="${bhz.upNum}" class="Fwidth"/></td>
           </tr>
           <tr>
                <td class="FL" align="right">下限：</td>
                <td><input type="text" name="downNum" value="${bhz.downNum}" class="Fwidth"/></td>
           </tr>
           <tr>
                <td class="FL" align="right">拌合站密码：</td>
                <td><input type="text" name="ext1" value="${bhz.ext1}" class="Fwidth"/></td>
           </tr>
            <tr>
                <td colspan="2" align="center">
                    <a href="javascript:;" class="bia" style="margin-left: 350px" onclick="bdz.CheckAdd();">修改</a>
                    <a href="javascript:history.back();" class="bia">取消</a>
                </td>
            </tr>
    </table>
    </form>
</div>
</body>
</html>