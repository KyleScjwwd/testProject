package com.bhz.dao.impl;

import org.hibernate.Query;

import com.bhz.dao.TbBaoJingPersonBaseDao;
import com.bhz.pojo.TbBaoJingPersonBase;
import com.bhz.util.BaseDaoImpl;

public class TbBaoJingPersonBaseDaoImpl extends BaseDaoImpl<TbBaoJingPersonBase> implements TbBaoJingPersonBaseDao{
	
	public void deleteBySql(String sql) throws Exception {
		Query query = this.getSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	public void updateBySql(String sql) throws Exception {
		Query query = this.getSession().createSQLQuery(sql);
		query.executeUpdate();
	}	
}
