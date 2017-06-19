package com.bhz.dao.impl;

import org.hibernate.Query;
import com.bhz.dao.TbDesignPhbmergeDao;
import com.bhz.pojo.TbDesignPhbmerge;
import com.bhz.util.BaseDaoImpl;

public class TbDesignPhbmergeDaoImpl extends BaseDaoImpl<TbDesignPhbmerge> implements TbDesignPhbmergeDao{

	public void deleteBySql(String sql) throws Exception {
		Query query = this.getSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	public void updateBySql(String sql) throws Exception {
		Query query = this.getSession().createSQLQuery(sql);
		query.executeUpdate();
	}
}
