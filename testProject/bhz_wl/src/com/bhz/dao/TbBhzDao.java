package com.bhz.dao;

import com.bhz.pojo.TbBhz;
import com.bhz.util.BaseDao;

public interface TbBhzDao extends BaseDao<TbBhz>{
	public void deleteByHql(String hql) throws Exception;
	public void deleteBySql(String hql) throws Exception;
	public void updateClientVersion(String hql) throws Exception;
	public void updateByHql(String hql) throws Exception;
}
