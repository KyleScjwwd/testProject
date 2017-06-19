package com.bhz.dao.impl;

import org.hibernate.Query;

import com.bhz.dao.TbBaoJingMainDao;
import com.bhz.pojo.TbBaoJingMain;
import com.bhz.util.BaseDaoImpl;

public class TbBaoJingMainDaoImpl extends BaseDaoImpl<TbBaoJingMain> implements TbBaoJingMainDao{
	
	public void deleteTbBaoJingMain(String hql)throws Exception{
		Query query = this.getSession().createQuery(hql);
		query.executeUpdate();
	}
	
	public void deleteByHql(String hql) throws Exception {
		Query query = this.getSession().createQuery(hql);
		query.executeUpdate();
	}
	
	public void deleteBySql(String sql) throws Exception {
		Query query = this.getSession().createSQLQuery(sql);
		query.executeUpdate();
	}
}
