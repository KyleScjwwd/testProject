package com.bhz.dao;

import com.bhz.pojo.TbBaoJingPersonBase;
import com.bhz.util.BaseDao;

public interface TbBaoJingPersonBaseDao extends BaseDao<TbBaoJingPersonBase>{
	public void deleteBySql(String sql) throws Exception;
	public void updateBySql(String sql) throws Exception;
}
