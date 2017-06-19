<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/4
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="p" uri="/page-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="newWeb/js/jquery.js"></script>
    <script type="text/javascript" src="js/givedata.js"></script>
    <script type="text/javascript" src="js/util.js"></script>
    <title></title>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".click").click(function () {
                $(".tip").fadeIn(200);
            });

            $(".tiptop a").click(function () {
                $(".tip").fadeOut(200);
            });

            $(".sure").click(function () {
                $(".tip").fadeOut(100);
            });

            $(".cancel").click(function () {
                $(".tip").fadeOut(100);
            });

        });
    </script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">生产数据监控</a></li>
        <li><a href="#">车载累计误差</a></li>
    </ul>
</div>
<div class="rightinfo">
    <form id="form1" action="mainAction!countCzTotalWc.action" method="post">
        <table class="tablelist">
            <tr>
                <td>
                    <label>标段名称:</label>
                    <select class="scinput" id="bd" name="bd" onchange="showBhz();" style="height: 25px">
                        <option value="">==请选择标段==</option>
                        <c:forEach var="b" items="${bdList}">
                            <option value="${b.bdCode}" ${bdCode eq b.bdCode?'selected':''}>${b.bdName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <label>拌合站名称：</label>
                    <select class="scinput" id="bhz" name="bhz" style="height: 25px">
                        <option value="">==请选择拌合站==</option>
                    </select>
                </td>
                <td>
                    <label>出料时间:</label>
                    <input id="sdate" name="sdate" type="text" value="${sdate}" class="scinput"
                           style="height: 25px;margin-top: 6px" onfocus="WdatePicker();"/>至
                    <input id="edate" name="edate" type="text" value="${edate}" class="scinput" style="height: 25px"
                           onfocus="WdatePicker();"/>
                </td>
                <td>

                </td>
            </tr>
            <tr>
                <td>
                    <label>浇筑部位:</label>
                    <input id="m5" name="m5" type="text" class="scinput" style="height: 25px;margin-top: 6px"
                           value="${m5}"/>
                </td>
                <td>
                    <label>强&nbsp;度&nbsp;等&nbsp;级：</label>
                    <select class="scinput" id="m8" name="m8" style="height: 25px">
                        <option value="">==请选择强度等级==</option>
                        <c:forEach var="q" items="${qdList}">
                            <option value="${q}" ${q eq m8?'selected':''}>${q}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <label>车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次:</label>
                    <input id="bhzCarOrder" name="bhzCarOrder" type="text" class="scinput" value="${bhzCarOrder}"
                           style="height: 25px;margin-top: 6px"/>
                </td>
                <td>
                    <input name="" type="button" class="btn1"
                           onclick="if($('#bd').val()=='' || $('#bhz').val()==''){alert('请选择标段及拌合站');return;}else{forwordURL();} "
                           value="查询"/>
                </td>
            </tr>
        </table>
    </form>
    <input type="hidden" id="bdCode" name="bdCode" value="${bdCode}"/>
    <input type="hidden" id="bhzCode" name="bhzCode" value="${bhzCode}"/>

    <c:choose>
        <c:when test="${fn:length(mergeList) eq 0}">
            <table class="tablelist">
                <tr>
                    <td style="text-align: center;color: red;">
                        材料数据未合并，无法准确统计，请先校正合并!
                    </td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
            <table class="tablelist">
                <tr>
                    <th style="text-align: center">标段</th>
                    <th style="text-align: center">拌合站名称</th>
                    <th style="text-align: center">车次</th>
                    <th style="text-align: center">车牌号</th>
                    <th style="width:90px;">浇筑部位</th>
                    <th style="text-align: center">强度等级</th>
                    <th colspan="2" style="text-align:center;width:90px;">起止时间</th>
                    <th style="text-align: center">累计方量</th>
                    <th style="text-align: center">报警状态</th>
                    <th style="text-align: center">配合比类型</th>
                    <c:forEach var="m" varStatus="s" items="${mergeList}">
                        <th>${m.mergeColsName}</th>
                        <c:if test="${m.mergeColsName eq '水'}"><c:set var="waterIndex" value="${s.index}"/></c:if>
                        <c:if test="${m.mergeColsName eq '水泥'}"><c:set var="cementIndex" value="${s.index}"/></c:if>
                        <c:if test="${m.mergeColsName eq '外掺材料'}"><c:set var="wcclIndex" value="${s.index}"/></c:if>
                    </c:forEach>
                    <c:if test="${waterIndex ne null && cementIndex ne null}">
                        <th style="text-align: center">水胶比</th>
                    </c:if>
                </tr>
                <c:choose>
                    <c:when test="${fn:length(objList.baseObj) eq 0}">
                        <tr>
                            <td colspan="26" style="color: red;text-align: center;">暂无累计误差数据记录!</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="m" items="${objList.baseObj}" varStatus="i">
                            <tr>
                                <td rowspan="4">${bdMap[m.value[0][1]]}</td>
                                <td rowspan="4">${tbBhz.bhzName}</td>
                                <td rowspan="4">${m.value[0][4]}</td>
                                <td rowspan="4">${m.value[0][3]}</td>
                                <td rowspan="4">${m.value[0][5]}</td>
                                <td rowspan="4">${m.value[0][6]}</td>
                                <td rowspan="4">
                                        ${m.value[0][7]}
                                </td>
                                <td rowspan="4">
                                        ${m.value[0][8]}
                                </td>
                                <td rowspan="4">
                                    <fmt:formatNumber value="${m.value[1][10]}" pattern="0.0"/>
                                </td>
                                <td rowspan="4">
                                    <c:set var="bjKey" value="${m.value[0][4]}-${m.value[0][1]}-${m.value[0][2]}"/>
									<c:set var="bjState" value="${bjTypeData[bjKey]}"/>
                                    <c:choose>
                                        <c:when test="${bjTypeMap[bjState] ne null}">
                                            ${bjTypeMap[bjState]}
                                        </c:when>
                                        <c:otherwise>
                                            未报警
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>累计施工(Kg)</td>
                                <c:forEach var="r" items="${mergeList}" varStatus="j">
                                    <td>
                                        <c:set var="n" value="${j.index+11}"/>
                                        <fmt:formatNumber value="${m.value[2][n]}" pattern="0.00"/>
                                    </td>
                                </c:forEach>
                                <c:set var="sjKey" value="${m.value[0][1]}-${m.value[0][5]}-${m.value[0][7]}-${r.mergeCols}"/>
                                <c:if test="${r.mergeColsName eq '水'}">
                                    <c:set var="Water" value="${phbMap[sjKey].ext1}"/>
                                </c:if>
                                <c:if test="${r.mergeColsName eq '水泥'}">
                                    <c:set var="Cement" value="${phbMap[sjKey].ext1}"/>
                                </c:if>
                                <c:if test="${r.mergeColsName eq '外掺材料'}">
                                    <c:set var="WCCL" value="${phbMap[sjKey].ext1}"/>
                                </c:if>
                                <td>
                                    <c:if test="${waterIndex ne null && cementIndex ne null}">
                                    <c:choose>
                                    <c:when test="${wcclIndex ne null && (m.value[2][11+cementIndex] + m.value[2][11+wcclIndex]) ne 0}">
                                        <c:set var="a" value="${m.value[2][11+waterIndex]}"/>
                                        <c:set var="b" value="${m.value[2][11+cementIndex] + m.value[2][11+wcclIndex]}"/>
                                        <c:set var="d" value="${a / b}"/>
                                        <fmt:formatNumber value="${d}" pattern="#.##"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${m.value[2][11+cementIndex] ne 0}">
                                            <c:set var="d" value="${m.value[2][11+waterIndex] / m.value[2][11+cementIndex]}"/>
                                            <fmt:formatNumber value="${d}" pattern="#.##"/>
                                        </c:if>
                                    </c:otherwise>
                                    </c:choose>
                                    </c:if>
                                </td>

                            </tr>
                            <tr>
                                <td>累计实际(Kg)</td>
                                <c:forEach var="r" items="${mergeList}" varStatus="j">
                                    <td>
                                        <c:set var="n" value="${j.index+11}"/>
                                        <fmt:formatNumber value="${m.value[1][n]}" pattern="0.00"/>
                                    </td>
                                </c:forEach>
                                <c:if test="${waterIndex ne null && cementIndex ne null}">
                                    <c:choose>
                                        <c:when test="${wcclIndex ne null && (m.value[1][11+cementIndex] + m.value[1][11+wcclIndex]) ne 0}">
                                            <c:set var="sjb_sj" value="${m.value[1][11+waterIndex] / (m.value[1][11+cementIndex] + m.value[1][11+wcclIndex])}"/>
                                            <td><fmt:formatNumber value="${sjb_sj}" pattern="#.##"/></td>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${m.value[1][11+cementIndex] ne 0}">
                                                    <c:set var="sjb_sj" value="${m.value[1][11+waterIndex] / m.value[1][11+cementIndex]}"/>
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
                            <tr>
                                <td>累计误差(Kg)</td>
                                <c:forEach var="r" items="${mergeList}" varStatus="j">
                                    <td>
                                        <c:set var="n" value="${j.index+11}"/>
                                        <c:set var="wc" value="${m.value[2][n]-m.value[1][n]}"/>
                                        <fmt:formatNumber value="${wc}" pattern="0.00"/>
                                    </td>
                                </c:forEach>
                                <c:if test="${waterIndex ne null && cementIndex ne null}">
                                    <td>
                                        <fmt:formatNumber var="l" value="${sjb_sj}" pattern="#.##"/>
                                        <fmt:formatNumber var="k" value="${d}" pattern="#.##"/>
                                        <fmt:formatNumber value="${l-k}" pattern="#.##"/>
                                    </td>
                                </c:if>
                            </tr>
                            <tr>
                                <td>误差百分率(%)</td>
                                <c:forEach var="r" items="${mergeList}" varStatus="j">
                                    <td>
                                        <c:set var="n" value="${j.index+11}"/>
                                        <c:set var="wc" value="${m.value[2][n]-m.value[1][n]}"/>
                                        <c:choose>
                                            <c:when test="${m.value[2][n] ne null && m.value[2][n]>0}">
                                                <c:set var="wcbfl" value="${wc/m.value[2][n]*100}"/>
                                                <fmt:formatNumber value="${wcbfl}" pattern="0.00"/>
                                            </c:when>
                                            <c:otherwise>
                                                &nbsp;
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </c:forEach>
                                <td>
                                    <c:if test="${waterIndex ne null && cementIndex ne null && l-k ne 0}">
                                        <fmt:formatNumber value="${(l-k)/k*100}" pattern="#.##"/>
                                    </c:if>
                                </td>
                                <%--<c:if test="${waterIndex ne null && cementIndex ne null}">
                                    <td>
                                    <fmt:formatNumber var="c" value="${sjb_sj}" pattern="#.##"/>
                                    <c:choose>
                                        <c:when test="${d ne 0 && d ne '' && d ne null && c ne 0 && c ne '' && c ne null}">
                                            <fmt:formatNumber value="${(c-d)/d*100}" pattern="#.##"/>
                                        </c:when>
                                        <c:otherwise>
                                            <td></td>
                                        </c:otherwise>
                                    </c:choose>
                                    </td>
                                </c:if>--%>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </table>

            <p:PagingTag
                    url="mainAction!countCzTotalWc.action?bd=${bdCode}&bhz=${bhzCode}&m5=${m5}&m8=${m8}&sdate=${sdate}&edate=${edate}&bhzCarOrder=${bhzCarOrder}"
                    page="${objList}" forPage="true" showCurAndSum="true"/>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
