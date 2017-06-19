package com.bhz.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bhz.dao.TbSignInDao;
import com.bhz.pojo.TbSignIn;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.Page;
import com.bhz.util.PageUtils.PagingFactory;

public class SignInService extends BaseService {
	private TbSignInDao tbSignInDao;
	private PagingFactory pageDao;

	public void setTbSignInDao(TbSignInDao tbSignInDao) {
		this.tbSignInDao = tbSignInDao;
	}
	public void setPageDao(PagingFactory pageDao) {
		this.pageDao = pageDao;
	}
	
	public boolean checkExist_Add(TbSignIn model) throws Exception {
		String hql = "from TbSignIn s where s.bdCode = '" + model.getBdCode() + "' and s.bhzCode = '" + model.getBhzCode() + "' and" +
					 " s.signInDate = '" + Util.getPatternDate(model.getSignInDate(), "yyyy-MM-dd") + "' and s.signInUser = '" + model.getSignInUser() + "'";
		List<TbSignIn> queryByHql = this.tbSignInDao.queryByHql(hql);
		if (queryByHql.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean checkExist_Update(TbSignIn model) throws Exception {
		String hql = "from TbSignIn s where s.bdCode = '" + model.getBdCode() + "' and s.bhzCode = '" + model.getBhzCode() + "' and" +
				 " s.signInDate = '" + Util.getPatternDate(model.getSignInDate(), "yyyy-MM-dd") + "' and s.signInUser = '" + model.getSignInUser() + "' and" +
				 " s.id <> '" + model.getId() + "'";
		List<TbSignIn> queryByHql = this.tbSignInDao.queryByHql(hql);
		if (queryByHql.size() == 0) {
			return false;
		} else {
			return true;
		}
	}	
	
	public void addSignIn(TbSignIn model) throws Exception {
		this.tbSignInDao.insert(model);
	}
	
	public Page getSignInByMultiFields(Page page, TbSignIn model, String sdate, String edate) throws Exception {
		if(page==null)
			page = new Page();
		String bdCode = model.getBdCode();
		String bhzCode = model.getBhzCode();
		String hql = "TbSignIn s where 1=1";
		
		if (!Util.isEmpty(bdCode))
			hql += " and s.bdCode = '" + bdCode + "'";
		if (!Util.isEmpty(bhzCode))
			hql += " and s.bhzCode = '" + bhzCode + "'";
		if (!Util.isEmpty(sdate))
			hql += " and s.signInDate >= '" + sdate + "'";
		if (!Util.isEmpty(edate))
			hql += " and s.signInDate <= '" + edate + "'";
		
		page.setObjName(hql);
		page.setOrderBy("signInDate desc");
		return pageDao.queryObjList(page);
	}
	
	public TbSignIn getSignInById(String id) throws Exception {
		return this.tbSignInDao.queryById(Integer.valueOf(id));
	}
	
	public void updateSignIn(TbSignIn model) throws Exception {
		this.tbSignInDao.update(model);
	}
	
}
