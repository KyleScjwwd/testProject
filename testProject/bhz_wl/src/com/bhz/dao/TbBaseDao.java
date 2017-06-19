package com.bhz.dao;

import com.bhz.pojo.TbBase;
import com.bhz.util.BaseDao;

import java.util.List;

public interface TbBaseDao extends BaseDao<TbBase>{

	public boolean updateTbBase(String hql)throws Exception;
	
	public void deleteByHql(String hql)throws Exception;
	
	public void deleteBySql(String sql)throws Exception;
	
	public int updateByHql(String hql)throws Exception;
	
	public String getMaxGroupId(String hql)throws Exception;
	
	public boolean insertBySql(String sql)throws Exception;

	public Double getQtByBhzCarOrder(String sql) throws Exception;

	public List getMinAndMaxTime(String hql) throws Exception;

	public List<TbBase> getTbBaseByBhzCarOrder(String hql) throws Exception;
	
}
