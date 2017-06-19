package com.bhz.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.bhz.pojo.TbBd;
import com.bhz.pojo.TbBhz;
import com.bhz.pojo.TbDesignPhb;
import com.bhz.pojo.TbDesignPhbmerge;
import com.bhz.pojo.TbIntensityGrade;
import com.bhz.pojo.TbParamSet;
import com.bhz.pojo.TdMergeColsOrder;
import com.bhz.service.BDService;
import com.bhz.service.BHZService;
import com.bhz.service.DesignPHBService;
import com.bhz.service.IntensityGradeService;
import com.bhz.service.MainService;
import com.bhz.util.ConstantUtil;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.Page;
import com.opensymphony.xwork2.ActionSupport;

public class DesignPHBAction extends ActionSupport {

	private static final long serialVersionUID = -5613345431632960437L;
	private Page page;
	private DesignPHBService designPHBService;
	private BDService bdService;
	private BHZService bhzServicce;
	private MainService mainService;
	private IntensityGradeService intensityGradeService;
	

	public void setPage(Page page) {
		this.page = page;
	}
	
	public Page getPage() {
		return page;
	}

	public void setDesignPHBService(DesignPHBService designPHBService) {
		this.designPHBService = designPHBService;
	}

	public void setBdService(BDService bdService) {
		this.bdService = bdService;
	}
	
	public void setBhzServicce(BHZService bhzServicce) {
		this.bhzServicce = bhzServicce;
	}
	
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}
	
	public void setIntensityGradeService(IntensityGradeService intensityGradeService) {
		this.intensityGradeService = intensityGradeService;
	}
	
	//跳转到设计配合比页面
	public String getDesignPHB() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		TbBhz tbBhz = new TbBhz();
		try {
			Map<String, String> map1 = new HashMap<String, String>();
			Map<String, String> map2 = new HashMap<String, String>();
			List<TbBd> allBD = bdService.queryTbBd(request);
			List<TbBhz> allBhz = bhzServicce.getAllBhz(request);
			List<TbBhz> BHZs = new ArrayList<TbBhz>();
			if (Util.isEmpty(bdCode)) {
				if (allBD.size() > 0) {
					bdCode = allBD.get(0).getBdCode();  //默认让标段选第一个
				}
			}
			if (!Util.isEmpty(bdCode)) {
				for(TbBhz t:allBhz)
					if(t.getBdCode().equals(bdCode))
						BHZs.add(t);
				if (Util.isEmpty(bhzCode)) {
					if (BHZs.size() > 0) {
						bhzCode = BHZs.get(0).getBhzCode();  //默认让拌合站选第一个
					}
				}
			}
			for (TbBd b : allBD) {
				map1.put(b.getBdCode(), b.getBdName());
			}
			for (TbBhz b : BHZs) {
				map2.put(b.getBhzCode(), b.getBhzName());
			}
			tbBhz = mainService.getTbBhz(bhzCode);
			List<TbIntensityGrade> qddjList = intensityGradeService.getAllIntensityGrade();
			if(tbBhz!=null && tbBhz.getMergeClos()!=null && "1".equals(tbBhz.getMergeClos())){
				List<TdMergeColsOrder> mergeColsList = mainService.queryMergeColsByCode(bdCode, bhzCode);
				Map<String,Map<String,TbDesignPhbmerge>> designPhbMerges = designPHBService.setDesignPhbMerge(bdCode, bhzCode, qddjList, mergeColsList);
				String ids = "";
				if(designPhbMerges!=null && designPhbMerges.size()>0){
					for(String s1:designPhbMerges.keySet()){
						Map<String,TbDesignPhbmerge> m = designPhbMerges.get(s1);
						for(String s2:m.keySet()){
							if(m.get(s2)!=null && m.get(s2).getId()!=null){
								ids = ids + m.get(s2).getId() + "_";
							}
						}
					}
					if(!Util.isEmpty(ids))
						ids = ids.substring(0, ids.length() - 1);
				}
				request.setAttribute("ids", ids);
				request.setAttribute("mergeColsList", mergeColsList);
				request.setAttribute("designPhbMerges", designPhbMerges);
			}else{
				List<TbParamSet> paramList = mainService.getParamByItem(bdCode, bhzCode);
				List<TbDesignPhb> designPhbs = designPHBService.setDesignPhb(bdCode, bhzCode, qddjList);
				String ids = "";
				if (designPhbs!=null && designPhbs.size()>=0) {
					for (TbDesignPhb d : designPhbs) {
						ids = ids + d.getId() + "_";
					}
					if(!Util.isEmpty(ids))
						ids = ids.substring(0, ids.length() - 1);
				}
				request.setAttribute("ids", ids);
				request.setAttribute("paramList", paramList);
				request.setAttribute("designPhbs", designPhbs);
			}
			request.setAttribute("map1", map1);
			request.setAttribute("map2", map2);
			request.setAttribute("bdCode", bdCode);
			request.setAttribute("bhzCode", bhzCode);			
			request.setAttribute("BDs", allBD);
			request.setAttribute("BHZs", BHZs);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if(tbBhz!=null && tbBhz.getMergeClos()!=null && "1".equals(tbBhz.getMergeClos())){
			return "getDesignPHBMerge";
		}else{
			return "getDesignPHB";
		}
	}
	
	//批量更新设计配合比
	public String updateDesignPHB() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		String msg = "";
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			List<TbParamSet> paramList = mainService.getParamByItem(bdCode, bhzCode);
			designPHBService.updateDesignPHB(request, paramList);
			msg = "成功批量更新";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "批量更新失败";
		}
		request.setAttribute("msg", msg);
		return getDesignPHB();
	}
	
	public String updateDesignPHBMerge(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String msg = "";
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Sys;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			designPHBService.updateDesignPHBMerge(request);
			msg = "成功批量更新";
		} catch (Exception e) {
			msg = "批量更新失败";
			System.out.println(e.getMessage());
		}
		request.setAttribute("msg", msg);
		return getDesignPHB();
	}
}
