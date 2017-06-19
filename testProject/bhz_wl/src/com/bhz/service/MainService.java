package com.bhz.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.bhz.dao.TbBaseDao;
import com.bhz.dao.TbDesignPhbDao;
import com.bhz.dao.TbDesignPhbmergeDao;
import com.bhz.dao.TbParamSetDao;
import com.bhz.dao.TdMergeColsOrderDao;
import com.bhz.pojo.TbBase;
import com.bhz.pojo.TbBhz;
import com.bhz.pojo.TbDesignPhb;
import com.bhz.pojo.TbDesignPhbmerge;
import com.bhz.pojo.TbParamSet;
import com.bhz.pojo.TdMergeColsOrder;
import com.bhz.util.ConstantUtil;
import com.bhz.util.FileUtil;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.Page;
import com.bhz.util.PageUtils.PagingFactory;

public class MainService extends BaseService{
	private TbBaseDao tbBaseDao;
	private PagingFactory pageDao;
	private TbParamSetDao tbParamSetDao;
	private TbDesignPhbDao tbDesignPhbDao;
	private TbDesignPhbmergeDao tbDesignPhbmergeDao;
	private TdMergeColsOrderDao tdMergeColsOrderDao;
	
	public TbBaseDao getTbBaseDao() {
		return tbBaseDao;
	}
	public void setTbBaseDao(TbBaseDao tbBaseDao) {
		this.tbBaseDao = tbBaseDao;
	}
	public TbParamSetDao getTbParamSetDao() {
		return tbParamSetDao;
	}
	public void setTbParamSetDao(TbParamSetDao tbParamSetDao) {
		this.tbParamSetDao = tbParamSetDao;
	}
	public void setTbDesignPhbDao(TbDesignPhbDao tbDesignPhbDao) {
		this.tbDesignPhbDao = tbDesignPhbDao;
	}
	public void setTbDesignPhbmergeDao(TbDesignPhbmergeDao tbDesignPhbmergeDao) {
		this.tbDesignPhbmergeDao = tbDesignPhbmergeDao;
	}
	public void setTdMergeColsOrderDao(TdMergeColsOrderDao tdMergeColsOrderDao) {
		this.tdMergeColsOrderDao = tdMergeColsOrderDao;
	}
	public PagingFactory getPageDao() {
		return pageDao;
	}
	public void setPageDao(PagingFactory pageDao) {
		this.pageDao = pageDao;
	}
	
	public Map<String,TbDesignPhb> getDesignPhbMap(String bdCode, String bhzCode)throws Exception{
		Map<String,TbDesignPhb> result = new HashMap<String,TbDesignPhb>();
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode))
			return result;
		List<TbDesignPhb> list = tbDesignPhbDao.queryByHql("from TbDesignPhb where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"'");
		for(TbDesignPhb t:list){
			if(!Util.isEmpty(t.getBdCode())){
				t.setBdCode(t.getBdCode().toUpperCase());
			}
			String key = t.getBdCode()+"-"+t.getBhzCode()+"-"+t.getIntensityGrade();
			result.put(key, t);
		}
		return result;
	}
	
	public Map<String,TbDesignPhbmerge> getDesignPhbMergeMap(String bdCode, String bhzCode)throws Exception{
		Map<String,TbDesignPhbmerge> result = new HashMap<String,TbDesignPhbmerge>();
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode))
			return result;
		List<TbDesignPhbmerge> list = tbDesignPhbmergeDao.queryByHql("from TbDesignPhbmerge where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"'");
		for(TbDesignPhbmerge t:list){
			if(!Util.isEmpty(t.getBdCode())){
				t.setBdCode(t.getBdCode().toUpperCase());
			}
			String key = t.getBdCode()+"-"+t.getBhzCode()+"-"+t.getIntensityGrade()+"-"+t.getMergeValue();
			result.put(key, t);
		}
		return result;
	}
	
	public List<TbBhz> queryBhzFlag(String path)throws Exception{
		List<TbBhz> result = new ArrayList<TbBhz>();
		/*String sql = "select a.bdCode,a.bhzCode,a.bhzName,max(b.m4) as maxdate from tbbhz a left join tbbase  b " +
				"on a.bhzCode=b.m6  group by a.bdCode,a.bhzCode,a.bhzName order by a.bdCode,a.bhzCode";
		String sql = "select distinct x.*, y.m3, (select max(signInDate) from tbSignIn where bdCode = x.bdCode and bhzCode = x.bhzCode) as signInDate from (select a.bdCode,a.bhzCode,a.bhzName,max(b.m4) as maxdate,a.lastCollTime " +
				"from tbbhz a left join tbbase b on a.bhzCode=b.m6 group by a.bdCode,a.bhzCode,a.bhzName,a.lastCollTime) x " +
				"left join tbbase y on x.maxdate=y.m4 and x.bhzCode=y.m6 order by x.bdCode, x.bhzCode";*/
		//数据大于50W,优化后sql
		String sql = "select " +
		        "distinct x.*,'' as m3,(select max(signInDate) " +
		        "from tbSignIn where bdCode = x.bdCode and bhzCode = x.bhzCode) as signInDate " +
		    "from " +
		        "(select a.bdCode,a.bhzCode, " +
		            "a.bhzName,(select max(convert(datetime,m4,120)) from tbbase where a.bhzCode=m6) as maxdate, " +
		            "a.lastCollTime from tbbhz a " +
		        "group by a.bdCode,a.bhzCode,a.bhzName,a.lastCollTime) x order by x.bdCode,x.bhzCode";
		List list = tbBhzDao.queryBySql(sql);
		TbBhz bhz = null;
		for(int i=0;i<list.size();i++){
			bhz = new TbBhz();
			Object[] obj = (Object[])list.get(i);
			bhz.setBdCode(obj[0].toString());
			bhz.setBhzCode(obj[1].toString());
			bhz.setBhzName(obj[2].toString());
			
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat sf = new SimpleDateFormat(pattern);
			String toDay = Util.getCurrentDate(pattern);
			if (obj[3]==null) {
				bhz.setExt1("2");	//当天无生产
			} else {
				String goDay = obj[3].toString();
				if(goDay.contains(toDay))
					bhz.setExt1("1");	//当天有生产
				else
					bhz.setExt1("2");	//当天无生产
			}
			
			if (obj[6]==null) {
				bhz.setIsSignIn("noSignIn.png");   //当天无签到
			} else {
				String goDay2 = sf.format(obj[6]);
				if(toDay.equals(goDay2))
					bhz.setIsSignIn("hasSignIn.png");	//当天有签到
				else
					bhz.setIsSignIn("noSignIn.png");	//当天无签到				
			}
			
			bhz.setLastTime(null==obj[3]?"":obj[3].toString());
			bhz.setGcmc(null==obj[4]?null:obj[5]==null?null:"");
			//Date fileMdfTime = FileUtil.getFileModifiedTime(path+"\\"+bhz.getBdCode()+"_"+bhz.getBhzCode()+ "_" + Util.getPatternDate(new Date(), "yyyy-MM-dd") + ".txt");
			if(obj[4]==null){
				bhz.setIsWeb("wifi-off.png");
			}else{
				String t=obj[4].toString();
				Date fileMdfTime=null;
				if(t.contains("/")){
					fileMdfTime=Util.getPatternDate(obj[4].toString(),"yyyy/MM/dd HH:mm:ss");
				}else if(!t.contains(":")){
					fileMdfTime=Util.getPatternDate(obj[4].toString(),"yyyy-MM-dd");
				} else{
					fileMdfTime=Util.getPatternDate(obj[4].toString(),"yyyy-MM-dd HH:mm:ss");
				}

				Date currTime = new Date();
				long c = currTime.getTime()-fileMdfTime.getTime();
				long r = c/(60*1000);
				if(ConstantUtil.webMinute>=r){
					bhz.setIsWeb("wifi-on.png");
				}else{
					bhz.setIsWeb("wifi-off.png");
				}
			}
			result.add(bhz);
		}
		return result;
	}
	
	public List<TbParamSet> queryParamSet(String pName,String pValue)throws Exception{
		StringBuffer hql = new StringBuffer("from TbParamSet where 1=1");
		if(!Util.isEmpty(pName)){
			hql.append(" and pname='"+pName+"'");
		}
		if(!Util.isEmpty(pValue)){
			hql.append(" and pvalue='"+pValue+"'");
		}
		List<TbParamSet> list = tbParamSetDao.queryByHql(hql.toString());
		return list;
	}
	
	public List<TbBase> queryTbBaseByBdAndBhz(String bdCode,String bhzCode)
		throws Exception{
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode))
			return new ArrayList<TbBase>();
		String hql = "from TbBase where m2='"+bdCode+"' and m6='"+bhzCode+"'";
		return tbBaseDao.queryByHql(hql);
	}
	
	public List<TbParamSet> setParam(String bd,String bhz)throws Exception{
		if(Util.isEmpty(bd) || Util.isEmpty(bhz))
			return null;
		String hql = "from TbParamSet where bdCode='"+bd+"' and bhzCode='"+bhz+"'";
		List<TbParamSet> list = tbParamSetDao.queryByHql(hql);
		if(list.size()==0){
			TbParamSet p = null;
			List<TbParamSet> newList = new ArrayList<TbParamSet>();
			for(int i=1;i<=20;i++){
				p = new TbParamSet();
				p.setBdCode(bd);
				p.setBhzCode(bhz);
				p.setPname("a"+i);
				p.setPvalue(Util.paramInitVal[i-1]);
				if(Util.isEmpty(p.getPvalue()))
					p.setFlag("0");
				else
					p.setFlag("1");
				newList.add(p);
				tbParamSetDao.insert(p);
			}
			return newList;
		}
		return list;
	}
	
	public void updateParam(HttpServletRequest request)throws Exception{
		for(int i=1;i<=20;i++){
			String pid = request.getParameter("pId"+i);
			String pval = request.getParameter("pVal"+i);
			String pflag = request.getParameter("pFlag"+i);
			if(!Util.isEmpty(pid)){
				TbParamSet t = tbParamSetDao.queryById(Integer.valueOf(pid));
				if(t!=null){
					t.setPvalue(pval);
					if(Util.isEmpty(pflag))
						t.setFlag("0");
					else
						t.setFlag("1");
					tbParamSetDao.update(t);
				}
			}
		}
	}
	
	public List<TbParamSet> getParamByItem(String bdCode,String bhzCode)
		throws Exception{
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode))
			return new ArrayList<TbParamSet>();
		String hql = "from TbParamSet where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"' and flag='1'";
		return tbParamSetDao.queryByHql(hql);
	}
	
	public Page getBaseByItem(Page page,String bdCode,String bhzCode,String m3,
			String m5,String m7,String m8,String sdate,String edate)
		throws Exception{
		Map<String,List<TbBase>> returnMap = new LinkedHashMap<String, List<TbBase>>();
		if(page==null)
			page = new Page();
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode))
			return page;
		StringBuffer hql = new StringBuffer("TbBase where m2='"+bdCode+"' and m6='"+bhzCode+"'");
		if(!Util.isEmpty(m3))
			hql.append(" and m3 like '%"+m3+"%'");
		if(!Util.isEmpty(m5))
			hql.append(Util.getNewString("m5",m5));
		if(!Util.isEmpty(m7))
			hql.append(" and m7='"+m7+"'");
		if(!Util.isEmpty(m8))
			hql.append(" and m8='"+m8+"'");
		if(!Util.isEmpty(sdate))
			hql.append(" and m4>='"+sdate+"'");
		if(!Util.isEmpty(edate))
			hql.append(" and m4<='"+edate+"'");
		page.setPageSize(48);
		page.setObjName(hql.toString());
		page.setOrderBy("m4 desc,groupId asc,m1 asc");
		page = pageDao.queryObjList(page);
		List baseList = page.getObjList();
		List<TbBase> list = null;
		for(int i=0;i<baseList.size();i++){
			TbBase b = (TbBase)baseList.get(i);
			if(!returnMap.containsKey(b.getGroupId())){
				list = new ArrayList<TbBase>();
				list.add(b);
				returnMap.put(b.getGroupId(), list);
			}else{
				returnMap.get(b.getGroupId()).add(b);
			}
		}
		page.setBaseMap(returnMap);
		return page;
	}
	
	public Page getBaseByItemForDistinctSql(Page page,String bdCode,String bhzCode,String m3,
			String m5,String m7,String m8,String bjType,String sdate,String edate)
		throws Exception{
		Map<String,List<TbBase>> returnMap = new LinkedHashMap<String, List<TbBase>>();
		if(page==null)
			page = new Page();
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode))
			return page;
		String fieldNames = "groupId,m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20,bhzCarNum,bhzCarOrder";
		StringBuffer sql = new StringBuffer("select "+fieldNames+" from TbBase where isShow is null and m2='"+bdCode+"' and m6='"+bhzCode+"'");
		if(!Util.isEmpty(m3))
			sql.append(" and m3 like '%"+m3+"%'");
		if(!Util.isEmpty(m5))
			sql.append(Util.getNewString("m5",m5));
		if(!Util.isEmpty(m7))
			sql.append(" and m7='"+m7+"'");
		if(!Util.isEmpty(m8))
			sql.append(" and m8 collate Chinese_PRC_CS_AS_WS='"+m8+"'");
		if(!Util.isEmpty(bjType))
			sql.append(" and bjType='"+bjType+"'");
		if(!Util.isEmpty(sdate))
			sql.append(" and CONVERT(datetime,m4)>='"+sdate+"'");
		if(!Util.isEmpty(edate))
			sql.append(" and CONVERT(datetime,m4)<='"+edate+"'");

		page.setPageSize(30);
		page.setObjName(sql.toString());
		page.setOrderBy("CONVERT(datetime,m4) desc,groupId asc,m1 asc");
		page = pageDao.queryObjListBySql(page);
		List baseList = page.getObjList();
		String[] arrFieldName = fieldNames.split(",");
		TbBase tbbase = null;
		List<TbBase> tbbaseList = new ArrayList<TbBase>();
		for(int i=0;i<baseList.size();i++){
			Object[] objs = (Object[])baseList.get(i);
			tbbase = new TbBase();
			for(int j=0;j<arrFieldName.length;j++){
				Util.setObjectValue(tbbase, arrFieldName[j], objs[j]==null?"":objs[j].toString());
				if(!Util.isEmpty(tbbase.getM2())){
					tbbase.setM2(tbbase.getM2().toUpperCase());
				}
			}
			tbbaseList.add(tbbase);
		}
		List<TbBase> list = null;
		for(TbBase b:tbbaseList){
			if(!returnMap.containsKey(b.getGroupId())){
				list = new ArrayList<TbBase>();
				list.add(b);
				returnMap.put(b.getGroupId(), list);
			}else{
				returnMap.get(b.getGroupId()).add(b);
			}
		}
		page.setPageSize(30/3);
		page.setBaseMap(returnMap);
		page.setTotalCount((int) Math.floor(page.getTotalCount()/3));
		return page;
	}
	
	public Page getBaseByMergeSql(Page page,String bdCode,String bhzCode,String m3,
			String m5,String m7,String m8,String bjType,String sdate,String edate,
			List<TdMergeColsOrder> mergeList)
		throws Exception{
		Map<String,List<Object[]>> returnMap = new LinkedHashMap<String, List<Object[]>>();
		if(page==null)
			page = new Page();
		String selClos = getMergeColsStr(mergeList);
		String fieldNames = "groupId,m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,bjType,bhzCarNum,bhzCarOrder,";
		if(!selClos.equals("")){
			fieldNames+=selClos;
		}
		StringBuffer sql = new StringBuffer("select "+fieldNames+" from TbBase where isShow is null and m2='"+bdCode+"' and m6='"+bhzCode+"'");
		if(!Util.isEmpty(m3))
			sql.append(" and m3 like '%"+m3+"%'");
		if(!Util.isEmpty(m5))
			sql.append(Util.getNewString("m5",m5));
		if(!Util.isEmpty(m7))
			sql.append(" and m7='"+m7+"'");
		if(!Util.isEmpty(m8))
			sql.append(" and m8 collate Chinese_PRC_CS_AS_WS='"+m8+"'");
		if(!Util.isEmpty(bjType))
			sql.append(" and bjType='"+bjType+"'");
		if(!Util.isEmpty(sdate))
			sql.append(" and CONVERT(datetime,m4)>='"+sdate+"'");
		if(!Util.isEmpty(edate))
			sql.append(" and CONVERT(datetime,m4)<='"+edate+"'");
		page.setPageSize(30);
		page.setObjName(sql.toString());
		page.setOrderBy("CONVERT(datetime,m4) desc,groupId asc,m1 asc");
		page = pageDao.queryObjListBySql(page);
		List baseList = page.getObjList();
		List<Object[]> list = null;
		for(int i=0;i<baseList.size();i++){
			Object[] objs = (Object[])baseList.get(i);
			if(objs[0]!=null){
				String key = objs[0].toString();
				if(!returnMap.containsKey(key)){
					list = new ArrayList<Object[]>();
					list.add(objs);
					returnMap.put(key, list);
				}else{
					returnMap.get(key).add(objs);
				}
			}
		}
		page.setPageSize(30/3);
		page.setBaseObj(returnMap);
		page.setTotalCount((int) Math.floor(page.getTotalCount() / 3));
		return page;
	}
	
	public String getMergeColsStr(List<TdMergeColsOrder> mergeList)throws Exception{
		StringBuffer sb = new StringBuffer("");
		int i = 1;
		for(TdMergeColsOrder t : mergeList){
			if(!Util.isEmpty(t.getMergeCols())){
				String[] arr  = t.getMergeCols().split("\\+");
				int j = 1;
				for(String s : arr){
					sb.append("convert(float,isnull("+s+",0))");
					if(j<arr.length){
						sb.append("+");
					}
					j++;
				}
			}
			if(!Util.isEmpty(sb.toString())){
				sb.append(" as "+t.getMergeColsName());
			}
			if(i<mergeList.size()){
				sb.append(",");
			}
			i++;
		}
		return sb.toString();
	}
	
	public List<TbBase> getBaseByGroupId(String groupId)throws Exception{
		String hql = "from TbBase where groupId='"+groupId+"'";
		return tbBaseDao.queryByHql(hql);
	}
	
	public List<double[]> getGiveDataPic(List<TbParamSet> paramList,List<TbBase> tbBaseList)throws Exception{
		TbBase shijiBase = new TbBase();
		TbBase shigongBase = new TbBase();
		List<double[]> resultList = new ArrayList<double[]>();
		for(TbBase t:tbBaseList){
			if(t.getM1().equals("1")){
				shijiBase = t;
			}else if(t.getM1().equals("2")){
				shigongBase = t;
			}
		}
		double totalShiji = 0;
		Integer totalShigong = 0;
		for(TbParamSet t:paramList){
			totalShiji += Util.getObjectValue(shijiBase, t.getPname());
			totalShigong += (int) Util.getObjectValue(shigongBase, t.getPname());
		}
		double[] data1 = new double[paramList.size()];
		double[] data2 = new double[paramList.size()];
		int i = 0;
		for(TbParamSet t:paramList){
			double shijiResult = Util.getObjectValue(shijiBase, t.getPname());
			double shigongResult = Util.getObjectValue(shigongBase, t.getPname());
			double d1 = shijiResult/totalShiji*100;
			double d2 = shigongResult/totalShigong*100;
			data1[i] = Util.toDecimal(d1, 2);
			data2[i] = Util.toDecimal(d2, 2);
			i++;
		}
		resultList.add(data1);
		resultList.add(data2);
		return resultList;
	}
	
	public boolean updateGiveData(HttpServletRequest request,String id,String groupId,String m7,
			String m5,String[] fieldName)throws Exception{
		StringBuffer fields = new StringBuffer();
		for(String key:fieldName){
			String val = request.getParameter(key);
			fields.append(",").append(key+"='"+val+"'");
		}
		String hql1 = "update TbBase set m7='"+m7+"',m5='"+m5+"'"+fields.toString()+" where id="+id;
		String hql2 = "update TbBase set m7='"+m7+"',m5='"+m5+"' where groupId='"+groupId+"'";
		boolean flag1 = tbBaseDao.updateTbBase(hql1);
		boolean flag2 = tbBaseDao.updateTbBase(hql2);
		if(flag1 && flag2)
			return true;
		return false;
	}
	
	public int updateTbBaseState(String type,String bdCode,String bhzCode,String m5,String m4)
		throws Exception{
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode))
			return 0;
		String isShowVal = "'1'";	//修改值
		if("delete".equals(type)){		//删除值
			isShowVal = "null";
		}
		String hql = "update TbBase set isShow="+isShowVal+" where m2='"+bdCode+"' and m6='"+bhzCode+"'";
		if(Util.isEmpty(m5))
			hql += " and m5 is null";
		else
			hql += " and m5 = '"+m5+"'";
		if(Util.isEmpty(m4))
			hql += " and m4 is null";
		else
			hql += " and m4 = '"+m4+"'";
		return tbBaseDao.updateByHql(hql);
	}
	
	public Page getFilterBaseData(Page page,String bdCode,String bhzCode,String m5,String m4)
		throws Exception{
		if(page==null)
			page = new Page();
		StringBuffer sql = new StringBuffer("select m1,m2,m3,m4,m5,m6,m7,m8,m9,m10 from TbBase a " +
				"where a.id in (select top 1 id from TbBase b where a.m2=b.m2 and a.m4=b.m4 and a.m5=b.m5 and a.m6=b.m6) " +
				"and a.isShow='1'");
		if(!Util.isEmpty(bdCode))
			sql.append(" and a.m2='"+bdCode+"'");
		if(!Util.isEmpty(bhzCode))
			sql.append(" and a.m6='"+bhzCode+"'");
		if(!Util.isEmpty(m5))
			sql.append(Util.getNewString("m5",m5));
		if(!Util.isEmpty(m4))
			sql.append(" and a.m4='"+m4+"'");
		page.setPageSize(50);
		page.setObjName(sql.toString());
		page.setOrderBy("a.m2 asc,a.m6 asc,a.m4 desc");
		page = pageDao.queryObjListBySql(page);
		return page;
	}
	
	//根据标段ID和拌合站拌合站ID统计数量
	public int getNumberByCode(String bdCode,String bhzCode)throws Exception{
		return tdMergeColsOrderDao.getCountByCode(bdCode, bhzCode);
	}
	
	//根据标段ID和拌合站拌合站ID
	public List<TdMergeColsOrder> queryMergeColsByCode(String bdCode,String bhzCode)
		throws Exception{
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode))
			return new ArrayList<TdMergeColsOrder>();
		String hql = "from TdMergeColsOrder where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"' order by orderNum";
		return tdMergeColsOrderDao.queryByHql(hql);
	}
	
	public Map<String, TdMergeColsOrder> getMapTdMergeColsOrder() throws Exception {
		String hql = "from TdMergeColsOrder";
		List<TdMergeColsOrder> resultList = tdMergeColsOrderDao.queryByHql(hql);
		Map<String, TdMergeColsOrder> mapMergeColsOrder = new HashMap<String, TdMergeColsOrder> ();
		for (TdMergeColsOrder m : resultList) {
			if (!Util.isEmpty(m.getBdCode()) && !Util.isEmpty(m.getBhzCode()) && !Util.isEmpty(m.getMergeCols())) {
				mapMergeColsOrder.put(m.getBdCode() + "-" + m.getBhzCode() + "-" + m.getMergeCols(), m);
			}
		}
		return mapMergeColsOrder;
	}
	
	//根据ID删除合并列数据
	public void deleteMergeCols(String id)throws Exception{
		if(Util.isEmpty(id))
			return;
		TdMergeColsOrder t = tdMergeColsOrderDao.queryById(Integer.valueOf(id));
		tbBaoJingSubMergeDao.deleteBySql("delete from tbBaoJingSubMerge where mainId in (select mainId from tbBaoJingMainMerge where bdCode='"+t.getBdCode()+"' and bhzCode='"+t.getBhzCode()+"')");
		tbBaoJingMainMergeDao.deleteBySql("delete from tbBaoJingMainMerge where bdCode='"+t.getBdCode()+"' and bhzCode='"+t.getBhzCode()+"' and mergeName='"+t.getMergeCols()+"'");
		//删除配合比
		tbDesignPhbmergeDao.deleteBySql("delete from TbDesignPhbmerge where bdCode='"+t.getBdCode()+"' and " +
				"bhzCode='"+t.getBhzCode()+"' and mergeValue='"+t.getMergeCols()+"'");
		//删除自身
		tdMergeColsOrderDao.delete(t);
		
		setMergeColsOrderCache();
	}
	
	//保存合并动态排序列
	public String saveMergeColsData(String bdCode,String bhzCode,String colsName,
			String colsValue,String sortIndex)throws Exception{
		String[] clos = colsName.split("\\+");
		String hql = "from TdMergeColsOrder where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"'";
		List<TdMergeColsOrder> mcoList = tdMergeColsOrderDao.queryByHql(hql);
		boolean flag = false;
		StringBuffer sb = new StringBuffer("");
		for(TdMergeColsOrder t:mcoList){
			sb.append(t.getMergeCols()).append("+");
		}
		if(!Util.isEmpty(sb.toString())){
			String[] arr = sb.toString().toString().split("\\+");
			for(String s1:arr){
				for(String s2:clos){
					if(s1.equals(s2)){
						flag = true;
						break;
					}
				}
			}
		}
		if(flag)
			return "1";
		TdMergeColsOrder t = new TdMergeColsOrder();
		t.setBdCode(bdCode);
		t.setBhzCode(bhzCode);
		t.setMergeCols(colsName);
		t.setMergeColsName(colsValue);
		if(!Util.isEmpty(sortIndex))
			t.setOrderNum(Integer.valueOf(sortIndex));
		tdMergeColsOrderDao.insert(t);
		
		setMergeColsOrderCache();
		return "2";
	}
	
	//判断tdMergeColsOrder表是否存在指定的合并列名
	public boolean checkClName(String clName) throws Exception {
		String hql = "from TdMergeColsOrder m where m.mergeColsName = '" + clName + "'";
		List<TdMergeColsOrder> queryByHql = this.tdMergeColsOrderDao.queryByHql(hql);
		if (queryByHql.size() == 0) {
			return false;
		} else {
			return true;
		}		
	}
	
	public void setMergeColsOrderCache() throws Exception {
		ConstantUtil.mergeColsMap = getMapTdMergeColsOrder();
	}
}
