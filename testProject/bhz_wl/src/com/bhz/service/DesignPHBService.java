package com.bhz.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.bhz.dao.TbDesignPhbDao;
import com.bhz.dao.TbDesignPhbmergeDao;
import com.bhz.pojo.TbDesignPhb;
import com.bhz.pojo.TbDesignPhbmerge;
import com.bhz.pojo.TbIntensityGrade;
import com.bhz.pojo.TbParamSet;
import com.bhz.pojo.TdMergeColsOrder;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.PagingFactory;

public class DesignPHBService {
	private PagingFactory pageDao;
	private TbDesignPhbDao tbDesignPhbDao;
	private TbDesignPhbmergeDao tbDesignPhbmergeDao;
	
	public void setTbDesignPhbmergeDao(TbDesignPhbmergeDao tbDesignPhbmergeDao) {
		this.tbDesignPhbmergeDao = tbDesignPhbmergeDao;
	}
	public void setTbDesignPhbDao(TbDesignPhbDao tbDesignPhbDao) {
		this.tbDesignPhbDao = tbDesignPhbDao;
	}
	public void setPageDao(PagingFactory pageDao) {
		this.pageDao = pageDao;
	}
	
	public List<TbDesignPhb> setDesignPhb(String bdCode, String bhzCode, List<TbIntensityGrade> intensityGrades)
		throws Exception {
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode) || (intensityGrades == null) || (intensityGrades.size() == 0))
			return null;
		String hql = "from TbDesignPhb where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"'";
		List<TbDesignPhb> list = tbDesignPhbDao.queryByHql(hql);
		if(list.size() == intensityGrades.size()){
			return list;
		}else{
			TbDesignPhb d = null;
			if(list.size()>0){
				//tbDesignPhbDao.deleteBySql("delete from TbDesignPhb where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"'" +
				//		" and intensityGrade not in ("+mergeIntensityGrade(intensityGrades)+")");
				for (TbIntensityGrade i : intensityGrades) {
					boolean finded = false;
					for (TbDesignPhb j : list) {
						if (i.getIntensityGrade().equals(j.getIntensityGrade())) {
							finded = true;
						}
					}
					if (!finded) {
						TbDesignPhb designPhb = new TbDesignPhb();
						designPhb.setBdCode(bdCode);
						designPhb.setBhzCode(bhzCode);
						designPhb.setIntensityGrade(i.getIntensityGrade());
						list.add(designPhb);
						tbDesignPhbDao.insert(designPhb);
					}
				}
				return list;
			}else{
				for(TbIntensityGrade t:intensityGrades){
					d = new TbDesignPhb();
					d.setBdCode(bdCode);
					d.setBhzCode(bhzCode);
					d.setIntensityGrade(t.getIntensityGrade());
					list.add(d);
					tbDesignPhbDao.insert(d);
				}
			}
			return list;
		}
	}
	
	public Map<String,Map<String,TbDesignPhbmerge>> setDesignPhbMerge(String bdCode, String bhzCode, 
			List<TbIntensityGrade> intensityGrades,List<TdMergeColsOrder> mergeColsList)
			throws Exception {
		Map<String,Map<String,TbDesignPhbmerge>> result = new LinkedHashMap<String, Map<String,TbDesignPhbmerge>>();
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode) || (intensityGrades == null) || (intensityGrades.size() == 0))
			return result;
		String hql = "from TbDesignPhbmerge where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"'";
		List<TbDesignPhbmerge> list = tbDesignPhbmergeDao.queryByHql(hql);
		for(TbDesignPhbmerge t:list){
			if(!result.containsKey(t.getIntensityGrade())){
				Map<String,TbDesignPhbmerge> subMap = new HashMap<String, TbDesignPhbmerge>();
				subMap.put(t.getMergeValue(), t);
				result.put(t.getIntensityGrade(), subMap);
			}else{
				result.get(t.getIntensityGrade()).put(t.getMergeValue(), t);
			}
		}
		if(list.size()==intensityGrades.size() && list.size()==mergeColsList.size()){
			return result;
		}else{
			TbDesignPhbmerge d = null;
			if(list.size()>0){
				for(TdMergeColsOrder i:mergeColsList){
					for (TbIntensityGrade j : intensityGrades) {
						boolean finded = false;
						for (TbDesignPhbmerge k : list) {
							if (k.getMergeValue().equals(i.getMergeCols()) && j.getIntensityGrade().equals(k.getIntensityGrade())) {
								finded = true;
							}
						}
						if (!finded) {
							d = new TbDesignPhbmerge();
							d.setBdCode(bdCode);
							d.setBhzCode(bhzCode);
							d.setIntensityGrade(j.getIntensityGrade());
							d.setMergeName(i.getMergeColsName());
							d.setMergeValue(i.getMergeCols());
							d.setOrderNum(i.getOrderNum());
							//添加到对象
							if(!result.containsKey(j.getIntensityGrade())){
								Map<String,TbDesignPhbmerge> subMap = new HashMap<String, TbDesignPhbmerge>();
								subMap.put(i.getMergeCols(), d);
								result.put(j.getIntensityGrade(), subMap);
							}else{
								result.get(j.getIntensityGrade()).put(i.getMergeCols(), d);
							}
							tbDesignPhbmergeDao.insert(d);
						}
					}
				}
			}else{
				for(TbIntensityGrade t:intensityGrades){
					for(TdMergeColsOrder m:mergeColsList){
						d = new TbDesignPhbmerge();
						d.setBdCode(bdCode);
						d.setBhzCode(bhzCode);
						d.setIntensityGrade(t.getIntensityGrade());
						d.setMergeName(m.getMergeColsName());
						d.setMergeValue(m.getMergeCols());
						d.setOrderNum(m.getOrderNum());
						//添加到对象
						if(!result.containsKey(t.getIntensityGrade())){
							Map<String,TbDesignPhbmerge> subMap = new HashMap<String, TbDesignPhbmerge>();
							subMap.put(m.getMergeCols(), d);
							result.put(t.getIntensityGrade(), subMap);
						}else{
							result.get(t.getIntensityGrade()).put(m.getMergeCols(), d);
						}
						tbDesignPhbmergeDao.insert(d);
					}
				}
			}
		}
		return result;
	}
	
	public String mergeIntensityGrade(List<TbIntensityGrade> intensityGrades) {
		if ((intensityGrades == null) || (intensityGrades.size() == 0)) {
			return "";
		} else {
			String s = "";
			for(int i = 0; i < intensityGrades.size(); i++) {
				s = s + "'" + intensityGrades.get(i).getIntensityGrade() + "',";
			}
			s = s.substring(0, s.length() - 1);
			return s;
		}
	}
	
	public void updateDesignPHB(HttpServletRequest request, List<TbParamSet> paramList) throws Exception {
		String idss = request.getParameter("ids");
		if(Util.isEmpty(idss)){
			throw new Exception("ids is null...");
		}else{
			String[] ids = idss.split("_");
			for (String id : ids) {
				TbDesignPhb d = tbDesignPhbDao.queryById(Integer.valueOf(id));
				for (TbParamSet p : paramList) {
					String value = request.getParameter(p.getPname() + "_" + id);
					Util.setObjectValue2(d, p.getPname(), value);
				}
				tbDesignPhbDao.update(d);
			}
		}
	}
	
	public void updateDesignPHBMerge(HttpServletRequest request)throws Exception{
		String idss = request.getParameter("ids");
		if(Util.isEmpty(idss)){
			throw new Exception("ids is null...");
		}else{
			String[] ids = idss.split("_");
			for (String id : ids) {
				String ext = request.getParameter("ext_"+id);
				if(Util.isEmpty(ext)){
					tbDesignPhbmergeDao.updateBySql("update TbDesignPhbmerge set ext1 = null where id="+id);
				}else{
					tbDesignPhbmergeDao.updateBySql("update TbDesignPhbmerge set ext1='"+ext+"' where id="+id);
				}
			}
		}
	}
}
