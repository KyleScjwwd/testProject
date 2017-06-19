package com.bhz.dao;

import com.bhz.pojo.UserInfo;
import com.bhz.util.BaseDao;

public interface UserInfoDao extends BaseDao<UserInfo>{
	public void deleteUser(String hql)throws Exception;
	public void deleteUserByBdCode(String hql)throws Exception;
	public void deleteBySql(String hql)throws Exception;
}
