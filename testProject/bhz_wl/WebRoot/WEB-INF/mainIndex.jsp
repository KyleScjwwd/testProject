<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" uri="/page-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <title>无标题文档</title>
    <link rel="stylesheet" type="text/css" href="newWeb/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
    <link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
    <script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="js/util.js"></script>
    <script type="text/javascript">
        function changeList(me) {
            $("#scFlag").val("");
            document.getElementById('Rdiv').style.display = 'none';
            document.getElementById('Rtable').style.display = 'none';
            document.getElementById(me.value).style.display = 'block';
        }
        function gotoSc(p) {
            var scFlag = $("#scFlag").val();
            var plType = $("#plType").val();
            window.location.href = "mainAction!mainIndex.action?scFlag=" + scFlag + "&plTyle=" + plType + "&cuPage=" + p;
        }
        function gotoPage(p) {
            var scFlag = $("#scFlag").val();
            var plType = $("#plType").val();
            window.location.href = "mainAction!mainIndex.action?scFlag=" + scFlag + "&plTyle=" + plType + "&cuPage=" + p;
        }

        function checkNetWork(){
            location.href=location.href;
        }

        $(document).ready(function() {
            setInterval('checkNetWork()',1000*60*2);
        });
    </script>
</head>

<body>
<div class="right">
    <div class="right_title">
        <div class="r_t_box">
            <a>图例&nbsp;&nbsp;生产状态</a>
            <a class="online">当天在生产</a>
            <a class="line">当天未生产</a>
            <a>
                选择生产状态：
                <select id="scFlag" class="scinput" style="height: 25px" onchange="gotoSc('${page.currentPage}');">
                    <option value="">全部</option>
                    <option value="2" ${scFlag eq '2'?'selected':''}>当天在生产</option>
                    <option value="1" ${scFlag eq '1'?'selected':''}>当天未生产</option>
                </select>
            </a>
            <a>
                选择排列方式：
                <select id="plType" class="scinput" style="height: 25px" onchange="changeList(this);">
                    <option value="Rdiv" ${plType eq 'Rdiv'?'selected':''}>图标排列</option>
                    <option value="Rtable" ${plType eq 'Rtable'?'selected':''}>列表排列</option>
                </select>
            </a>
        </div>
    </div>
    <!-- 图标排列 -->
    <div class="right_content" id="Rdiv" ${plType eq 'Rtable'?'style="display:none;"':''}>
        <div style="height: 33px">
            <div class="divtopleft">
                <span></span>
                <p>当天生产状态</p>
            </div>
            <div class="divtopmiddle"></div>
            <div class="divtopright"></div>
        </div>
        <div>
            <div class="middleleft"></div>
            <div class="sc_box">
                <c:forEach var="b" items="${bhzList}">
                    <c:choose>
                        <c:when test="${b.ext1 eq '1'}">
                            <a class="zsc" onclick="openLoad();"
                               href="mainAction!getGiveDate.action?bd=${b.bdCode}&bhz=${b.bhzCode}&isCurrent=1">
                                <img style="margin-top:5px;" border="0"
                                     src="images/bhz/${b.isWeb}?randomNum=${randomNum}" title="${b.isWeb eq 'wifi-on.png'?'已联网':'未联网'}"/>
                                    ${bdMap[b.bdCode].bdName}&nbsp;${b.bhzName}
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a class="wsc" onclick="openLoad();"
                               href="mainAction!getGiveDate.action?bd=${b.bdCode}&bhz=${b.bhzCode}&isCurrent=2">
                                <img style="margin-top:5px;" border="0"
                                     src="images/bhz/${b.isWeb}?randomNum=${randomNum}" title="${b.isWeb eq 'wifi-on.png'?'已联网':'未联网'}"/>
                                    ${bdMap[b.bdCode].bdName}&nbsp;${b.bhzName}
                            </a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
            <div class="middleright"></div>
        </div>


    </div>
    <!-- 列表排列 -->
    <div class="right_table" id="Rtable" ${plType eq 'Rdiv'?'style="display:none;"':''}>
        <table class="tablelist">
            <tr>
                <th style="text-align: center">标段</th>
                <th style="text-align: center">拌合站名称</th>
                <th style="text-align: center">工程名称</th>
                <th style="text-align: center">出料时间</th>
                <th style="text-align: center">生产状态</th>
                <th style="text-align: center">联网状态</th>
                <th style="text-align: center">登记</th>
                <th style="text-align: center">详情</th>
            </tr>
            <c:forEach var="s" items="${bhzSet}">
                <c:set var="mList" value="${bhzMap[s]}"/>
                <c:forEach var="b" items="${mList}" varStatus="i">
                    <c:choose>
                        <c:when test="${b.ext1 eq '1'}">
                            <c:set var="picName" value="online_04.png"/>
                            <c:set var="url" value="mainAction!getGiveDate.action?bd=${b.bdCode}&bhz=${b.bhzCode}&isCurrent=1"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="picName" value="line_04.png"/>
                            <c:set var="url" value="mainAction!getGiveDate.action?bd=${b.bdCode}&bhz=${b.bhzCode}&isCurrent=2"/>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${i.count==1}">
                            <tr>
                                <td rowspan="${fn:length(mList)}">${bdMap[b.bdCode].bdName}</td>
                                <td>${b.bhzName}</td>
                                <td>${b.gcmc}</td>
                                <td>${b.lastTime}</td>
                                <td>
                                    <img src="images/bhz/${picName}" width="17" height="16"/>
                                </td>
                                <td>
                                    <img border="0" src="images/bhz/${b.isWeb}" width="17" height="16"
                                         title="${b.isWeb eq 'wifi-on.png'?'已联网':'未联网'}"/>
                                </td>
                                <td>
                                    <img src="images/bhz/${b.isSignIn}" width="17" height="16"
                                         title="${b.isSignIn eq 'hasSignIn.png'?'已登记':'未登记'}"/>
                                </td>
                                <td>
                                    <a onclick="openLoad();" href="${url}">查看</a>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td>
                                    ${b.bhzName}
                                </td>
                                <td>
                                    ${b.gcmc}
                                </td>
                                <td>
                                    ${b.lastTime}
                                </td>
                                <td>
                                    <img src="images/bhz/${picName}" width="17" height="16"/>
                                </td>
                                <td>
                                    <img border="0" src="images/bhz/${b.isWeb}" width="17" height="16" title="${b.isWeb eq 'wifi-on.png'?'已联网':'未联网'}"/>
                                </td>
                                <td>
                                    <img src="images/bhz/${b.isSignIn}" width="17" height="16" title="${b.isSignIn eq 'hasSignIn.png'?'已登记':'未登记'}"/>
                                </td>
                                <td>
                                    <a onclick="openLoad();" href="${url}">查看</a>
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:forEach>
        </table>
        <div class="pagin">
            <div class="message" style="height: 40px;border:0px ;">共<i class="blue">${page.totalRecord}</i>条记录，当前显示第&nbsp;<i class="blue">${page.currentPage}</i>页</div>
            <ul class="paginList">
                <li class="paginItem"><a href="javascript:;" onclick="gotoPage('1');" title="首页"><span class="pagepre"></span></a></li>
                <c:forEach var="i" begin="1" end="${page.totalPage}">
                    <c:choose>
                        <c:when test="${page.currentPage eq i}">
                            <li class="paginItem current"><a href="javascript:;" title="第${i}页">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="paginItem"><a href="javascript:;" onclick="gotoPage('${i}');" title="第${i}页">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <li class="paginItem"><a href="javascript:;"  onclick="gotoPage('${page.totalPage}');" title="尾页"><span class="pagenxt"></span></a></li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>