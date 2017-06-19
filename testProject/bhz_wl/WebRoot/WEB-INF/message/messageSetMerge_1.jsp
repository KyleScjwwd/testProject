<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>合并列短信报警设置</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/givedata.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/selMessageUser.js"></script>
<script type="text/javascript">
	function saveParam() {
		var bd = $("#bdCode").val();
		var bhz = $("#bhzCode").val();
		var phbQd = $("#phbQd").val();
		var mergeColsList = $("#mergeColsList").val();
		if(mergeColsList==null || mergeColsList=="0"){
			alert("没有可以保存的数据!");
			return false;
		}
		if(bd=="" || bhz==""){
			alert("请先选择标段和拌合站!");
			return false;
		}
		if(phbQd==""){
			alert("请选择配合比强度!");
			return false;
		}
		if(confirm("确定保存")){
			$("#spanMsg").html("保存数据中...");
			$("#form2").attr("action","messageAction!saveMessage.action?bd="+bd+"&bhz="+bhz+"&phbQd="+phbQd);
			$("#form2").submit();
		}
		return false;
	}
</script>
</head>

<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ3"/>
<div class="all">
    <div class="scsj">
    <p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">合并列短信报警 </a></p>
    </div>
    <form id="form1" action="messageAction!setMessage.action" method="post">
    <table class="Tab">
        <tr>
            <td class="FL">标段名称：</td>
            <td>
            	<input type="hidden" id="mergeColsList" value="${fn:length(mergeColsList)}"/>
				<input type="hidden" id="bdCode" name="bdCode" value="${bdCode}"/>
				<input type="hidden" id="bhzCode" name="bhzCode" value="${bhzCode}"/>
                <select class="Fwidth" id="bd" name="bd" onchange="showBhz();" style="width:150px;">
                	<option value="">==请选择标段==</option>
						<c:forEach var="b" items="${bdList}">
					  		<option <c:if test="${bdCode eq b.bdCode}">selected="selected"</c:if> value="${b.bdCode}">${b.bdName}</option>
					  	</c:forEach>
              	</select>
            </td>
            <td class="FL">拌合站名称：</td>
            <td>
            	<select class="Fwidth" name="bhz" id="bhz" style="width:150px;">
               		<option value="">==请选择拌合站==</option>
               	</select>
            </td>
            <td class="FL">配合比强度：</td>
            <td>
            	<select class="Fwidth" name="phbQd" id="phbQd" style="width:150px;">
	               	<option value="">==配合比强度==</option>
	               	<c:forEach var="q" items="${qdList}">
	               		<option value="${q}" ${q eq phbQd?'selected':''}>${q}</option>
	               	</c:forEach>
               	</select>
            </td>
            <td><a href="javascript:;" class="search" onclick="seleParam();">查询</a></td>
			<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
            	<td><a href="javascript:;" class="tjia" onclick="saveParam();">保存</a></td>
            </c:if>
            <td><span id="spanMsg" style="color: red">${msg}</span></td>
        </tr>
    </table>
    </form>
        <div class="list">
            	<a class="onmouse" href="messageAction!getMessageUser.action">通迅录设置</a>
            	<a class="mouse">短信报警设置</a>
            	<a class="onmouse" href="messageAction!setBaoJingBase.action">报警基本设置</a>
            	<a class="onmouse" href="messageAction!setBaoJingPersonBase.action">报警人员基本设置</a>
	         	<a class="onmouse" href="messageAction!setMessageSendType.action">短信发送方式设置</a>
            	<a class="onmouse" href="messageAction!msgHistory.action">发送记录查询</a>
        </div>
        <div class="sj_list">
        	<form id="form2" method="post">
            <table class="message" >
                <tr class="onbg">
                    <td>材料名称</td>
                    <td>是否短信报警</td>
                    <td>短信内容水泥值-C不在范围内（-C将替换为实际值）</td>
                    <td>发送级别（初/中/高）级</td>
                    <td>手机号码</td>
                    <td>下限</td>
                    <td>上限</td>
                </tr>
                <c:choose>
					<c:when test="${fn:length(mergeColsList)==0}">
						<td colspan="7" align="center" height="50" style="color:red">
							<c:choose>
								<c:when test="${nodata eq '1'}">该标段下的拌合站暂未设置参数，请先至参数页面进行设置!</c:when>
								<c:otherwise>请先选择需要查询的标段和拌合站!</c:otherwise>
							</c:choose>				
						</td>
					</c:when>
					<c:otherwise>
					  	<tr>
						  	<c:set var="mapKey1" value="${bdCode}-${bhzCode}-${phbQd}-bht"/>
						    <td rowspan="3">拌合时间</td>
						    <td rowspan="3">
						    <input type="radio" name="isBaojing-bht" <c:if test="${mainMergeMap[mapKey1].isBaojing eq '1'}">checked="checked"</c:if> value="1"/>报警
						    <input type="radio" name="isBaojing-bht" <c:if test="${mainMergeMap[mapKey1].isBaojing eq '0'}">checked="checked"</c:if> value="0"/>不报警
						    </td>
						    <td rowspan="3"><textarea rows="4" cols="60" name="msgContent-bht" type="_moz">${mainMergeMap[mapKey1].msgContent}</textarea></td>
						    <td>初级(项目部)<c:set var="subKey" value="${mapKey1}-0"/><input type="hidden" name="sendType-bht-0" value="0"/></td>
						    <td>
						    <input type="button" value="选择" onclick="openPhoneDiv('mobile-bht-0');"/>
						    <input type="hidden" name="mobile-bht-0" value="${subMergeMap[subKey].mobile}"/>
						    <input type="hidden" id="mobile-bht-0" name="mobile_bdCode-bht-0" value="${subMergeMap[subKey].mobile_bdCode}"/>
						    </td>
						    <td><input type="text" style="width:60px" name="down-bht-0" value="${subMergeMap[subKey].downValue}"/></td>
						    <td><input type="text" style="width:60px" name="up-bht-0" value="${subMergeMap[subKey].upValue}"/></td>
					  	</tr>
					  	<tr>
						    <td>中级(监理)<c:set var="subKey" value="${mapKey1}-1"/><input type="hidden" name="sendType-bht-1" value="1"/></td>
						    <td>
						    <input type="button" value="选择" onclick="openPhoneDiv('mobile-bht-1');"/>
						    <input type="hidden" name="mobile-bht-1" value="${subMergeMap[subKey].mobile}"/>
						    <input type="hidden" id="mobile-bht-1" name="mobile_bdCode-bht-1" value="${subMergeMap[subKey].mobile_bdCode}"/>
						    </td>
						    <td><input type="text" style="width:60px" name="down-bht-1" value="${subMergeMap[subKey].downValue}"/></td>
						    <td><input type="text" style="width:60px" name="up-bht-1" value="${subMergeMap[subKey].upValue}"/></td>
					  	</tr>
					  	<tr>
						    <td>高级(指挥部)<c:set var="subKey" value="${mapKey1}-2"/><input type="hidden" name="sendType-bht-2" value="2"/></td>
						    <td>
						    <input type="button" value="选择" onclick="openPhoneDiv('mobile-bht-2');"/>
						    <input type="hidden" name="mobile-bht-2" value="${subMergeMap[subKey].mobile}"/>
						    <input type="hidden" id="mobile-bht-2" name="mobile_bdCode-bht-2" value="${subMergeMap[subKey].mobile_bdCode}"/>
						    </td>
						    <td><input type="text" style="width:60px" name="down-bht-2" value="${subMergeMap[subKey].downValue}"/></td>
						    <td><input type="text" style="width:60px" name="up-bht-2" value="${subMergeMap[subKey].upValue}"/></td>
					  	</tr>
					  	<c:forEach var="p" items="${mergeColsList}" varStatus="i">
							<tr <c:if test="${i.index%2==0}">bgcolor="#F0F0F0"</c:if>>
							  	<c:set var="mapKey2" value="${bdCode}-${bhzCode}-${phbQd}-${p.mergeCols}"/>
							    <td rowspan="3">${p.mergeColsName}</td>
							    <td rowspan="3">
							    <input type="radio" name="isBaojing-${p.mergeCols}" <c:if test="${mainMergeMap[mapKey2].isBaojing eq '1'}">checked="checked"</c:if> value="1"/>报警
							    <input type="radio" name="isBaojing-${p.mergeCols}" <c:if test="${mainMergeMap[mapKey2].isBaojing eq '0'}">checked="checked"</c:if> value="0"/>不报警</td>
							    <td rowspan="3"><textarea rows="4" cols="60" name="msgContent-${p.mergeCols}" type="_moz">${mainMergeMap[mapKey2].msgContent}</textarea></td>
							    <td>初级(项目部)<c:set var="subKey" value="${mapKey2}-0"/><input type="hidden" name="sendType-${p.mergeCols}-0" value="0"/></td>
							    <td>
							    <input type="button" value="选择" onclick="openPhoneDiv('mobile-${p.mergeCols}-0');"/>
							    <input type="hidden" name="mobile-${p.mergeCols}-0" value="${subMergeMap[subKey].mobile}"/>
							    <input type="hidden" id="mobile-${p.mergeCols}-0" name="mobile_bdCode-${p.mergeCols}-0" value="${subMergeMap[subKey].mobile_bdCode}"/>
							    </td>
							    <td><input type="text" style="width:60px" name="down-${p.mergeCols}-0" value="${subMergeMap[subKey].downValue}"/></td>
							    <td><input type="text" style="width:60px" name="up-${p.mergeCols}-0" value="${subMergeMap[subKey].upValue}"/></td>
						    </tr>
						  	<tr <c:if test="${i.index%2==0}">bgcolor="#F0F0F0"</c:if>>
							    <td>中级(监理)<c:set var="subKey" value="${mapKey2}-1"/><input type="hidden" name="sendType-${p.mergeCols}-1" value="1"/></td>
							    <td>
							    <input type="button" value="选择" onclick="openPhoneDiv('mobile-${p.mergeCols}-1');"/>
							    <input type="hidden" name="mobile-${p.mergeCols}-1" value="${subMergeMap[subKey].mobile}"/>
							    <input type="hidden" id="mobile-${p.mergeCols}-1" name="mobile_bdCode-${p.mergeCols}-1" value="${subMergeMap[subKey].mobile_bdCode}"/>
							    </td>
							    <td><input type="text" style="width:60px" name="down-${p.mergeCols}-1" value="${subMergeMap[subKey].downValue}"/></td>
							    <td><input type="text" style="width:60px" name="up-${p.mergeCols}-1" value="${subMergeMap[subKey].upValue}"/></td>
						  	</tr>
						  	<tr <c:if test="${i.index%2==0}">bgcolor="#F0F0F0"</c:if>>
							    <td>高级(指挥部)<c:set var="subKey" value="${mapKey2}-2"/><input type="hidden" name="sendType-${p.mergeCols}-2" value="2"/></td>
							    <td>
							    <input type="button" value="选择" onclick="openPhoneDiv('mobile-${p.mergeCols}-2');"/>
							    <input type="hidden" name="mobile-${p.mergeCols}-2" value="${subMergeMap[subKey].mobile}"/>
							    <input type="hidden" id="mobile-${p.mergeCols}-2" name="mobile_bdCode-${p.mergeCols}-2" value="${subMergeMap[subKey].mobile_bdCode}"/>
							    </td>
							    <td><input type="text" style="width:60px" name="down-${p.mergeCols}-2" value="${subMergeMap[subKey].downValue}"/></td>
							    <td><input type="text" style="width:60px" name="up-${p.mergeCols}-2" value="${subMergeMap[subKey].upValue}"/></td>
						  	</tr>
					  	</c:forEach>
					</c:otherwise>
				</c:choose>
            </table>
            </form>
            <div class="clear"></div>
        </div>
</div>
<div id="popDiv" class="all_title" style="display:none;">
	<div class="title">手机通迅选择</div>
	<table class="tx_tab">
    	<tr>
        	<td style="width:30%; padding-left:20px;">
            	<p><img src="images/page.gif" width="18" height="18" />标段列表</p>
                <div class="tx_a">
                	<input type="hidden" id="mobileId" value=""/>
                	<c:forEach var="b" items="${bdList}">
                	<a id="treeSpan${b.bdCode}" href="javascript:;" onclick="getPhoneInfo('${b.bdCode}');">
                    	<img src="images/join.gif" width="18" height="18" />
                        <img src="images/page.gif" width="18" height="18" />
                        ${b.bdName}
                    </a>
                    </c:forEach>
                </div>
            </td>
           	<td style="width:70%;">
           		<div id="lianxiDiv" style="height:250px;width: 100%;overflow-y:scroll;text-align: left;"></div>
           	</td>
        </tr>
    </table>
    <div class="tit_but">
    	<div class="tit_a">
	    	<input type="hidden" id="allSelectBd" value="" />
            <a href="javascript:;" onclick="isChecked('1');">全选</a>
            <a href="javascript:;" onclick="isChecked('0');">反选</a>
            <a href="javascript:;" onclick="queDing();">确定</a>
            <a href="javascript:;" onclick="closeDiv();">关闭</a>
        </div>
    </div>
</div>
<div id="bg" class="bg" style="display:none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe> 
</body>
</html>