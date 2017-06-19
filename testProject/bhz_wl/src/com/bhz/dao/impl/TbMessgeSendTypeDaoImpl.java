package com.bhz.dao.impl;

import org.hibernate.Query;

import com.bhz.dao.TbMessgeSendTypeDao;
import com.bhz.pojo.TbMessgeSendType;
import com.bhz.util.BaseDaoImpl;

public class TbMessgeSendTypeDaoImpl extends BaseDaoImpl<TbMessgeSendType> implements TbMessgeSendTypeDao{
	
	public void deleteByHql(String hql) throws Exception {
		Query query = this.getSession().createQuery(hql);
		query.executeUpdate();
	}
	
	public int updateByHql(String hql)throws Exception{
		Query query = this.getSession().createQuery(hql);
		return query.executeUpdate();
	}
}
