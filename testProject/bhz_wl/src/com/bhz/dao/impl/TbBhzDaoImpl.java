package com.bhz.dao.impl;

import org.hibernate.Query;

import com.bhz.dao.TbBhzDao;
import com.bhz.pojo.TbBhz;
import com.bhz.util.BaseDaoImpl;

public class TbBhzDaoImpl extends BaseDaoImpl<TbBhz> implements TbBhzDao{
	
	public void deleteByHql(String hql) throws Exception {
		Query query = this.getSession().createQuery(hql);
		query.executeUpdate();
	}
	
	public void deleteBySql(String hql) throws Exception {
		Query query = this.getSession().createSQLQuery(hql);
		query.executeUpdate();
	}

	public void updateClientVersion(String hql) throws Exception {
		Query query = this.getSession().createSQLQuery(hql);
		query.executeUpdate();
	}

	public void updateByHql(String hql) throws Exception {
		Query query = this.getSession().createSQLQuery(hql);
		query.executeUpdate();
	}
}
