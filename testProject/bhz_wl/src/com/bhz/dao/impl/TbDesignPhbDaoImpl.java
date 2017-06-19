package com.bhz.dao.impl;

import org.hibernate.Query;

import com.bhz.dao.TbDesignPhbDao;
import com.bhz.pojo.TbDesignPhb;
import com.bhz.util.BaseDaoImpl;

public class TbDesignPhbDaoImpl extends BaseDaoImpl<TbDesignPhb> implements TbDesignPhbDao {
	public void deleteByHql(String hql) throws Exception {
		Query query = this.getSession().createQuery(hql);
		query.executeUpdate();
	}
	
	public void deleteBySql(String sql) throws Exception {
		Query query = this.getSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	public void updateBySql(String sql) throws Exception {
		Query query = this.getSession().createSQLQuery(sql);
		query.executeUpdate();
	}
}
