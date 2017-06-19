package com.bhz.dao;

import com.bhz.pojo.TbMessgeSendType;
import com.bhz.util.BaseDao;

public interface TbMessgeSendTypeDao extends BaseDao<TbMessgeSendType>{
	
	public void deleteByHql(String hql)throws Exception;
	
	public int updateByHql(String hql)throws Exception;
}
