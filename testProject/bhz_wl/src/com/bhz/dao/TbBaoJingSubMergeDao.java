package com.bhz.dao;

import com.bhz.pojo.TbBaoJingSubMerge;
import com.bhz.util.BaseDao;

public interface TbBaoJingSubMergeDao extends BaseDao<TbBaoJingSubMerge>{

	public void deleteTbBaoJingSubMerge(String hql)throws Exception;
	public void deleteByHql(String hql)throws Exception;
	public void deleteBySql(String hql)throws Exception;
}
