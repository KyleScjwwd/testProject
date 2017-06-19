package com.bhz.service;

import java.util.List;

import com.bhz.dao.TbBaoJingPersonBaseDao;
import com.bhz.pojo.TbBaoJingPersonBase;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.PagingFactory;

public class BaoJingPersonBaseService extends BaseService {
	
	private TbBaoJingPersonBaseDao tbBaoJingPersonBaseDao;
	private PagingFactory pageDao;
	
	public void setTbBaoJingPersonBaseDao(TbBaoJingPersonBaseDao tbBaoJingPersonBaseDao) {
		this.tbBaoJingPersonBaseDao = tbBaoJingPersonBaseDao;
	}
	
	public void setPageDao(PagingFactory pageDao) {
		this.pageDao = pageDao;
	}

	public boolean checkExist_Add(String bdCode, String bhzCode) throws Exception {
		String hql = "from TbBaoJingPersonBase where bdCode = '" + bdCode + "' and bhzCode = '" + bhzCode + "'";
		List<TbBaoJingPersonBase> queryByHql = this.tbBaoJingPersonBaseDao.queryByHql(hql);
		if (queryByHql.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void cascadeUpdateBaoJingSubMerge(TbBaoJingPersonBase baoJingPersonBase) throws Exception {
		if (baoJingPersonBase == null)
			return;
		String bdCode = baoJingPersonBase.getBdCode();
		String bhzCode = baoJingPersonBase.getBhzCode();
		String userMobileCj = baoJingPersonBase.getUserMobileCj();
		String userMobileZj = baoJingPersonBase.getUserMobileZj();
		String userMobileGj = baoJingPersonBase.getUserMobileGj();
		String mobileCJ_bdCode = baoJingPersonBase.getMobileCJ_bdCode();	
		String mobileZJ_bdCode = baoJingPersonBase.getMobileZJ_bdCode();		
		String mobileGJ_bdCode = baoJingPersonBase.getMobileGJ_bdCode();
		String sql = "";
		StringBuffer sb = new StringBuffer();
		
		if (!Util.isEmpty(bdCode) && !Util.isEmpty(bhzCode)) {
			sql = "update tbBaoJingSubMerge set mobile = '" + userMobileCj + "', mobile_bdCode = '" + mobileCJ_bdCode +
				  "' where bdCode = '" + bdCode + "' and bhzCode = '" + bhzCode + "' and sendType = '0'";
			sb.append(sql);
			
			sql = "update tbBaoJingSubMerge set mobile = '" + userMobileZj + "', mobile_bdCode = '" + mobileZJ_bdCode +
					  "' where bdCode = '" + bdCode + "' and bhzCode = '" + bhzCode + "' and sendType = '1'";
			sb.append(sql);
			
			sql = "update tbBaoJingSubMerge set mobile = '" + userMobileGj + "', mobile_bdCode = '" + mobileGJ_bdCode +
					  "' where bdCode = '" + bdCode + "' and bhzCode = '" + bhzCode + "' and sendType = '2'";
			sb.append(sql);			
		}
		
		if (sb.length() > 0)
			this.tbBaoJingPersonBaseDao.updateBySql(sb.toString());
		//重新设置进入缓存
		setBaoJingCacheMerge();
	}
	
	public void addBaoJingPersonBase(TbBaoJingPersonBase baoJingPersonBase) throws Exception {
		this.tbBaoJingPersonBaseDao.insert(baoJingPersonBase);
		cascadeUpdateBaoJingSubMerge(baoJingPersonBase);
	}

	public TbBaoJingPersonBase getBaoJingPersonBaseById(String id) throws Exception {
		return this.tbBaoJingPersonBaseDao.queryById(Integer.valueOf(id));
	}

	public boolean checkExist_Update(String bdCode, String bhzCode, String id) throws Exception {
		String hql = "from TbBaoJingPersonBase where bdCode = '" + bdCode + "' and bhzCode = '" + bhzCode + "' and id <> '" + id + "'";
		List<TbBaoJingPersonBase> queryByHql = this.tbBaoJingPersonBaseDao.queryByHql(hql);
		if (queryByHql.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void updateBaoJingPersonBase(TbBaoJingPersonBase baoJingPersonBase) throws Exception {
		this.tbBaoJingPersonBaseDao.update(baoJingPersonBase);
		cascadeUpdateBaoJingSubMerge(baoJingPersonBase);
	}

	public void deleteBaoJingPersonBaseById(String id) throws Exception {
		String sql = "delete from tbBaoJingPersonBase where ID = '" + id + "'";
		this.tbBaoJingPersonBaseDao.deleteBySql(sql);
	}
}
