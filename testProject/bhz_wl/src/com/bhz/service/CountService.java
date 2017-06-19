package com.bhz.service;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.bhz.dao.TbBaseDao;
import com.bhz.dao.TbIntensityGradeDao;
import com.bhz.dao.TbParamSetDao;
import com.bhz.pojo.TbBase;
import com.bhz.pojo.TbIntensityGrade;
import com.bhz.pojo.TbParamSet;
import com.bhz.pojo.TdMergeColsOrder;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.Page;
import com.bhz.util.PageUtils.PagingFactory;

public class CountService extends BaseService{
	private TbBaseDao tbBaseDao;
	private PagingFactory pageDao;
	private TbParamSetDao tbParamSetDao;
	private TbIntensityGradeDao tbIntensityGradeDao;
	
	public void setTbParamSetDao(TbParamSetDao tbParamSetDao) {
		this.tbParamSetDao = tbParamSetDao;
	}
	public void setTbBaseDao(TbBaseDao tbBaseDao) {
		this.tbBaseDao = tbBaseDao;
	}
	public void setPageDao(PagingFactory pageDao) {
		this.pageDao = pageDao;
	}
	public void setTbIntensityGradeDao(TbIntensityGradeDao tbIntensityGradeDao) {
		this.tbIntensityGradeDao = tbIntensityGradeDao;
	}
	
	public Page getTbBaseList(Page page,String bhzCode,String isCurrent,
			String sdate,String edate)throws Exception{
		if(page==null)
			page = new Page();
		List<String[]> returnList = new ArrayList<String[]>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd\nHH:mm:ss");
		String currDate = Util.getCurrentDate("yyyy-MM-dd");
		if(!Util.isEmpty(isCurrent) && isCurrent.equals("1")){
			sdate = currDate+" 00:00:00";
			edate = currDate+" 23:59:59";
		}
		StringBuffer hql = new StringBuffer("TbBase where m1='1' and m6='"+bhzCode+"'");
		if(!Util.isEmpty(sdate)){
			hql.append(" and m4>='"+sdate+"'");
		}
		if(!Util.isEmpty(edate)){
			hql.append(" and m4<='"+edate+"'");
		}
		page.setPageSize(10);
		page.setObjName(hql.toString());
		page.setOrderBy("m4 desc");
		page = pageDao.queryObjList(page);
		List baseList = page.getObjList();
		String[] result = null;
		for(int i=0;i<baseList.size();i++){
			TbBase t = (TbBase)baseList.get(i);
			result = new String[3];
			result[0] = t.getM9();
			result[1] = t.getM6();
			if(t.getM4()!=null){
				//result[2] = sf.format(t.getM4());
				result[2] = t.getM4();
			}
			returnList.add(result);
		}
		page.setObjList(returnList);
		return page;
	}
	
	public Map<String,String> getParamValueMap(String bdCode,String bhzCode)throws Exception{
		String hql = "from TbParamSet where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"' and flag='1'";
		List<TbParamSet> paramSetList =  tbParamSetDao.queryByHql(hql);
		Map<String,String> resultMap = new TreeMap<String,String>();
		for(TbParamSet s : paramSetList){
			resultMap.put(s.getPname()+"_s1", s.getPvalue());	//实际
			resultMap.put(s.getPname()+"_s2", s.getPvalue()+"(施工)");	//施工
		}
		return resultMap;
	}
	
	public Page getCountClylData(Page page,String bdCode,String bhzCode,String sdate,String edate)throws Exception{
		List<Object> returnObj = new ArrayList<Object>();
		if(page==null)
			page = new Page();
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode))
			return new Page();
		StringBuffer hql = new StringBuffer("TbBase where m1<>'0' and m2='"+bdCode+"' and m6='"+bhzCode+"'");
		if(!Util.isEmpty(sdate))
			hql.append(" and m4>='"+sdate+"'");
		if(!Util.isEmpty(edate))
			hql.append(" and m4<='"+edate+"'");
		page.setPageSize(6);
		page.setObjName(hql.toString());
		page = pageDao.queryObjList(page);
		List baseList = page.getObjList();
		Map<String,List<TbBase>> baseMap = new TreeMap<String, List<TbBase>>();
		Map<String,String> paramSetMap = getParamValueMap(bdCode, bhzCode);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd\nHH:mm:ss");
		String[] rowKeys = new String[paramSetMap.size()];
		String[] columnKeys = new String[baseList.size()/2];
		double[][] data = new double[paramSetMap.size()][baseList.size()/2];
		int i = 0,j=0;
		for(String p : paramSetMap.keySet()){
			rowKeys[i] = paramSetMap.get(p);
			i++;
		}
		List<TbBase> list = null;
		for(int k=0;k<baseList.size();k++){
			TbBase b = (TbBase)baseList.get(k);
			if("1".equals(b.getM1())){
				if(b.getM4()!=null){
					columnKeys[j] = sf.format(b.getM4());
					j++;
				}
				if(!baseMap.containsKey("shiji")){
					list = new ArrayList<TbBase>();
					list.add(b);
					baseMap.put("shiji", list);
				}else{
					baseMap.get("shiji").add(b);
				}
			}else if("2".equals(b.getM1())){
				if(!baseMap.containsKey("shigong")){
					list = new ArrayList<TbBase>();
					list.add(b);
					baseMap.put("shigong", list);
				}else{
					baseMap.get("shigong").add(b);
				}
			}
		}
		int l = 0;
		for(String p : paramSetMap.keySet()){
			String key = p.substring(0, p.length()-3);
			for(int k=0;k<columnKeys.length;k++){
				if(p.contains("_s1")){
					double val = Util.getObjectValue(baseMap.get("shiji").get(k), key);
					data[l][k] = val;
				}else if(p.contains("_s2")){
					double val = Util.getObjectValue(baseMap.get("shigong").get(k), key);
					data[l][k] = val;
				}
			}
			l++;
		}
		
		returnObj.add(rowKeys);
		returnObj.add(columnKeys);
		returnObj.add(data);
		page.setObjList(returnObj);
		return page;
	}
	
	public List getCountCnfxData(String bdCode,String bhzCode,String sdate,String edate)throws Exception{
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode))
			return new ArrayList();
		StringBuffer sql = new StringBuffer("select convert(varchar(7),m4,120) as date,sum(m10) as fangliang,count(*) as panshu " +
				"from tbbase where m1='1' and m2='"+bdCode+"' and m6='"+bhzCode+"'");
		if(!Util.isEmpty(sdate))
			sql.append(" and m4>='"+sdate+"'");
		if(!Util.isEmpty(edate))
			sql.append(" and m4<='"+edate+"'");
		sql.append("group by convert(varchar(7),m4,120)");
		List list = tbBaseDao.queryBySql(sql.toString());
		return list;
	}
	
	public List<Object> getCountCnfxData_SJ(List<TbParamSet> paramList,List sumList,
			String bdCode,String bhzCode,String sdate,String edate)throws Exception{
		List<Object> list = new ArrayList<Object>();
		if(paramList.size()==0 || sumList.size()==0)
			return list;
		//实际数据
		String hql = "from TbBase where m1 = '1' and m2='"+bdCode+"' and m6='"+bhzCode+"'";
		List<TbBase> sjBaseList = tbBaseDao.queryByHql(hql);
		String[] keys = new String[paramList.size()];
		Double[] data = new Double[paramList.size()];
		double totalNum = 0d;
		for(int i=0;i<sumList.size();i++){
			Object[] obj = (Object[])sumList.get(i);
			if(obj[1]!=null)
				if(!obj[1].toString().equals(""))
					totalNum += Double.valueOf(obj[1].toString());
		}
		int i = 0;
		for(TbParamSet s:paramList){
			double val = 0d;
			for(TbBase b:sjBaseList){
				val += Util.getObjectValue(b, s.getPname());
			}
			keys[i] = s.getPvalue();
			if(totalNum>0)
				data[i] = Util.toDecimal(val/totalNum*100,2);
			else
				data[i] = 0d;
			i++;
		}
		list.add(keys);
		list.add(data);
		return list;
	}
	
	public List<Object> getCountCnfxData_LL(List<TbParamSet> paramList,List sumList,
			String bdCode,String bhzCode,String sdate,String edate)throws Exception{
		List<Object> list = new ArrayList<Object>();
		if(paramList.size()==0 || sumList.size()==0)
			return list;
		//理论数据
		String hql = "from TbBase where m1 = '2' and m2='"+bdCode+"' and m6='"+bhzCode+"'";
		TbBase sjBaseObj = null;
		if(tbBaseDao.queryByHql(hql).size()>0)
			sjBaseObj =tbBaseDao.queryByHql(hql).get(0);
		String[] keys = new String[paramList.size()];
		Double[] data = new Double[paramList.size()];
		double totalNum = 0d;
		for(int i=0;i<sumList.size();i++){
			Object[] obj = (Object[])sumList.get(i);
			if(obj[1]!=null)
				if(!obj[1].toString().equals(""))
					totalNum += Double.valueOf(obj[1].toString());
		}
		int i = 0;
		for(TbParamSet s:paramList){
			double val = Util.getObjectValue(sjBaseObj, s.getPname());
			keys[i] = s.getPvalue();
			if(totalNum>0)
				data[i] = Util.toDecimal(val/totalNum*100,2);
			else
				data[i] = 0d;
			i++;
		}
		list.add(keys);
		list.add(data);
		return list;
	}
	
	public List<TbParamSet> getParamSetList(String bdCode)throws Exception{
		List<TbParamSet> returnList = new ArrayList<TbParamSet>();
		if(Util.isEmpty(bdCode))
			return returnList;
		String hql = "from TbParamSet where bdCode='"+bdCode+"' and flag='1' " +
						"and pvalue like '%水泥%' order by bhzCode,pName";
		List<TbParamSet> list = tbParamSetDao.queryByHql(hql);
		Map<String,String> map = new HashMap<String,String>();
		for(TbParamSet t:list){
			String key = t.getBdCode()+"-"+t.getBhzCode();
			if(map.containsKey(key)){
				returnList.add(t);
			}else{
				if(map.size()==0){
					returnList.add(t);
				}
				map.put(key, "1");
			}
		}
		return returnList;
	}
	
	public List<TbIntensityGrade> getQdGradeBybdAndbhz(String bdCode,String bhzCode)
		throws Exception{
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode))
			return null;
		String hql = "from TbIntensityGrade where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"'";
		return tbIntensityGradeDao.queryByHql(hql);
	}
	
	public List<String> getDistQiangDu()throws Exception{
		String hql = "from TbIntensityGrade order by intensityGrade";
		List<TbIntensityGrade> list = tbIntensityGradeDao.queryByHql(hql);
		List<String> result = new ArrayList<String>();
		for(TbIntensityGrade t:list){
			result.add(t.getIntensityGrade());
		}
		return result;
	}
	
	public List getTotalConcrete(HttpServletRequest request,String morebdCode,String bdCode,String bhzCode,
			String m8,String m5,String sdate,String edate,String isGet)throws Exception{
		String strWhere = getStrWhere(request,morebdCode, bdCode, bhzCode, m8, m5, sdate, edate, isGet);
		Map<String,String> connectMap = getConnectStr(morebdCode, bdCode, bhzCode, m8, m5, sdate, edate);
		String selColumn = getSqlColumn(connectMap);
		StringBuffer sumColumn = new StringBuffer("");
		sumColumn.append(" ,");
		for(int i=1;i<=20;i++){
			sumColumn.append("isnull(sum(convert(float,a"+i+")),0)");
			if(i<20){
				sumColumn.append("+");
			}
		}
		sumColumn.append(" as totalNum ");
		String sql = "select sum(m10) as totalFl,sum(totalNum) as totalZl from ( " +
				"select "+selColumn+",sum(m10) as m10 "+sumColumn.toString()+" from TbBase " +
				"where m1=1 "+strWhere+" group by "+selColumn+" ) z";
		List returnList = tbBaseDao.queryBySql(sql);
		return returnList;
	}
	
	public Page getTotalConcrete(HttpServletRequest request,Page page,String morebdCode,String bdCode,String bhzCode,
			String m8,String m5,String sdate,String edate,String isGet)throws Exception{
		if(page==null)
			page = new Page();
		String strWhere = getStrWhere(request, morebdCode, bdCode, bhzCode, m8, m5, sdate, edate, isGet);
		Map<String,String> connectMap = getConnectStr(morebdCode, bdCode, bhzCode, m8, m5, sdate, edate);
		String selColumn = getSqlColumn(connectMap);
		StringBuffer sumColumn = new StringBuffer("");
		sumColumn.append(" ,");
		for(int i=1;i<=20;i++){
			sumColumn.append("isnull(sum(convert(float,a"+i+")),0)");
			if(i<20){
				sumColumn.append("+");
			}
		}
		sumColumn.append(" as totalNum ");
		String sql = "select "+selColumn+",sum(m10) as m10 "+sumColumn.toString()+" from TbBase " +
				"where m1=1 "+strWhere+" group by "+selColumn;
		page.setObjName(sql);
		page.setOrderBy(selColumn);
		page.setPageSize(30);
		Page returnPage = pageDao.queryObjListBySql(page);
		return returnPage;
	}
	
	public List<TbBase> getDetailConcrete(HttpServletRequest request,String morebdCode,String bdCode,String bhzCode,String m8,
			String m5,String sdate,String edate,String isGet)throws Exception{
		String strWhere = getStrWhere(request, morebdCode, bdCode, bhzCode, m8, m5, sdate, edate, isGet);
		Map<String,String> connectMap = getConnectStr(morebdCode, "bd", "bhz", null, null, sdate, edate);
		String selColumn = getSqlColumn(connectMap);
		StringBuffer sumColumn = new StringBuffer("");
		for(int i=1;i<=20;i++){
			sumColumn.append("sum(convert(float,a"+i+")) as a"+i);
			if(i<20){
				sumColumn.append(",");
			}
		}
		String sql = "select "+selColumn+","+sumColumn.toString()+" from TbBase " +
		"where m1=1 "+strWhere+" group by "+selColumn+" order by "+selColumn;
		List pageList = tbBaseDao.queryBySql(sql);
		String fieldNames = selColumn+",a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20";
		String[] arrFieldName = fieldNames.split(",");
		TbBase tbbase = null;
		List<TbBase> tbbaseList = new ArrayList<TbBase>();
		for(int i=0;i<pageList.size();i++){
			Object[] objs = (Object[])pageList.get(i);
			tbbase = new TbBase();
			for(int j=0;j<arrFieldName.length;j++){
				Util.setObjectValue(tbbase, arrFieldName[j], objs[j]==null?"":objs[j].toString());
			}
			tbbaseList.add(tbbase);
		}
		return tbbaseList;
	}
	
	public List getDetailConcreteMerge(HttpServletRequest request,String morebdCode,String bdCode,String bhzCode,String m8,
			String m5,String sdate,String edate,String isGet,List<TdMergeColsOrder> mergeList)throws Exception{
		List returnList = new ArrayList();
		if(mergeList==null || mergeList.size()==0)
			return returnList;
		String strWhere = getStrWhere(request, morebdCode, bdCode, bhzCode, m8, m5, sdate, edate, isGet);
		Map<String,String> connectMap = getConnectStr(morebdCode, "bd", "bhz", null, null, sdate, edate);
		String selColumn = getSqlColumn(connectMap);
		String selClos = getMergeColsStr(mergeList);
		String sql = "select "+selColumn+","+selClos+" from TbBase " +
		"where m1=1 "+strWhere+" group by "+selColumn+" order by "+selColumn;
		returnList = tbBaseDao.queryBySql(sql);
		return returnList;
	}
	
	//统计累计误差
	public Page getCountWcData(Page page,HttpServletRequest request,String bdCode,String bhzCode,String m5,
			String m8,String sdate,String edate,List<TdMergeColsOrder> mergeList)throws Exception{
		Map<String,List<Object[]>> result = new LinkedHashMap<String, List<Object[]>>();
		if(page==null)
			page = new Page();
		if(mergeList==null || mergeList.size()==0)
			return page;
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode))
			return page;
		String strWhere = getStrWhere(request, null, bdCode, bhzCode,  m8, m5,sdate, edate, null);
		String selColumn = "max(groupId) as groupId,m1,m2,m6,m5,m8,min(m4) as minM4,max(m4) as maxM4,sum(convert(float,isnumeric(m10))) as 方量";
		String selClos = getMergeColsStr(mergeList);
		String sql = "select "+selColumn+","+selClos+" from TbBase " +
		"where 1=1 "+strWhere+" group by m1,m2,m6,m5,m8";
		page.setPageSize(30);
		page.setObjName(sql.toString());
		page.setOrderBy("CONVERT(datetime,min(m4)) desc,groupId,m1");
		page = pageDao.queryObjListBySql(page);
		List baseList = page.getObjList();
		List<Object[]> list = null;
		for(int i=0;i<baseList.size();i++){
			Object[] objs = (Object[])baseList.get(i);
			if(objs[0]!=null){
				String groupId = objs[0]==null?"":objs[0].toString();
				String key = groupId;
				if(!result.containsKey(key)){
					list = new ArrayList<Object[]>();
					list.add(objs);
					result.put(key, list);
				}else{
					result.get(key).add(objs);
				}
			}
		}
		page.setPageSize(30/3);
		page.setBaseObj(result);
		page.setTotalCount((int) Math.floor(page.getTotalCount()/3));
		return page;
	}

	//车载积累误差统计
	public Page getCountCzWcData( Page page,HttpServletRequest request,String bdCode,String bhzCode,String m5,
								  String m8, String sdate, String edate, String bhzCarOrder, List<TdMergeColsOrder> mergeList){
		Map<String,List<Object[]>> result = new LinkedHashMap<String, List<Object[]>>();
		try {
			if(page==null)
				page = new Page();
			if(mergeList==null || mergeList.size()==0)
				return page;
			if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode))
                return page;
			String strWhere=getStrWhere(request,bdCode,bhzCode,m5,m8,sdate,edate,bhzCarOrder);
			String selClos = getMergeColsStr(mergeList);
			String sql="select m1,m2,m6,bhzCarNum,bhzCarOrder,m5,m8,min(m4) as minM4,max(m4) as maxM4,bhzCarBjType from TbBase where 1=1"
					+strWhere+" group by m1,m2,m6,m5,m8,bhzCarNum,bhzCarOrder,bhzCarBjType";
			page.setPageSize(30);
			page.setObjName(sql);
			page.setOrderBy("CONVERT(datetime,max(m4)) desc");
			page = pageDao.queryObjListBySql(page);
			List baseList = page.getObjList();

			String sql1="select m1,bhzCarOrder,sum(m10) as 方量,"+selClos+" from TbBase where 1=1 "+strWhere+" group by m1,bhzCarOrder,m10 order by max(m4) desc";

			page.setPageSize(30);
			page.setObjName(sql1);
			page.setOrderBy(null);
			page=pageDao.queryObjCzListBySql(page);

			List baseList1=page.getObjList();

			Map<String,List<Object[]>> tmp = new LinkedHashMap<String, List<Object[]>>();
			tmp=Util.createNewMap(tmp,baseList1);
			Iterator it=tmp.keySet().iterator();
			while (it.hasNext()){
				String key= (String) it.next();
				List<Object[]> objs=tmp.get(key);
				for (int i = 0; i <baseList.size() ; i++) {
					Object [] o= (Object[]) baseList.get(i);
					if(key.equals(o[4])){
						Object[] a=objs.get(0);
						List<Object []> tmpObjs =Util.createNewObjArray(objs,o,o.length+a.length-2);
						result.put(key,tmpObjs);
					}
				}

			}
			page.setPageSize(10);
			page.setBaseObj(result);
			//page.setTotalCount((int) Math.floor(page.getTotalCount()/3));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return page;
	}
	
	public Map<String,List<TbParamSet>> getParamSetMap()throws Exception{
		String hql = "from TbParamSet where flag='1'";
		List<TbParamSet> paramList = tbParamSetDao.queryByHql(hql);
		Map<String,List<TbParamSet>> result = new LinkedHashMap<String, List<TbParamSet>>();
		List<TbParamSet> objList = null;
		for(TbParamSet t:paramList){
			String key = t.getBdCode()+"-"+t.getBhzCode();
			if(!result.containsKey(key)){
				objList = new ArrayList<TbParamSet>();
				objList.add(t);
				result.put(key, objList);
			}else{
				result.get(key).add(t);
			}
		}
		return result;
	}

	public String getStrWhere(HttpServletRequest request,String bdCode,String bhzCode,String m5,
							  String m8,String sdate,String edate,String bhzCarOrder){
		String strWhere = "";
		try {
			if(!Util.isEmpty(bdCode))
                strWhere +=" and m2 = '"+bdCode+"'";
			if(!Util.isEmpty(bhzCode))
				strWhere +=" and m6 = '"+bhzCode+"'";
			if(!Util.isEmpty(m8))
				strWhere +=" and m8 collate Chinese_PRC_CS_AS_WS= '"+m8+"'";
			if(!Util.isEmpty(m5)){
				//strWhere += Util.getNewString("m5",m5);
				strWhere += " and m5 like '%"+m5+"%' ";
			}
			if(!Util.isEmpty(sdate))
				strWhere +=" and m4>='"+sdate+"'";
			if(!Util.isEmpty(edate))
				strWhere +=" and m4<='"+edate+"'";
			if(!Util.isEmpty(bhzCarOrder)){
				strWhere +=" and bhzCarOrder like '%"+bhzCarOrder+"%' ";
			}
			//权限过滤对应标段及拌合站
			String username = Util.getUserName(request);
			if(!"admin".equals(username)){
				Map<String,String> filterData = getBdBhzFilterAuth(request);
				String bdIn = filterData.get("bdIn");
				if(!Util.isEmpty(bdIn)){
					strWhere+=" and m2 in ("+bdIn+")";
				}else{
					strWhere+=" and 1=2";
				}
				String bhzIn = filterData.get("bhzIn");
				if(!Util.isEmpty(bhzIn)){
					strWhere+=" and m6 in ("+bhzIn+")";
				}else{
					strWhere+=" and 1=2";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strWhere;
	}

	public String getStrWhere(HttpServletRequest request,String morebdCode,String bdCode,String bhzCode,String m8,
			String m5,String sdate,String edate,String isGet)throws Exception{
		String strWhere = "";
		if(!Util.isEmpty(morebdCode)){
			String inStr = getInString(morebdCode);
			strWhere +=" and m2 in (" + inStr + ")";
		}
		if(!Util.isEmpty(bdCode))
			strWhere +=" and m2 = '"+bdCode+"'";
		if(!Util.isEmpty(bhzCode))
			strWhere +=" and m6 = '"+bhzCode+"'";
		if(!Util.isEmpty(m8))
			strWhere +=" and m8 collate Chinese_PRC_CS_AS_WS= '"+m8+"'";
		if(!Util.isEmpty(m5)){
			strWhere += Util.getNewString("m5",m5);
		}
		if(!Util.isEmpty(sdate))
			strWhere +=" and m4>='"+sdate+"'";
		if(!Util.isEmpty(edate))
			strWhere +=" and m4<='"+edate+"'";
		//权限过滤对应标段及拌合站
		String username = Util.getUserName(request);
		if(!"admin".equals(username)){
			Map<String,String> filterData = getBdBhzFilterAuth(request);
			String bdIn = filterData.get("bdIn");
			if(!Util.isEmpty(bdIn)){
				strWhere+=" and m2 in ("+bdIn+")";
			}else{
				strWhere+=" and 1=2";
			}
			String bhzIn = filterData.get("bhzIn");
			if(!Util.isEmpty(bhzIn)){
				strWhere+=" and m6 in ("+bhzIn+")";
			}else{
				strWhere+=" and 1=2";
			}
		}
		return strWhere;
	}
	
	public Map<String,String> getConnectStr(String morebdCode,String bdCode,String bhzCode,
			String m8,String m5,String sdate,String edate)throws Exception{
		Map<String,String> resultMap = new LinkedHashMap<String, String>();
		if(!Util.isEmpty(morebdCode) || !Util.isEmpty(bdCode)){
			resultMap.put("m2", "标段名称");
		}
		if(!Util.isEmpty(bhzCode)){
			resultMap.put("m6", "拌合站名称");
		}
		if(!Util.isEmpty(m8)){
			resultMap.put("m8", "强度等级");
		}
		if(!Util.isEmpty(m5)){
			resultMap.put("m5", "使用部位");
		}
//		if(!Util.isEmpty(sdate)){
//			resultMap.put("m4", "出料时间");
//		}
//		if(!Util.isEmpty(edate)){
//			resultMap.put("m4", "出料时间");
//		}
		if(resultMap.size()==0){
			resultMap.put("m2", "标段名称");
		}
		return resultMap;
	}
	
	public String getSqlColumn(Map<String,String> m)throws Exception{
		String result = "";
		int i = 1;
		for(String s:m.keySet()){
			result+=s;
			if(i < m.size()){
				result+=",";
			}
			i++;
		}
		return result.replace("m4", "convert(varchar(10),m4,120)");
	}
	
	public String getInString(String str) {
		String result = "";
		String[] arr = str.split(",");
		int i = 0;
		for (String s : arr) {
			i++;
			result += "'" + s + "'";
			if (arr.length > i) {
				result += ",";
			}
		}
		return result;
	}
	
	public String getMergeColsStr(List<TdMergeColsOrder> mergeList)throws Exception{
		StringBuffer sb = new StringBuffer("");
		int i = 1;
		for(TdMergeColsOrder t : mergeList){
			if(!Util.isEmpty(t.getMergeCols())){
				String[] arr  = t.getMergeCols().split("\\+");
				int j = 1;
				sb.append("sum(");
				for(String s : arr){
					sb.append("convert(float,isnull("+s+",0))");
					if(j<arr.length){
						sb.append("+");
					}
					j++;
				}
			}
			if(!Util.isEmpty(sb.toString())){
				sb.append(") as "+t.getMergeColsName());
			}
			if(i<mergeList.size()){
				sb.append(",");
			}
			i++;
		}
		return sb.toString();
	}
}
