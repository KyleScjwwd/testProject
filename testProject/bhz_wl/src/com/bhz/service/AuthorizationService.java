package com.bhz.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bhz.dao.TbBaseAuthorityDao;
import com.bhz.dao.UserAuthorityInfoDao;
import com.bhz.pojo.TbBaseAuthority;
import com.bhz.pojo.TbBd;
import com.bhz.pojo.TbBhz;
import com.bhz.pojo.UserAuthorityInfo;
import com.bhz.util.Util;

public class AuthorizationService {
	private TbBaseAuthorityDao tbBaseAuthorityDao;
	private UserAuthorityInfoDao userAuthorityInfoDao;

	public void setTbBaseAuthorityDao(TbBaseAuthorityDao tbBaseAuthorityDao) {
		this.tbBaseAuthorityDao = tbBaseAuthorityDao;
	}
	
	public void setUserAuthorityInfoDao(UserAuthorityInfoDao userAuthorityInfoDao) {
		this.userAuthorityInfoDao = userAuthorityInfoDao;
	}

	public List<TbBaseAuthority> getAllBaseAuthority() throws Exception {
		return tbBaseAuthorityDao.queryAll();
	}
	
	public List<UserAuthorityInfo> getUserAuthByUserName(String username)throws Exception{
		if(Util.isEmpty(username))
			return null;
		String hql = "from UserAuthorityInfo where userName = '"+username+"'";
		return userAuthorityInfoDao.queryByHql(hql);
	}
	
	public Map<String,String> getUserAuthorityInfo(String username)throws Exception{
		Map<String,String> result = new HashMap<String,String>();
		if(Util.isEmpty(username))
			return result;
		String hql = "from UserAuthorityInfo where userName='"+username+"'";
		List<UserAuthorityInfo> userAuths = userAuthorityInfoDao.queryByHql(hql);
		for(UserAuthorityInfo u:userAuths){
			result.put(u.getUserName()+"-"+u.getFunCode(), u.getAuthValue());
		}
		return result;		
	}
	
	public void saveUserAuthorization(HttpServletRequest request, String username, List<TbBd> bdList, List<TbBhz> bhzList) throws Exception {
		//保存权限前先删除该用户已有的权限信息
		userAuthorityInfoDao.deleteByHql("delete from UserAuthorityInfo where userName = '" + username + "'");
		
		List<TbBaseAuthority> baseAuthorityList = getAllBaseAuthority();
		UserAuthorityInfo userAuthorityInfo = null;
		for (TbBaseAuthority a : baseAuthorityList) {
			String value = request.getParameter(a.getFunCode());
			if (!Util.isEmpty(value)) {
				userAuthorityInfo = new UserAuthorityInfo();
				userAuthorityInfo.setUserName(username);
				userAuthorityInfo.setFunCode(a.getFunCode());
				userAuthorityInfo.setAuthValue(value);
				
				userAuthorityInfoDao.insert(userAuthorityInfo);
			}
		}
		
		for (TbBd bd : bdList) {
			String value = request.getParameter("bd" + bd.getBdCode());
			if (!Util.isEmpty(value)) {
				userAuthorityInfo = new UserAuthorityInfo();
				userAuthorityInfo.setUserName(username);
				userAuthorityInfo.setFunCode("bd" + bd.getBdCode());
				userAuthorityInfo.setAuthValue(value);
				
				userAuthorityInfoDao.insert(userAuthorityInfo);
			}
		}
		
		for (TbBhz bhz : bhzList) {
			String value = request.getParameter("bhz" + bhz.getBhzCode());
			if (!Util.isEmpty(value)) {
				userAuthorityInfo = new UserAuthorityInfo();
				userAuthorityInfo.setUserName(username);
				userAuthorityInfo.setFunCode("bhz" + bhz.getBhzCode());
				userAuthorityInfo.setAuthValue(value);
				
				userAuthorityInfoDao.insert(userAuthorityInfo);
			}
		}
	}
}
