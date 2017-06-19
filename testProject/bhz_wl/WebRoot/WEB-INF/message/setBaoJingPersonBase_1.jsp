<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/page-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>报警人员基本设置</title>
	<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
	<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
	<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
	<script type="text/javascript" src="js/givedata.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/selMessageUser.js"></script>
</head>

<body>
<c:set var="authPage" value="${sessionScope.username}-baseBHZ3"/>
<div class="all">
    <div class="scsj">
    	<p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">报警人员基本设置 </a></p>
    </div>
	<input type="hidden" id="bdCode" name="bdCode" value="${bdCode}"/>
	<input type="hidden" id="bhzCode" name="bhzCode" value="${bhzCode}"/>
   	<form id="FindBaoJingPersonBaseForm" action="messageAction!setBaoJingPersonBase.action" method="post" >
   	<table class="Tab">
   		<tr>
            <td class="FL">标段：</td>
            <td>
	            <select class="Fwidth" id="bd" name="bd" onchange="showBhz();" style="width: 120px">            
	            	<option value="">==请选择标段==</option>
					<c:forEach var="b" items="${BDs}">
						<option value="${b.bdCode}">${b.bdName}</option>
					</c:forEach>
	            </select>
            </td>
            <td class="FL">拌合站：</td>
            <td>
	            <select class="Fwidth" name="bhz" id="bhz" style="width: 120px">            
	            	<option value="">==请选择拌合站==</option>
	            </select>
            </td>
            <td><a href="#" class="search" onclick="$(FindBaoJingPersonBaseForm).submit();">查询</a></td>
            <c:if test="${sessionScope.userauthor[authPage] eq '2' || sessionScope.username eq 'admin'}"> 
   				<td><a href="messageAction!toAddBaoJingPersonBasePage.action" class="tjia">添加</a></td>
   			</c:if>
   		</tr>
   	</table>
   	</form>
        <div class="list">
            	<a class="onmouse" href="messageAction!getMessageUser.action">通迅录设置</a>
            	<a class="onmouse" href="messageAction!setMessage.action">短信报警设置</a>
            	<a class="onmouse" href="messageAction!setBaoJingBase.action">报警基本设置</a>
            	<a class="mouse">报警人员基本设置</a>
	         	<a class="onmouse" href="messageAction!setMessageSendType.action">短信发送方式设置</a>
            	<a class="onmouse" href="messageAction!msgHistory.action">发送记录查询</a>
        </div>
        <div class="sj_list">
            <table class="message" >
                <tr class="onbg">
                    <td>标段</td>
                    <td>拌合站</td>
                    <td width="20%">发送级别（初/中/高）级</td>
                    <td width="50%">报警发送人员</td>
                    <td>操作</td>
                </tr>
                <c:choose>
					<c:when test="${fn:length(objPage.objList)==0}">
						<td colspan="5" align="center" height="50" style="color:red">您还没有设置报警基本人员信息!</td>
					</c:when>
					<c:otherwise>
					  	<c:forEach var="b" items="${objPage.objList}" varStatus="i">
							<tr <c:if test="${i.index%2==0}">bgcolor="#F0F0F0"</c:if>>
							    <td rowspan="3">${bdMap[b.bdCode]}</td>
							    <td rowspan="3">${bhzMap[b.bhzCode]}</td>
							    <td>初级(项目部)</td>
							    <td>
							    	<c:set var="msgUsers" value=""/>
							    	<c:forEach var="u" items="${messageUserList}">
							    		<c:if test="${fn:contains(b.userMobileCj, u.telphone)==true}">
							    			<c:set var="msgUsers" value="${msgUsers} ${u.realName}(${u.telphone})"/>
							    		</c:if>
							    	</c:forEach>
							    	${msgUsers}
							    </td>
							    <td rowspan="3">
			                        <div  class="tdwid">
			                            <div class="tr_odiv">
			                                <span class="tr_bg"></span>
			                                <a href="messageAction!toUpdateBaoJingPersonPage.action?id=${b.id}">编缉</a>
			                            </div>
			                            <div class="tr_div">
			                                <span class="tdbg"></span>
			                                <a href="messageAction!deleteBaoJingPersonBase.action?id=${b.id}" del="del">删除</a>
			                            </div>
			                        </div>						    	
							    </td>
						    </tr>
						  	<tr <c:if test="${i.index%2==0}">bgcolor="#F0F0F0"</c:if>>
							    <td>中级(监理)</td>
							    <td>
							    	<c:set var="msgUsers" value=""/>
							    	<c:forEach var="u" items="${messageUserList}">
							    		<c:if test="${fn:contains(b.userMobileZj, u.telphone)==true}">
							    			<c:set var="msgUsers" value="${msgUsers} ${u.realName}(${u.telphone})"/>
							    		</c:if>
							    	</c:forEach>
							    	${msgUsers}
							    </td>
						  	</tr>
						  	<tr <c:if test="${i.index%2==0}">bgcolor="#F0F0F0"</c:if>>
							    <td>高级(指挥部)</td>
							    <td>
							    	<c:set var="msgUsers" value=""/>
							    	<c:forEach var="u" items="${messageUserList}">
							    		<c:if test="${fn:contains(b.userMobileGj, u.telphone)==true}">
							    			<c:set var="msgUsers" value="${msgUsers} ${u.realName}(${u.telphone})"/>
							    		</c:if>
							    	</c:forEach>
							    	${msgUsers}							    
							    </td>
						  	</tr>
					  	</c:forEach>
					</c:otherwise>
				</c:choose>
            </table>
            <div class="clear"></div>
            <p:PagingTag url="messageAction!setBaoJingPersonBase.action" page="${objPage}" forPage="true" showCurAndSum="true"/>
        </div>
</div>
</body>
</html>