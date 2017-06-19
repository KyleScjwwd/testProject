<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加数据过滤条件</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/filterData.js"></script>
</head>

<body style="overflow: hidden;">
<div class="all">
    <div class="scsj">
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">系统管理</a></p>
    </div>
    <table class="Tab">
        <tr>
            <td>
            	标段:
            	<select name="bdCode" class="Fwidth" style="width:120px;" onclick="getBhzInfo();">
                  <option value="">==请选择标段==</option>
                  <c:forEach var="b" items="${bdList}">
				  	<option value="${b.bdCode}" ${b.bdCode==bdCode?'selected':''}>${b.bdName}</option>
			 	  </c:forEach>
                </select>
                               拌合站:
                <select name="bhzCode" class="Fwidth" style="width:120px;" onclick="getBaseInfo();">
                  	<option value="">==请选择拌合站==</option>
                </select>
                 浇筑部位:
               <input type="text" class="Fwidth" style="width:140px;" name="m5"/>
               <!-- 
               <select name="m5" class="Fwidth" style="width:140px;">
                  	<option value="">==请选择浇筑部位==</option>
               </select>
                -->
                 出料时间:
               <input type="text" class="Fwidth" style="width:140px;" name="m4" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
               <!-- 
               <select name="m4" class="Fwidth" style="width:140px;">
                  	<option value="">==请选择出料时间==</option>
               </select>
               -->
            </td>
        </tr>
    </table>
    <div style="text-align:center;margin-top:20px;">
    <input style="width:45px;height:25px;" type="button" value="确定" onclick="saveFilterData();"/>
    <input style="width:45px;height:25px;" type="button" value="取消" onclick="closeDiv();"/>
    </div>
</div>
</body>
</html>