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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
        <li><a href="#">构件累计误差</a></li>
    </ul>
</div>
<div class="rightinfo">
    <form id="form1" action="mainAction!countTotalWc.action"  method="post">
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
                    <label>强度等级:</label>
                    <select class="scinput" id="m8" name="m8" style="height: 25px">
                        <option value="">==请选择强度等级==</option>
                        <c:forEach var="q" items="${qdList}">
                            <option value="${q}" ${q eq m8?'selected':''}>${q}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <label>浇筑部位:</label>
                    <input id="m5" name="m5" type="text" class="scinput" value="${m5}" style="height: 25px;margin-top: 6px" />
                </td>
                <td>
                    <label>出&nbsp;料&nbsp;时&nbsp;间：</label>
                    <input id="sdate" name="sdate" type="text"  value="${sdate}" class="scinput" style="height: 25px;margin-top: 6px" onfocus="WdatePicker();"/>至
                    <input id="edate" name="edate" type="text" value="${edate}"  class="scinput" style="height: 25px" onfocus="WdatePicker();"/>
                </td>
                <td>
                    <input name="" type="button" class="btn1" onclick="if($('#bd').val()=='' || $('#bhz').val()==''){alert('请选择标段及拌合站');return;}else{forwordURL();}" value="查询"/>
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
                    <td  style="text-align: center;color: red;">
                        材料数据未合并，无法准确统计，请先校正合并!
                    </td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
            <table class="tablelist">
                <tr>
                    <th>标段</th>
                    <th>拌合站名称</th>
                    <th style="width:90px;">浇筑部位</th>
                    <th>强度等级</th>
                    <th colspan="2" style="width:90px;">起止时间</th>
                    <th>累计方量</th>
                    <th>配合比类型</th>
                    <c:forEach var="m" varStatus="s" items="${mergeList}">
                        <th>${m.mergeColsName}</th>
                    </c:forEach>
                </tr>
                <c:choose>
                    <c:when test="${fn:length(objList.baseObj) eq 0}">
                        <tr>
                            <td colspan="25" style="color: red;text-align: center;">暂无累计误差数据记录!</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="m" items="${objList.baseObj}" varStatus="i">
                            <tr>
                                <td rowspan="4">${bdMap[m.value[2][2]]}</td>
                                <td rowspan="4">${tbBhz.bhzName}</td>
                                <td rowspan="4">${m.value[2][4]}</td>
                                <td rowspan="4">${m.value[2][5]}</td>
                                <td rowspan="4">
                                        ${m.value[2][6]}
                                </td>
                                <td rowspan="4">
                                        ${m.value[2][7]}
                                </td>
                                <td rowspan="4">
                                    <fmt:formatNumber value="${m.value[2][8]}" pattern="0.0"/>
                                </td>
                                <td>累计施工(Kg)</td>
                                <c:forEach var="r" items="${mergeList}" varStatus="j">
                                    <td>
                                        <c:set var="n" value="${j.index+9}"/>
                                        <fmt:formatNumber value="${m.value[2][n]}" pattern="0.00"/>
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <td>累计实际(Kg)</td>
                                <c:forEach var="r" items="${mergeList}" varStatus="j">
                                    <td>
                                        <c:set var="n" value="${j.index+9}"/>
                                        <fmt:formatNumber value="${m.value[1][n]}" pattern="0.00"/>
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <td>累计误差(Kg)</td>
                                <c:forEach var="r" items="${mergeList}" varStatus="j">
                                    <td>
                                        <c:set var="n" value="${j.index+9}"/>
                                        <c:set var="wc" value="${m.value[2][n]-m.value[1][n]}"/>
                                        <fmt:formatNumber value="${wc}" pattern="0.00"/>
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <td>误差百分率(%)</td>
                                <c:forEach var="r" items="${mergeList}" varStatus="j">
                                    <td>
                                        <c:set var="n" value="${j.index+9}"/>
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
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </table>

            <p:PagingTag url="mainAction!countTotalWc.action?bd=${bdCode}&bhz=${bhzCode}&m5=${m5}&m8=${m8}&sdate=${sdate}&edate=${edate}&subType=1"
                         page="${objList}" forPage="true" showCurAndSum="true"/>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
