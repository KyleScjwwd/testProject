package com.bhz.dao.impl;

import org.hibernate.Query;

import com.bhz.dao.TbIntensityGradeDao;
import com.bhz.pojo.TbIntensityGrade;
import com.bhz.util.BaseDaoImpl;

public class TbIntensityGradeDaoImpl extends BaseDaoImpl<TbIntensityGrade> implements TbIntensityGradeDao{
	public void deleteByHql(String hql) throws Exception {
		Query query = this.getSession().createQuery(hql);
		query.executeUpdate();
	}
}
