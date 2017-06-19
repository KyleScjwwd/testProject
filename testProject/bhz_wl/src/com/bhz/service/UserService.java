package com.bhz.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bhz.dao.UserInfoDao;
import com.bhz.pojo.UserInfo;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.Page;
import com.bhz.util.PageUtils.PagingFactory;

public class UserService extends BaseService{

	private UserInfoDao  userInfoDao;
	private PagingFactory pageDao;

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}
	public void setPageDao(PagingFactory pageDao) {
		this.pageDao = pageDao;
	}
	
	public Integer queryUserPwd(String username,String password)throws Exception{
		String sql = "select id from userinfo where username collate Chinese_PRC_CS_AS_WS='"+username+"' " +
		"and userpwd='"+password+"'";
		List list = userInfoDao.queryBySql(sql);
		if(list.size()>0){
			return Integer.valueOf(list.get(0).toString());
		}
		return null;
	}
	
	public boolean updateUserPwd(Integer id,String subPwd)throws Exception{
		if(id==null)
			return false;
		UserInfo u = userInfoDao.queryById(id);
		if(u==null)
			return false;
		u.setUserpwd(subPwd);
		userInfoDao.update(u);
		return true;
	}
	
	public Page getUserByPage(HttpServletRequest request,Page page) throws Exception {
		if(page==null)
			page = new Page();
		String hql = "UserInfo where 1=1";
		String username = Util.getUserName(request);
		if(!"admin".equals(username)){
			Map<String,String> filterData = getBdBhzFilterAuth(request);
			String bdIn = filterData.get("bdIn");
			if(!Util.isEmpty(bdIn)){
				hql+=" and bdCode in ("+bdIn+")";
			}else{
				hql+=" and 1=2";
			}
			String bhzIn = filterData.get("bhzIn");
			if(!Util.isEmpty(bhzIn)){
				hql+=" and bhzCode in ("+bhzIn+")";
			}else{
				hql+=" and 1=2";
			}
		}
		page.setObjName(hql);
		return pageDao.queryObjList(page);
	}

	public List<UserInfo> getAllUser() throws Exception {
		return this.userInfoDao.queryAll();
	}

	public void deleteUser(Integer id) throws Exception {
		UserInfo u = this.userInfoDao.queryById(id);
		this.userInfoDao.delete(u);
		userInfoDao.deleteBySql("delete from UserAuthorityInfo where userName='"+u.getUsername()+"'");
	}

	public void addUser(UserInfo model) throws Exception {
		this.userInfoDao.insert(model);
	}
	
	//根据某个用户名判断是否已存在该用户,返回false说明不存在指定的用户
	public boolean CheckUser(String username) throws Exception {
		String hql = "from UserInfo u where u.username = '" + username + "'";
		List<UserInfo> queryByHql = this.userInfoDao.queryByHql(hql);
		if (queryByHql.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public UserInfo getUserById(Integer id) throws Exception {
		return this.userInfoDao.queryById(id);
	}

	public void updateUser(UserInfo model) throws Exception {
		this.userInfoDao.update(model);
	}

	public void deleteUserByBhzCode(String bhzCode) throws Exception {
		String hql = "delete from UserInfo u where u.bhzCode = '" + bhzCode + "'";
		this.userInfoDao.deleteUser(hql);
	}

	public Page getUsersByMultiFields(HttpServletRequest request,Page page,UserInfo model) throws Exception {
		if(page==null)
			page = new Page();
		String bdCode = model.getBdCode();
		String bhzCode = model.getBhzCode();
		String username = model.getUsername();
		String userpwd = model.getUserpwd();
		
		String hql = "UserInfo u where 1=1";
		if (!Util.isEmpty(bdCode)) {
			hql = hql + " and u.bdCode = '" + bdCode + "'";
		}
		if (!Util.isEmpty(bhzCode)) {
			hql = hql + " and u.bhzCode = '" + bhzCode + "'";
		}
		if (!Util.isEmpty(username)) {
			hql = hql + " and u.username = '" + username + "'";
		}
		if (!Util.isEmpty(userpwd)) {
			hql = hql + " and u.userpwd = '" + userpwd + "'";
		}
		String uname = Util.getUserName(request);
		if(!"admin".equals(uname)){
			Map<String,String> filterData = getBdBhzFilterAuth(request);
			String bdIn = filterData.get("bdIn");
			if(!Util.isEmpty(bdIn)){
				hql+=" and u.bdCode in ("+bdIn+")";
			}else{
				hql+=" and 1=2";
			}
			String bhzIn = filterData.get("bhzIn");
			if(!Util.isEmpty(bhzIn)){
				hql+=" and u.bhzCode in ("+bhzIn+")";
			}else{
				hql+=" and 1=2";
			}
		}
		page.setObjName(hql);
		return pageDao.queryObjList(page);
	}

	public void deleteUserByBdCode(String bdCode) throws Exception {
		String hql = "delete from UserInfo u where u.bdCode = '" + bdCode + "'";
		this.userInfoDao.deleteUserByBdCode(hql);
	}
}
