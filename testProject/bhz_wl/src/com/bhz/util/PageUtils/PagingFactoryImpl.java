package com.bhz.util.PageUtils;

import com.bhz.util.Util;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PagingFactoryImpl extends HibernateDaoSupport implements PagingFactory {
	
	public Integer queryObjCount(Page page) {
        StringBuffer queryHQL=new StringBuffer("SELECT COUNT(*) FROM "+page.getObjName());
        //如果查询条件不为空
        if(page.getCondition()!=null && !page.getCondition().trim().equals(""))
            queryHQL.append(" WHERE "+page.getCondition());
        //如果排序不为空
        // if(page.getOrderBy()!=null && !page.getOrderBy().trim().equals(""))
        //     queryHQL.append(" order by "+page.getOrderBy());
        return Integer.parseInt(this.getSession().createQuery(queryHQL.toString()).uniqueResult().toString());   
    }
	
    public Page queryObjList(Page page) {
            StringBuffer queryHQL=new StringBuffer();
            if(page.getQueryField()!=null && !page.getQueryField().trim().equals(""))
                queryHQL.append("SELECT "+page.getQueryField()+" FROM "+page.getObjName());
            else
                queryHQL.append("FROM "+page.getObjName());
            if(page.getCondition()!=null && !page.getCondition().trim().equals(""))
                queryHQL.append(" WHERE "+page.getCondition());
            if(page.getOrderBy()!=null)
            	queryHQL.append(" order by "+page.getOrderBy());
            Query query=this.getSession().createQuery(queryHQL.toString());   
            query.setFirstResult(page.getStartIndex());   
            query.setMaxResults(page.getPageSize());   
            page.setTotalCount(this.queryObjCount(page));   
            page.setObjList(query.list());   
            return page;   
       }
    
    public Integer queryObjCountBySql(Page page){
    	/*String sql = page.getObjName();
    	Query query = getSession().createSQLQuery(sql);
    	return query.list().size();*/
    	StringBuffer sql = new StringBuffer("select count(*) from ( "); 
    	sql.append(page.getObjName());
    	sql.append(" ) gsk ");
		//String newSql = sql.replace(sql.substring(sql.indexOf("select") + 6,sql.indexOf("from")), " count(*) ");
    	Query query = getSession().createSQLQuery(sql.toString());
    	return Integer.valueOf(query.uniqueResult().toString());
    }

    public Page queryObjListBySql(Page page){
    	StringBuffer sql = new StringBuffer(page.getObjName());
    	if(page.getOrderBy()!=null)
    		sql.append(" order by "+page.getOrderBy());
    	Query query = getSession().createSQLQuery(sql.toString());
    	query.setFirstResult(page.getStartIndex());
    	query.setMaxResults(page.getPageSize());
    	page.setTotalCount(this.queryObjCountBySql(page));
    	page.setObjList(query.list());
    	return page;
    }

    public Integer queryObjCzCountBySql(Page page){
        StringBuffer sql = new StringBuffer("select count(*) from (select BhzCarOrder from tbBase where 1=1  ");
        String objNmae=page.getObjName();
        objNmae=objNmae.substring(objNmae.indexOf("and"),objNmae.indexOf("group by"));
        sql.append(objNmae);
        sql.append(" group by BhzCarOrder) gsk ");
        //String newSql = sql.replace(sql.substring(sql.indexOf("select") + 6,sql.indexOf("from")), " count(*) ");
        Query query = getSession().createSQLQuery(sql.toString());
        return Integer.valueOf(query.uniqueResult().toString());
    }

    public Page queryObjCzListBySql(Page page){
        StringBuffer sql = new StringBuffer(page.getObjName());
        if(page.getOrderBy()!=null)
            sql.append(" order by "+page.getOrderBy());
        Query query = getSession().createSQLQuery(sql.toString());
        query.setFirstResult(page.getStartIndex());
        query.setMaxResults(page.getPageSize());
        page.setTotalCount(this.queryObjCzCountBySql(page));
        page.setObjList(query.list());
        return page;
    }
}
