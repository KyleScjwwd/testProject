package com.bhz.dao;

import com.bhz.pojo.TbBaoJingMainMerge;
import com.bhz.util.BaseDao;

public interface TbBaoJingMainMergeDao extends BaseDao<TbBaoJingMainMerge>{

	public void deleteTbBaoJingMainMerge(String hql)throws Exception;
	public void deleteByHql(String hql)throws Exception;
	public void deleteBySql(String hql)throws Exception;
}
