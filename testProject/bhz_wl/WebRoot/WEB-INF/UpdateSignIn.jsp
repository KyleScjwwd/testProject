<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>修改生产状态</title>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="css/LAYER/ymPrompt.css"/>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="js/common.js"></script>
    <script type="text/javascript" src="js/User.js"></script>
    <script type="text/javascript" src="js/SignIn.js"></script>
    <script type="text/javascript" src="js/util.js"></script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">系统管理</a></li>
        <li><a href="#">修改生产状态</a></li>
    </ul>
</div>
<div class="rightinfo">
    <input type="hidden" id="hidBd" value="${si.bdCode}"/>
    <input type="hidden" id="hidBhz" value="${si.bhzCode}"/>

    <form name="UpdateSignInForm" id="UpdateSignInForm" action="signInAction!updateSignIn.action" method="post">
        <input type="hidden" name="id" value="${si.id}"/>
        <table class="tablelist">
            <tr>
                <td width="30%" align="right">所属标段：</td>
                <td width="70%">
                    <select name="bdCode" class="scinput" style="height: 25px">
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
                    <select name="bhzCode" class="scinput" style="height: 25px">
                        <option value="">==请选择拌合站==</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="FL" align="right">生产日期：</td>
                <td>
                    <input type="text" name="signInDate"
                           value='<fmt:formatDate value="${si.signInDate}" pattern="yyyy-MM-dd"/>' class="scinput" style="height: 25px"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
                </td>
            </tr>
            <tr>
                <td class="FL" align="right">签到人：</td>
                <td><input type="text" name="signInUser" value="${si.signInUser}" class="scinput" style="height: 25px"/></td>
            </tr>
            <tr>
                <td colspan="2" align="center">

                    <input type="button" class="btn1" style="margin-left: 350px" onclick="si.CheckUpdate();" value="修改"/>
                    <input type="button" class="btn1" onclick="javascript:history.back()" value="取消"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>