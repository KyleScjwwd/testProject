package com.bhz.dao;

import com.bhz.pojo.TbIntensityGrade;
import com.bhz.util.BaseDao;

public interface TbIntensityGradeDao extends BaseDao<TbIntensityGrade>{
	public void deleteByHql(String hql)throws Exception;
}
