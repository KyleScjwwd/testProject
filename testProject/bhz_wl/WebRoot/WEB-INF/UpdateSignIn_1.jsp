<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改生产状态</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<link rel="stylesheet" type="text/css" href="css/LAYER/ymPrompt.css"/>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/User.js"></script>
<script type="text/javascript" src="js/SignIn.js"></script>
<script type="text/javascript" src="js/util.js"></script>
</head>
<body>
<div class="all">
    <div class="scsj">
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">系统管理</a>&gt;&gt;<a href="javascript:;">修改生产状态</a></p>
    </div>
    <%@ include file="base/sysLinked.jsp" %>
    <input type="hidden" id="hidBd" value="${si.bdCode}"/>
    <input type="hidden" id="hidBhz" value="${si.bhzCode}"/>
    <form name="UpdateSignInForm" id="UpdateSignInForm" action="signInAction!updateSignIn.action" method="post">
    <input type="hidden" name="id" value="${si.id}"/>
    <table class="bjdiv">
    	<tr>
        	<td width="30%" align="right">所属标段：</td>
            <td width="70%">
            <select name="bdCode" class="Fwidth">
					<option value="">==请选择标段==</option>
					<c:forEach var="b" items="${BDs}">
						<option value="${b.bdCode}" ${b.bdCode==si.bdCode?'selected':''}>${b.bdName}</option>
					</c:forEach>
			</select>
            </td>
            </tr>
            <tr>
                <td align="right">所属拌合站：</td>
                <td>
                <select name="bhzCode" class="Fwidth">
					<option value="">==请选择拌合站==</option>
				</select>
                </td>
      	   </tr>
            <tr>
                <td class="FL" align="right">生产日期：</td>
                <td><input type="text" name="signInDate" value='<fmt:formatDate value="${si.signInDate}" pattern="yyyy-MM-dd"/>'  class="Fwidth" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"/></td>
           </tr>
           <tr>
                <td class="FL" align="right">签到人：</td>
                <td><input type="text" name="signInUser" value="${si.signInUser}" class="Fwidth"/></td>
           </tr>
            <tr>
                <td colspan="2" align="center">
                    <a href="javascript:;" class="bia" style="margin-left: 350px" onclick="si.CheckUpdate();">修改</a>
                    <a href="javascript:history.back();" class="bia">取消</a>
                </td>
            </tr>
    </table>
    </form>
</div>
</body>
</html>