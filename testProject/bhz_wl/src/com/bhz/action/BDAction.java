package com.bhz.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import com.bhz.pojo.TbBd;
import com.bhz.service.BDService;
import com.bhz.service.BHZService;
import com.bhz.service.UserService;
import com.bhz.util.ConstantUtil;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.Page;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import java.io.PrintWriter;

public class BDAction extends ActionSupport implements ModelDriven<TbBd> {

	private static final long serialVersionUID = -5518500763154076564L;
	private TbBd model = new TbBd();
	private BDService bdService;
	private BHZService bhzService;
	private UserService userService;
	private String flagBdCode;
	private Page page;

	public void setPage(Page page) {
		this.page = page;
	}
	public void setBhzService(BHZService bhzService) {
		this.bhzService = bhzService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setFlagBdCode(String flagBdCode) {
		this.flagBdCode = flagBdCode;
	}
	public void setBdService(BDService bdService) {
		this.bdService = bdService;
	}

	public TbBd getModel() {
		return this.model;
	}

	// 跳转至标段管理页面，同时要查出所有的标段信息列出来
	public String toBDPage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			Page allBD = bdService.getBDByPage(request,page);
			request.setAttribute("objPage", allBD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toBDPage";
	}

	// 添加一个标段，由于标段编号必须唯一，所以添加标段之前要先检查标段编号是否已存在
	public String addBD() {
		boolean f = false;
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			f = bdService.checkBdCode(model.getBdCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String regex = "^[A-Za-z0-9]+$";
		if(!model.getBdCode().matches(regex)){
			request.setAttribute("hint", " 标段编号只能是字母和数字!");
			request.setAttribute("bd", model);
			return "addError";
		}else{
			if (f) {
				request.setAttribute("hint", " 这个标段编号已存在！");
				request.setAttribute("bd", model);
				return "addError";
			} else {
				try {
					bdService.addBD(model);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return toBDPage();
			}
		}
	}

	public String checkBDtoBHZ() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		PrintWriter out=response.getWriter();
		String bdCode=request.getParameter("bdCode");
		boolean f=bdService.checkBDtoBHZ(bdCode);
		out.print(f);
		return null;
	}

	// 根据“标段编号”删除一个标段，同时该标段下的拌合站，人员也要一并删除
	public String deleteBD() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			bdService.deleteBD(model.getBdCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toBDPage();
	}

	// 转到添加标段页面
	public String toAddBdPage() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "toAddBdPage";
	}

	// 转到更新标段页面
	public String toUpdatePage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			model = bdService.getBDByBdCode(model.getBdCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("bd", model);
		return "toUpdatePage";
	}

	// 修改标段，先要检查标段编号是否已存在
	public String UpdateBD() {
		boolean f = false;
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			f = bdService.checkBdCode(model.getBdCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (f && !flagBdCode.equals(model.getBdCode())) {
			request.setAttribute("hint", " 这个标段编号已存在！");
			request.setAttribute("existName", model.getBdCode());
			model.setBdCode(null);
			request.setAttribute("bd", model);
			return "updateError";
		} else {
			try {
				bdService.updateBD(model);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return toBDPage();
		}
	}

	// 根据多个条件查询标段信息
	public String findBD() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			Page objPage = bdService.getBDsByMultiFields(request,page,model);
			request.setAttribute("objPage",objPage);
			request.setAttribute("bd", model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toBDPage";
	}
}
