package com.bhz.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.bhz.dao.UserAuthorityDao;
import com.bhz.dao.UserInfoDao;
import com.bhz.pojo.UserAuthority;

public class LoginService {
	private UserInfoDao userInfoDao;
	private UserAuthorityDao userAuthorityDao;

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}
	public void setUserAuthorityDao(UserAuthorityDao userAuthorityDao) {
		this.userAuthorityDao = userAuthorityDao;
	}
	
	public boolean userLogin(String username,String password)throws Exception{
		String sql = "select username from userinfo where username collate Chinese_PRC_CS_AS_WS='"+username+"' " +
				"and userpwd='"+password+"'";
		List list = userInfoDao.queryBySql(sql);
		if(list.size()>0)
			return true;
		return false;
	}
	
	public Map<String,String> getUserAuthority(String username)throws Exception{
		Map<String,String> result = new HashMap<String,String>();
		List<UserAuthority> userAuths = userAuthorityDao.queryByHql("from UserAuthority where userName='"+username+"'");
		if(userAuths.size()>0){
			for(UserAuthority u:userAuths){
				result.put(u.getUserName()+"-"+u.getFunCode(), u.getAuthValue());
			}
		}
		return result;
	}
}
