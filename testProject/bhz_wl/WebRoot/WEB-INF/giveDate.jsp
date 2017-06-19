<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/4
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>无标题文档</title>
    <link rel="stylesheet" href="newWeb/css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="newWeb/js/jquery.js"></script>
    <script type="text/javascript" src="js/givedata.js"></script>
    <script type="text/javascript" src="js/util.js"></script>
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

<body style="height:100%">
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">拌合站监控系统</a></li>
        <li><a href="#">生产数据监控</a></li>
        <li><a href="#">每盘数据监控</a></li>
    </ul>
</div>

<div class="rightinfo">
    <form id="form1" action="mainAction!getGiveDate.action"  method="post">
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
                <td>
                    <label>报警状态:</label>
                    <select class="scinput" id="bjType" name="bjType" style="height: 25px">
                        <option value="">==请选择报警状态==</option>
                        <c:forEach var="b" items="${bjTypeMap}">
                            <option value="${b.key}" ${bjType eq b.key?'selected':''}>${b.value}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <label>浇筑部位:</label>
                    <input id="m5" name="m5" type="text" class="scinput" style="height: 25px;margin-top: 6px" />
                </td>
                <td colspan="2">
                    <label>出&nbsp;料&nbsp;时&nbsp;间：</label>
                    <input id="sdate" name="sdate" type="text"  value="${sdate}" class="scinput" style="height: 25px;margin-top: 6px" onfocus="WdatePicker();"/>至
                    <input id="edate" name="edate" type="text" value="${edate}"  class="scinput" style="height: 25px" onfocus="WdatePicker();"/>
                </td>
                <td>
                    <input name="" type="button" class="btn1" onclick="forwordURL();" value="查询"/>
                </td>
            </tr>
        </table>
    </form>
    <input type="hidden" id="bdCode" name="bdCode" value="${bdCode}"/>
    <input type="hidden" id="bhzCode" name="bhzCode" value="${bhzCode}"/>
    <c:choose>
        <c:when test="${fn:length(paramList) eq 0}">
            <table class="tablelist">
                <tr>
                    <td style="text-align: center;color: red;">
                        <c:choose>
                            <c:when test="${nodata eq '1'}">该标段下的拌合站无查询项,请先至系统参数页进行设置!</c:when>
                            <c:otherwise>请选择需要查询的标段和拌合站!</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
            <table class="tablelist center">
                <thead>
                <tr>
                    <th>拌合站名称</th>
                    <th>浇筑部位</th>
                    <th>强度等级</th>
                    <th>车次</th>
                    <th>车牌号</th>
                    <th style="width: 90px">出料时间</th>
                    <th>方量</th>
                    <th>报警状态</th>
                    <th>配合比类型</th>
                    <c:forEach var="p" items="${paramList}">
                        <th>${p.pvalue}</th>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${fn:length(objPage.baseMap) eq 0}">
                        <tr><td colspan="26" style="color: red;text-align: center;">暂无数据记录!</td></tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="m" items="${objPage.baseMap}" varStatus="i">
                            <c:set var="shejiKey" value="${m.value[0].m2}-${m.value[0].m6}-${m.value[0].m8}"/>
                            <tr ${i.index%2==0?'bgcolor="#F0F0F0"':''}>
                                <td rowspan="4">${tbBhz.bhzName}</td>
                                <td rowspan="4">${m.value[0].m5}</td>
                                <td rowspan="4">${m.value[0].m8}</td>
                                <td rowspan="4">${m.value[0].bhzCarOrder}</td>
                                <td rowspan="4">${m.value[0].bhzCarNum}</td>
                                <td rowspan="4">${m.value[0].m4}</td>
                                <td rowspan="4">${m.value[0].m10}</td>
                                <td rowspan="4">
                                    <c:set var="bjKey" value="${m.value[0].groupId}-${m.value[0].m2}-${m.value[0].m6}"/>
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
                                <td>设计(Kg)<c:set var="sheji"/></td>
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
                            </tr>
                            <tr ${i.index%2==0?'bgcolor="#F0F0F0"':''}>
                                <td>施工(Kg)<c:set var="shigong"/></td>
                                <c:forEach var="p" items="${paramList}">
                                    <td>
                                        <c:choose>
                                            <c:when test="${m.value[2][p.pname] ne '' && m.value[2][p.pname] ne null}">
                                                <fmt:formatNumber value="${m.value[2][p.pname]}" pattern="#.##"/>
                                            </c:when>
                                            <c:otherwise>

                                            </c:otherwise>
                                        </c:choose>

                                    </td>
                                </c:forEach>
                            </tr>
                            <tr ${i.index%2==0?'bgcolor="#F0F0F0"':''}>
                                <td>实际(Kg)<c:set var="shiji"/></td>
                                <c:forEach var="p" items="${paramList}">
                                    <td>
                                        <c:set var="shejiResult" value="0"/>
                                        <c:set var="shejiValue" value="${phbMap[shejiKey][p.pname]}"/>
                                        <c:set var="fanglValue" value="${m.value[1].m10}"/>
                                        <c:if test="${shejiValue ne null && fanglValue ne null && shejiResult ne '' && fanglValue ne ''}">
                                            <c:set var="shejiResult" value="${shejiValue*fanglValue}"/>
                                        </c:if>
                                        <c:choose>
                                            <c:when test="${m.value[1][p.pname] ne null && m.value[1][p.pname] ne '' }">
                                                <c:set var="shijiResult" value="${m.value[1][p.pname]}"/>
                                                <c:choose>
                                                    <c:when test="${shijiResult<shejiResult && shejiResult ne '' && shejiResult ne null}">
                                                        <font color="red"><fmt:formatNumber value="${shijiResult}" pattern="#.##"/></font>
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
                                <td style="width:75px;">误差(Kg)</td>
                                <c:forEach var="p" items="${paramList}">
                                    <td>
                                        <c:choose>
                                            <c:when test="${m.value[1][p.pname] ne '' && m.value[1][p.pname] ne null &&  m.value[2][p.pname] ne '' && m.value[2][p.pname] ne null}">
                                                <c:set var="result" value="${m.value[1][p.pname]-m.value[2][p.pname]}" />
                                                <c:if test="${result!=0 && result ne '' && result ne null}">
                                                    <c:set var="mkey" value="${bdCode}-${bhzCode}-${p.pname}"/>
                                                    <c:set var="sub" value="${subMap[mkey]}" />
                                                    <c:choose>
                                                        <c:when test="${sub.downValue ne null && sub.downValue ne '' && sub.upValue ne null && sub.upValue ne ''}">
                                                            <c:set var="cv" value="${result/m.value[2][p.pname]*100}"/>
                                                            <c:choose>
                                                                <c:when test="${cv<=sub.upValue && cv>=sub.downValue && cv ne '' && cv ne null}">
                                                                    <fmt:formatNumber value="${result}" pattern="#.#"/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span style="color:red;"><fmt:formatNumber value="${result}" pattern="#.#"/></span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <fmt:formatNumber value="${result}" pattern="#.#"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>

            <p:PagingTag url="mainAction!getGiveDate.action?bd=${bdCode}&bhz=${bhzCode}&m3=${m3}&m5=${m5}&m7=${m7}&m8=${m8}&bjType=${bjType}&sdate=${sdate}&edate=${edate}&subType=1"
                         page="${objPage}" forPage="true" showCurAndSum="true"/>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
