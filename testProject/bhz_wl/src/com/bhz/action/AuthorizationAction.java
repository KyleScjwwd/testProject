package com.bhz.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.bhz.pojo.TbBaseAuthority;
import com.bhz.pojo.TbBd;
import com.bhz.pojo.TbBhz;
import com.bhz.pojo.UserAuthorityInfo;
import com.bhz.pojo.UserInfo;
import com.bhz.service.AuthorizationService;
import com.bhz.service.BDService;
import com.bhz.service.BHZService;
import com.bhz.service.UserService;
import com.bhz.util.ConstantUtil;
import com.bhz.util.Util;
import com.opensymphony.xwork2.ActionSupport;

public class AuthorizationAction extends ActionSupport {

	private static final long serialVersionUID = 6915214614288019991L;
	private String targetUrl = "";	//实际url
	private BDService bdService;
	private BHZService bhzServive;
	private UserService userService;
	private AuthorizationService authorizationService;

	public String getTargetUrl() {
		return targetUrl;
	}
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	public void setBdService(BDService bdService) {
		this.bdService = bdService;
	}
	public void setBhzServive(BHZService bhzServive) {
		this.bhzServive = bhzServive;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setAuthorizationService(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}
	
	public String setAuthorization() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			List<TbBd> allBD = bdService.getAllBD();
			Map<String, String> bdMap = new HashMap<String, String>();
			for (TbBd b : allBD) {
				bdMap.put(b.getBdCode(), b.getBdName());
			}
			List<UserInfo> users = userService.getAllUser();
			request.setAttribute("users", users);
			request.setAttribute("bdMap", bdMap);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "setAuthorization";
	}
	
	public String setUserAuthorization() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			List<TbBaseAuthority> baseAuthorityList = authorizationService.getAllBaseAuthority();
			List<UserAuthorityInfo> userAuthorityInfoList = authorizationService.getUserAuthByUserName(username);
			List<TbBd> bdList = bdService.getAllBD();
			List<TbBhz> bhzList = bhzServive.getAllBHZ();
			
			List<TbBaseAuthority> list_level1 = new ArrayList<TbBaseAuthority>();
			List<TbBaseAuthority> list_level2 = new ArrayList<TbBaseAuthority>();
			Map<String, String> authorityMap = new HashMap<String, String>();
			
			for (TbBaseAuthority a : baseAuthorityList) {
				if (1 == a.getLeve())
					list_level1.add(a);
				else if (2 == a.getLeve())
					list_level2.add(a);
			}
			
			for (UserAuthorityInfo u : userAuthorityInfoList) {
				authorityMap.put(u.getFunCode(), u.getAuthValue());
			}
			
			request.setAttribute("username", username);
			request.setAttribute("list_level1", list_level1);
			request.setAttribute("list_level2", list_level2);
			request.setAttribute("bdList", bdList);
			request.setAttribute("bhzList", bhzList);
			request.setAttribute("authorityMap", authorityMap);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "setUserAuthorization";
	}
	
	public String saveUserAuthorization() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String msg = "";
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			authorizationService.saveUserAuthorization(request, username, bdService.getAllBD(), bhzServive.getAllBHZ());
			msg = "权限保存成功";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			msg = "权限保存失败";
		}
		request.setAttribute("msg", msg);
		return setUserAuthorization();
	}
	
	//左侧列表根据权限重定向
	public String leftAuth(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String funCode = request.getParameter("funCode");
		try {
			if("fun01".equals(funCode)){		//系统管理
				String k = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
				if(!Util.isUserAuthor(request, k, "1") && !Util.isUserAuthor(request, k, "2"))
					return "noauth";
				targetUrl = getSubLinkAddr(request, funCode, 9);
			}else if("fun02".equals(funCode)){	//统计分析
				String k = Util.getUserName(request)+"-base"+ConstantUtil.fun_Count;
				if(!Util.isUserAuthor(request, k, "1") && !Util.isUserAuthor(request, k, "2"))
					return "noauth";
				targetUrl = getSubLinkAddr(request, funCode, 2);
			}else if("fun03".equals(funCode)){	//短信报警
				String k = Util.getUserName(request)+"-base"+ConstantUtil.fun_Msg;
				if(!Util.isUserAuthor(request, k, "1") && !Util.isUserAuthor(request, k, "2"))
					return "noauth";
				targetUrl = getSubLinkAddr(request, funCode, 3);
			}else if("fun04".equals(funCode)){	//生产数据
				String k = Util.getUserName(request)+"-base"+ConstantUtil.fun_Data;
				if(!Util.isUserAuthor(request, k, "1") && !Util.isUserAuthor(request, k, "2"))
					return "noauth";
				targetUrl = "mainAction!getGiveDate.action";
			}else if("fun05".equals(funCode)){	//生产状态
				String k = Util.getUserName(request)+"-base"+ConstantUtil.fun_State;
				if(!Util.isUserAuthor(request, k, "1") && !Util.isUserAuthor(request, k, "2"))
					return "noauth";
				targetUrl = "mainAction!mainIndex.action";
			}
		} catch (Exception e) {
			System.out.println();
		}
		if(targetUrl.equals("")){
			return "noauth";
		}else{
			return "targetUrl";
		}
	}

	//统计分析
	public String sysCountAuth(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String funCode = request.getParameter("funCode");
		try {
			if(1==1){
				if("fun0201".equals(funCode)){
					String k = Util.getUserName(request)+"-base"+ConstantUtil.fun_Data;
					if(!Util.isUserAuthor(request, k, "1") && !Util.isUserAuthor(request, k, "2"))
						return "noauth";
					targetUrl = "countAction!countGzbwSn.action";
				}else if("fun0202".equals(funCode)){
					String k = Util.getUserName(request)+"-base"+ConstantUtil.fun_Data;
					if(!Util.isUserAuthor(request, k, "1") && !Util.isUserAuthor(request, k, "2"))
						return "noauth";
					targetUrl = "countAction!countSnCn.action";
				}else{
					return "noauth";//无权限
				}
				request.setAttribute("targetUrl", targetUrl);
				request.getSession().setAttribute("linkFunCode", funCode);
			}else{
				return "noauth";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if(targetUrl.equals("")){
			return "noauth";
		}else{
			return "targetUrl";
		}
	}

	//生产状态
	public String sysMainIndexAuth(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String funCode = request.getParameter("funCode");
		try {
			if(1==1){
				if("fun05".equals(funCode)){
					String k = Util.getUserName(request)+"-base"+ConstantUtil.fun_Data;
					if(!Util.isUserAuthor(request, k, "1") && !Util.isUserAuthor(request, k, "2"))
						return "noauth";
					targetUrl = "mainAction!mainIndex.action";
				}else{
					return "noauth";//无权限
				}
				request.setAttribute("targetUrl", targetUrl);
				request.getSession().setAttribute("linkFunCode", funCode);
			}else{
				return "noauth";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if(targetUrl.equals("")){
			return "noauth";
		}else{
			return "targetUrl";
		}
	}

	//生产数据
	public String sysGiveDataAuth(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String funCode = request.getParameter("funCode");
		try {
			if(1==1){
				if("fun0401".equals(funCode)){
					String k = Util.getUserName(request)+"-base"+ConstantUtil.fun_Data;
					if(!Util.isUserAuthor(request, k, "1") && !Util.isUserAuthor(request, k, "2"))
						return "noauth";
					targetUrl = "mainAction!getGiveDate.action";
				}else if("fun0402".equals(funCode)) {
					String k = Util.getUserName(request)+"-base"+ConstantUtil.fun_Data;
					if(!Util.isUserAuthor(request, k, "1") && !Util.isUserAuthor(request, k, "2"))
						return "noauth";
					targetUrl = "mainAction!countTotalWc.action";
				}else if("fun0403".equals(funCode)){
					String k = Util.getUserName(request)+"-base"+ConstantUtil.fun_Data;
					if(!Util.isUserAuthor(request, k, "1") && !Util.isUserAuthor(request, k, "2"))
						return "noauth";
					targetUrl = "mainAction!countCzTotalWc.action";
				}else if("fun0404".equals(funCode)){
					String k = Util.getUserName(request)+"-base"+ConstantUtil.fun_Data;
					if(!Util.isUserAuthor(request, k, "1") && !Util.isUserAuthor(request, k, "2"))
						return "noauth";
					targetUrl = "countAction!getBhTime.action";
				}else{
					return "noauth";//无权限
				}
				request.setAttribute("targetUrl", targetUrl);
				request.getSession().setAttribute("linkFunCode", funCode);
			}else{
				return "noauth";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if(targetUrl.equals("")){
			return "noauth";
		}else{
			return "targetUrl";
		}
	}

	//短信报警
	public String sysMessageAuth(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String funCode = request.getParameter("funCode");
		try {
			if(1==1){
				if("fun0301".equals(funCode)){
					String k = Util.getUserName(request)+"-base"+ConstantUtil.fun_Data;
					if(!Util.isUserAuthor(request, k, "1") && !Util.isUserAuthor(request, k, "2"))
						return "noauth";
					targetUrl = "messageAction!getMessageUser.action";
				}else if("fun0302".equals(funCode)) {
					String k = Util.getUserName(request)+"-base"+ConstantUtil.fun_Data;
					if(!Util.isUserAuthor(request, k, "1") && !Util.isUserAuthor(request, k, "2"))
						return "noauth";
					targetUrl = "messageAction!setMessage.action";
				}else if("fun0303".equals(funCode)){
					String k = Util.getUserName(request)+"-base"+ConstantUtil.fun_Data;
					if(!Util.isUserAuthor(request, k, "1") && !Util.isUserAuthor(request, k, "2"))
						return "noauth";
					targetUrl = "messageAction!setBaoJingBase.action";
				}else if("fun0304".equals(funCode)){
					String k = Util.getUserName(request)+"-base"+ConstantUtil.fun_Data;
					if(!Util.isUserAuthor(request, k, "1") && !Util.isUserAuthor(request, k, "2"))
						return "noauth";
					targetUrl = "messageAction!setBaoJingPersonBase.action";
				}else if("fun0305".equals(funCode)){
					String k = Util.getUserName(request)+"-base"+ConstantUtil.fun_Data;
					if(!Util.isUserAuthor(request, k, "1") && !Util.isUserAuthor(request, k, "2"))
						return "noauth";
					targetUrl = "messageAction!setMessageSendType.action";
				}else if("fun0306".equals(funCode)){
					String k = Util.getUserName(request)+"-base"+ConstantUtil.fun_Data;
					if(!Util.isUserAuthor(request, k, "1") && !Util.isUserAuthor(request, k, "2"))
						return "noauth";
					targetUrl = "messageAction!msgHistory.action";
				}else{
					return "noauth";//无权限
				}
				request.setAttribute("targetUrl", targetUrl);
				request.getSession().setAttribute("linkFunCode", funCode);
			}else{
				return "noauth";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if(targetUrl.equals("")){
			return "noauth";
		}else{
			return "targetUrl";
		}
	}


	//系统管理中tab卡列表统一管理重定向并判断是否具有权限
	public String sysSystemAuth(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String funCode = request.getParameter("funCode");
		try {
			if(1==1){
				if("fun0101".equals(funCode)){//标段管理
					targetUrl = "BDAction!toBDPage.action";
				}else if("fun0102".equals(funCode)){//拌合站管理
					targetUrl = "BHZAction!toBHZPage.action";
				}else if("fun0103".equals(funCode)){//人员
					targetUrl = "UserAction!toUserPage.action";
				}else if("fun0104".equals(funCode)){//参数
					targetUrl = "mainAction!setParam.action";
				}else if("fun0105".equals(funCode)){//等级强度
					targetUrl = "intensityGradeAction!toIntensityGradePage.action";
				}else if("fun0106".equals(funCode)){//设计配合比
					targetUrl = "designPHBAction!getDesignPHB.action";
				}else if("fun0107".equals(funCode)){//过滤条件
					targetUrl = "mainAction!getFilterData.action";
				}else if("fun0108".equals(funCode)){//权限
					targetUrl = "authorizationAction!setAuthorization.action";
				}else if("fun0109".equals(funCode)){//合并列
					targetUrl = "mainAction!setMerge.action";
				}else if("fun01010".equals(funCode)){//拌合签到
					targetUrl = "signInAction!FindSignIn.action";
				}else if("fun0201".equals(funCode)){//混凝土产量
					targetUrl = "countAction!countGzbwSn.action";
				}else if("fun0202".equals(funCode)){//材料用量
					targetUrl = "countAction!countSnCn.action";
				}else if("fun0301".equals(funCode)){//通迅录设置
					targetUrl = "messageAction!getMessageUser.action";
				}else if("fun0302".equals(funCode)){//短信报警设置
					targetUrl = "messageAction!setMessage.action";
				}else if("fun0303".equals(funCode)){//发送记录查询
					targetUrl = "messageAction!msgHistory.action";
				}else if("fun0304".equals(funCode)){//报警基本设置
					targetUrl = "messageAction!setBaoJingBase.action";
				}else if("fun0305".equals(funCode)){//报警人员基本设置
					targetUrl = "messageAction!setBaoJingPersonBase.action";
				}else if("fun0306".equals(funCode)){//短信发送方式设置
					targetUrl = "messageAction!setMessageSendType.action";
				}else{
					return "noauth";//无权限
				}
				request.setAttribute("targetUrl", targetUrl);
				request.getSession().setAttribute("linkFunCode", funCode);
			}else{
				return "noauth";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if(targetUrl.equals("")){
			return "noauth";
		}else{
			return "targetUrl";
		}
	}

	public String getSubLinkAddr(HttpServletRequest request,String funCode,int endNum)throws Exception{
		String result = "";
		for(int i=1;i<=endNum;i++){
			String subFunCode = funCode+"0"+i;
			if(1==1){
				result = "authorizationAction!sysSystemAuth.action?funCode="+subFunCode;
				break;
			}
		}
		return result;
	}
}
