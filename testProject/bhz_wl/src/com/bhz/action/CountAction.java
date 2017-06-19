package com.bhz.action;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

import com.bhz.pojo.TbBase;
import com.bhz.pojo.TbBd;
import com.bhz.pojo.TbBhz;
import com.bhz.pojo.TbIntensityGrade;
import com.bhz.pojo.TbParamSet;
import com.bhz.pojo.TdMergeColsOrder;
import com.bhz.service.CountService;
import com.bhz.service.MainService;
import com.bhz.util.ConstantUtil;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.Page;
import com.bhz.util.jfreechat.BarGroupChart;
import com.bhz.util.jfreechat.LineChart;
import com.bhz.util.jfreechat.PieLeaveChart;
import com.opensymphony.xwork2.ActionSupport;

public class CountAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private Page page;
	private MainService mainService;
	private CountService countService;

	public void setPage(Page page) {
		this.page = page;
	}
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}
	public void setCountService(CountService countService) {
		this.countService = countService;
	}
	
	public String getBhTime(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String bdCode = request.getParameter("bd");
		String bhzCode = request.getParameter("bhz");
		String sdate = request.getParameter("sdate");
		String edate = request.getParameter("edate");
		String isCurrent = request.getParameter("isCurrent");
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Data;
			if(!Util.isUserAuthor(request, key, "1") && !Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			String bdName = "",bhzName = "";
			double randomNum = Math.random();
			TbBd tbBd = mainService.getTbBd(bdCode);
			TbBhz tbBhz = mainService.getTbBhz(bhzCode);
			List<TbBd> bdList = mainService.queryTbBd(request);
			Map<String,String> xianMap = countService.getTbBhzMap(bhzCode);
			Page datas = countService.getTbBaseList(page, bhzCode, isCurrent, sdate, edate);
			String path = ServletActionContext.getServletContext().getRealPath("/jfreeChartFile");
			if(tbBd!=null)
				bdName = tbBd.getBdName();
			if(tbBhz!=null)
				bhzName = tbBhz.getBhzName();
			LineChart.makeLineChart(datas.getObjList(), xianMap, bdName+bhzName+"拌合时间实时监控", "", "单位 / 秒", "bhTime.jpg", path+"\\");
			request.setAttribute("sdate", sdate);
			request.setAttribute("edate", edate);
			request.setAttribute("bdCode", bdCode);
			request.setAttribute("bhzCode", bhzCode);
			request.setAttribute("bdList", bdList);
			request.setAttribute("objPage", datas);
			request.setAttribute("randomNum", randomNum);
			request.setAttribute("isCurrent", isCurrent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "showbhtime";
	}
	
	public String countClyx(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String bdCode = request.getParameter("bd");
		String bhzCode = request.getParameter("bhz");
		String sdate = request.getParameter("sdate");
		String edate = request.getParameter("edate");
		try {
			double randomNum = Math.random();
			List<TbBd> bdList = mainService.queryTbBd(request);
			Page objPage = countService.getCountClylData(page, bdCode, bhzCode,sdate, edate);
			List objList = objPage.getObjList();
			if(objList!=null && objList.size()>0){
				TbBd tbBd = mainService.getTbBd(bdCode);
				TbBhz tbBhz = mainService.getTbBhz(bhzCode);
				String bdName = "",bhzName = "";
				if(tbBd!=null)
					bdName = tbBd.getBdName();
				if(tbBhz!=null)
					bhzName = tbBhz.getBhzName();
				String[] rowKeys = (String[])objList.get(0);
				String[] columnKeys = (String[])objList.get(1);
				double[][] data = (double[][])objList.get(2);
				String chartName = bdName+bhzName+"材料用量实时监控";
				String path = ServletActionContext.getServletContext().getRealPath("/jfreeChartFile");
				BarGroupChart.makeBarGroupChart(chartName, path+"\\", rowKeys, columnKeys, data);
			}
			request.setAttribute("bdCode", bdCode);
			request.setAttribute("bhzCode", bhzCode);
			request.setAttribute("bdList", bdList);
			request.setAttribute("objPage", objPage);
			request.setAttribute("objList", objList);
			request.setAttribute("sdate", sdate);
			request.setAttribute("edate", edate);
			request.setAttribute("randomNum", randomNum);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return "countclyl";
	}
	
	public String countCnfx(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String bdCode = request.getParameter("bd");
		String bhzCode = request.getParameter("bhz");
		String sdate = request.getParameter("sdate");
		String edate = request.getParameter("edate");
		try {
			double randomNum = Math.random();
			String path = ServletActionContext.getServletContext().getRealPath("/jfreeChartFile");
			List<TbBd> bdList = mainService.queryTbBd(request);
			List<TbParamSet> paramList = mainService.getParamByItem(bdCode, bhzCode);	//动态列
			List list = countService.getCountCnfxData(bdCode, bhzCode, sdate, edate);
			BarGroupChart.makeBarChart(list,"拌合站产能分析","","",path+"\\", "cnfx_bar.jpg");
			
			List<Object> list_sj = countService.getCountCnfxData_SJ(paramList, list, bdCode, bhzCode, sdate, edate);
			PieLeaveChart.makePieChart(list_sj, "实际材料用量", path+"\\", "cnfx_sj_pie.jpg", 500, 500);
			
			List<Object> list_ll = countService.getCountCnfxData_LL(paramList, list, bdCode, bhzCode, sdate, edate);
			PieLeaveChart.makePieChart(list_ll, "理论材料用量", path+"\\", "cnfx_sg_pie.jpg", 500, 500);
			request.setAttribute("bdCode", bdCode);
			request.setAttribute("bhzCode", bhzCode);
			request.setAttribute("bdList", bdList);
			request.setAttribute("randomNum", randomNum);
			request.setAttribute("sdate", sdate);
			request.setAttribute("edate", edate);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "countcnfx";
	}
	
	public String getAllBdInfo(){
		HttpServletRequest request=ServletActionContext.getRequest();
		try {
			List<TbBd> bdList = mainService.queryTbBd(request);
			request.setAttribute("tbbds", bdList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "showAllBd";
	}
	
	public String getQiangDuInfo(){
		PrintWriter out = null;
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		try {
			String content = "";
			out=response.getWriter();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			List<TbIntensityGrade> gradeList = countService.getQdGradeBybdAndbhz(bdCode, bhzCode);
			for(TbIntensityGrade g:gradeList)
				content+=g.getIntensityGrade()+",";
			out.print(content);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}
	
	public String countTotalGzbwSn(){
		PrintWriter out = null;
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String bdCode = request.getParameter("bd");
		String bhzCode = request.getParameter("bhz");
		String m8 = request.getParameter("m8");
		String m5 = request.getParameter("m5");
		String sdate = request.getParameter("sdate");
		String edate = request.getParameter("edate");
		String morebdCode = request.getParameter("morebdCode");
		String isGet = request.getParameter("isGet");
		try {
			String content = "";
			out=response.getWriter();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			List list = countService.getTotalConcrete(request, morebdCode, bdCode, bhzCode, m8, m5, sdate, edate, isGet);
			DecimalFormat df = new DecimalFormat("0.00");
			if(list!=null && list.size()>0){
				Object[] obj = (Object[])list.get(0);
				if(obj[0]!=null){
					content += df.format(obj[0]);
				}
				content += "|";
				if(obj[1]!=null){
					content += df.format(obj[1]);
				}
			}else{
				content = "0|0";
			}
			out.print(content);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}
	
	public String countGzbwSn(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String bdCode = request.getParameter("bd");
		String bhzCode = request.getParameter("bhz");
		String m8 = request.getParameter("m8");
		String m5 = request.getParameter("m5");
		String sdate = request.getParameter("sdate");
		String edate = request.getParameter("edate");
		String isMoreBdBox = request.getParameter("isMoreBdBox");
		String morebdCode = request.getParameter("morebdCode");
		String morebd = request.getParameter("morebd");
		String isGet = request.getParameter("isGet");
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Count;
			if(!Util.isUserAuthor(request, key, "1") && !Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			//if("1".equals(isGet))
				//m5 = new String(m5.getBytes("ISO-8859-1"),Util.charSetName);
			Map<String,String> bdMap = new HashMap<String, String>();
			List<TbBd> bdList = mainService.queryTbBd(request);
			for(TbBd t:bdList){
				if(!Util.isEmpty(t.getBdCode())){
					t.setBdCode(t.getBdCode().toUpperCase());
				}
				bdMap.put(t.getBdCode(), t.getBdName());
			}
			Map<String,TbBhz> bhzMap = mainService.getMapTbBhz();
			List<String> qdList = countService.getDistQiangDu();
			Map<String,String> columnMap = countService.getConnectStr(morebdCode, bdCode, bhzCode, m8, m5, sdate, edate);
			Page objPage = countService.getTotalConcrete(request, page, morebdCode, bdCode, bhzCode, m8, m5, sdate, edate, isGet);
			request.setAttribute("bdCode", bdCode);
			request.setAttribute("bhzCode", bhzCode);
			request.setAttribute("m8", m8);
			request.setAttribute("m5", m5);
			request.setAttribute("sdate", sdate);
			request.setAttribute("edate", edate);
			request.setAttribute("bdList", bdList);
			request.setAttribute("bdMap", bdMap);
			request.setAttribute("bhzMap", bhzMap);
			request.setAttribute("qdList", qdList);
			request.setAttribute("objPage", objPage);
			request.setAttribute("columnMap", columnMap);
			request.setAttribute("isMoreBdBox", isMoreBdBox);
			request.setAttribute("morebdCode", morebdCode);
			request.setAttribute("morebd", morebd);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "countgzbwsn";
	}
	
	public String countSnCn(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String bdCode = request.getParameter("bd");
		String bhzCode = request.getParameter("bhz");
		String m8 = request.getParameter("m8");
		String m5 = request.getParameter("m5");
		String sdate = request.getParameter("sdate");
		String edate = request.getParameter("edate");
		String isMoreBdBox = request.getParameter("isMoreBdBox");
		String morebdCode = request.getParameter("morebdCode");
		String morebd = request.getParameter("morebd");
		String isGet = request.getParameter("isGet");
		String[] clNames = request.getParameterValues("clNames");
		String countType = request.getParameter("countType");
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Count;
			if(!Util.isUserAuthor(request, key, "1") && !Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			String[] countParamVal = null;
			//if("1".equals(isGet))
			//	m5 = new String(m5.getBytes("ISO-8859-1"),Util.charSetName);
			if(clNames==null || clNames.length<=0)
				countParamVal = Util.countParamVal;
			else
				countParamVal = clNames;
			Map<String,String> bdMap = new HashMap<String, String>();
			List<TbBd> bdList = mainService.queryTbBd(request);
			for(TbBd t:bdList){
				if(!Util.isEmpty(t.getBdCode())){
					t.setBdCode(t.getBdCode().toUpperCase());
				}
				bdMap.put(t.getBdCode(), t.getBdName());
			}
			Map<String,TbBhz> bhzMap = mainService.getMapTbBhz();
			List<String> qdList = countService.getDistQiangDu();
			Map<String,String> columnMap = countService.getConnectStr(morebdCode, "bd", "bhz", null, null, sdate, edate);
			if("1".equals(countType)){	//按合并列查询
				List<TdMergeColsOrder> mergeList = mainService.queryMergeColsByCode(bdCode, bhzCode);
				List objList = countService.getDetailConcreteMerge(request, morebdCode, bdCode, bhzCode, m8, m5, sdate, edate, isGet, mergeList);
				request.setAttribute("mergeList", mergeList);
				request.setAttribute("objList", objList);
			}else{	//按仓位查询
				Map<String,List<TbParamSet>> paramMap = countService.getParamSetMap();
				List<TbBase> objPage = countService.getDetailConcrete(request, morebdCode, bdCode, bhzCode, m8, m5, sdate, edate, isGet);
				request.setAttribute("paramMap", paramMap);
				request.setAttribute("objPage", objPage);
				request.setAttribute("boxParamVal", Util.countParamVal);
				String clNamesStr = "";
				if(clNames!=null && clNames.length>0)
					for(String s:clNames)
						clNamesStr+=s+",";
				request.setAttribute("clNamesStr", clNamesStr);
			}
			request.setAttribute("bdCode", bdCode);
			request.setAttribute("bhzCode", bhzCode);
			request.setAttribute("m8", m8);
			request.setAttribute("m5", m5);
			request.setAttribute("sdate", sdate);
			request.setAttribute("edate", edate);
			request.setAttribute("countParamVal", countParamVal);
			request.setAttribute("bdList", bdList);
			request.setAttribute("bdMap", bdMap);
			request.setAttribute("bhzMap", bhzMap);
			request.setAttribute("qdList", qdList);
			request.setAttribute("columnMap", columnMap);
			request.setAttribute("isMoreBdBox", isMoreBdBox);
			request.setAttribute("morebdCode", morebdCode);
			request.setAttribute("morebd", morebd);
			request.setAttribute("countType", countType);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "countsncn";
	}
	
	public boolean isNumber(String str) throws Exception{
		if(Util.isEmpty(str))
			return false;
		Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?$");  
        Matcher match=pattern.matcher(str);
        return match.matches();
	}
}
