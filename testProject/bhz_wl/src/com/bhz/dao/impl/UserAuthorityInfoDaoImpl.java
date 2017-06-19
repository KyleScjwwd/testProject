package com.bhz.dao.impl;

import org.hibernate.Query;

import com.bhz.dao.UserAuthorityInfoDao;
import com.bhz.pojo.UserAuthorityInfo;
import com.bhz.util.BaseDaoImpl;

public class UserAuthorityInfoDaoImpl extends BaseDaoImpl<UserAuthorityInfo> implements UserAuthorityInfoDao{

	public void deleteByHql(String hql) throws Exception {
		Query query = this.getSession().createQuery(hql);
		query.executeUpdate();		
	}

}
