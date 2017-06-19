package com.bhz.dao.impl;

import org.hibernate.Query;

import com.bhz.dao.TbBaseDao;
import com.bhz.pojo.TbBase;
import com.bhz.util.BaseDaoImpl;

import java.util.List;

public class TbBaseDaoImpl extends BaseDaoImpl<TbBase> implements TbBaseDao{

	public boolean updateTbBase(String hql)throws Exception{
		Query query = this.getSession().createQuery(hql);
		int result = query.executeUpdate();
		if(result>0)
			return true;
		else
			return false;
	}
	
	public void deleteByHql(String hql) throws Exception {
		Query query = this.getSession().createQuery(hql);
		query.executeUpdate();
	}
	
	public void deleteBySql(String sql) throws Exception {
		Query query = this.getSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	public int updateByHql(String hql)throws Exception{
		Query query = this.getSession().createQuery(hql);
		return query.executeUpdate();
	}
	
	public String getMaxGroupId(String hql)throws Exception{
		Object obj = this.getSession().createQuery(hql).uniqueResult();
		if(obj==null)
			return null;
		String result = obj.toString();
		return result;
	}
	
	public boolean insertBySql(String sql)throws Exception{
		Query query = this.getSession().createSQLQuery(sql);
		int result = query.executeUpdate();
		if(result>0){
			return true;
		}else{
			return false;
		}
	}

	public Double getQtByBhzCarOrder(String sql) throws Exception {
		List list=this.getSession().createSQLQuery(sql).list();
        if(null!=list && list.size()>0){
            Object [] objects= (Object[]) list.get(0);
            return (Double) objects[1];
        }else{
            return Double.valueOf(0);
        }
	}

	public List getMinAndMaxTime(String hql) throws Exception {
		return this.getSession().createQuery(hql).list();
	}

	public List<TbBase> getTbBaseByBhzCarOrder(String hql) throws Exception {
		return this.getSession().createQuery(hql).list();
	}
}
