package com.bhz.dao.impl;

import org.hibernate.Query;

import com.bhz.dao.UserInfoDao;
import com.bhz.pojo.UserInfo;
import com.bhz.util.BaseDaoImpl;

public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo> implements UserInfoDao{
	
	public void deleteUser(String hql)throws Exception{
		Query query = this.getSession().createQuery(hql);
		query.executeUpdate();
	}

	public void deleteUserByBdCode(String hql) throws Exception {
		Query query = this.getSession().createQuery(hql);
		query.executeUpdate();
	}
	
	public void deleteBySql(String sql) throws Exception {
		Query query = this.getSession().createSQLQuery(sql);
		query.executeUpdate();
	}
}
