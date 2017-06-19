package com.bhz.util;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T> {

	/**
	 * 新增记录
	 * @param t
	 * @throws Exception
	 */
	public void insert(T t) throws Exception;

	/**
	 * 修改记录
	 * @param t
	 * @throws Exception
	 */
	public void update(T t) throws Exception;

	/**
	 * 删除记录
	 * @param t
	 * @throws Exception
	 */
	public void delete(T t) throws Exception;
	
	/**
	 * 删除或更新记录
	 * @param t
	 * @throws Exception
	 */
	public void insertOrUpdate(T t) throws Exception;

	/**
	 * 查询全部记录
	 * @return
	 * @throws Exception
	 */
	public List<T> queryAll() throws Exception;
	
	/**
	 * 根据hql语句查询集记录
	 * @param hql
	 * @return
	 * @throws Exception
	 */
	public List<T> queryByHql(String hql) throws Exception;
	
	/**
	 * 根据主键查询记录
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public T queryById(Serializable  id) throws Exception;
	
	/**
	 * 查询所有记录数
	 * @return
	 * @throws Exception
	 */
	public Integer count() throws Exception;
	
	/**
     * 根据字段名和字段值为条件查询记录数
     * @param fieldNames
     * @param values
     * @return
     * 2009-11-26 上午11:16:47
     */
    public Integer count(String[] fieldNames, Object... values)throws Exception;
    
    /**
     * 根据SQL查询
     * @param sql
     * @return
     * @throws Exception
     */
    public List queryBySql(String sql)throws Exception; 
}