<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/5
  Time: 13:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="/page-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
    <link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
    <link rel="stylesheet" type="text/css" href="css/LAYER/ymPrompt.css"/>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/count.js"></script>
    <script type="text/javascript" src="js/util.js"></script>
    <script type="text/javascript" src="js/other/sqmqcl.js"></script>
    <script type="text/javascript" src="js/other/ymPrompt.js"></script>
    <title></title>
    <script type="text/javascript">
        $(document).ready(function () {
            var clNamesStr = $("#clNamesStr").val();
            selRadio();
            if (clNamesStr != null && clNamesStr != "") {
                var arrClName = clNamesStr.split(",");
                $("input[name='clNames']").each(function () {
                    var val = $(this).val();
                    for (var i = 0; i < arrClName.length; i++) {
                        if (arrClName[i] == val) {
                            $(this).attr("checked", true);
                        }
                    }
                });
            }
        });
        function selRadio() {
            var rt = $("input[name='countType']:checked").val();
            if (rt == "1") {
                $("#tdName").hide();
                $("#tdValue").hide();
                $("#isMoreBdBox").attr("disabled", true);
            } else {
                $("#tdName").show();
                $("#tdValue").show();
                $("#isMoreBdBox").attr("disabled", false);
            }
        }
        function forwordURL2() {
            var rt = $("input[name='countType']:checked");
            if (rt.length == 0) {
                alert("至少需要选择某种【条件查询】!");
                return;
            }
            if (rt.val() == "1") {
                var bd = $("#bd").val();
                var bhz = $("#bhz").val();
                if (bd == "") {
                    alert("按合并材料统计必须选择标段!");
                    return;
                }
                if (bhz == "") {
                    alert("按合并材料统计必须选择拌合站!");
                    return;
                }
            }
            openLoad();
            $("#form1").submit();
        }
    </script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">统计分析</a></li>
        <li><a href="#">材料用量统计</a></li>
    </ul>
</div>
<div class="rightinfo">
    <input type="hidden" id="bdCode" name="bdCode" value="${bdCode}"/>
    <input type="hidden" id="bhzCode" name="bhzCode" value="${bhzCode}"/>
    <input type="hidden" id="intensityGrade" name="intensityGrade" value="${m8}"/>
    <input type="hidden" id="clNamesStr" name="clNamesStr" value="${clNamesStr}"/>

    <form id="form1" action="countAction!countSnCn.action" method="post">
        <table class="tablelist">
            <tr>
                <td>条件查询：</td>
                <td colspan="6">
                    <input type="radio" id="countType1" name="countType" value="0"
                           onclick="selRadio();" ${countType eq '0'?'checked':''} checked="checked"/><label
                        for="countType1" onclick="selRadio();">按现场仓位统计</label>
                    <input type="radio" id="countType2" name="countType" value="1"
                           onclick="selRadio();" ${countType eq '1'?'checked':''}/><label for="countType2"
                                                                                          onclick="selRadio();">按合并材料统计</label>
                </td>
            </tr>
            <tr>
                <td>标段名称：</td>
                <td>
                    <div class="oneBdDiv">
                        <select class="scinput" style="height: 25px;" id="bd" name="bd" onchange="showBhzTo();">
                            <option value="">==请选择标段==</option>
                            <c:forEach var="b" items="${bdList}">
                                <option value="${b.bdCode}" ${bdCode eq b.bdCode?'selected':''}>${b.bdName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="moreBdDiv" style="display:none;">
                        <input type="hidden" id="morebdCode" name="morebdCode" value="${morebdCode}"/>
                        <input type="text" class="scinput" style="height: 25px;" id="morebd" name="morebd"
                               value="${morebd}" readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <div class="oneBdDiv">拌合站名称：</div>
                    <div class="moreBdDiv" style="display:none;">
                        <input type="button" class="btn1" onclick="showPage('countAction!getAllBdInfo.action',600,400,'多标段选择');" value="选择标段"/>
                    </div>
                </td>
                <td>
                    <div class="oneBdDiv">
                        <select class="scinput" style="height: 25px;" name="bhz" id="bhz">
                            <option value="">==请选择拌合站==</option>
                        </select>
                    </div>
                </td>
                <td colspan="2">
                    <input type="checkbox" id="isMoreBdBox" name="isMoreBdBox" value="1"
                           onclick="isMoreBd();" ${isMoreBdBox eq '1'?'checked':''}/>&nbsp;是否选择多标段查询
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>强度等级：</td>
                <td>
                    <select class="scinput" style="height: 25px;" id="m8" name="m8">
                        <option value="">==请选择强度等级==</option>
                        <c:forEach var="q" items="${qdList}">
                            <option value="${q}" ${q eq m8?'selected':''}>${q}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>使用部位：</td>
                <td><input type="text" class="scinput" style="height: 25px;" id="m5" name="m5" value="${m5}"/></td>
                <td>出料时间：</td>
                <td>
                    <input type="text" class="scinput"  name="sdate" style="height: 25px;margin-top: 6px" value="${sdate}" id="sdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
                    至
                    <input type="text" class="scinput"  name="edate" style="height: 25px" value="${edate}" id="edate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>
                    <div id="tdName">材料名称：</div>
                </td>
                <td colspan="5">
                    <div id="tdValue">
                        <c:forEach var="cpVal" items="${boxParamVal}">
                            <input type="checkbox" name="clNames" value="${cpVal}"/>${cpVal}&nbsp;&nbsp;
                        </c:forEach>
                    </div>
                </td>
                <td>
                    <input type="button" class="btn1"  onclick="forwordURL2()" value="查询"/>
                </td>
            </tr>
        </table>
    </form>

    <c:choose>
        <c:when test="${countType eq '1'}">
            <table class="tablelist">
                <c:choose>
                    <c:when test="${fn:length(mergeList) eq 0}">
                        <tr>
                            <td colspan="10"><font color="red">未查询到合并列!</font></td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <c:forEach var="c" items="${columnMap}" varStatus="i">
                                <th>${c.value}</th>
                            </c:forEach>
                            <c:forEach var="cpVal" items="${mergeList}">
                                <th>${cpVal.mergeColsName}(kg)</th>
                            </c:forEach>
                        </tr>
                        <c:choose>
                            <c:when test="${fn:length(objList) eq 0}">
                                <tr>
                                    <td colspan="10"><font color="red">暂无数据记录!</font></td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="o" items="${objList}" varStatus="k">
                                    <c:set var="mk" value="${k.index%2}"/>
                                    <tr ${mk eq 0?'bgcolor="#F0F0F0"':''}>
                                        <!-- 标段 -->
                                        <c:forEach var="c" items="${columnMap}" varStatus="inx">
                                            <c:set var="indexNum" value="${inx.index}"/>
                                            <c:set var="fName" value="${o[indexNum]}"/>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${c.key eq 'm2'}">${bdMap[fn:toUpperCase(fName)]}</c:when>
                                                    <c:when test="${c.key eq 'm6'}">${bhzMap[fName].bhzName}</c:when>
                                                    <c:otherwise>${fName}</c:otherwise>
                                                </c:choose>
                                            </td>
                                        </c:forEach>
                                        <!-- 数据 -->
                                        <c:forEach var="m" items="${mergeList}" varStatus="inx2">
                                            <td>
                                                <c:set var="indexNum2" value="${inx2.index+2}"/>
                                                <fmt:formatNumber value="${o[indexNum2]}" pattern="0.00"/>
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </table>
        </c:when>
        <c:otherwise>
            <table class="tablelist">
                <tr>
                    <c:forEach var="c" items="${columnMap}" varStatus="i">
                        <th>${c.value}</th>
                    </c:forEach>
                    <c:forEach var="cpVal" items="${countParamVal}">
                        <th>${cpVal}(kg)</th>
                    </c:forEach>
                </tr>
                <c:choose>
                    <c:when test="${fn:length(objPage) eq 0}">
                        <tr>
                            <td colspan="10"><font color="red">暂无数据记录!</font></td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="d" items="${objPage}" varStatus="j">
                            <c:set var="mo" value="${j.index%2}"/>
                            <c:set var="paramKey" value="${fn:toUpperCase(d.m2)}-${d.m6}"/>
                            <tr ${mo eq 0?'bgcolor="#F0F0F0"':''}>
                                <c:forEach var="c" items="${columnMap}" varStatus="k">
                                    <c:set var="fName" value="${d[c.key]}"/>
                                    <td>
                                        <c:choose>
                                            <c:when test="${c.key eq 'm2'}">${bdMap[fn:toUpperCase(fName)]}</c:when>
                                            <c:when test="${c.key eq 'm6'}">${bhzMap[fName].bhzName}</c:when>
                                            <c:otherwise>${fName}</c:otherwise>
                                        </c:choose>
                                    </td>
                                </c:forEach>
                                <c:forEach var="cpVal" items="${countParamVal}" varStatus="m">
                                    <td>
                                        <c:set var="detailNum" value="0"/>
                                        <c:forEach var="p" items="${paramMap[paramKey]}">
                                            <c:if test="${fn:contains(p.pvalue, cpVal)}">
                                                <c:if test="${d[p.pname] ne null && d[p.pname] ne ''}">
                                                    <c:set var="detailNum" value="${detailNum+d[p.pname]}"/>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                        <fmt:formatNumber value="${detailNum}" pattern="0.00"/>
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="${fn:length(columnMap)}">合计：</td>
                            <c:forEach var="cpVal" items="${countParamVal}">
                                <td>
                                    <c:set var="detailNum" value="0"/>
                                    <c:forEach var="d" items="${objPage}">
                                        <c:set var="paramKey" value="${d.m2}-${d.m6}"/>
                                        <c:forEach var="p" items="${paramMap[paramKey]}">
                                            <c:if test="${fn:contains(p.pvalue, cpVal)}">
                                                <c:set var="detailNum" value="${detailNum+d[p.pname]}"/>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                    <fmt:formatNumber value="${detailNum}" pattern="0.00"/>
                                </td>
                            </c:forEach>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
