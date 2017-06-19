package com.bhz.dao;

import com.bhz.pojo.TbDesignPhbmerge;
import com.bhz.util.BaseDao;

public interface TbDesignPhbmergeDao extends BaseDao<TbDesignPhbmerge>{
	public void deleteBySql(String sql)throws Exception;
	public void updateBySql(String sql)throws Exception;
}
