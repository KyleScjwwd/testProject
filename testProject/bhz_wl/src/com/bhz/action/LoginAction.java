package com.bhz.action;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bhz.pojo.TbBd;
import com.bhz.pojo.TbBhz;
import com.bhz.service.*;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import com.bhz.util.ConstantUtil;
import com.bhz.util.EncryptData;
import com.bhz.util.FileUtil;
import com.bhz.util.JDomReaderXml;
import com.bhz.util.PropertiesRW;
import com.bhz.util.Util;
import com.bhz.pojo.TbSignIn;
import com.bhz.pojo.UserAuthority;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	private LoginService loginService;
	private MessageService messageService;
	private AuthorizationService authorizationService;
	private BHZService bhzService;
	private SignInService signInService;
	private BDService bdService;
	
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}	
	public void setAuthorizationService(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}
	public void setBhzService(BHZService bhzService) {
		this.bhzService = bhzService;
	}
	public void setSignInService(SignInService signInService) {
		this.signInService = signInService;
	}

	public void setBdService(BDService bdService) {
		this.bdService = bdService;
	}

	public String getGroupId(){
		PrintWriter out = null;
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		try {
			out = response.getWriter();
			if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode))
				out.print("");
			String key = bdCode+"_"+bhzCode;
			String groupIdPath = ConstantUtil.rootPath+"WEB-INF/classes/config/custGroupId.properties";
			PropertiesRW rw = new PropertiesRW(groupIdPath);
			String result = rw.readValue(key);
			if(Util.isEmpty(result)){
				out.print("");
			}else{
				rw.writeProperties(key, "");
				out.print("init@"+result);
			}
		} catch (Exception e) {
			out.print("");
			System.out.println(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}
	
	public String getMaxGroupId(){
		PrintWriter out = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		try {
			out = response.getWriter();
			if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode)){
				out.print("");
			}else{
				String result = bhzService.getMaxGroupId(bdCode, bhzCode);
				if (Util.isEmpty(result)) {
					out.print("");
				} else {
					out.print(result);
				}
			}
		} catch (Exception e) {
			out.print("");
			System.out.println(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}
	
	//拌合站Delphi客户端签到
	public String SetProductionState() {
		PrintWriter out = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		try {
			out = response.getWriter();
			if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode)){
				out.print("没有标段编号或拌合站编号");
			}else {
				List<TbBhz> tbBhzs=bhzService.queryTbBhzByBd(request, bdCode);
				if(null==tbBhzs || tbBhzs.size()==0){
					out.print("没有对应的标段编号或拌合站编号");
				}else{
					TbSignIn si = new TbSignIn();
					si.setBdCode(bdCode);
					si.setBhzCode(bhzCode);
					si.setSignInUser(bhzCode);
					si.setSignInDate(Util.getPatternDate(Util.getCurrentDate("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
					si.setSignInUser(Util.getUserName(request));
					if (!signInService.checkExist_Add(si)) {
						signInService.addSignIn(si);
					}
					out.print("ok");
				}
			}
		} catch (Exception e) {
			out.print("error");
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	//拌合站客户端版本上传
	public String setBhzVesion() {
		PrintWriter out = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");		
		String bhzVersion = request.getParameter("bhzVersion");
		String cjParam=request.getParameter("cjParam");
		String lastCollTime=request.getParameter("lastCollTime");
		try {
			out = response.getWriter();
			if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode)){
				out.print("");
			}else{
				String lastOnlineTime=Util.getPatternDate(new Date(),"yyyy-MM-dd HH:mm:ss");
				try {
					bhzService.updateClientVersionByCode(bdCode, bhzCode, bhzVersion,cjParam,lastCollTime,lastOnlineTime);
				}catch (Exception e){
					e.printStackTrace();
				}

				TbBhz tbBhz=bhzService.getBhzByCode(bdCode, bhzCode);
				if(null==tbBhz || tbBhz.getCjStartTime()==null || "".equals(tbBhz.getCjStartTime())){
					out.print("ok");
				}else{
					String cjStartTime=tbBhz.getCjStartTime();
					tbBhz.setCjStartTime("");
					bhzService.updateBHZ(tbBhz);
					out.print("ok:"+cjStartTime);
				}
			}
		} catch (Exception e) {
			out.print("error");
			e.printStackTrace();
		}
		return null;
	}


	public String setBhzParam(){
		PrintWriter out = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		try {
			out=response.getWriter();
			TbBhz tbBhz=bhzService.getBhzByCode(bdCode, bhzCode);
			if(tbBhz.getCjStartTime().equals("")){
				out.print("ok");
			}else{
				String cjStartTime=tbBhz.getCjStartTime();
				tbBhz.setCjStartTime("");
				bhzService.updateBHZ(tbBhz);
				out.print("ok:"+cjStartTime);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}


		return null;
	}


	public String updateStateAndLog(){
		PrintWriter out = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		try {
			out = response.getWriter();
			if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode)){
				out.print("error");
			}else{
				InputStream is = request.getInputStream();
				String path = ServletActionContext.getServletContext().getRealPath("/jfreeChartFile");
				String fileName = path + "\\" + bdCode + "_" + bhzCode + "_" + Util.getPatternDate(new Date(), "yyyy-MM-dd") + ".txt";
				boolean flag = FileUtil.writeFile(is, fileName);
				if(flag){
					out.print("ok");
				}else{
					out.print("error");
				}
			}
			return null;
		} catch (Exception e) {
			out.print("error");
			System.out.println(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}


	public String getBhzInfo() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		String bdCode=request.getParameter("bdCode");
		String bhzCode=request.getParameter("bhzCode");
		String pwd=request.getParameter("pwd");
		TbBhz tbBhz=bhzService.getBhzInfo(bdCode, bhzCode, pwd);
		TbBd  tbBd=bdService.getBDByBdCode(bdCode);
		if(null!=tbBhz && null!=tbBd){
			out.print("ok:"+tbBd.getBdName()+":"+tbBhz.getBhzName());
		}else{
			out.print("error");
		}
		return null;
	}
	
	public  String testService(){
		PrintWriter out = null;
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Lock lock = new ReentrantLock();
		lock.lock();
		try {
			System.out.println("upload begin...");
			out = response.getWriter();
			InputStream is = request.getInputStream();
			System.out.println("inputStream size="+is.available());
			String path = ServletActionContext.getServletContext().getRealPath("/jfreeChartFile");
			String fileObj = path + "\\" + "ds.xml";
			boolean flag = JDomReaderXml.writeFile(is, fileObj);
			if(flag){
				File f1 = new File(fileObj);
				InputStream in = new FileInputStream(f1);
			    byte[] byt = new byte[in.available()];
			    in.read(byt);
			    messageService.saveSendDataOrFile("1", byt);
			    out.print("ok");
			}
		} catch (Exception e) {
			out.print("error:"+e.getMessage());
			System.out.println(e.getMessage());
		} finally {
			lock.unlock();
			out.close();
		}
		return null;
	}

	public String userLogin(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			if(username!=null && password!=null){
				boolean isLogin = loginService.userLogin(username, password);
				if(isLogin){
					Map<String,String> userauthor = authorizationService.getUserAuthorityInfo(username);
					request.getSession().setAttribute("username", username);
					request.getSession().setAttribute("userauthor", userauthor);
					return SUCCESS;
				}
			}
			request.setAttribute("loginMsg", "error");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ERROR;
	}
	
	//外部系统访问
	@SuppressWarnings("unchecked")
	public String outUserLogin(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String call=request.getParameter("callback");
		String lgnm = request.getParameter("lgnm");
		String projectName = request.getParameter("pname");
		String projectId = request.getParameter("projectId");
		String bhzbase = request.getParameter("bhzbase");
		String bhzbd = request.getParameter("bhzbd");
		String bhzbhz = request.getParameter("bhzbhz");
		//String referer = request.getHeader("referer");
		try {
			if(Util.isEmpty(lgnm) || Util.isEmpty(projectName))
					return "outerror";
			if(Util.isEmpty(projectId))
				return "outerror";
			EncryptData ed = new EncryptData("td77011");
			String username = ed.decrypt(lgnm);
			String pname = ed.decrypt(projectName);
			Map<String,String> userauthor = setUserBhzAuthor(username, bhzbase, bhzbd, bhzbhz);
			request.setAttribute("projectId", projectId);
			request.getSession().setAttribute("pid", projectId);
			request.getSession().setAttribute("pname", pname);
			request.getSession().setAttribute("username", username);
			request.getSession().setAttribute("userauthor", userauthor);

			response.getWriter().print(call+"({'msg':'success'})");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String exitLogin(){
		HttpServletRequest request=ServletActionContext.getRequest();
		try {
			HttpSession session = request.getSession();
			session.removeAttribute("pid");
			session.removeAttribute("pname");
			session.removeAttribute("username");	//用户名
			session.removeAttribute("userauthor");	//用户权限
			session.removeAttribute("linkFunCode");	//公共链接
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "exit";
	}
	
	public Map<String,String> setUserBhzAuthor(String username,String bhzbase,String bhzbd,String bhzbhz)
		throws Exception{
		UserAuthority userAuth = null;
		List<UserAuthority> list = new ArrayList<UserAuthority>();
		Map<String,String> result = new HashMap<String,String>();
		EncryptData ed = new EncryptData("td77011");
		if(!Util.isEmpty(bhzbase)){
			String bhzbases = ed.decrypt(bhzbase);
			String[] arr = bhzbases.split(",");
			for(String s:arr){
				if(s.contains("_")){
					userAuth = setUserAuthValue(userAuth, "base", username, s);
					list.add(userAuth);
				}
			}
		}
		if(!Util.isEmpty(bhzbd)){
			String bhzbds = ed.decrypt(bhzbd);
			String[] arr = bhzbds.split(",");
			for(String s:arr){
				if(s.contains("_")){
					userAuth = setUserAuthValue(userAuth, "bd", username, s);
					list.add(userAuth);
				}
			}
		}
		if(!Util.isEmpty(bhzbhz)){
			String bhzbhzs = ed.decrypt(bhzbhz);
			String[] arr = bhzbhzs.split(",");
			for(String s:arr){
				if(s.contains("_")){
					userAuth = setUserAuthValue(userAuth, "bhz", username, s);
					list.add(userAuth);
				}
			}
		}
		for(UserAuthority u:list){
			result.put(u.getUserName()+"-"+u.getFunCode(), u.getAuthValue());
		}
		return result;
	}
	
	public UserAuthority setUserAuthValue(UserAuthority userAuth,String fix,String username,String s)
		throws Exception{
		userAuth = new UserAuthority();
		String[] arr = s.split("_");
		userAuth.setUserName(username);
		if(Util.isEmpty(arr[0]))
			userAuth.setFunCode("");
		else
			userAuth.setFunCode(fix+arr[0]);
		userAuth.setAuthValue(Util.isEmpty(arr[1])?"":arr[1]);
		return userAuth;
	}

	public String getNowDate(){
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			response.getWriter().print(dateFormat.format(new Date()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}