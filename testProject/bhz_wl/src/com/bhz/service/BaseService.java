package com.bhz.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import com.bhz.dao.TbBaoJingMainDao;
import com.bhz.dao.TbBaoJingMainMergeDao;
import com.bhz.dao.TbBaoJingSubDao;
import com.bhz.dao.TbBaoJingSubMergeDao;
import com.bhz.dao.TbBdDao;
import com.bhz.dao.TbBhzDao;
import com.bhz.pojo.TbBaoJingMain;
import com.bhz.pojo.TbBaoJingMainMerge;
import com.bhz.pojo.TbBaoJingSub;
import com.bhz.pojo.TbBaoJingSubMerge;
import com.bhz.pojo.TbBd;
import com.bhz.pojo.TbBhz;
import com.bhz.util.ConstantUtil;
import com.bhz.util.Util;

public class BaseService {
	public TbBdDao tbBdDao;
	public TbBhzDao tbBhzDao;
	public TbBaoJingSubDao tbBaoJingSubDao;
	public TbBaoJingSubMergeDao tbBaoJingSubMergeDao;
	public TbBaoJingMainDao tbBaoJingMainDao;
	public TbBaoJingMainMergeDao tbBaoJingMainMergeDao;
	
	public void setTbBdDao(TbBdDao tbBdDao) {
		this.tbBdDao = tbBdDao;
	}
	public void setTbBhzDao(TbBhzDao tbBhzDao) {
		this.tbBhzDao = tbBhzDao;
	}
	public void setTbBaoJingMainDao(TbBaoJingMainDao tbBaoJingMainDao) {
		this.tbBaoJingMainDao = tbBaoJingMainDao;
	}
	public void setTbBaoJingMainMergeDao(TbBaoJingMainMergeDao tbBaoJingMainMergeDao) {
		this.tbBaoJingMainMergeDao = tbBaoJingMainMergeDao;
	}
	public void setTbBaoJingSubDao(TbBaoJingSubDao tbBaoJingSubDao) {
		this.tbBaoJingSubDao = tbBaoJingSubDao;
	}
	public void setTbBaoJingSubMergeDao(TbBaoJingSubMergeDao tbBaoJingSubMergeDao) {
		this.tbBaoJingSubMergeDao = tbBaoJingSubMergeDao;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,String> getBdBhzFilterAuth(HttpServletRequest request)throws Exception{
		Map<String,String> result = new HashMap<String,String>();
		Object obj = request.getSession().getAttribute("userauthor");
		if(obj==null)
			return result;
		Map<String,String> userAuthMap = (Map<String,String>)obj;
		if(userAuthMap==null || userAuthMap.size()==0)
			return result;
		Set<String> keys = userAuthMap.keySet();
		StringBuffer bdCodes = new StringBuffer("");
		StringBuffer bhzCodes = new StringBuffer("");
		for(String s:keys){
			String val = userAuthMap.get(s);
			if("1".equals(val) || "2".equals(val)){
				if(s.contains("-bd")){
					String bdCode = s.substring(s.lastIndexOf("bd")+2,s.length());
					bdCodes.append("'"+bdCode+"'").append(",");
				}else if(s.contains("-bhz")){
					String bhzCode = s.substring(s.lastIndexOf("bhz")+3,s.length());
					bhzCodes.append("'"+bhzCode+"'").append(",");
				}
			}
		}
		if(!Util.isEmpty(bdCodes.toString())){
			String bd = bdCodes.toString();
			result.put("bdIn", bd.substring(0,bd.length()-1));
		}
		if(!Util.isEmpty(bhzCodes.toString())){
			String bhz = bhzCodes.toString();
			result.put("bhzIn", bhz.substring(0,bhz.length()-1));
		}
		return result;
	}
	
	public List<TbBd> queryTbBd(HttpServletRequest request)throws Exception{
		List<TbBd> result = new ArrayList<TbBd>();
		List<TbBd> list = tbBdDao.queryByHql("from TbBd order by orderNum");
		for(TbBd t : list){
			String bdKey = Util.getUserName(request)+"-bd"+t.getBdCode();
			if(Util.isUserAuthor(request, bdKey, "1") || Util.isUserAuthor(request, bdKey, "2")){
				result.add(t);
			}
		}
		return result;
	}

	public List<TbBd> getAllBD()throws Exception{
		return tbBdDao.queryByHql("from TbBd order by orderNum");
	}
	
	public TbBd getTbBd(String bdCode)throws Exception{
		if(Util.isEmpty(bdCode))
			return null;
		TbBd tbBd = tbBdDao.queryById(bdCode);
		return tbBd;
	}
	
	public Map<String,TbBd> getMapTbBd()throws Exception{
		List<TbBd> tbBds = getAllBD();
		Map<String,TbBd> mapTbBd = new HashMap<String,TbBd>();
		for(TbBd t : tbBds){
			mapTbBd.put(t.getBdCode(), t);
		}
		return mapTbBd;
	}
	
	public Map<String, String> getBdMap() throws Exception {
		List<TbBd> tbBds = getAllBD();
		Map<String, String> bdMap = new HashMap<String, String>();
		for (TbBd t : tbBds) {
			bdMap.put(t.getBdCode(), t.getBdName());
		}
		return bdMap;
	}
	
	public List<TbBhz> getAllBhz(HttpServletRequest request) throws Exception {
		List<TbBhz> result = new ArrayList<TbBhz>();
		List<TbBhz> objList = tbBhzDao.queryAll();
		for(TbBhz t:objList){
			String bhzKey = Util.getUserName(request)+"-bhz"+t.getBhzCode();
			if(Util.isUserAuthor(request, bhzKey, "1") || Util.isUserAuthor(request, bhzKey, "2")){
				result.add(t);
			}
		}
		return result;
	}

	public List<TbBhz> getAllBHZ() throws Exception {
		return tbBhzDao.queryAll();
	}
	
	public TbBhz getTbBhz(String bhzCode)throws Exception{
		if(Util.isEmpty(bhzCode))
			return null;
		String hql = "from TbBhz where bhzCode='"+bhzCode+"'";
		List<TbBhz> list = tbBhzDao.queryByHql(hql);
		if(list.size()==0)
			return null;
		return list.get(0);
	}
	
	public Map<String,TbBhz> getMapTbBhz()throws Exception{
		List<TbBhz> bhzList = getAllBHZ();
		Map<String,TbBhz> mapTbBhz = new HashMap<String,TbBhz>();
		for(TbBhz t : bhzList){
			mapTbBhz.put(t.getBhzCode(), t);
		}
		return mapTbBhz;
	}
	
	public Map<String, String> getBhzMap() throws Exception {
		List<TbBhz> bhzList = getAllBHZ();
		Map<String, String> mapTbBhz = new HashMap<String, String>();
		for(TbBhz t : bhzList){
			mapTbBhz.put(t.getBhzCode(), t.getBhzName());
		}
		return mapTbBhz;
	}
	
	public List<TbBhz> queryTbBhzByBd(HttpServletRequest request,String bdCode)throws Exception{
		List<TbBhz> result = new ArrayList<TbBhz>();
		if(Util.isEmpty(bdCode))
			return result;
		String hql = "from TbBhz where bdCode='"+bdCode+"'";
		List<TbBhz> objList = tbBhzDao.queryByHql(hql);
		for(TbBhz t:objList){
			//String bhzKey = Util.getUserName(request)+"-bhz"+t.getBhzCode();
			//if(Util.isUserAuthor(request, bhzKey, "1") || Util.isUserAuthor(request, bhzKey, "2")){
				result.add(t);
			//}
		}
		return result;
	}
	
	public Map<String,String> getTbBhzMap(String bhzCode)throws Exception{
		Map<String,String> returnMap = new HashMap<String,String>();
		String hql = "from TbBhz where bhzCode='"+bhzCode+"'";
		List<TbBhz> bhzList = tbBhzDao.queryByHql(hql);
		if(bhzList.size()>0){
			TbBhz bhz = bhzList.get(0);
			returnMap.put("up", bhz.getUpNum());
			returnMap.put("down", bhz.getDownNum());
		}
		return returnMap;
	}

	public Collection<TbBhz> getBHZsByBdCode(HttpServletRequest request,String bdCode)
		throws Exception {
		return queryTbBhzByBd(request, bdCode);
	}
	
	public List<TbBaoJingMain> getBaoJingMList()throws Exception{
		String mainHql = "from TbBaoJingMain where isBaojing='1'";
		List<TbBaoJingMain> mainList = tbBaoJingMainDao.queryByHql(mainHql);
		return mainList;
	}
	
	public List<TbBaoJingMainMerge> getBaoJingMainMergeList() throws Exception{
		String mainHql = "from TbBaoJingMainMerge where isBaojing = '1'";
		List<TbBaoJingMainMerge> mainList = tbBaoJingMainMergeDao.queryByHql(mainHql);
		return mainList;
	}
	
	public List<TbBaoJingSub> getBaoJingSList()throws Exception{
		List<TbBaoJingSub> subList = tbBaoJingSubDao.queryAll();
		return subList;
	}
	
	public List<TbBaoJingSubMerge> getBaoJingSubMergeList()throws Exception{
		List<TbBaoJingSubMerge> subList = tbBaoJingSubMergeDao.queryAll();
		return subList;
	}
	
	public void setBdCache() throws Exception {
		ConstantUtil.bdMap = getMapTbBd();
	}
	
	public void setBhzCache() throws Exception {
		ConstantUtil.bhzMap = getMapTbBhz();
	}
	
	public void setBaoJingCache()throws Exception{
		ConstantUtil.bjMainMap = new HashMap<String,List<TbBaoJingMain>>();
		ConstantUtil.bjSubMap = new HashMap<String,List<TbBaoJingSub>>();
		
		List<TbBaoJingMain> mainList = getBaoJingMList();
		List<TbBaoJingSub> subList = getBaoJingSList();
		List<TbBaoJingMain> mList = null;		
		for(TbBaoJingMain m:mainList){
			String key = m.getBdCode()+"-"+m.getBhzCode()+"-"+m.getPhbQd();
			if(!ConstantUtil.bjMainMap.containsKey(key)){
				mList = new ArrayList<TbBaoJingMain>();
				mList.add(m);
				ConstantUtil.bjMainMap.put(key, mList);
			}else{
				ConstantUtil.bjMainMap.get(key).add(m);
			}
		}
		List<TbBaoJingSub> sList = null;
		for(TbBaoJingSub s:subList){
			String key = s.getBdCode()+"-"+s.getBhzCode()+"-"+s.getPhbQd();
			if(!ConstantUtil.bjSubMap.containsKey(key)){
				sList = new ArrayList<TbBaoJingSub>();
				sList.add(s);
				ConstantUtil.bjSubMap.put(key, sList);
			}else{
				ConstantUtil.bjSubMap.get(key).add(s);
			}
		}
		System.out.println("bjMainMap size="+ConstantUtil.bjMainMap.size());
		System.out.println("bjSubMap size="+ConstantUtil.bjSubMap.size());
	}
	
	public void setBaoJingCacheMerge() throws Exception {
		ConstantUtil.bjMainMergeMap = new HashMap<String, List<TbBaoJingMainMerge>>();
		ConstantUtil.bjSubMergeMap = new HashMap<String, List<TbBaoJingSubMerge>>();
		
		List<TbBaoJingMainMerge> mainList = getBaoJingMainMergeList();
		List<TbBaoJingSubMerge> subList = getBaoJingSubMergeList();
		List<TbBaoJingMainMerge> mList = null;
		for(TbBaoJingMainMerge m : mainList) {
			String key = m.getBdCode() + "-" + m.getBhzCode() + "-" + m.getPhbQd();
			if(!ConstantUtil.bjMainMergeMap.containsKey(key)) {
				mList = new ArrayList<TbBaoJingMainMerge>();
				mList.add(m);
				ConstantUtil.bjMainMergeMap.put(key, mList);
			} else {
				ConstantUtil.bjMainMergeMap.get(key).add(m);
			}
		}
		List<TbBaoJingSubMerge> sList = null;
		for(TbBaoJingSubMerge s : subList) {
			String key = s.getBdCode() + "-" + s.getBhzCode() + "-" + s.getPhbQd();
			if(!ConstantUtil.bjSubMergeMap.containsKey(key)) {
				sList = new ArrayList<TbBaoJingSubMerge>();
				sList.add(s);
				ConstantUtil.bjSubMergeMap.put(key, sList);				
			} else {
				ConstantUtil.bjSubMergeMap.get(key).add(s);
			}
		}
		System.out.println("bjMainMergeMap size=" + ConstantUtil.bjMainMergeMap.size());
		System.out.println("bjSubMergeMap size=" + ConstantUtil.bjSubMergeMap.size());		
	}
	
}
