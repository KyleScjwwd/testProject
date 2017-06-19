package com.bhz.util;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	protected Class<T> entityClass;

	protected EntityManager em;

	private String baseCount;

	public BaseDaoImpl() {
		if (entityClass == null) {
			entityClass = (Class<T>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		}
	}

	public void insert(T t) throws Exception {
		this.getHibernateTemplate().save(t);
	}

	public void update(T t) throws Exception {
		this.getHibernateTemplate().update(t);
	}

	public void delete(T t) throws Exception {
		this.getHibernateTemplate().delete(t);
	}

	public void insertOrUpdate(T t) throws Exception {
		this.getHibernateTemplate().saveOrUpdate(t);
	}

	@SuppressWarnings("unchecked")
	public List<T> queryAll() throws Exception {
		return this.getHibernateTemplate().loadAll(entityClass);
	}

	@SuppressWarnings("unchecked")
	public List<T> queryByHql(String hql) throws Exception {
		List<T> list = this.getHibernateTemplate().find(hql);
		return list;
	}

	@SuppressWarnings("unchecked")
	public T queryById(Serializable id) throws Exception {
		return (T) this.getHibernateTemplate().get(entityClass, id);
	}

	public Integer count() throws Exception {
		return count(null);
	}

	public Integer count(String[] fieldNames, Object... values)
			throws Exception {
		Query query = null;
		if (fieldNames == null || values == null || fieldNames.length == 0
				|| values.length == 0 || fieldNames.length != values.length) {
			query = em.createQuery(baseCount);
		} else {
			StringBuffer sb = new StringBuffer(baseCount);
			sb.append(" WHERE");
			for (int i = 0; i < fieldNames.length; i++) {
				if (i > 0) {
					sb.append(" AND");
				}
				sb.append(" t.").append(fieldNames[i]).append(" = ?").append(
						i + 1);
			}
			query = em.createQuery(sb.toString());
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}
		return (Integer) em.createQuery(baseCount).getSingleResult();
	}
	
	public List queryBySql(String sql)throws Exception{
		Session session = this.getSession();
		List list = session.createSQLQuery(sql).list();
		return list;
	}
}
