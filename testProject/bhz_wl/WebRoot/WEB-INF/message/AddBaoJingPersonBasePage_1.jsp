<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加报警人员基本信息</title>
<link rel="stylesheet" type="text/css" href="css/bhz/index.css"/>
<link rel="stylesheet" type="text/css" href="css/bhz/style.css"/>
<script type="text/javascript" src="js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/givedata.js"></script>
<script type="text/javascript" src="js/BjPersonBase.js"></script>
<script type="text/javascript" src="js/selMessageUser.js"></script>
</head>
<body>
<div class="all">
    <div class="scsj">
    	<p>您当前的位置：<a href="javascript:;">拌合站监控系统 </a>&gt;&gt;<a href="javascript:;">短信报警</a>&gt;&gt;<a href="javascript:;">添加报警人员基本信息</a></p>
    </div>
    <div class="list">
        	<a class="onmouse" href="messageAction!getMessageUser.action">通迅录设置</a>
        	<a class="onmouse" href="messageAction!setMessage.action">短信报警设置</a>
        	<a class="onmouse" href="messageAction!setBaoJingBase.action">报警基本设置</a>
        	<a class="onmouse" href="messageAction!setBaoJingPersonBase.action">报警人员基本设置</a>
         	<a class="onmouse" href="messageAction!setMessageSendType.action">短信发送方式设置</a>
        	<a class="onmouse" href="messageAction!msgHistory.action">发送记录查询</a>
    </div>
    <form id="AddBaoJingPersonBaseForm" action="messageAction!addBaoJingPersonBase.action" method="post">
    <table class="bjdiv">
    	<tr>
        	<td width="30%" align="right">标段：</td>
            <td width="70%">
	            <select name="bdCode" id="bd" class="Fwidth" onchange="showBhz();">
					<option value="null">==请选择标段==</option>
					<c:forEach var="b" items="${bdList}">
						<option value="${b.bdCode}">${b.bdName}</option>
					</c:forEach>
				</select>
            </td>            
        </tr>
        <tr>
        	<td align="right">拌合站：</td>
            <td>
            	<select class="Fwidth" name="bhzCode" id="bhz" class="Fwidth">
               		<option value="">==请选择拌合站==</option>
               	</select>
           </td>           
      	</tr>
      	<tr>
      		<td class="FL" align="right">发送级别-初级（项目部）：</td>
      		<td>
      			<input type="button" value="选择" onclick="openPhoneDiv('userMobileCj');" style="float: left;"/>
      			<a id="userMobileCj" style="margin-top: 5px;"></a>
      			<input type="hidden" name="userMobileCj" value=""/>
      			<input type="hidden" id="userMobileCj" name="mobileCJ_bdCode" value=""/>
      		</td>
      	</tr>
      	<tr>
      		<td class="FL" align="right">发送级别-中级（监理）：</td>
      		<td>
      			<input type="button" value="选择" onclick="openPhoneDiv('userMobileZj');" style="float: left;"/>
      			<a id="userMobileZj" style="margin-top: 5px;"></a>
      			<input type="hidden" name="userMobileZj" value=""/>
      			<input type="hidden" id="userMobileZj" name="mobileZJ_bdCode" value=""/>
      		</td>
      	</tr>
      	<tr>
      		<td class="FL" align="right">发送级别-高级（指挥部）：</td>
      		<td>
      			<input type="button" value="选择" onclick="openPhoneDiv('userMobileGj');" style="float: left;"/>
      			<a id="userMobileGj" style="margin-top: 5px;"></a>
      			<input type="hidden" name="userMobileGj" value=""/>
      			<input type="hidden" id="userMobileGj" name="mobileGJ_bdCode" value=""/>
      		</td>      		
      	</tr>
        <tr>
            <td colspan="2" align="center">
            	<a href="javascript:;" class="bia" style="margin-left: 350px" onclick="BjPersonBase.CheckAddOrUpdate('add');">添加</a>
            	<a href="javascript:history.back();" class="bia">取消</a>
            </td>
       </tr>
    </table>
    </form>
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