package com.bhz.dao;

import com.bhz.pojo.TdMergeColsOrder;
import com.bhz.util.BaseDao;

public interface TdMergeColsOrderDao extends BaseDao<TdMergeColsOrder>{

	public int getCountByCode(String bdCode,String bhzCode)throws Exception; 
}
