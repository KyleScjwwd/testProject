package com.bhz.service;

import java.util.List;

import com.bhz.dao.TbBaoJingBaseDao;
import com.bhz.dao.TbBaoJingPersonBaseDao;
import com.bhz.dao.TbIntensityGradeDao;
import com.bhz.dao.TdMergeColsOrderDao;
import com.bhz.pojo.TbBaoJingBase;
import com.bhz.pojo.TbBaoJingMainMerge;
import com.bhz.pojo.TbBaoJingSubMerge;
import com.bhz.pojo.TbIntensityGrade;
import com.bhz.pojo.TdMergeColsOrder;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.PagingFactory;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class BaoJingBaseService extends BaseService {

	public TbBaoJingBaseDao tbBaoJingBaseDao;
	private TbBaoJingPersonBaseDao tbBaoJingPersonBaseDao;
	private TdMergeColsOrderDao tdMergeColsOrderDao;
	private TbIntensityGradeDao tbIntensityGradeDao;
	private PagingFactory pageDao;
	
	public void setTbBaoJingBaseDao(TbBaoJingBaseDao tbBaoJingBaseDao) {
		this.tbBaoJingBaseDao = tbBaoJingBaseDao;
	}
	public void setTbBaoJingPersonBaseDao(TbBaoJingPersonBaseDao tbBaoJingPersonBaseDao) {
		this.tbBaoJingPersonBaseDao = tbBaoJingPersonBaseDao;
	}
	public void setTdMergeColsOrderDao(TdMergeColsOrderDao tdMergeColsOrderDao) {
		this.tdMergeColsOrderDao = tdMergeColsOrderDao;
	}
	public void setTbIntensityGradeDao(TbIntensityGradeDao tbIntensityGradeDao) {
		this.tbIntensityGradeDao = tbIntensityGradeDao;
	}
	public void setPageDao(PagingFactory pageDao) {
		this.pageDao = pageDao;
	}
	
	//级联保存报警信息，flag为0表示增加，1表示更新，2表示删除
	public void cascadeBaoJingInfo(TbBaoJingBase b, String flag) throws Exception {
		if (b == null || Util.isEmpty(b.getClName()))
			return;
		List<TdMergeColsOrder> mergeCols = this.tdMergeColsOrderDao.queryByHql("from TdMergeColsOrder where mergeColsName = '" + b.getClName() + "'");
		List<TbIntensityGrade> intensityGrades = this.tbIntensityGradeDao.queryByHql("from TbIntensityGrade");
		String mainID = "";
		String bdCode = "";
		String bhzCode = "";
		String intensityGrade = "";
		String mergeCol = "";

		TbBaoJingMainMerge mainObj = null;
		TbBaoJingSubMerge subObj = null;
		for (TdMergeColsOrder m : mergeCols) {			
			bdCode = m.getBdCode();
			bhzCode = m.getBhzCode();
			mergeCol = m.getMergeCols();
			
			if (!Util.isEmpty(m.getMergeCols())) {
				//删除tbBaoJingMainMerge和tbBaoJingSubMerge中对应的数据
				tbBaoJingSubMergeDao.deleteBySql("delete from tbBaoJingSubMerge where mainId in (select mainId from tbBaoJingMainMerge where bdCode = '" + bdCode + "' and bhzCode = '" + bhzCode + "' and mergeName = '" + m.getMergeCols() + "')");
				tbBaoJingMainMergeDao.deleteBySql("delete from tbBaoJingMainMerge where bdCode = '" + bdCode + "' and bhzCode = '" + bhzCode + "' and mergeName = '" + m.getMergeCols() + "'");				
			}
			
			if (!"2".equals(flag)) {
				for (TbIntensityGrade i : intensityGrades) {
					intensityGrade = i.getIntensityGrade();
					mainID = bdCode + "-" + bhzCode + "-" + intensityGrade + "-" + mergeCol;
					
					mainObj = new TbBaoJingMainMerge();
					mainObj.setMainId(mainID);
					mainObj.setBdCode(bdCode);
					mainObj.setBhzCode(bhzCode);
					mainObj.setPhbQd(intensityGrade);
					mainObj.setMergeName(mergeCol);
					mainObj.setIsBaojing(b.getIsBaojing());
					mainObj.setMsgContent(b.getMsgContent());
					tbBaoJingMainMergeDao.insert(mainObj);
					
					for(int j = 0; j < 3; j++) {
						subObj = new TbBaoJingSubMerge();
						subObj.setMainId(mainID);
						subObj.setBdCode(bdCode);
						subObj.setBhzCode(bhzCode);
						subObj.setPhbQd(intensityGrade);
						subObj.setSendType(String.valueOf(j));
						if (j == 0) {
							subObj.setDownValue(b.getDownValueCj());
							subObj.setUpValue(b.getUpValueCj());
						} else if (j == 1) {
							subObj.setDownValue(b.getDownValueZj());
							subObj.setUpValue(b.getUpValueZj());						
						} else if (j == 2) {
							subObj.setDownValue(b.getDownValueGj());
							subObj.setUpValue(b.getUpValueGj());						
						}
						tbBaoJingSubMergeDao.insert(subObj);
					}
				}				
			}
		}
		//重新设置进入缓存
		setBaoJingCacheMerge();		
	}
	
	public void addBaoJingBase(TbBaoJingBase b) throws Exception {
		this.tbBaoJingBaseDao.insert(b);
		cascadeBaoJingInfo(b, "0");
	}
	
	public void updateBaoJingBase(TbBaoJingBase b) throws Exception {
		this.tbBaoJingBaseDao.update(b);
		cascadeBaoJingInfo(b, "1");
	}
	
	public void deleteBaoJingBase(TbBaoJingBase b) throws Exception {
		this.tbBaoJingBaseDao.delete(b);
		cascadeBaoJingInfo(b, "2");
	}
	
	public TbBaoJingBase getBaoJingBaseById(String id) throws Exception {
		return this.tbBaoJingBaseDao.queryById(Integer.valueOf(id));
	}
	
	public boolean checkClName_Add(String clName) throws Exception {
		String hql = "from TbBaoJingBase b where b.clName = '" + clName + "'";
		List<TbBaoJingBase> queryByHql = this.tbBaoJingBaseDao.queryByHql(hql);
		if (queryByHql.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean checkClName_Update(String clName, String id) throws Exception {
		String hql = "from TbBaoJingBase b where b.clName = '" + clName + "' and b.id <> '" + id + "'";
		List<TbBaoJingBase> queryByHql = this.tbBaoJingBaseDao.queryByHql(hql);
		if (queryByHql.size() == 0) {
			return false;
		} else {
			return true;
		}		
	}
}
