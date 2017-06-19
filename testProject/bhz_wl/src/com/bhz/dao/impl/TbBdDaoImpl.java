package com.bhz.dao.impl;

import org.hibernate.Query;

import com.bhz.dao.TbBdDao;
import com.bhz.pojo.TbBd;
import com.bhz.util.BaseDaoImpl;

import java.util.List;

public class TbBdDaoImpl extends BaseDaoImpl<TbBd> implements TbBdDao{
	public void deleteByHql(String hql) throws Exception {
		Query query = this.getSession().createQuery(hql);
		query.executeUpdate();
	}
	
	public void deleteBySql(String sql) throws Exception {
		Query query = this.getSession().createSQLQuery(sql);
		query.executeUpdate();
	}

	public List queryByHql(String hql) throws Exception {
		return super.queryByHql(hql);
	}
}
