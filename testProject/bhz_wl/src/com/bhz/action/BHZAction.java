package com.bhz.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.bhz.pojo.TbBd;
import com.bhz.pojo.TbBhz;
import com.bhz.service.BDService;
import com.bhz.service.BHZService;
import com.bhz.service.UserService;
import com.bhz.util.ConstantUtil;
import com.bhz.util.FileUtil;
import com.bhz.util.PropertiesRW;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.Page;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BHZAction extends ActionSupport implements ModelDriven<TbBhz> {

	private static final long serialVersionUID = -4231299884218380168L;
	private TbBhz model = new TbBhz();
	private BHZService bhzServive;
	private BDService bdService;
	private UserService userServive;
	private Page page;

	public void setUserServive(UserService userServive) {
		this.userServive = userServive;
	}
	public void setBdService(BDService bdService) {
		this.bdService = bdService;
	}
	public void setBhzServive(BHZService bhzServive) {
		this.bhzServive = bhzServive;
	}
	public TbBhz getModel() {
		return this.model;
	}
	public void setPage(Page page) {
		this.page = page;
	}

	// 转到拌合站页面
	public String toBHZPage() {
		List<TbBd> allBD;
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			allBD = bdService.queryTbBd(request);
			Map<String, String> map = new HashMap<String, String>();
			for (TbBd b : allBD) {
				map.put(b.getBdCode(), b.getBdName());
			}
			request.setAttribute("map", map);
			Page allBHZ = bhzServive.getBhzByPage(request,page);
			request.setAttribute("objPage", allBHZ);
			request.setAttribute("BDs", allBD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toBHZPage";
	}

	// 转到添加拌合站页面
	public String toAddBHZPage() {
		List<TbBd> allBD;
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			allBD = bdService.queryTbBd(request);
			request.setAttribute("BDs", allBD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toAddBHZPage";
	}

	// 添加拌合站
	public String addBHZ() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			String regex = "^[A-Za-z0-9]+$";
			if(!model.getBhzCode().matches(regex)){
				return toAddBHZPage();
			}else{
				boolean flag = bhzServive.isBhzCode(model.getBhzCode());
				if(flag){
					request.setAttribute("bhzCodeIn", "1");
					return toAddBHZPage();
				}else{
					bhzServive.addBHZ(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toBHZPage();
	}

	// 根据拌合站id删除拌合站，且删除拌合站时其所属人员也要一并删除
	public String deleteBHZ() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			bhzServive.deleteBhz(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toBHZPage();
	}

	// 转到更新拌合站页面，要实现被修改拌合站信息回显
	public String toUpdatePage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			request.setAttribute("bhz", bhzServive.getBHZById(model.getId()));
			request.setAttribute("BDs", bdService.queryTbBd(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toUpdatePage";
	}

	// 更新拌合站
	public String updateBHZ() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			bhzServive.updateBHZ(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toBHZPage();
	}

	// 根据多个条件查询拌合站
	public String findBHZ() {
		List<TbBd> BDs = new ArrayList<TbBd>();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			Page objPage = bhzServive.getBHZsByMultiFields(request,page,model);
			BDs = bdService.queryTbBd(request);
			Map<String, String> map = new HashMap<String, String>();
			for (TbBd b : BDs) {
				map.put(b.getBdCode(), b.getBdName());
			}
			request.setAttribute("map", map);
			request.setAttribute("BDs", BDs);
			request.setAttribute("objPage", objPage);
			request.setAttribute("bhz", model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toBHZPage";
	}
	
	//设置是否为合并列显示
	public String setMergeShow(){
		PrintWriter out = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String id = request.getParameter("id");
		String flag = request.getParameter("flag");
		try {
			out = response.getWriter();
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(Util.isUserAuthor(request, key, "2")){
				if(Util.isEmpty(id) || Util.isEmpty(flag)){
					out.print("0");
				}else{
					String result = bhzServive.updateBhzIsMerge(id, flag);
					out.print(result);
				}
			}else{
				out.print("0");
			}
		} catch (Exception e) {
			out.print("0");
			System.out.println(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}
	
	//施工配比校正
	public String setSgphbTest(){
		PrintWriter out = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String id = request.getParameter("id");
		String flag = request.getParameter("flag");
		try {
			out = response.getWriter();
			String key = Util.getUserName(request)+"-"+ConstantUtil.fun_Sys;
			if(Util.isUserAuthor(request, key, "2")){
				if(Util.isEmpty(id) || Util.isEmpty(flag)){
					out.print("0");
				}else{
					String result = bhzServive.updateBhzIsTestSgpb(id, flag);
					out.print(result);
				}
			}else{
				out.print("0");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	//设置上传数据信息
	public String setOrDelDataInfo(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		try {
			TbBd bdObj = bhzServive.getTbBd(bdCode);
			TbBhz bhzObj = bhzServive.getTbBhz(bhzCode);
			request.setAttribute("CjStartTime", bhzObj.getCjStartTime());
			request.setAttribute("lastCollTime", bhzObj.getLastCollTime());
			request.setAttribute("bdObj", bdObj);
			request.setAttribute("bhzObj", bhzObj);
			request.setAttribute("bdCode", bdCode);
			request.setAttribute("bhzCode", bhzCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "setdeldata";
	}
	
	//保存设置上传数据信息
	public String saveSetDataInfo(){
		PrintWriter out = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		String maxCjStartTime = request.getParameter("maxCjStartTime");
		String custCjStartTime = request.getParameter("custCjStartTime");

		try {
			out = response.getWriter();
			if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode)){
				out.print("error");
			}else{
				String key = bdCode+"_"+bhzCode;
				if(!Util.isEmpty(custCjStartTime)){
					//if(!Util.isEmpty(maxCjStartTime)){
						//操作数据库并写入文件
						bhzServive.deleteDataBycjTime(bdCode, bhzCode,custCjStartTime);
						bhzServive.setCjTime(bdCode,bhzCode,custCjStartTime);
					//}
					//写入文件
					//String path = ConstantUtil.rootPath+"WEB-INF/classes/config/custGroupId.properties";
					//PropertiesRW rw = new PropertiesRW(path);
					//rw.writeProperties(key, custCjStartTime);
				}
				out.print("success");
			}
		} catch (Exception e) {
			out.print("error");
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}
	
	public String getclDate(){
		PrintWriter out = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		String selCode = request.getParameter("selCode");
		try {
			out = response.getWriter();
			if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode) || Util.isEmpty(selCode)){
				out.print("无记录");
			}else{
				String result = bhzServive.getClDateByGroupId(bdCode, bhzCode, selCode);
				out.print(result);
			}
		} catch (Exception e) {
			out.print("无记录");
			System.out.println(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}
	
	public String lookLogFile() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		try {
			String result = "";
			String path = ServletActionContext.getServletContext().getRealPath("/jfreeChartFile");
			StringBuffer fileContent = FileUtil.readFile(path + "\\" + bdCode + "_" + bhzCode + "_" + Util.getPatternDate(new Date(), "yyyy-MM-dd") + ".txt");
			if (!Util.isEmpty(fileContent.toString())) {
				result = fileContent.toString();
				result = result.replaceAll("\n", "<br>");
				request.setAttribute("bdCode", bdCode);
				request.setAttribute("bhzCode", bhzCode);
			}
			request.setAttribute("fileContent", result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "looklogfile";
	}
}
