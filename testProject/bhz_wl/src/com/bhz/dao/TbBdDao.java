package com.bhz.dao;

import com.bhz.pojo.TbBd;
import com.bhz.util.BaseDao;

import java.util.List;

public interface TbBdDao extends BaseDao<TbBd>{
	public void deleteByHql(String hql)throws Exception;
	public void deleteBySql(String hql)throws Exception;
	public List queryByHql(String hql) throws Exception;
}
