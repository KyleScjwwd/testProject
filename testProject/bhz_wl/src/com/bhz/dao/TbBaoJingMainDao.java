package com.bhz.dao;

import com.bhz.pojo.TbBaoJingMain;
import com.bhz.util.BaseDao;

public interface TbBaoJingMainDao extends BaseDao<TbBaoJingMain>{

	public void deleteTbBaoJingMain(String hql)throws Exception;
	public void deleteByHql(String hql)throws Exception;
	public void deleteBySql(String hql)throws Exception;
}
