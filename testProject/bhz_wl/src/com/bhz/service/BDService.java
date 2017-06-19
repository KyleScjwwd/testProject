package com.bhz.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.bhz.dao.TbBaseDao;
import com.bhz.dao.TbDesignPhbDao;
import com.bhz.pojo.TbBd;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.Page;
import com.bhz.util.PageUtils.PagingFactory;

public class BDService extends BaseService{
	private TbBaseDao tbBaseDao;
	private TbDesignPhbDao tbDesignPhbDao;
	private PagingFactory pageDao;

	public void setTbBaseDao(TbBaseDao tbBaseDao) {
		this.tbBaseDao = tbBaseDao;
	}
	public void setTbDesignPhbDao(TbDesignPhbDao tbDesignPhbDao) {
		this.tbDesignPhbDao = tbDesignPhbDao;
	}
	public void setPageDao(PagingFactory pageDao) {
		this.pageDao = pageDao;
	}
	
	public Page getBDByPage(HttpServletRequest request,Page page) throws Exception {
		if(page==null)
			page = new Page();
		String sql = "TbBd where 1=1";
		String username = Util.getUserName(request);
		if(!"admin".equals(username)){
			Map<String,String> filterData = getBdBhzFilterAuth(request);
			String bdIn = filterData.get("bdIn");
			if(!Util.isEmpty(bdIn)){
				sql+=" and bdCode in ("+bdIn+")";
			}else{
				sql+=" and 1=2";
			}
		}
		page.setObjName(sql);
		page.setOrderBy("orderNum asc");
		return pageDao.queryObjList(page);
	}

	public void deleteBD(String bdCode) throws Exception {
		if(Util.isEmpty(bdCode))
			return;
		tbBdDao.deleteBySql("delete from TbBd where bdCode = '" + bdCode + "'");
		tbBhzDao.deleteBySql("delete from TbBhz where bdCode = '" + bdCode + "'");
		tbBaseDao.deleteBySql("delete from TbBase where m2 = '" + bdCode + "'");
		tbDesignPhbDao.deleteBySql("delete from TbDesignPhb where bdCode = '" + bdCode + "'");
		tbBaoJingMainDao.deleteBySql("delete from TbBaoJingMain where bdCode = '" + bdCode + "'");
		tbBaoJingMainMergeDao.deleteBySql("delete from TbBaoJingMainMerge where bdCode = '" + bdCode + "'");
		tbBaoJingSubDao.deleteBySql("delete from TbBaoJingSub where bdCode = '" + bdCode + "'");
		tbBaoJingSubMergeDao.deleteBySql("delete from TbBaoJingSubMerge where bdCode = '" + bdCode + "'");
		tbBaseDao.deleteBySql("delete from tbBaoJingPersonBase where bdCode = '" + bdCode + "'");	//删除报警基本人员信息配置表
		//更新报警设置缓存
		setBdCache();
		setBaoJingCache();
		setBaoJingCacheMerge();
	}

	public void addBD(TbBd model) throws Exception {
		this.tbBdDao.insert(model);
		setBdCache();
	}

	public boolean checkBdCode(String bdCode) throws Exception {
		String hql = "from TbBd b where b.bdCode = '" + bdCode + "'";
		List<TbBd> queryByHql = this.tbBdDao.queryByHql(hql);
		if (queryByHql.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean checkBDtoBHZ(String bdCode) throws Exception{
		String hql=" from TbBhz b where b.bdCode='"+bdCode+"'";
		List<TbBd> queryByHql = this.tbBdDao.queryByHql(hql);
		if (queryByHql.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	public void updateBD(TbBd model) throws Exception {
		if(Util.isEmpty(model.getBdCode()))
			return;
		this.tbBdDao.update(model);
		setBdCache();
	}

	public TbBd getBDByBdCode(String bdCode) throws Exception {
		//由于bdCode就是主键，所以这里的ById，Id就是bdCode
		return this.tbBdDao.queryById(bdCode);
	}
	
	public Page getBDsByMultiFields(HttpServletRequest request,Page page,TbBd model) throws Exception {
		if(page==null)
			page = new Page();
		String bdCode = model.getBdCode();
		String bdName = model.getBdName();
		Integer orderNum = model.getOrderNum();
		String hql = "TbBd b where 1=1";
		if (!Util.isEmpty(bdCode)) {
			hql += " and b.bdCode = '" + bdCode + "'";
		}
		if (!Util.isEmpty(bdName)) {
			hql += " and b.bdName like '%" + bdName + "%'";
		}
		if (orderNum!=null) {
			hql += " and b.orderNum = " + orderNum;
		}
		String username = Util.getUserName(request);
		if(!"admin".equals(username)){
			Map<String,String> filterData = getBdBhzFilterAuth(request);
			String bdIn = filterData.get("bdIn");
			if(!Util.isEmpty(bdIn)){
				hql+=" and b.bdCode in ("+bdIn+")";
			}else{
				hql+=" and 1=2";
			}
		}
		page.setObjName(hql);
		page.setOrderBy("orderNum");
		return pageDao.queryObjList(page);
	}

	public TbBd getBdByBdCode(String bdCode) throws Exception {
		String hql=" from TbBd where  bdCode='"+bdCode+"'";
		List tbBds=tbBhzDao.queryByHql(hql);
		return null!=tbBds&&tbBds.size()>0? (TbBd) tbBds.get(0) :null;
	}
}
