package com.bhz.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bhz.service.BHZService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import com.bhz.pojo.TbBaoJingSub;
import com.bhz.pojo.TbBase;
import com.bhz.pojo.TbBd;
import com.bhz.pojo.TbBhz;
import com.bhz.pojo.TbDesignPhb;
import com.bhz.pojo.TbDesignPhbmerge;
import com.bhz.pojo.TbParamSet;
import com.bhz.pojo.TdMergeColsOrder;
import com.bhz.service.CountService;
import com.bhz.service.MainService;
import com.bhz.service.MessageService;
import com.bhz.util.ConstantUtil;
import com.bhz.util.PropertiesRW;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.Page;
import com.bhz.util.PageUtils.PageList;
import com.bhz.util.jfreechat.PieLeaveChart;
import com.opensymphony.xwork2.ActionSupport;

public class MainAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private String extStr = ".000";
	private Page page;
	private MainService mainService;
	private CountService countService;
	private MessageService messageService;
	private BHZService bhzService;

	public MainService getMainService() {
		return mainService;
	}
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	public void setCountService(CountService countService) {
		this.countService = countService;
	}
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	public void setBhzService(BHZService bhzService) {
		this.bhzService = bhzService;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public String main(){
		HttpServletRequest request=ServletActionContext.getRequest();
		try {
			PropertiesRW rw = new PropertiesRW(ConstantUtil.rootPath + "WEB-INF\\classes\\config.properties");
			String projectTitle = rw.readValue("projectTitle");
			request.setAttribute("bhzTitle", projectTitle);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "main";
	}
	
	public String mainTop(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String projectId = request.getParameter("projectId");
		try {
			String hqmsSite = Util.readProperties("hqmsWeb");
			request.setAttribute("hqmsSite", hqmsSite);
			request.setAttribute("projectId", projectId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "top";
	}
	
	public String mainIndex(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String scFlag = request.getParameter("scFlag");
		String plType = request.getParameter("plTyle");
		String cuPage = request.getParameter("cuPage");
		Integer currentPage = 1;
		try {
			String path = ServletActionContext.getServletContext().getRealPath("/jfreeChartFile");
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_State;
			if(!Util.isUserAuthor(request, key, "1") && !Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			if(Util.isEmpty(plType))
				plType = "Rdiv";
			if(!Util.isEmpty(cuPage))
				currentPage = Integer.valueOf(cuPage);
			String username = Util.getUserName(request);
			Map<String,TbBd> bdMap = mainService.getMapTbBd();
			List<TbBhz> objList = mainService.queryBhzFlag(path);//图标排列
			//标段/拌合站权限控制
			List<TbBhz> bhzList = new ArrayList<TbBhz>();
			for(TbBhz t:objList){
				String bdKey = username+"-bd"+t.getBdCode();
				if(Util.isUserAuthor(request, bdKey, "1") || Util.isUserAuthor(request, bdKey, "2")){
					String bhzKey = username+"-bhz"+t.getBhzCode();
					if(Util.isUserAuthor(request, bhzKey, "1") || Util.isUserAuthor(request, bhzKey, "2")){
						bhzList.add(t);
					}
				}
			}
			/*********按生产状态筛选start***********/
			if(!Util.isEmpty(scFlag)){
				List<TbBhz> delList = new ArrayList<TbBhz>();
				for(TbBhz t:bhzList){
					if(scFlag.equals(t.getExt1())){
						delList.add(t);
					}
				}
				bhzList.removeAll(delList);
			}
			/*********按生产状态筛选end*************/
			Map<String,List<TbBhz>> bhzMap = getTbBhzMap(bhzList);//列表排列
			/**********分页*********/
			Set<String> set =  bhzMap.keySet();
			List<String> strList = new ArrayList<String>();
			if(set!=null)
				strList.addAll(set);
			PageList<String> page = new PageList<String>(strList);
			request.setAttribute("page", page);
			/**********************/
			request.setAttribute("scFlag", scFlag);
			request.setAttribute("plType", plType);
			request.setAttribute("username", username);
			request.setAttribute("bdMap", bdMap);
			request.setAttribute("bhzList", bhzList);
			request.setAttribute("bhzMap", bhzMap);
			request.setAttribute("randomNum", Math.random());
			request.setAttribute("bhzSet", page.getPage(currentPage));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return "index";
	}
	
	public Map<String,List<TbBhz>> getTbBhzMap(List<TbBhz> list)throws Exception{
		Map<String,List<TbBhz>> bhzMap = new TreeMap<String,List<TbBhz>>();
		List<TbBhz> bhzList = null;
		for(TbBhz b : list){
			if(bhzMap.containsKey(b.getBdCode())){
				bhzList = bhzMap.get(b.getBdCode());
				bhzList.add(b);
				bhzMap.put(b.getBdCode(), bhzList);
			}else{
				bhzList = new ArrayList<TbBhz>();
				bhzList.add(b);
				bhzMap.put(b.getBdCode(), bhzList);
			}
		}
		return bhzMap;
	}
	
	public String getGiveDate(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String bdCode = request.getParameter("bd");
		String bhzCode = request.getParameter("bhz");
		String m3 = request.getParameter("m3");
		String m5 = request.getParameter("m5");
		String m7 = request.getParameter("m7");
		String m8 = request.getParameter("m8");
		String sdate = request.getParameter("sdate");
		String edate = request.getParameter("edate");
		String subType = request.getParameter("subType");
		String isCurrent = request.getParameter("isCurrent");
		String bjType = request.getParameter("bjType");
		TbBhz tbBhz = new TbBhz();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Data;
			if(!Util.isUserAuthor(request, key, "1") && !Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			if(!Util.isEmpty(subType) && subType.equals("1")){
				//m3 = new String(m3.getBytes("ISO-8859-1"),Util.charSetName);
				//m5 = new String(m5.getBytes("ISO-8859-1"),Util.charSetName);
			}
			List<String> qdList = countService.getDistQiangDu();
			List<TbBd> bdList = mainService.queryTbBd(request);
			tbBhz = mainService.getTbBhz(bhzCode);
			if (tbBhz!=null && tbBhz.getMergeClos()!=null && "1".equals(tbBhz.getMergeClos())) {
				List<TdMergeColsOrder> mergeList = mainService.queryMergeColsByCode(bdCode, bhzCode);
				Map<String,TbDesignPhbmerge> phbMap = mainService.getDesignPhbMergeMap(bdCode, bhzCode);
				page = mainService.getBaseByMergeSql(page, bdCode, bhzCode, m3, m5, m7, m8, bjType, sdate, edate, mergeList);
				request.setAttribute("phbMap", phbMap);
				request.setAttribute("mergeList", mergeList);
			} else {
				Map<String,TbBaoJingSub> subMap = messageService.getBaoJingSub(bdCode, bhzCode);
				Map<String,TbDesignPhb> phbMap = mainService.getDesignPhbMap(bdCode, bhzCode);
				List<TbParamSet> paramList = mainService.getParamByItem(bdCode, bhzCode);
				if(!Util.isEmpty(bdCode) && !Util.isEmpty(bhzCode) && paramList.size()==0){
					request.setAttribute("nodata", "1");
				}
				page = mainService.getBaseByItemForDistinctSql(page, bdCode, bhzCode, m3, m5, m7, m8, bjType, sdate, edate);
				request.setAttribute("paramList", paramList);
				request.setAttribute("subMap", subMap);
				request.setAttribute("phbMap", phbMap);
			}
			Map<String,String> bjTypeData = messageService.getBjType(bdCode, bhzCode, "1");
			request.setAttribute("bjTypeData", bjTypeData);
			request.setAttribute("bdCode", bdCode);
			request.setAttribute("bhzCode", bhzCode);
			request.setAttribute("m3", m3);
			request.setAttribute("m5", m5);
			request.setAttribute("m7", m7);
			request.setAttribute("m8", m8);
			request.setAttribute("sdate", sdate);
			request.setAttribute("edate", edate);
			request.setAttribute("qdList", qdList);
			request.setAttribute("tbBhz", tbBhz);
			request.setAttribute("bdList", bdList);
			request.setAttribute("objPage", page);
			request.setAttribute("isCurrent", isCurrent);
			request.setAttribute("bjTypeMap", ConstantUtil.bjTypeMap);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if(tbBhz!=null && tbBhz.getMergeClos()!=null && "1".equals(tbBhz.getMergeClos()))
			return "givemergedate";
		else
			return "givedate";
	}
	
	//统计累计误差
	public String countTotalWc()throws Exception{
		HttpServletRequest request=ServletActionContext.getRequest();
		String bdCode = request.getParameter("bd");
		String bhzCode = request.getParameter("bhz");
		String m5 = request.getParameter("m5");
		String m8 = request.getParameter("m8");
		String sdate = request.getParameter("sdate");
		String edate = request.getParameter("edate");
		try {
			List<String> qdList = countService.getDistQiangDu();
			Map<String,String> bdMap = new HashMap<String,String>();
			List<TbBd> bdList = mainService.queryTbBd(request);
			for(TbBd t:bdList){
				bdMap.put(t.getBdCode(), t.getBdName());
			}
			TbBhz tbBhz = mainService.getTbBhz(bhzCode);
			List<TdMergeColsOrder> mergeList = mainService.queryMergeColsByCode(bdCode, bhzCode);
			Page objList = countService.getCountWcData(page, request, bdCode, bhzCode, m5, m8, sdate, edate, mergeList);
			request.setAttribute("objList", objList);
			request.setAttribute("mergeList", mergeList);
			request.setAttribute("tbBhz", tbBhz);
			request.setAttribute("qdList", qdList);
			request.setAttribute("bdList", bdList);
			request.setAttribute("bdMap", bdMap);
			request.setAttribute("bdCode", bdCode);
			request.setAttribute("bhzCode", bhzCode);
			request.setAttribute("m5", m5);
			request.setAttribute("m8", m8);
			request.setAttribute("sdate", sdate);
			request.setAttribute("edate", edate);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "countwc";
	}

	//车载积累误差
	public String countCzTotalWc(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String bdCode = request.getParameter("bd");
		String bhzCode = request.getParameter("bhz");
		String m5 = request.getParameter("m5");
		String m8 = request.getParameter("m8");
		String sdate = request.getParameter("sdate");
		String edate = request.getParameter("edate");
		String bhzCarOrder=request.getParameter("bhzCarOrder");
		try {
			List<String> qdList = countService.getDistQiangDu();
			Map<String,String> bdMap = new HashMap<String,String>();
			List<TbBd> bdList = mainService.queryTbBd(request);
			for(TbBd t:bdList){
				bdMap.put(t.getBdCode(), t.getBdName());
			}
			TbBhz tbBhz = mainService.getTbBhz(bhzCode);
			List<TdMergeColsOrder> mergeList = mainService.queryMergeColsByCode(bdCode, bhzCode);
			Map<String,TbDesignPhb> phbMap = mainService.getDesignPhbMap(bdCode, bhzCode);
			Page objList = countService.getCountCzWcData(page,request, bdCode, bhzCode, m5, m8, sdate, edate,bhzCarOrder, mergeList);
			Map<String,String> bjTypeData = messageService.getBjType(bdCode, bhzCode, "2");
			request.setAttribute("bjTypeData", bjTypeData);
			request.setAttribute("objList", objList);
			request.setAttribute("mergeList", mergeList);
			request.setAttribute("tbBhz", tbBhz);
			request.setAttribute("qdList", qdList);
			request.setAttribute("bdList", bdList);
			request.setAttribute("bdMap", bdMap);
			request.setAttribute("phbMap", phbMap);
			request.setAttribute("bdCode", bdCode);
			request.setAttribute("bhzCode", bhzCode);
			request.setAttribute("m5", m5);
			request.setAttribute("m8", m8);
			request.setAttribute("sdate", sdate);
			request.setAttribute("edate", edate);
			request.setAttribute("bjTypeMap", ConstantUtil.bjTypeMap);
			request.setAttribute("bhzCarOrder", bhzCarOrder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "countCzwc";
	}

	public String getGiveDetail(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String bdCode = request.getParameter("bd");
		String bhzCode = request.getParameter("bhz");
		String groupId = request.getParameter("groupId");
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Data;
			if(!Util.isUserAuthor(request, key, "1") && !Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			List<TbParamSet> paramList = mainService.getParamByItem(bdCode, bhzCode);
			List<TbBase> tbBaseList = mainService.getBaseByGroupId(groupId);
			TbBase tbBase = new TbBase();
			for(TbBase t:tbBaseList){
				if("0".equals(t.getM1())){
					tbBase = t;
				}
			}
			if(paramList.size()>0){ //生产图片
				List<double[]> dataList = mainService.getGiveDataPic(paramList, tbBaseList);
				createPicImage(dataList.get(0), "shijiPic.jpg", "实际配合比饼状图", paramList);
				createPicImage(dataList.get(1), "shigongPic.jpg", "施工配合比饼状图", paramList);
			}
			request.setAttribute("tbBase", tbBase);
			request.setAttribute("paramList", paramList);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return "givedetail";
	}
	
	public String updateGiveData(){
		PrintWriter out = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String id = request.getParameter("lilunId");
		String groupId = request.getParameter("groupId");
		String m7 = request.getParameter("m7");
		String m5 = request.getParameter("m5");
		String[] fieldName = request.getParameterValues("fieldName");
		try {
			out = response.getWriter();
			boolean flag = mainService.updateGiveData(request, id, groupId, m7, m5, fieldName);
			if(flag)
				out.print("success");
			else
				out.print("error");
			return null;
		} catch (Exception e) {
			out.print("error");
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public void createPicImage(double[] data,String chartName,String chartTitle,List<TbParamSet> paramList){
		String[] keys = new String[paramList.size()];
		try {
			int i = 0;
			for(TbParamSet t:paramList){
				keys[i] = t.getPvalue();
				i++;
			}
			String path = ServletActionContext.getServletContext().getRealPath("/jfreeChartFile");
			//PieChart.makePieChart(data, keys, chartName, chartTitle, path+"\\");		//三维成图
			PieLeaveChart.makePieChart(data, keys, chartName, chartTitle, path+"\\", 650, 420);	//二维成图
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage()+"生成饼状图错误...");
		}
	}
	
	public String setParam(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String bd = request.getParameter("bd");
		String bhz = request.getParameter("bhz");
		String bhzName = request.getParameter("bhzName");
		try {
			Map<String,String> bdMap = new HashMap<String,String>();
			List<TbBd> tbBds = mainService.queryTbBd(request);
			List<TbParamSet> tbParams = mainService.setParam(bd, bhz);
			for(TbBd b:tbBds)
				bdMap.put(b.getBdCode(), b.getBdName());
			request.setAttribute("bd", bd);
			request.setAttribute("bhz", bhz);
			request.setAttribute("bhzName", bhzName);
			request.setAttribute("tbBds", tbBds);
			request.setAttribute("tbParams", tbParams);
			request.setAttribute("bdMap", bdMap);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "setparam";
	}
	
	public String updateParam(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String bhzName=request.getParameter("bhzName");
		String msg = "";
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			mainService.updateParam(request);
			msg = "成功批量更新";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			msg = "批量更新失败";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("bhzName",bhzName);
		return setParam();
	}
	
	public String getFilterData(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		String m5 = request.getParameter("m5");
		String m4 = request.getParameter("m4");
		String subType = request.getParameter("subType");
		try {
			if(!Util.isEmpty(subType) && "1".equals(subType)){
				//m5 = new String(m5.getBytes("ISO-8859-1"),Util.charSetName);
			}
			Map<String,String> bdMap = new HashMap<String,String>();
			List<TbBd> bdList = mainService.queryTbBd(request);
			for(TbBd t:bdList){
				bdMap.put(t.getBdCode(), t.getBdName());
			}
			Map<String,String> bhzMap = new HashMap<String,String>();
			List<TbBhz> bhzList = mainService.getAllBHZ();
			for(TbBhz t:bhzList){
				bhzMap.put(t.getBhzCode(), t.getBhzName());
			}
			if (!Util.isEmpty(bdCode)) {
				List<TbBhz> BHZs = new ArrayList<TbBhz>();
				for(TbBhz t:bhzList){
					if(bdCode.equals(t.getBdCode())){
						BHZs.add(t);
					}
				}
				request.setAttribute("BHZs", BHZs);
			}
			if(!Util.isEmpty(m4))
				m4 = m4+extStr;
			Page objPage = mainService.getFilterBaseData(page, bdCode, bhzCode, m5, m4);
			request.setAttribute("objPage", objPage);
			request.setAttribute("bdList", bdList);
			request.setAttribute("bdMap", bdMap);
			request.setAttribute("bhzMap", bhzMap);
			request.setAttribute("bdCode", bdCode);
			request.setAttribute("bhzCode", bhzCode);
			request.setAttribute("m5", m5);
			request.setAttribute("m4", m4);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "fiterdata";
	}
	
	public String addFilterData(){
		HttpServletRequest request=ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			List<TbBd> bdList = mainService.queryTbBd(request);
			request.setAttribute("bdList", bdList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "addFiterdata";
	}
	
	public String getBhzInfo(){
		PrintWriter out = null;
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String bdCode = request.getParameter("bdCode");
		try {
			String content = "";
			out=response.getWriter();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			List<TbBhz> bhzList = mainService.queryTbBhzByBd(request,bdCode);
			for(TbBhz t:bhzList){
				content+=t.getBhzCode()+"|"+t.getBhzName()+",";
			}
			out.print(content);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}
	
	public String getBaseInfo(){
		PrintWriter out = null;
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		try {
			out=response.getWriter();
			Map<String,Set<String>> result = new HashMap<String, Set<String>>();
			Set<String> m5Set = new HashSet<String>();
			Set<String> m4Set = new HashSet<String>();
			List<TbBase> baseList = mainService.queryTbBaseByBdAndBhz(bdCode, bhzCode);
			for(TbBase t : baseList){
				m5Set.add(t.getM5());
				if(t.getM4()==null){
					m4Set.add(null);
				}else{
					//m4Set.add(Util.getPatternDate(t.getM4(), "yyyy-MM-dd HH:mm:ss"));
					m4Set.add(t.getM4());
				}
			}
			result.put("m5", m5Set);
			result.put("m4", m4Set);
			JSONObject jsonObject = JSONObject.fromObject(result);
			out.write(jsonObject.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}
	
	public String saveFilterData(){
		PrintWriter out = null;
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		String m5 = request.getParameter("m5");
		String m4 = request.getParameter("m4");
		try {
			out=response.getWriter();
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return null;
			}
			if(!Util.isEmpty(m4))
				m4 = m4+extStr;
			int result = mainService.updateTbBaseState("update", bdCode, bhzCode, m5, m4);
			out.print(result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}
	
	public String deleteFilterData(){
		PrintWriter out = null;
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		String m5 = request.getParameter("m5");
		String m4 = request.getParameter("m4");
		try {
			out=response.getWriter();
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return null;
			}
			if(!Util.isEmpty(m4)){
				String pattern = "yyyy-MM-dd HH:mm:ss";
				Date d = Util.getPatternDate(m4, pattern);
				m4 = Util.getPatternDate(d, pattern)+extStr;
			}
			int result = mainService.updateTbBaseState("delete", bdCode, bhzCode, m5, m4);
			out.print(result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}
	
	public String setMerge(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String isDel = request.getParameter("isDel");
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		try {
			//是否删除
			if(!Util.isEmpty(isDel) && "1".equals(isDel)){
				String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
				if(!Util.isUserAuthor(request, key, "2")){
					return "noauth";
				}
				String id = request.getParameter("id");
				mainService.deleteMergeCols(id);
			}
			List<TbBd> tbBds = mainService.queryTbBd(request);
			if(!Util.isEmpty(bdCode) && !Util.isEmpty(bhzCode)){
				Map<String,TbBd> bdMap = new HashMap<String,TbBd>();
				for(TbBd t:tbBds){
					bdMap.put(t.getBdCode(), t);
				}
				Map<String,TbBhz> bhzMap = mainService.getMapTbBhz();
				List<TdMergeColsOrder> mcoList = mainService.queryMergeColsByCode(bdCode, bhzCode);
				request.setAttribute("bdMap", bdMap);
				request.setAttribute("bhzMap", bhzMap);
				request.setAttribute("mcoList", mcoList);
				request.setAttribute("bdCode", bdCode);
				request.setAttribute("bhzCode", bhzCode);
			}
			request.setAttribute("tbBds", tbBds);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "mergePage";
	}
	
	public String addMerge(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String bdCode = request.getParameter("bd");
		String bhzCode = request.getParameter("bhz");
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			Map<String,TbBd> bdMap = mainService.getMapTbBd();
			Map<String,TbBhz> bhzMap = mainService.getMapTbBhz();
			List<TbParamSet> paramList = mainService.getParamByItem(bdCode, bhzCode);
			if(paramList==null || paramList.size()==0){
				request.setAttribute("paramIsNull", "1");
			}
			request.setAttribute("bdMap", bdMap);
			request.setAttribute("bhzMap", bhzMap);
			request.setAttribute("bdCode", bdCode);
			request.setAttribute("bhzCode", bhzCode);
			request.setAttribute("paramList", paramList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "mergeAdd";
	}
	
	public String saveMerge(){
		PrintWriter out = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		String colsName = request.getParameter("colsName");
		String colsValue = request.getParameter("colsValue");
		String sortIndex = request.getParameter("sortIndex");
		try {
			out = response.getWriter();
			if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode) || Util.isEmpty(colsName)){
				out.print("0");
			}else{
				String result = mainService.saveMergeColsData(bdCode, bhzCode, colsName, colsValue, sortIndex);
				out.print(result);
			}
			return null;
		} catch (Exception e) {
			out.print("0");
			System.out.println(e.getMessage());
		} finally { 
			out.flush();
			out.close();
		}
		return null;
	}

}