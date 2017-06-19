<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="/page-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>数据监控</title>
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
        <table class="Tab">
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
                <td>
                    <select class="Fwidth" id="bhz" name="bhz">
                        <option value="">==请选择拌合站==</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="FL">浇筑部位：</td>
                <td><input type="text" name="m5" value="${m5}" class="Fwidth"/></td>
                <td class="FL">强度等级：</td>
                <td>
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
                    <input type="text" class="Fwidth" style="width:100px;" name="sdate" value="${sdate}" id="sdate"
                           onfocus="WdatePicker();"/>至
                    <input type="text" class="Fwidth" style="width:100px;" name="edate" value="${edate}" id="edate"
                           onfocus="WdatePicker();"/>
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
        <c:when test="${fn:length(paramList) eq 0}">
            <table class="list_tab">
                <tr class="onbg">
                    <td height="30" style="text-align: center;color: red;">
                        <c:choose>
                            <c:when test="${nodata eq '1'}">该标段下的拌合站无查询项,请先至系统参数页进行设置!</c:when>
                            <c:otherwise>请选择需要查询的标段和拌合站!</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
            <table class="list_tab">
                <tr class="onbg">
                    <td style="width:75px;">拌合站名称</td>
                    <td style="width:90px;">浇筑部位</td>
                    <td style="width:55px;">强度等级</td>
                    <td style="width:80px;">出料时间</td>
                    <td style="width:40px;">方量</td>
                    <td style="width:55px;">报警状态</td>
                    <td style="width:75px;">配合比类型</td>
                    <c:forEach var="p" items="${paramList}">
                        <td>${p.pvalue}</td>
                    </c:forEach>
                    <!--
                 <td>详细</td>
                  -->
                </tr>
                <c:choose>
                    <c:when test="${fn:length(objPage.baseMap) eq 0}">
                        <tr>
                            <td colspan="25" style="color: red;text-align: center;">暂无数据记录!</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="m" items="${objPage.baseMap}" varStatus="i">
                            <c:set var="shejiKey" value="${m.value[0].m2}-${m.value[0].m6}-${m.value[0].m8}"/>
                            <tr ${i.index%2==0?'bgcolor="#F0F0F0"':''}>
                                <td rowspan="4" style="width:75px;">${tbBhz.bhzName}</td>
                                <td rowspan="4" style="width:90px;">${m.value[0].m5}</td>
                                <td rowspan="4" style="width:55px;">${m.value[0].m8}</td>
                                <td rowspan="4" style="width:80px;"><fmt:formatDate value="${m.value[0].m4}"
                                                                                    pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td rowspan="4" style="width:40px;">${m.value[0].m10}</td>
                                <td rowspan="4" style="width:55px;">
                                    <c:set var="bjState" value="${m.value[0].bjType}"/>
                                        ${bjTypeMap[bjState]}
                                </td>
                                <td style="width:75px;">设计<c:set var="sheji"/></td>
                                <c:forEach var="p" items="${paramList}">
                                    <td>
                                        <c:set var="shejiValue" value="${phbMap[shejiKey][p.pname]}"/>
                                        <c:set var="fanglValue" value="${m.value[0].m10}"/>
                                        <c:set var="resLen" value=""/>
                                        <c:set var="patt" value="0"/>
                                        <c:if test="${shejiValue ne null && fanglValue ne null && shejiValue ne '' && fanglValue ne ''}">
                                            <!-- 根据后台参数判断保留小数位数 -->
                                            <c:set var="len" value="${fn:length(shejiValue)}"/>
                                            <c:set var="bs" value="${fn:indexOf(shejiValue,'.')}"/>
                                            <c:if test="${bs>0 && len ne null}">
                                                <c:set var="te" value="${fn:substring(shejiValue, bs+1, len)}"/>
                                                <c:forEach var="num" begin="1" end="${fn:length(te)}">
                                                    <c:set var="resLen" value="${resLen}0"/>
                                                </c:forEach>
                                            </c:if>
                                            <c:if test="${fn:length(resLen)>0}">
                                                <c:set var="patt" value="0.${resLen}"/>
                                            </c:if>
                                            <fmt:formatNumber value="${shejiValue*fanglValue}" pattern="${patt}"/>
                                        </c:if>
                                    </td>
                                </c:forEach>
                                <!--
						<td rowspan="4" style="text-align: center"><a href="javascript:;" style="color:#2d8edf;" onclick="openwin('${m.value[0].m2}','${m.value[0].m6}','${m.value[0].groupId}');">查看</a></td>
						 -->
                            </tr>
                            <tr ${i.index%2==0?'bgcolor="#F0F0F0"':''}>
                                <td style="width:75px;">施工<c:set var="shigong"/></td>
                                <c:forEach var="p" items="${paramList}">
                                    <td><fmt:formatNumber value="${m.value[2][p.pname]}" pattern="#.##"/></td>
                                </c:forEach>
                            </tr>
                            <tr ${i.index%2==0?'bgcolor="#F0F0F0"':''}>
                                <td style="width:75px;">实际<c:set var="shiji"/></td>
                                <c:forEach var="p" items="${paramList}">
                                    <td>
                                        <c:set var="shejiResult" value="0"/>
                                        <c:set var="shejiValue" value="${phbMap[shejiKey][p.pname]}"/>
                                        <c:set var="fanglValue" value="${m.value[1].m10}"/>
                                        <c:if test="${shejiValue ne null && fanglValue ne null}">
                                            <c:set var="shejiResult" value="${shejiValue*fanglValue}"/>
                                        </c:if>
                                        <c:choose>
                                            <c:when test="${m.value[1][p.pname] ne null}">
                                                <c:set var="shijiResult" value="${m.value[1][p.pname]}"/>
                                                <c:choose>
                                                    <c:when test="${shijiResult<shejiResult}">
                                                        <font color="red"><fmt:formatNumber value="${shijiResult}"
                                                                                            pattern="#.##"/></font>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <fmt:formatNumber value="${shijiResult}" pattern="#.##"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:formatNumber value="${m.value[1][p.pname]}" pattern="#.##"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr ${i.index%2==0?'bgcolor="#F0F0F0"':''}>
                                <td style="width:75px;">误差</td>
                                <c:forEach var="p" items="${paramList}">
                                    <td>
                                        <c:set var="result" value="${m.value[1][p.pname]-m.value[2][p.pname]}"/>
                                        <c:if test="${result!=0}">
                                            <c:set var="mkey" value="${bdCode}-${bhzCode}-${p.pname}"/>
                                            <c:set var="sub" value="${subMap[mkey]}"/>
                                            <c:choose>
                                                <c:when test="${sub.downValue ne null && sub.downValue ne '' && sub.upValue ne null && sub.upValue ne ''}">
                                                    <c:set var="cv" value="${result/m.value[2][p.pname]*100}"/>
                                                    <c:choose>
                                                        <c:when test="${cv<=sub.upValue && cv>=sub.downValue}">
                                                            <fmt:formatNumber value="${result}" pattern="#.#"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span style="color:red;"><fmt:formatNumber value="${result}"
                                                                                                       pattern="#.#"/></span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <fmt:formatNumber value="${result}" pattern="#.#"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </table>
            <div class="clear"></div>
            <p:PagingTag url="mainAction!getGiveDate.action?bd=${bdCode}&bhz=${bhzCode}&m3=${m3}&m5=${m5}&m7=${m7}&m8=${m8}&bjType=${bjType}&sdate=${sdate}&edate=${edate}&subType=1"
                    page="${objPage}" forPage="true" showCurAndSum="true"/>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>