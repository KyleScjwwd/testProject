<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户权限设置</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/authorization.js"></script>
</head>

<body>
<div class="all">
	<div class="sj_list">
	<form id="saveAuthorization" action="authorizationAction!saveUserAuthorization.action" method="post" >
	<input type="hidden" name="username" value="${username}" />
		<table class="message">
			<tr class="onbg">
				<td style="text-align: left;" colspan="2">
					<b>&nbsp权限设置 - ${username}</b>
					<b style="color: red; margin-left: 5%">提示：权限设置后，该用户需要重新登录方可生效</b>
					<a href="#" class="tjia" onclick="authoriz.checkSave();" style="display: inline-block; float: right;">保存</a>
					<font style="color: red; margin-right: 70px; float: right;">${msg}</font>
				</td>
			</tr>
			<c:choose>
				<c:when test="${fn:length(list_level1)>0}">
					<c:forEach var="a" items="${list_level1}">
						<c:set var="isChecked" value="${'1'==authorityMap[a.funCode]?'checked':''}"/>
						<tr>
							<td style="width: 20px;"><a href="#" extend="extend" id="a_${a.id}" style="font-size: large;">-</a></td>
							<td style="text-align: left;">&nbsp${a.funName}&nbsp<input type="checkbox" name="${a.funCode}" value="1" id="cb_${a.id}" ${isChecked} /></td>
						</tr>
						<tr id="tr_${a.id}">
							<td colspan="2">
								<c:forEach var="aa" items="${list_level2}">
								<c:choose>
									<c:when test="${aa.parentCode==a.funCode}">
										<div style="float: left; border: 1px solid blue; margin:3px 3px 3px 3px; width: 260px;">
											<font style="color: red;">${aa.funName}：</font>
											操作<input type="radio" name="${aa.funCode}" value="2" ${isChecked!='checked'?'disabled':''} ${'2'==authorityMap[aa.funCode]?'checked':''} />&nbsp
											浏览<input type="radio" name="${aa.funCode}" value="1" ${isChecked!='checked'?'disabled':''} ${'1'==authorityMap[aa.funCode]?'checked':''} />&nbsp
											禁止<input type="radio" name="${aa.funCode}" value="0" ${isChecked!='checked'?'disabled':''} ${'0'==authorityMap[aa.funCode]?'checked':''} />
										</div>
									</c:when>
								</c:choose>
								</c:forEach>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr><td colspan="2">暂无权限项目</td></tr>
				</c:otherwise>
			</c:choose>     		
		</table>
		<c:choose>
			<c:when test="${fn:length(bdList)>0}">
			<div style="width: 100%; float: left; margin-left: 1px">
				<c:forEach var="b" items="${bdList}">
				<c:set var="bdCode" value="bd${b.bdCode}" />
				<c:set var="isChecked2" value="${'2'==authorityMap[bdCode] || '1'==authorityMap[bdCode] || '0'==authorityMap[bdCode]?'checked':''}"/>
					<table class="message" style="width: 270px; margin-top: -1px; margin-left: -1px">
						<tr>
							<td style="width: 20px;"><a href="#" extend="extend" id="a_${b.bdCode}" style="font-size: large;">-</a></td>
							<td style="text-align: left;">
								&nbsp${b.bdName}&nbsp
								操作<input type="radio" name="bd${b.bdCode}" value="2" id="ratio_${b.bdCode}" control="control" ${'2'==authorityMap[bdCode]?'checked':''} />&nbsp
								浏览<input type="radio" name="bd${b.bdCode}" value="1" id="ratio_${b.bdCode}" control="control" ${'1'==authorityMap[bdCode]?'checked':''} />&nbsp
								禁止<input type="radio" name="bd${b.bdCode}" value="0" id="ratio_${b.bdCode}" control="control" ${'0'==authorityMap[bdCode]?'checked':''} />
							</td>
						</tr>
						<c:set var="hasBHZ" value="0" />
						<c:forEach var="bhz" items="${bhzList}">
							<c:choose>
								<c:when test="${bhz.bdCode==b.bdCode}"><c:set var="hasBHZ" value="1" /></c:when>
							</c:choose>
						</c:forEach>
						<c:choose>
						<c:when test="${hasBHZ=='1'}">
							<tr id="tr_${b.bdCode}">
								<td colspan="2">
									<c:forEach var="bhz" items="${bhzList}">
									<c:set var="bhzCode" value="bhz${bhz.bhzCode}" />
									<c:choose>
										<c:when test="${bhz.bdCode==b.bdCode}">
											<div style="float: left; border: 1px solid blue; margin:3px 3px 3px 3px; width: 260px;">
												<font style="color: red;">${bhz.bhzName}：</font>
												操作<input type="radio" name="bhz${bhz.bhzCode}" value="2" ${isChecked2!='checked'?'disabled':''} ${'2'==authorityMap[bhzCode]?'checked':''} />
												浏览<input type="radio" name="bhz${bhz.bhzCode}" value="1" ${isChecked2!='checked'?'disabled':''} ${'1'==authorityMap[bhzCode]?'checked':''} />
												禁止<input type="radio" name="bhz${bhz.bhzCode}" value="0" ${isChecked2!='checked'?'disabled':''} ${'0'==authorityMap[bhzCode]?'checked':''} />
											</div>
										</c:when>
									</c:choose>		
									</c:forEach>
								</td>
							</tr>
						</c:when>
						</c:choose>
					</table>	
				</c:forEach>
			</div>					
			</c:when>
		</c:choose> 
	</form>
	</div>
</div>
</body>
</html>