<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/5
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="/page-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript">
        function setbaseData() {
            var bdCode = $("input[name='bdCode']").val();
            var bhzCode = $("input[name='bhzCode']").val();
            var maxCjStartTime = $("input[name='maxCjStartTime']").val();
            var custCjStartTime = $("input[name='custCjStartTime']").val();
            /*var req = /^[0-9]*[1-9][0-9]*$/;
            if (req.test(custCode) == false) {
                alert("自定义编号只能是整数!");
                return;
            }*/
            if(custCjStartTime==''){
                alert("自定义时间不能为空!");
                return;
            }

            if (confirm("您确认要设置吗？")) {
                $.ajax({
                    type: "post",
                    url: "BHZAction!saveSetDataInfo.action",
                    data: {bdCode: bdCode, bhzCode: bhzCode, maxCjStartTime: maxCjStartTime, custCjStartTime: custCjStartTime},
                    timeout: 10000,
                    success: function (data) {
                        if (data == "success") {
                            alert("设置成功!");
                            closeDiv();
                        } else {
                            alert("设置失败!");
                            closeDiv();
                        }
                    }
                });
            }
            return;
        }
        function getcldate() {
            var bdCode = $("input[name='bdCode']").val();
            var bhzCode = $("input[name='bhzCode']").val();
            var selCode = $("input[name='selCode']").val();
            if (selCode == "") {
                alert("请输入需要查询的编号!");
                return;
            }
            $.ajax({
                type: "post",
                url: "BHZAction!getclDate.action",
                data: {bdCode: bdCode, bhzCode: bhzCode, selCode: selCode},
                timeout: 10000,
                success: function (data) {
                    $("#cldatemsg").html(data);
                }
            });
        }
        function closeDiv() {
            parent.ymPrompt.close();
        }
    </script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">系统管理</a></li>
        <li><a href="#"><font color="red">${bdObj.bdName}</font>标<font color="red">${bhzObj.bhzName}</font>拌合站</a></li>
    </ul>
</div>
<div class="rightinfo">
    <table class="tablelist" style="width: 500px">
        <tr>
            <td>
                <input type="hidden" name="bdCode" value="${bdCode}"/>
                <input type="hidden" name="bhzCode" value="${bhzCode}"/>
                最后采集时间:
                <input type="text" name="maxCjStartTime" value="${lastCollTime}" class="scinput" style="height: 25px;" readonly="readonly"/>
                设置时间:
                <input type="text" name="custCjStartTime" value="${cjStartTime}" class="scinput" style="height: 25px;"/>
            </td>
        </tr>
        <tr>
            <td style="text-align: center">
                <input class="btn1"  type="button" value="确定" onclick="setbaseData();"/>
                <input class="btn1"  type="button" value="取消" onclick="closeDiv();"/>
            </td>
        </tr>
    </table>
    <%--<div style="margin-top:50px;">
        <font color="gray">出料时间查询</font>&nbsp;&nbsp;&nbsp;&nbsp;<font id="cldatemsg" color="red"></font>
        <div style="margin-top:10px;">
            输入编号：<input type="text" name="selCode" class="scinput" style="height: 25px;"/>
            <input  class="btn1"  type="button" value="查询" onclick="getcldate();"/>
        </div>
    </div>--%>
</div>
</body>
</html>
