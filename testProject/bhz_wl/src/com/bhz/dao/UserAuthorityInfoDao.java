package com.bhz.dao;

import com.bhz.pojo.UserAuthorityInfo;
import com.bhz.util.BaseDao;

public interface UserAuthorityInfoDao extends BaseDao<UserAuthorityInfo>{
	public void deleteByHql(String hql)throws Exception;
}
