package com.bhz.service;

import java.util.ArrayList;
import java.util.List;

import com.bhz.dao.TbDesignPhbDao;
import com.bhz.dao.TbDesignPhbmergeDao;
import com.bhz.dao.TbIntensityGradeDao;
import com.bhz.pojo.TbIntensityGrade;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.Page;
import com.bhz.util.PageUtils.PagingFactory;

public class IntensityGradeService {

	private TbIntensityGradeDao tbIntensityGradeDao;
	private TbDesignPhbDao tbDesignPhbDao;
	private TbDesignPhbmergeDao tbDesignPhbmergeDao;
	private PagingFactory pageDao;

	public void setTbIntensityGradeDao(TbIntensityGradeDao tbIntensityGradeDao) {
		this.tbIntensityGradeDao = tbIntensityGradeDao;
	}
	
	public void setTbDesignPhbmergeDao(TbDesignPhbmergeDao tbDesignPhbmergeDao) {
		this.tbDesignPhbmergeDao = tbDesignPhbmergeDao;
	}
	
	public void setTbDesignPhbDao(TbDesignPhbDao tbDesignPhbDao) {
		this.tbDesignPhbDao = tbDesignPhbDao;
	}

	public void setPageDao(PagingFactory pageDao) {
		this.pageDao = pageDao;
	}

	public Page getAllIntensityGrade(Page page) {
		if (page == null)
			page = new Page();
		page.setObjName("TbIntensityGrade");
		page.setPageSize(30);
		return pageDao.queryObjList(page);
	}

	public Page getIntensityGradeByMultiFields(Page page, String intensityGrade)
			throws Exception {
		if (page == null)
			page = new Page();

		String hql = "TbIntensityGrade where 1=1";
		if (!Util.isEmpty(intensityGrade)) {
			hql += " and intensityGrade like '%" + intensityGrade + "%'";
		}

		page.setObjName(hql);
		page.setPageSize(30);
		return pageDao.queryObjList(page);
	}

	public List<TbIntensityGrade> getIntensityGradeByMultiFields(String bdCode,
			String bhzCode) throws Exception {
		if (Util.isEmpty(bdCode) || Util.isEmpty(bhzCode)) {
			return new ArrayList<TbIntensityGrade>();
		}
		String hql = "from TbIntensityGrade where bdCode = '" + bdCode
				+ "' and bhzCode = '" + bhzCode + "'";
		return tbIntensityGradeDao.queryByHql(hql);
	}

	public List<TbIntensityGrade> getAllIntensityGrade() throws Exception {
		String hql = "from TbIntensityGrade ";
		return tbIntensityGradeDao.queryByHql(hql);
	}

	public void addIntensityGrade(TbIntensityGrade model) throws Exception {
		tbIntensityGradeDao.insert(model);
	}

	// 删除强度等级时还要同时删除该强度等级对应的设计配合比
	public void deleteIntensityGrade(TbIntensityGrade model) throws Exception {
		TbIntensityGrade qddj = tbIntensityGradeDao.queryById(model.getId());
		tbIntensityGradeDao.deleteByHql("delete from TbIntensityGrade where id = '"+ qddj.getId() + "'");
		tbDesignPhbDao.deleteBySql("delete from TbDesignPhb where intensityGrade collate Chinese_PRC_CS_AS_WS = '"+ qddj.getIntensityGrade() + "'");
		tbDesignPhbmergeDao.deleteBySql("delete from TbDesignPhbmerge where intensityGrade collate Chinese_PRC_CS_AS_WS = '"+ qddj.getIntensityGrade() + "'");
	}

	public TbIntensityGrade getIntensityGradeById(Integer id) throws Exception {
		return tbIntensityGradeDao.queryById(id);
	}

	public void updateIntensityGrade(TbIntensityGrade model) throws Exception {
		TbIntensityGrade t = tbIntensityGradeDao.queryById(model.getId());
		if(t!=null){
			tbDesignPhbDao.updateBySql("update TbDesignPhb set intensityGrade='"+model.getIntensityGrade()+"' " +
					"where intensityGrade collate Chinese_PRC_CS_AS_WS='"+t.getIntensityGrade()+"'");
			tbDesignPhbmergeDao.updateBySql("update TbDesignPhbmerge set intensityGrade='"+model.getIntensityGrade()+"' " +
					"where intensityGrade collate Chinese_PRC_CS_AS_WS='"+t.getIntensityGrade()+"'");
		}
		t.setIntensityGrade(model.getIntensityGrade());
		tbIntensityGradeDao.update(t);
	}

	// 用于新增强度等级时判断是否已存在
	public Boolean checkExist_Add(TbIntensityGrade model) throws Exception {
		if (!Util.isEmpty(model.getIntensityGrade())) {
			// 区分大小写
			String sql = "select intensityGrade from TbIntensityGrade where intensityGrade collate Chinese_PRC_CS_AS_WS= '"
					+ model.getIntensityGrade() + "'";
			List queryByHql = tbIntensityGradeDao.queryBySql(sql);
			if (queryByHql.size() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	// 用于修改强度等级时判断是否存在
	public Boolean checkExist_Update(TbIntensityGrade model) throws Exception {
		if ((model.getId() != null)
				&& (!Util.isEmpty(model.getIntensityGrade()))) {
			String sql = "select intensityGrade from TbIntensityGrade where intensityGrade collate Chinese_PRC_CS_AS_WS= '"
				+ model.getIntensityGrade() + "' and id <> '" + model.getId() + "'";
			List queryByHql = tbIntensityGradeDao.queryBySql(sql);
			if (queryByHql.size() == 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
}
