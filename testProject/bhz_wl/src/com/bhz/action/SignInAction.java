package com.bhz.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.bhz.pojo.TbBd;
import com.bhz.pojo.TbBhz;
import com.bhz.pojo.TbSignIn;
import com.bhz.service.BDService;
import com.bhz.service.BHZService;
import com.bhz.service.SignInService;
import com.bhz.util.ConstantUtil;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.Page;
import com.googlecode.jsonplugin.annotations.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class SignInAction extends ActionSupport implements ModelDriven<TbSignIn> {
	
	private static final long serialVersionUID = 8073410565455060523L;
	private TbSignIn model = new TbSignIn();
	private Page page;
	private SignInService signInService;
	private BDService bdService;
	private BHZService bhzServicce;
	private String hint;

	public String getHint() {
		return hint;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	@JSON(serialize = false)
	public TbSignIn getModel() {
		return this.model;
	}
	public void setSignInService(SignInService signInService) {
		this.signInService = signInService;
	}
	public void setBdService(BDService bdService) {
		this.bdService = bdService;
	}
	public void setBhzServicce(BHZService bhzServicce) {
		this.bhzServicce = bhzServicce;
	}
	
	//查询签到记录
	public String FindSignIn() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String sdate = request.getParameter("sdate");
			String edate = request.getParameter("edate");
			
			List<TbBd> allBD = bdService.queryTbBd(request);
			List<TbBhz> allBHZ = bhzServicce.getAllBhz(request);
			Map<String, String> bdMap = new HashMap<String, String>();
			for (TbBd b : allBD) {
				bdMap.put(b.getBdCode(), b.getBdName());
			}
			Map<String, String> bhzMap = new HashMap<String, String>();
			for (TbBhz b : allBHZ) {
				bhzMap.put(b.getBhzCode(), b.getBhzName());
			}
			Page objPage = signInService.getSignInByMultiFields(page, model, sdate, edate);
			request.setAttribute("bdMap", bdMap);
			request.setAttribute("bhzMap", bhzMap);
			request.setAttribute("BDs", allBD);
			request.setAttribute("si", model);
			request.setAttribute("sdate", sdate);
			request.setAttribute("edate", edate);
			request.setAttribute("objPage", objPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "SignIn";
	}
	
	//转到添加签到记录页面
	public String toAddSignInPage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if (!Util.isUserAuthor(request, key, "2")) {
				return "noauth";
			}
			model.setSignInUser(Util.getUserName(request));
			model.setSignInDate(new Date());
			request.setAttribute("BDs", bdService.queryTbBd(request));
			request.setAttribute("si", model);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return "toAddSignInPage";
	}
	
	//添加签到记录
	public String addSignIn() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if (!Util.isUserAuthor(request, key, "2")) {
				return "noauth";
			}
			signInService.addSignIn(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FindSignIn();
	}
	
	//添加时，检查是否已存在该签到记录
	public String checkSignIn_Add() {
		try {
			if (signInService.checkExist_Add(model)) {
				hint = "该签到记录已存在,请修改!";
			} else {
				hint = "";
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "jsonString";
	}
	
	//修改时，检查是否已存在该签到记录
	public String checkSignIn_Update() {
		try {
			if (signInService.checkExist_Update(model)) {
				hint = "该签到记录已存在,请修改!";
			} else {
				hint = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "jsonString";
	}
	
	//跳转到修改签到记录页面
	public String toUpdatePage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if (!Util.isUserAuthor(request, key, "2")) {
				return "noauth";
			}
			List<TbBd> allBD = bdService.queryTbBd(request);
			String id = request.getParameter("id");
			model = signInService.getSignInById(id);
			
			request.setAttribute("BDs", allBD);
			request.setAttribute("si", model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toUpdatePage";
	}
	
	//修改签到记录
	public String updateSignIn() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if (!Util.isUserAuthor(request, key, "2")) {
				return "noauth";
			}
			signInService.updateSignIn(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.setBdCode("");
		model.setBhzCode("");
		return FindSignIn();
	}

}
