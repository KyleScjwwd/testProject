package com.bhz.dao.impl;

import org.hibernate.Query;

import com.bhz.dao.TbBaoJingMainMergeDao;
import com.bhz.pojo.TbBaoJingMainMerge;
import com.bhz.util.BaseDaoImpl;

public class TbBaoJingMainMergeDaoImpl extends BaseDaoImpl<TbBaoJingMainMerge> implements TbBaoJingMainMergeDao{
	
	public void deleteTbBaoJingMainMerge(String hql)throws Exception{
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
