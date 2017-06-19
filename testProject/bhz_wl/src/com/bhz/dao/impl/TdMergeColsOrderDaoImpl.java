package com.bhz.dao.impl;

import org.hibernate.Query;
import com.bhz.dao.TdMergeColsOrderDao;
import com.bhz.pojo.TdMergeColsOrder;
import com.bhz.util.BaseDaoImpl;

public class TdMergeColsOrderDaoImpl extends BaseDaoImpl<TdMergeColsOrder> implements TdMergeColsOrderDao{
	
	public int getCountByCode(String bdCode,String bhzCode)throws Exception{
		String hql = "select count(*) from TdMergeColsOrder where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"'";
		Query query = this.getSession().createQuery(hql);
		Object obj = query.uniqueResult();
		if(obj==null)
			return 0;
		else
			return Integer.parseInt(String.valueOf(obj));
	}
}
