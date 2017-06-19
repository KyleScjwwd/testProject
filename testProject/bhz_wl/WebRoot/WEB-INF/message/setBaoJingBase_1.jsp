<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>报警基本设置</title>
	<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
	<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
	<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
</head>

<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ3"/>
<div class="all">
    <div class="scsj">
    	<p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">报警基本设置 </a></p>
    </div>
    	<table class="Tab">
    		<tr>
    			<c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}">
    				<td><a href="messageAction!toAddBaoJingBasePage.action" class="tjia" style="margin-left: 450px;">添加</a></td>
    			</c:if>
    		</tr>
    	</table>
        <div class="list">
            	<a class="onmouse" href="messageAction!getMessageUser.action">通迅录设置</a>
            	<a class="onmouse" href="messageAction!setMessage.action">短信报警设置</a>
            	<a class="mouse">报警基本设置</a>
            	<a class="onmouse" href="messageAction!setBaoJingPersonBase.action">报警人员基本设置</a>
	         	<a class="onmouse" href="messageAction!setMessageSendType.action">短信发送方式设置</a>
            	<a class="onmouse" href="messageAction!msgHistory.action">发送记录查询</a>
        </div>
        <div class="sj_list">
            <table class="message" >
                <tr class="onbg">
                    <td>材料名称</td>
                    <td>是否短信报警</td>
                    <td>短信内容水泥值-C不在范围内（-C将替换为实际值）</td>
                    <td>发送级别（初/中/高）级</td>
                    <td>下限</td>
                    <td>上限</td>
                    <td>操作</td>
                </tr>
                <c:choose>
					<c:when test="${fn:length(objPage.objList)==0}">
						<td colspan="7" align="center" height="50" style="color:red">您还没有设置报警基本信息!</td>
					</c:when>
					<c:otherwise>
					  	<c:forEach var="b" items="${objPage.objList}" varStatus="i">
							<tr <c:if test="${i.index%2==0}">bgcolor="#F0F0F0"</c:if>>
							    <td rowspan="3">${b.clName}</td>
							    <td rowspan="3">${b.isBaojing=='1' ? '报警' : '不报警'}</td>
							    <td rowspan="3"><textarea rows="4" cols="60" type="_moz">${b.msgContent}</textarea></td>
							    <td>初级(项目部)</td>
							    <td>${b.downValueCj}</td>
							    <td>${b.upValueCj}</td>
							    <td rowspan="3">
			                        <div  class="tdwid">
			                            <div class="tr_odiv">
			                                <span class="tr_bg"></span>
			                                <a href="messageAction!toUpdatePage.action?id=${b.id}">编缉</a>
			                            </div>
			                            <div class="tr_div">
			                                <span class="tdbg"></span>
			                                <a href="messageAction!deleteBaoJingBase.action?id=${b.id}" del="del">删除</a>
			                            </div>
			                        </div>						    	
							    </td>
						    </tr>
						  	<tr <c:if test="${i.index%2==0}">bgcolor="#F0F0F0"</c:if>>
							    <td>中级(监理)</td>
							    <td>${b.downValueZj}</td>
							    <td>${b.upValueZj}</td>
						  	</tr>
						  	<tr <c:if test="${i.index%2==0}">bgcolor="#F0F0F0"</c:if>>
							    <td>高级(指挥部)</td>
							    <td>${b.downValueGj}</td>
							    <td>${b.upValueGj}</td>
						  	</tr>
					  	</c:forEach>
					</c:otherwise>
				</c:choose>
            </table>
        </div>
</div>
</body>
</html>