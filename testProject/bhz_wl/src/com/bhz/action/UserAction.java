package com.bhz.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.bhz.pojo.TbBd;
import com.bhz.pojo.TbBhz;
import com.bhz.pojo.UserInfo;
import com.bhz.service.BDService;
import com.bhz.service.BHZService;
import com.bhz.service.UserService;
import com.bhz.util.ConstantUtil;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.Page;
import com.googlecode.jsonplugin.annotations.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends ActionSupport implements ModelDriven<UserInfo> {

	private static final long serialVersionUID = -7271119156721769230L;
	private UserInfo model = new UserInfo();
	private UserService userService;
	private BDService bdService;
	private BHZService bhzServicce;
	private Collection<TbBhz> BHZs;
	private Page page;

	public void setBhzServicce(BHZService bhzServicce) {
		this.bhzServicce = bhzServicce;
	}

	public Collection<TbBhz> getBHZs() {
		return BHZs;
	}

	public void setBdService(BDService bdService) {
		this.bdService = bdService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setPage(Page page) {
		this.page = page;
	}

	@JSON(serialize = false)
	public UserInfo getModel() {
		return this.model;
	}
	
	//转到修改个人密码页面
	public String toUpdateUserPwd(){
		return "updatepwd";
	}
	
	//修改个人密码
	public String updateUserPwd(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		String subPwd = request.getParameter("subPwd");
		try {
			if(Util.isEmpty(oldPwd) || Util.isEmpty(newPwd) || Util.isEmpty(subPwd)){
				request.setAttribute("msgInfo", "<font color='red'>密码输入不能为空!</font>");
			}else{
				if(newPwd.equals(subPwd)){
					Object obj = request.getSession().getAttribute("username");
					if(obj==null){
						request.setAttribute("msgInfo", "<font color='red'>修改密码错误!</font>");
					}else{
						String username = obj.toString();
						Integer id = userService.queryUserPwd(username, oldPwd);
						if(id==null){
							request.setAttribute("msgInfo", "<font color='red'>老密码错误!</font>");
						}else{
							boolean flag = userService.updateUserPwd(id, subPwd);
							if(flag){
								request.setAttribute("msgInfo", "<font color='green'>修改密码成功!</font>");
							}else{
								request.setAttribute("msgInfo", "<font color='red'>修改密码错误!</font>");
							}
						}
					}
				}else{
					request.setAttribute("msgInfo", "<font color='red'>两次密码输入不一致!</font>");
				}
			}
		}catch(Exception e){
			request.setAttribute("msgInfo", "<font color='red'>修改密码错误!</font>");
		}
		return "updatepwd";
	}

	// 转到人员管理页面
	public String toUserPage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			List<TbBd> allBD = bdService.queryTbBd(request);
			List<TbBhz> allBHZ = bhzServicce.getAllBhz(request);
			Map<String, String> map = new HashMap<String, String>();
			for (TbBd b : allBD) {
				map.put(b.getBdCode(), b.getBdName());
			}
			request.setAttribute("map", map);
			Map<String, String> map2 = new HashMap<String, String>();
			for (TbBhz b : allBHZ) {
				map2.put(b.getBhzCode(), b.getBhzName());
			}
			
			request.setAttribute("map", map);
			request.setAttribute("map2", map2);
			Page objPage = userService.getUserByPage(request,page);
			request.setAttribute("objPage", objPage);
			request.setAttribute("BDs", allBD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toUserPage";
	}

	// 根据人员的id删除一个人员
	public String deleteUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			userService.deleteUser(model.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toUserPage();
	}

	// 转到添加人员页面
	public String toAddUserPage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			request.setAttribute("BDs", bdService.queryTbBd(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toAddUserPage";
	}

	// 添加人员时验证用户名是否已存在
	public String addUser() {
		boolean f = false;
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			f = userService.CheckUser(model.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (f) {
			// 返回添加页面时所属标段，所属拌合站和密码应该回显
			try {
				request.setAttribute("BDs", bdService.getAllBD());
				request.setAttribute("BHZs", bhzServicce.getAllBHZ());
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("user", model);
			request.setAttribute("hint", " 这个用户名已存在！");
			return "addError";
		} else {
			try {
				userService.addUser(model);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return toUserPage();
		}
	}

	// 跳转到修改人员页面，此页面也需要回显被修改人员的信息
	// 而且带回给修改页面的拌合站集合应该只是该用户所属标段下的拌合站
	public String toUpdatePage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			request.setAttribute("BDs", bdService.queryTbBd(request));
			request.setAttribute("BHZs", bhzServicce.getBHZsByBdCode(request,model.getBdCode()));
			request.setAttribute("user", userService.getUserById(model.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toUpdatePage";
	}

	// 完成人员修改，也要检查用户名是否已存在
	public String updateUser() {
		boolean f = false;
		boolean g = false;
		List<TbBd> allBD = null;
		List<TbBhz> allBHZ = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			f = userService.CheckUser(model.getUsername());
			allBD = bdService.getAllBD();
			allBHZ = bhzServicce.getAllBHZ();
			g = userService.getUserById(model.getId()).getUsername().equals(
					model.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (f && !g) {
			request.setAttribute("BDs", allBD);
			request.setAttribute("BHZs", allBHZ);
			request.setAttribute("hint", " 这个用户名已存在！");
			request.setAttribute("existName", model.getUsername());
			model.setUsername(null);
			request.setAttribute("user", model);
			return "updateError";
		} else {
			try {
				userService.updateUser(model);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return toUserPage();
		}
	}

	// Ajax，根据标段获得该标段下所有的拌合站
	@JSON(serialize = false)
	public String getBHZsByBdcode() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			BHZs = bhzServicce.getBHZsByBdCode(request,model.getBdCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "jsonString";
	}

	// 根据多个条件查询人员
	public String findUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			List<TbBd> allBD = bdService.queryTbBd(request);
			List<TbBhz> allBHZ = bhzServicce.getAllBhz(request);
			Map<String, String> map = new HashMap<String, String>();
			for (TbBd b : allBD) {
				map.put(b.getBdCode(), b.getBdName());
			}
			Map<String, String> map2 = new HashMap<String, String>();
			for (TbBhz b : allBHZ) {
				map2.put(b.getBhzCode(), b.getBhzName());
			}
			Page objPage = userService.getUsersByMultiFields(request,page,model);
			request.setAttribute("map", map);
			request.setAttribute("map2", map2);
			request.setAttribute("BDs", allBD);
			request.setAttribute("user", model);
			request.setAttribute("BHZs", allBHZ);
			request.setAttribute("objPage", objPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toUserPage";
	}
}
