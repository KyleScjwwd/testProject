package com.bhz.dao;

import com.bhz.pojo.TbBaoJingSub;
import com.bhz.util.BaseDao;

public interface TbBaoJingSubDao extends BaseDao<TbBaoJingSub>{

	public void deleteTbBaoJingSub(String hql)throws Exception;
	public void deleteByHql(String hql)throws Exception;
	public void deleteBySql(String hql)throws Exception;
}
