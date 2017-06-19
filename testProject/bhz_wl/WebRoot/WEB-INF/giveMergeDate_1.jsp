<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>合并列数据监控</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/givedata.js"></script>
<script type="text/javascript" src="js/util.js"></script>
</head>

<body>
<div class="all">
    <div class="scsj">
            <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">生产数据监控 </a></p>
    </div>
    <input type="hidden" id="bdCode" name="bdCode" value="${bdCode}"/>
    <input type="hidden" id="bhzCode" name="bhzCode" value="${bhzCode}"/>
    <form id="form1" action="mainAction!getGiveDate.action" method="post"> 
    <table class="Tab" border="0">
        <tr>
            <td class="FL" width="20%">标段名称：</td>
            <td>
               <select class="Fwidth" id="bd" name="bd" onchange="showBhz();">
                  <option value="">==请选择标段==</option>
                  <c:forEach var="b" items="${bdList}">
					<option value="${b.bdCode}" ${bdCode eq b.bdCode?'selected':''}>${b.bdName}</option>
				  </c:forEach>
              </select>
            </td>
            <td class="FL">拌合站名称：</td>
            <td colspan="2">
               <select class="Fwidth" id="bhz" name="bhz">
                  <option value="">==请选择拌合站==</option>
               </select>
            </td>   
        </tr>
        <tr>
            <td class="FL">浇筑部位：</td>
            <td><input type="text" name="m5" value="${m5}" class="Fwidth"/></td>
            <td class="FL">强度等级：</td>
            <td colspan="2">
            <select class="Fwidth" id="m8" name="m8">
                <option value="">==请选择强度等级==</option>
                <c:forEach var="q" items="${qdList}">
               	<option value="${q}" ${q eq m8?'selected':''}>${q}</option>
               	</c:forEach>
            </select>
            </td>   
        </tr>
        <tr>
            <td class="FL">出料时间：</td>
            <td>
            <input type="text" class="Fwidth" style="width:100px;" name="sdate" value="${sdate}" id="sdate" onfocus="WdatePicker();"/>至
          	<input type="text" class="Fwidth" style="width:100px;" name="edate" value="${edate}" id="edate" onfocus="WdatePicker();"/>
            </td>   
            <td class="FL">报警状态：</td>
            <td>
            <select class="Fwidth" id="bjType" name="bjType">
                <option value="">==请选择报警状态==</option>
                <c:forEach var="b" items="${bjTypeMap}">
                <option value="${b.key}" ${bjType eq b.key?'selected':''}>${b.value}</option>
                </c:forEach>
            </select>
            </td>
            <td><a href="javascript:;" class="search" onclick="forwordURL();">查询</a></td>   
        </tr>
    </table>
    </form>
        <div class="list">
            <a class="mouse">每盘生产数据</a>
            <a class="onmouse" href="mainAction!countTotalWc.action?bd=${bdCode}&bhz=${bhzCode}">累积误差</a>
            <a class="onmouse" href="countAction!getBhTime.action?bd=${bdCode}&bhz=${bhzCode}&isCurrent=${isCurrent}">拌合时间</a>
        </div>
        <c:choose>
			<c:when test="${fn:length(mergeList) eq 0}">
				<table class="list_tab">
					<tr class="onbg">
						<td height="30" style="text-align: center;color: red;">
							该标段下的拌合站无查询项,请先至合并列设置!
						</td>
					</tr>
				</table>
			</c:when>
			<c:otherwise>
			<table class="list_tab" >
            <tr class="onbg">
                <td style="width:75px;">拌合站名称</td>
                <td style="width:90px;">浇筑部位</td>
                <td style="width:55px;">强度等级</td>
                <td style="width:80px;">出料时间</td>
                <td style="width:40px;">方量</td>
                <td style="width:55px;">报警状态</td>
                <td style="width:75px;">配合比类型</td>
                <c:forEach var="m" varStatus="s" items="${mergeList}">
    				<td>${m.mergeColsName}</td>
    				<c:if test="${m.mergeColsName eq '水'}"><c:set var="waterIndex" value="${s.index}"/></c:if>
    				<c:if test="${m.mergeColsName eq '水泥'}"><c:set var="cementIndex" value="${s.index}"/></c:if>
    				<c:if test="${m.mergeColsName eq '外掺材料'}"><c:set var="wcclIndex" value="${s.index}"/></c:if>
   				</c:forEach>
   				<c:if test="${waterIndex ne null && cementIndex ne null}">
   					<td>水胶比</td>
   				</c:if>
            </tr>
            <c:choose>
  				<c:when test="${fn:length(objPage.baseObj) eq 0}">
  				<tr><td colspan="25" style="color: red;text-align: center;">暂无数据记录!</td></tr>
  				</c:when>
  				<c:otherwise>
  				<c:forEach var="m" items="${objPage.baseObj}" varStatus="i">
  					<!-- 设计 -->
  					<tr ${i.index%2==0?'bgcolor="#F0F0F0"':''}>
						<td rowspan="4" style="width:75px;">${tbBhz.bhzName}</td>
						<td rowspan="4" style="width:90px;">${m.value[0][5]}</td>
						<td rowspan="4" style="width:55px;">${m.value[0][8]}</td>
						<td rowspan="4" style="width:80px;"><fmt:formatDate value="${m.value[0][4]}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td rowspan="4" style="width:40px;">${m.value[0][10]}</td>
						<td rowspan="4" style="width:55px;">
						<c:set var="bjState" value="${m.value[0][11]}"/>
						${bjTypeMap[bjState]}
						</td>
						<td style="width:75px;">设计<c:set var="sheji"/></td>
						<c:forEach var="r" items="${mergeList}">
							<td>
								<c:set var="sjKey" value="${m.value[0][2]}-${m.value[0][6]}-${m.value[0][8]}-${r.mergeCols}"/>
								${phbMap[sjKey].ext1}
							</td>
		    				<c:if test="${r.mergeColsName eq '水'}"><c:set var="Water" value="${phbMap[sjKey].ext1}"/></c:if>
		    				<c:if test="${r.mergeColsName eq '水泥'}"><c:set var="Cement" value="${phbMap[sjKey].ext1}"/></c:if>	
		    				<c:if test="${r.mergeColsName eq '外掺材料'}"><c:set var="WCCL" value="${phbMap[sjKey].ext1}"/></c:if>
						</c:forEach>
						<c:if test="${waterIndex ne null && cementIndex ne null}">
							<c:choose>
								<c:when test="${WCCL ne null && (Cement + WCCL) ne 0}">
									<td><fmt:formatNumber value="${Water/(Cement+WCCL)}" pattern="#.##"/></td>
								</c:when>
								<c:otherwise>
									<c:if test="${Cement ne 0}">
										<td><fmt:formatNumber value="${Water/Cement}" pattern="#.##"/></td>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:if>
					</tr>
					<!-- 施工 -->
					<tr ${i.index%2==0?'bgcolor="#F0F0F0"':''}>
						<td style="width:75px;">施工<c:set var="shigong"/></td>
						<c:forEach var="p" begin="12" end="${fn:length(mergeList)+11}">
							<td><fmt:formatNumber value="${m.value[2][p]}" pattern="#.##"/></td>
						</c:forEach>
						<c:if test="${waterIndex ne null && cementIndex ne null}">
							<c:choose>
								<c:when test="${wcclIndex ne null && (m.value[2][12+cementIndex] + m.value[2][12+wcclIndex]) ne 0}">
									<c:set var="sjb_sg" value="${m.value[2][12+waterIndex] / (m.value[2][12+cementIndex] + m.value[2][12+wcclIndex])}"/>
									<td><fmt:formatNumber value="${sjb_sg}" pattern="#.##"/></td>
								</c:when>
								<c:otherwise>
									<c:if test="${m.value[2][12+cementIndex] ne 0}">
										<c:set var="sjb_sg" value="${m.value[2][12+waterIndex] / m.value[2][12+cementIndex]}"/>
										<td><fmt:formatNumber value="${sjb_sg}" pattern="#.##"/></td>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:if>
					</tr>
					<!-- 实际 -->
					<tr ${i.index%2==0?'bgcolor="#F0F0F0"':''}>
						<td style="width:75px;">实际<c:set var="shiji"/></td>
						<c:forEach var="p" begin="12" end="${fn:length(mergeList)+11}">
							<td>
								<c:set var="shejiResult" value="0"/>
								<!-- 
								<c:set var="shejiValue" value="${phbMap[shejiKey][p.pname]}"/>
								 -->
								<c:set var="fanglValue" value="${m.value[1][10]}"/>
								<c:if test="${shejiValue ne null && fanglValue ne null}">
								<c:set var="shejiResult" value="${shejiValue*fanglValue}"/>
								</c:if>
								<c:choose>
								<c:when test="${m.value[1][p] ne null}">
									<c:set var="shijiResult" value="${m.value[1][p]}"/>
									<c:choose>
									<c:when test="${shijiResult<shejiResult}">
									<font color="red"><fmt:formatNumber value="${shijiResult}" pattern="#.##"/></font>
									</c:when>
									<c:otherwise>
									<fmt:formatNumber value="${shijiResult}" pattern="#.##"/>
									</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
								<fmt:formatNumber value="${m.value[1][p]}" pattern="#.##"/>
								</c:otherwise>
								</c:choose>		
							</td>
						</c:forEach>
						<c:if test="${waterIndex ne null && cementIndex ne null}">
							<c:choose>
								<c:when test="${wcclIndex ne null && (m.value[1][12+cementIndex] + m.value[1][12+wcclIndex]) ne 0}">
									<c:set var="sjb_sj" value="${m.value[1][12+waterIndex] / (m.value[1][12+cementIndex] + m.value[1][12+wcclIndex])}"/>
									<td><fmt:formatNumber value="${sjb_sj}" pattern="#.##"/></td>
								</c:when>
								<c:otherwise>
									<c:choose>
									<c:when test="${m.value[1][12+cementIndex] ne 0}">
										<c:set var="sjb_sj" value="${m.value[1][12+waterIndex] / m.value[1][12+cementIndex]}"/>
										<td><fmt:formatNumber value="${sjb_sj}" pattern="#.##"/></td>
									</c:when>
									<c:otherwise>
										<td>&nbsp;</td>
									</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:if>
					</tr>
					<!-- 误差 -->
					<tr ${i.index%2==0?'bgcolor="#F0F0F0"':''}>
						<td style="width:75px;">误差</td>
						<c:forEach var="p" begin="12" end="${fn:length(mergeList)+11}">
							<td>
								<c:set var="result" value="${m.value[1][p]-m.value[2][p]}" />
								<c:if test="${result!=0}">
								<c:set var="mkey" value="${bdCode}-${bhzCode}-${p}"/>
								<fmt:formatNumber value="${result}" pattern="#.##"/>
								</c:if>
							</td>
						</c:forEach>
						<c:if test="${waterIndex ne null && cementIndex ne null}">
							<td>
								<fmt:formatNumber var="a" value="${sjb_sg}" pattern="#.##"/>
								<fmt:formatNumber var="b" value="${sjb_sj}" pattern="#.##"/>
								<fmt:formatNumber value="${a-b}" pattern="#.##"/>
							</td>
						</c:if>
					</tr>
  				</c:forEach>
  				</c:otherwise>
  		   	</c:choose>
        	</table>
	        <div class="clear"></div>
	        <p:PagingTag url="mainAction!getGiveDate.action?bd=${bdCode}&bhz=${bhzCode}&m3=${m3}&m5=${m5}&m7=${m7}&m8=${m8}&bjType=${bjType}&sdate=${sdate}&edate=${edate}&subType=1" page="${objPage}" forPage="true" showCurAndSum="true"/>
		</c:otherwise>
		</c:choose>
</div>
</body>
</html>