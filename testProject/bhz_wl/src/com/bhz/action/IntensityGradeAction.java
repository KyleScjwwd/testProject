package com.bhz.action;

import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.bhz.pojo.TbBd;
import com.bhz.pojo.TbBhz;
import com.bhz.pojo.TbIntensityGrade;
import com.bhz.service.BDService;
import com.bhz.service.BHZService;
import com.bhz.service.IntensityGradeService;
import com.bhz.util.ConstantUtil;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.Page;
import com.googlecode.jsonplugin.annotations.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class IntensityGradeAction extends ActionSupport implements ModelDriven<TbIntensityGrade> {

	private static final long serialVersionUID = -4123382507987922674L;
	private TbIntensityGrade model = new TbIntensityGrade();
	private Page page;
	private IntensityGradeService intensityGradeService;
	private BDService bdService;
	private BHZService bhzServicce;
	private String hint;

	public String getHint() {
		return hint;
	}

	@JSON(serialize = false)
	public TbIntensityGrade getModel() {
		return this.model;
	}
	
	public void setPage(Page page) {
		this.page = page;
	}
	
	public void setIntensityGradeService(IntensityGradeService intensityGradeService) {
		this.intensityGradeService = intensityGradeService;
	}

	public void setBdService(BDService bdService) {
		this.bdService = bdService;
	}

	public void setBhzServicce(BHZService bhzServicce) {
		this.bhzServicce = bhzServicce;
	}
	//跳转到强度等级页面
	public String toIntensityGradePage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			Page objPage = intensityGradeService.getAllIntensityGrade(page);
			request.setAttribute("objPage", objPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "intensityGrade";
	}
	
	//查询强度等级
	@JSON(serialize = false)
	public String getIntensityGrade() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			Page objPage = intensityGradeService.getIntensityGradeByMultiFields(page, model.getIntensityGrade());
			request.setAttribute("objPage", objPage);
			request.setAttribute("qddj", model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "intensityGrade";
	}
	
	//跳转到添加强度等级页面
	public String toAddIntensityGradePage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "toAddIntensityGradePage";
	}
	
	//添加强度等级
	public String addIntensityGrade() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			intensityGradeService.addIntensityGrade(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toIntensityGradePage();
	}
	
	//添加时，检查强度等级
	public String checkIntensityGrade_Add() {
		try {
			if (intensityGradeService.checkExist_Add(model)) {
				hint = "该强度等级已存在,请修改!";
			} else {
				hint = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "jsonString";
	}
	
	//修改时，检查强度等级
	public String checkIntensityGrade_Update() {
		try {
			if (intensityGradeService.checkExist_Update(model)) {
				hint = "该强度等级已存在,请修改!";
			} else {
				hint = "";
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "jsonString";
	}
	
	//删除强度等级
	public String deleteIntensityGrade() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			intensityGradeService.deleteIntensityGrade(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toIntensityGradePage();
	}
	
	//跳转到修改强度等级页面
	public String toUpdatePage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			TbIntensityGrade qddj = intensityGradeService.getIntensityGradeById(model.getId());
			List<TbBd> allBD = bdService.getAllBD();
			Collection<TbBhz> BHZs = bhzServicce.getBHZsByBdCode(request,qddj.getBdCode());
			
			request.setAttribute("BDs", allBD);
			request.setAttribute("BHZs", BHZs);
			request.setAttribute("qddj", qddj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toUpdatePage";
	}
	
	//修改强度等级
	public String updateIntensityGrade() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			intensityGradeService.updateIntensityGrade(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toIntensityGradePage();
	}
}
