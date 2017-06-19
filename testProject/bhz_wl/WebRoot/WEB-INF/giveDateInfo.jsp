<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset==UTF-8" />
<link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/table.css" rel="stylesheet" type="text/css" />
<script src="js/jquery/jquery-1.6.3.min.js" type="text/javascript"></script>
<script src="js/jquery/jquery.form.js" type="text/javascript"></script>
<title>拌合站监控信息系统-生产数据监控-详细信息</title>
<script type="text/javascript">
function updateGiveData(){
	if (confirm("确定修改该数据信息？")){
		var options = { 
	        url:'mainAction!updateGiveData.action', //提交给哪个执行
	        type:'post', 
	        success: function(data){
	        	if(data=="success"){
	        		alert("保存成功!");
	        	}else{
	        		alert("保存失败!");
	        	}
	        }
	    };
	    $('#dataForm').ajaxSubmit(options); 
	}
    return false;
}
</script>
</head>

<body>
<div style="font-size: 13px;margin-left: 6px;margin-top: 4px">拌合站监控系统&nbsp;&gt;&gt;&nbsp;生产数据监控&nbsp;&gt;&gt;&nbsp;详细信息</div>
<form id="dataForm">
<div id="advancedDIV" style="margin-top:3px;margin-left: 6px;width:97%">
<table width="100%" border="0">
  <tr>
    <td align="center">配合比编号:</td>
    <td>
    <input type="hidden" name="lilunId" value="${tbBase.id}"/>
    <input type="hidden" name="groupId" value="${tbBase.groupId}"/>
    <input type="text" name="m7" value="${tbBase.m7}"/>
    </td>
    <td align="center">浇筑部位:</td>
    <td><input type="text" name="m5" value="${tbBase.m5}"/></td>
  </tr>
</table>
</div>
<div id="UpdatePanel1" style="margin-top: 5px;">
<table width="99%" border="0" class="table_02" style="margin-left: 6px;">
  <tr>
    <td rowspan="2" class="title_bg" style="text-align: center" width="18%">设计配合比</td>
    <c:forEach var="p" items="${paramList}">
    <td class="title_bg" style="text-align: center">${p.pvalue}</td>
    </c:forEach>
    <td class="title_bg" style="text-align: center" width="5%">操作</td>
  </tr>
  <tr>
  	<c:forEach var="p" items="${paramList}">
    <td style="text-align: center">
    <input type="hidden" name="fieldName" value="${p.pname}"/>
    <input type="text" style="width:50px;" name="${p.pname}" value="${tbBase[p.pname]}"/>
    </td>
    </c:forEach>
    <td><a class="button quickbtn" style="text-align:center" href="javascript:void(0);" onclick="updateGiveData();">修改&nbsp;&nbsp;&nbsp;</a></td>
  </tr>
</table>
</div>
</form>
<div id="UpdatePanel1" style="margin-top: 5px;">
<table width="99%" border="0" class="tablesorter" style="margin-left: 6px;">
  <tr>
    <td height="400"><img src="${pageContext.request.contextPath}/jfreeChartFile/shijiPic.jpg" border="0" /></td>
    <td><img src="${pageContext.request.contextPath}/jfreeChartFile/shigongPic.jpg" border="0" /></td>
  </tr>
</table>
</div>
</body>
</html>
