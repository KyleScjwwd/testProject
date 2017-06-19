package com.bhz.dao.impl;

import org.hibernate.Query;

import com.bhz.dao.TbBaoJingSubDao;
import com.bhz.pojo.TbBaoJingSub;
import com.bhz.util.BaseDaoImpl;

public class TbBaoJingSubDaoImpl extends BaseDaoImpl<TbBaoJingSub> implements TbBaoJingSubDao{

	public void deleteTbBaoJingSub(String hql)throws Exception{
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
