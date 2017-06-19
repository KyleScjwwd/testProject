package com.bhz.dao;

import com.bhz.pojo.TbDesignPhb;
import com.bhz.util.BaseDao;

public interface TbDesignPhbDao extends BaseDao<TbDesignPhb>{
	public void deleteByHql(String hql)throws Exception;
	public void deleteBySql(String sql)throws Exception;
	public void updateBySql(String sql)throws Exception;
}
