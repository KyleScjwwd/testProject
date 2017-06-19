package com.bhz.dao.impl;

import org.hibernate.Query;

import com.bhz.dao.TbBaoJingSubMergeDao;
import com.bhz.pojo.TbBaoJingSubMerge;
import com.bhz.util.BaseDaoImpl;

public class TbBaoJingSubMergeDaoImpl extends BaseDaoImpl<TbBaoJingSubMerge> implements TbBaoJingSubMergeDao{

	public void deleteTbBaoJingSubMerge(String hql)throws Exception{
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
